package net.artux.mathc;

import net.artux.mathc.data.DataInputModel;
import net.artux.mathc.data.DataModel;
import net.artux.mathc.ui.InputForm;
import net.artux.mathc.ui.MainForm;

public class Application{

    private final MainForm mainForm; // структура окна входа
    private final InputForm inputForm;
    private final DataModel dataModel;
    private final DataInputModel inputModel;

    Application(){
        mainForm = new MainForm(this);
        dataModel = new DataModel(mainForm);
        inputModel = new DataInputModel(dataModel);
        mainForm.setVisible(true);
        inputForm = new InputForm(this, inputModel);
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

    public DataInputModel getInputModel() {
        return inputModel;
    }
}
