package net.artux.mathc.ui;

import net.artux.mathc.data.DataModel;
import net.artux.mathc.data.Solution;
import net.artux.mathc.data.SolutionException;
import net.artux.mathc.model.Expression;
import net.artux.mathc.model.ExpressionPart;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Enumeration;

public class MainForm extends JFrame implements DataChangeListener {

    private JPanel root;
    private JTextField resultField;
    private JTextField expressionField;
    private JList<String> list1;
    private JButton resultButton;
    private JButton autoButton;
    private JButton tickButton;
    private JSlider timeSlider;
    private JLabel tickTime;
    private JButton clearButton;
    private JPanel listPanel;
    private JTable table1;
    private final DataModel dataModel;
    private final DefaultListModel<String> listModel;

    public MainForm(){
        dataModel = new DataModel(this);
        listModel = new DefaultListModel<>();
        list1.setModel(listModel);
        listPanel.setBackground(list1.getBackground());

        DefaultTableModel tableModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0)
                    return false;
                else
                    return true;
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

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        resultButton.addActionListener(e -> {
            try {
                dataModel.allTicks();
            } catch (SolutionException ex) {
                error(ex);
            }
        });

        tickButton.addActionListener(e -> {
            try {
                dataModel.tick();
            } catch (Exception ex) {
                error(ex);
            }
        });

        autoButton.addActionListener(e -> {
            try {
                dataModel.delayTicks(timeSlider.getValue()* 10L);
            } catch (SolutionException ex) {
                error(ex);
            }
        });

        timeSlider.addChangeListener(e -> tickTime.setText("Время такта: " + (timeSlider.getValue()/100f) + " с."));
        clearButton.addActionListener(e -> dataModel.clear());

        expressionField.addActionListener(e -> {
            Expression expression = Expression.parseExpression(expressionField.getText());
            dataModel.setExpression(expression);
            tableModel.getDataVector().removeAllElements();
            for (ExpressionPart part : expression.getContent()) {
                Object[] row = {part.getValue(), ""};
                if (!part.isCommand())
                tableModel.addRow(row);
            }
        });
    }


    @Override
    public void updateSolution(Solution solution) {
        listModel.removeAllElements();
        if (solution!=null) {
            Enumeration<ExpressionPart> expressionPartEnumeration = solution.getStack().elements();
            while (expressionPartEnumeration.hasMoreElements()) {
                listModel.add(0, expressionPartEnumeration.nextElement().getValue());
            }

            Expression e = new Expression(solution.getResultExpression());
            resultField.setText(e.toString());
        }else{
            resultField.setText("");
        }
    }

    @Override
    public void error(Exception e) {
        JOptionPane.showMessageDialog(this, e.getMessage());
    }
}
