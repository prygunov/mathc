import javax.swing.*;

public class MainForm extends JFrame{
    private JPanel root;
    private JTextField textField1;
    private JTextField textField2;
    private JList list1;
    private JButton результатButton;
    private JButton автоматическиButton;
    private JButton тактButton;
    private JSlider slider1;

    MainForm(){
        setContentPane(root);
        setSize(700, 650);
        setLocationByPlatform(true);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

}
