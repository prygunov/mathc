package net.artux.mathc.ui;

import net.artux.mathc.Application;
import net.artux.mathc.data.Solution;
import net.artux.mathc.data.SolutionException;
import net.artux.mathc.model.Expression;
import net.artux.mathc.model.ExpressionPart;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Enumeration;

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
    private final DefaultListModel<ExpressionPart> listModel;
    private final DefaultTableModel tableModel;
    private final Application application;

    public MainForm(Application application) {
        this.application = application;

        listModel = new DefaultListModel<ExpressionPart>();
        list1.setModel(listModel);
        listPanel.setBackground(list1.getBackground());

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
            } catch (SolutionException ex) {
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

    @Override
    public void updateSolution(Solution solution) {
        listModel.removeAllElements();
        if (solution != null) {
            expressionField.setText(solution.getExpression().toString());
            Enumeration<ExpressionPart> expressionPartEnumeration = solution.getStack().elements();
            while (expressionPartEnumeration.hasMoreElements()) {
                listModel.add(0, expressionPartEnumeration.nextElement());
            }
            list1.setSelectedIndex(solution.getStack().getElementCount()-1 - solution.getStack().getPeekIndex());
            list1.setEnabled(false);

            Expression e = new Expression(solution.getResultExpression());
            resultField.setText(e.toString());
            if (solution.isDone()) {
                tableModel.getDataVector().removeAllElements();
                for (ExpressionPart part : e.getContent()) {
                    Object[] row = {part.getValue(), ""};
                    if (!part.isCommand())
                        tableModel.addRow(row);
                }
            }
        } else {
            expressionField.setText("Кликните для ввода");
            resultField.setText("");
        }
    }

    @Override
    public void error(Exception e) {
        JOptionPane.showMessageDialog(this, e.getMessage());
    }
}
