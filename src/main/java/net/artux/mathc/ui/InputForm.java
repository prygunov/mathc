package net.artux.mathc.ui;

import net.artux.mathc.Operation;
import net.artux.mathc.data.DataInputModelImpl;
import net.artux.mathc.validation.ValidationException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InputForm extends JFrame {

    private JPanel root;
    private JTextField inputField;
    private JPanel valuesPanel;
    private JPanel operationsPanel;
    private JButton btnAccept;
    private JButton btnClear;
    private JButton btnDeleteLast;

    private final DataInputModelImpl dataInputModel;

    ActionListener symbolActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton but = (JButton) e.getSource();
            dataInputModel.add((Character) but.getClientProperty("symbol"));
            updateUI();
        }
    };

    ActionListener funcActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton but = (JButton) e.getSource();
            dataInputModel.add((Operation) but.getClientProperty("operation"));
            updateUI();
        }
    };


    public InputForm(DataInputModelImpl dataInputModel) {
        this.dataInputModel = dataInputModel;

        setContentPane(root);
        setSize(500, 350);
        setLocationByPlatform(true);
        setTitle("Мастер функций");

        GridLayout grid = new GridLayout(0, 3);
        valuesPanel.setLayout(grid);
        GridLayout grid2 = new GridLayout(0, 3);
        operationsPanel.setLayout(grid2);

        for (char value : dataInputModel.getSymbols()) {
            JButton but = new JButton(String.valueOf(value));
            but.putClientProperty("symbol", value);
            but.addActionListener(symbolActionListener);
            valuesPanel.add(but);
        }

        for (Operation operation : dataInputModel.getOperations()) {
            JButton but = new JButton(operation.getName());
            but.putClientProperty("operation", operation);
            but.addActionListener(funcActionListener);
            operationsPanel.add(but);
        }

        btnAccept.addActionListener(e -> {
            try {
                if (dataInputModel.accept()){
                    setVisible(false);
                }else
                    JOptionPane.showMessageDialog(this, "Выражение не задано","Ошибка", JOptionPane.ERROR_MESSAGE);
            } catch (ValidationException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(),"Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        });
        btnClear.addActionListener(e -> {
            dataInputModel.clear();
            updateUI();
        });
        btnDeleteLast.addActionListener(e -> {
            dataInputModel.removeLast();
            updateUI();
        });

        valuesPanel.revalidate();
        valuesPanel.repaint();
    }

    void updateUI(){
        inputField.setText(dataInputModel.getExpression().toString());
    }

}
