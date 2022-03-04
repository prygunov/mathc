package net.artux.mathc.ui;

import net.artux.mathc.Application;
import net.artux.mathc.data.SolutionException;
import net.artux.mathc.model.Expression;
import net.artux.mathc.model.ExpressionPart;
import net.artux.mathc.util.Stack;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Enumeration;
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
    private final DefaultListModel listModel;
    private final DefaultTableModel tableModel;
    private final Application application;

    public MainForm(Application application) {
        this.application = application;

        listModel = new DefaultListModel<>();
        list1.setModel(listModel);
        listPanel.setBackground(list1.getBackground());
        list1.setEnabled(false); // отключаем редактирование

        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0; // запрещает редактировать первую колонку
            }
        };
        tableModel.addColumn("Переменные");
        tableModel.addColumn("Значения");
        table1.setModel(tableModel);
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

        resultCheckBox.addChangeListener(e ->
            application.getDataModel().setCountResult(resultCheckBox.isSelected()));

        tableModel.addTableModelListener(e -> {
            if (e.getType() == TableModelEvent.UPDATE){
                String key = (String) tableModel.getValueAt(e.getFirstRow(), 0);
                Object value = tableModel.getValueAt(e.getFirstRow(), 1);
                if (value instanceof Double)
                    application.getDataModel().setValue(key, (Double) value);
                else try {
                    Double d = Double.parseDouble(String.valueOf(value));
                    application.getDataModel().setValue(key, d);
                }catch (NumberFormatException numberFormatException){
                    error(new NumberFormatException("Не удалось преобразовать " + value));
                    tableModel.setValueAt(application.getDataModel().getValues().get(key), e.getFirstRow(), 1);
                }

            }
        });

        timeSlider.addChangeListener(e -> tickTime.setText("Время такта: " + (timeSlider.getValue() / 100f) + " с."));
        clearButton.addActionListener(e -> application.getDataModel().clear());

        expressionField.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                application.getInputForm().setVisible(true); // показываем окно ввода
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
    public void updateInputExpression(Expression expression) {
        expressionField.setText(expression.toString());
        tableModel.getDataVector().removeAllElements();
        for (Map.Entry<String, Double> entry : application.getDataModel().getValues().entrySet()) {
            Object[] row = {entry.getKey(), entry.getValue()};
            tableModel.addRow(row);
        }
    }

    @Override
    public void updatePostfix(String s) {
        resultField.setText(s);
    }

    @Override
    public void updateResult(Double d) {
        if (d.isNaN() || d.isInfinite())
            resultValueTextField.setText("Ошибка, не число");
        else
            resultValueTextField.setText(String.valueOf(d));
    }

    @Override
    public void updateStack(Stack<?> stack) {
        listModel.removeAllElements();
        Enumeration<?> expressionPartEnumeration = stack.elements();
        while (expressionPartEnumeration.hasMoreElements()) {
            listModel.add(0, expressionPartEnumeration.nextElement());
        }
        //список перевернут, привязан к низу поля, поэтому считаем позицию указателя так
        list1.setSelectedIndex(stack.getElementCount() - 1 - stack.getPeekIndex());
    }

    @Override
    public void timerStatusChanged(boolean isTimerRunning) {
        if (isTimerRunning)
            autoButton.setText("Остановить");
        else
            autoButton.setText("Автоматически");
    }

    @Override
    public void error(Exception e) {
         JOptionPane.showMessageDialog(this, e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
    }
}
