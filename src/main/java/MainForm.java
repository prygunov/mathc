import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.Timer;

public class MainForm extends JFrame{
    private JPanel root;
    private JTextField resultField;
    private JTextField expressionField;
    private JList list1;
    private JButton resultButton;
    private JButton autoButton;
    private JButton tickButton;
    private JSlider slider1;
    private JLabel tickTime;
    private DataModel dataModel;
    private DefaultListModel<String> listModel;


    MainForm(){
        dataModel = new DataModel();
        listModel = new DefaultListModel<>();

        setContentPane(root);
        setSize(700, 650);
        setLocationByPlatform(true);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        resultButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Solution s = dataModel.getSolution();
                if (s == null)
                    s = dataModel.prepareSolution(expressionField.getText());
                while (!s.done)
                    s.tick();
                updateUI(s);
            }
        });

        tickButton.addActionListener(e -> {
            if (dataModel.getSolution() == null)
                dataModel.prepareSolution(expressionField.getText());
            dataModel.getSolution().tick();
            updateUI(dataModel.getSolution());
        });


        autoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
    }

    void updateUI(Solution solution){
        list1.setModel(listModel);
        listModel.removeAllElements();
        Enumeration<ExpressionPart> expressionPartEnumeration = solution.getStack().elements();
        while (expressionPartEnumeration.hasMoreElements()){
            listModel.addElement(expressionPartEnumeration.nextElement().getValue());
        }

        Expression e = new Expression(solution.getResultExpression());
        resultField.setText(e.toString());
    }

}
