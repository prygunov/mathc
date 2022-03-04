package net.artux.mathc;

import net.artux.mathc.data.DataInputModelImpl;
import net.artux.mathc.data.DataModelImpl;
import net.artux.mathc.ui.InputForm;
import net.artux.mathc.ui.MainForm;

public class Application{

    private final InputForm inputForm; // структура окна ввода
    private final DataModelImpl dataModel; // мод

    Application(){
        // структура главного окна
        MainForm mainForm = new MainForm(this);
        dataModel = new DataModelImpl(mainForm);
        DataInputModelImpl inputModel = new DataInputModelImpl(dataModel);
        inputForm = new InputForm(inputModel);

        mainForm.setVisible(true);
    }

    public InputForm getInputForm() {
        return inputForm;
    }

    public DataModelImpl getDataModel() {
        return dataModel;
    }
}
