package net.artux.mathc.ui;

import net.artux.mathc.Application;
import net.artux.mathc.Config;
import net.artux.mathc.Operation;
import net.artux.mathc.data.SolutionException;
import net.artux.mathc.model.Expression;
import net.artux.mathc.model.ExpressionPart;
import net.artux.mathc.validation.ValidationException;
import net.artux.mathc.validation.Validator;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.*;

public class InputForm extends JFrame {

    private JPanel root;
    private JTextField inputField;
    private JPanel valuesPanel;
    private JPanel operationsPanel;
    private JButton btnAccept;
    private JButton btnClear;
    private JButton btnDeleteLast;

    private final Application application;
    private static final String[] symbols = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "(", ")"};
    private static final Collection<Operation> operations = Config.getSupportedOperations();

    private Expression exp;
    private ExpressionPart lastPart;

    ActionListener symbolActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton but = (JButton) e.getSource();
            ExpressionPart expressionPart = new ExpressionPart(but.getText(), false);
            exp.getContent().add(expressionPart);
            lastPart = expressionPart;
            updateUI();
        }
    };

    ActionListener funcActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton but = (JButton) e.getSource();
            Operation operation = Config.supportedOperations.get(but.getText());
            ExpressionPart expressionPart = new ExpressionPart(operation.getName(), true);
            exp.getContent().add(expressionPart);
            if (expressionPart.isFunction()){
                expressionPart = new ExpressionPart("(", false);
                exp.getContent().add(expressionPart);
            }
            lastPart = expressionPart;
            updateUI();
        }
    };


    public InputForm(Application application) {
        this.application = application;

        setContentPane(root);
        setSize(500, 350);
        setLocationByPlatform(true);
        setTitle("Мастер функций");

        exp = new Expression(new ArrayList<>());

        GridLayout grid = new GridLayout(0, 3);
        valuesPanel.setLayout(grid);
        GridLayout grid2 = new GridLayout(0, 3);
        operationsPanel.setLayout(grid2);

        for (String value : symbols) {
            JButton but = new JButton(value);
            valuesPanel.add(but);
            but.addActionListener(symbolActionListener);
        }

        for (Operation operation : operations) {
            JButton but = new JButton(operation.getName());
            operationsPanel.add(but);
            but.addActionListener(funcActionListener);
        }

        btnAccept.addActionListener(e -> {
            try {
                if (Validator.isValid(exp)){
                    application.getDataModel().setExpression(exp);
                    setVisible(false);
                }
            } catch (ValidationException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(),"Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        });
        btnClear.addActionListener(e -> {
            exp = new Expression(new ArrayList<>());
            updateUI();
        });
        btnDeleteLast.addActionListener(e -> {
            if (exp.getContent().size()>0) {
                exp.getContent().remove(lastPart);
                if (exp.getContent().size() != 0)
                    lastPart = exp.getContent().get(exp.getContent().size() - 1);
                else lastPart = null;
                updateUI();
            }
        });

        valuesPanel.revalidate();
        valuesPanel.repaint();
    }

    void updateUI(){
        inputField.setText(exp.toString());
    }

    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
        try {
            exp = application.getDataModel().getExpression();
        } catch (SolutionException ignored) {}
    }
}
