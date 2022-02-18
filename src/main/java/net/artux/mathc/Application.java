package net.artux.mathc;

import net.artux.mathc.data.DataModel;
import net.artux.mathc.ui.InputForm;
import net.artux.mathc.ui.MainForm;

public class Application{

    private final MainForm mainForm; // структура окна входа
    private final InputForm inputForm;
    private final DataModel dataModel;

    Application(){
        mainForm = new MainForm(this);
        dataModel = new DataModel(mainForm);
        mainForm.setVisible(true);
        inputForm = new InputForm(this);
    }

    public MainForm getMainForm() {
        return mainForm;
    }

    public InputForm getInputForm() {
        return inputForm;
    }

    public DataModel getDataModel() {
        return dataModel;
    }
}
