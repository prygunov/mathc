package net.artux.mathc.ui;

import net.artux.mathc.Application;
import net.artux.mathc.data.Solution;
import net.artux.mathc.data.SolutionException;
import net.artux.mathc.model.Expression;
import net.artux.mathc.model.ExpressionPart;
import net.artux.mathc.util.Stack;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class MainForm extends JFrame implements DataChangeListener {

    private JPanel root;
    private JTextField resultField;
    private JTextField expressionField;
    private JList<ExpressionPart> list1;
    private JButton resultButton;
    private JButton autoButton;
    private JButton tickButton;
    private JSlider timeSlider;
    private JLabel tickTime;
    private JButton clearButton;
    private JPanel listPanel;
    private JTable table1;
    private JCheckBox resultCheckBox;
    private JTextField resultValueTextField;
    private JButton setValuesButton;
    private final DefaultListModel listModel;
    private final DefaultTableModel tableModel;
    private final Application application;

    public MainForm(Application application) {
        this.application = application;

        listModel = new DefaultListModel<>();
        list1.setModel(listModel);
        listPanel.setBackground(list1.getBackground());
        list1.setEnabled(false);

        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0;
            }
        };
        tableModel.addColumn("Переменные");
        tableModel.addColumn("Значения");
        table1.setModel(tableModel);
        table1.getColumnName(1);
        table1.getTableHeader().setReorderingAllowed(false);
        table1.getTableHeader().setResizingAllowed(false);

        setContentPane(root);
        setSize(700, 650);
        setLocationByPlatform(true);
        setTitle("Трансляция выражений");

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        resultButton.addActionListener(e -> {
            try {
                application.getDataModel().allTicks();
            } catch (Exception ex) {
                error(ex);
            }
        });

        tickButton.addActionListener(e -> {
            try {
                application.getDataModel().tick();
            } catch (Exception ex) {
                error(ex);
            }
        });

        autoButton.addActionListener(e -> {
            try {
                application.getDataModel().delayTicks(timeSlider.getValue() * 10L);
            } catch (SolutionException ex) {
                error(ex);
            }
        });

        setValuesButton.addActionListener(e -> {
            try {
                application.getDataModel().setValues(getValues());
            } catch (Exception ex) {
                error(ex);
            }

        });
        resultCheckBox.addChangeListener(e -> application.getDataModel().setCountResult(resultCheckBox.isSelected()));

        timeSlider.addChangeListener(e -> tickTime.setText("Время такта: " + (timeSlider.getValue() / 100f) + " с."));
        clearButton.addActionListener(e -> application.getDataModel().clear());

        expressionField.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                application.getInputForm().setVisible(true);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    Map<String, Double> getValues() {
        HashMap<String, Double> values = new HashMap<>();
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            values.put((String) tableModel.getValueAt(i, 0), Double.parseDouble((String) tableModel.getValueAt(i, 1)));
        }
        return values;
    }

    @Override
    public void updateInputExpression(Expression expression) {
        expressionField.setText(expression.toString());
        tableModel.getDataVector().removeAllElements();
        for (ExpressionPart part : expression.getContent()) {
            Object[] row = {part.getValue(), ""};
            if (!part.isCommand() && !part.getValue().equals(")")
                    && !part.getValue().equals("("))
                tableModel.addRow(row);
        }
    }

    @Override
    public void updatePostfix(String s) {
        resultField.setText(s);
    }

    @Override
    public void updateResult(Double d) {
        resultValueTextField.setText(String.valueOf(d));
    }

    @Override
    public void updateStack(Stack stack) {
        listModel.removeAllElements();
        Enumeration expressionPartEnumeration = stack.elements();
        while (expressionPartEnumeration.hasMoreElements()) {
            listModel.add(0, expressionPartEnumeration.nextElement());
        }
        list1.setSelectedIndex(stack.getElementCount() - 1 - stack.getPeekIndex());
    }

    @Override
    public void error(Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
    }
}
