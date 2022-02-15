import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class MainForm extends JFrame{

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
    private final Timer timer;

    MainForm(){

        dataModel = new DataModel();
        listModel = new DefaultListModel<>();
        list1.setModel(listModel);
        timer = new Timer();
        listPanel.setBackground(list1.getBackground());

        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Переменные");
        tableModel.addColumn("Значения");
        table1.setModel(tableModel);

        setContentPane(root);
        setSize(700, 650);
        setLocationByPlatform(true);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        resultButton.addActionListener(e -> {
            Solution s = dataModel.getSolution();
            if (s == null)
                s = dataModel.prepareSolution(expressionField.getText());
            while (!s.done)
                s.tick();
            updateUI(s);
        });

        tickButton.addActionListener(e -> {
            if (dataModel.getSolution() == null)
                dataModel.prepareSolution(expressionField.getText());
            dataModel.getSolution().tick();
            updateUI(dataModel.getSolution());
        });


        autoButton.addActionListener(e -> {
            if (dataModel.getSolution() == null)
                dataModel.prepareSolution(expressionField.getText());

            timer.schedule(new Repeater(dataModel.getSolution()), timeSlider.getValue()* 10L);
        });
        timeSlider.addChangeListener(e -> tickTime.setText("Время такта: " + (timeSlider.getValue()/100f) + " с."));
        clearButton.addActionListener(e -> {
            dataModel.clear();
            updateUI(null);
        });
        expressionField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Expression expression = Expression.parseExpression(expressionField.getText());
                tableModel.getDataVector().removeAllElements();
                for (ExpressionPart part : expression.getContent()) {
                    Object[] row = {part.getValue(), ""};
                    if (!part.isCommand())
                    tableModel.addRow(row);
                }
            }
        });
    }

    void updateUI(Solution solution){
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

    class Repeater extends TimerTask{

        private final Solution solution;

        public Repeater(Solution solution) {
            this.solution = solution;
        }

        @Override
        public void run() {
            if (!solution.done) {
                solution.tick();
                updateUI(solution);
                timer.schedule(new Repeater(solution), timeSlider.getValue()* 10L);
            }
        }
    }

}
