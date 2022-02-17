package net.artux.mathc;

import net.artux.mathc.ui.MainForm;

public class Application{

    private final MainForm mainForm; // структура окна входа

    Application(){
        mainForm = new MainForm();
        mainForm.setVisible(true);
    }


}
