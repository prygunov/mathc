package net.artux.mathc;

import net.artux.mathc.ui.InputForm;
import net.artux.mathc.ui.MainForm;

public class Application{

    private final MainForm mainForm; // структура окна входа
    private final InputForm inputForm;

    Application(){
        mainForm = new MainForm();
        mainForm.setVisible(true);
        inputForm = new InputForm();
        inputForm.setVisible(true);
    }


}
