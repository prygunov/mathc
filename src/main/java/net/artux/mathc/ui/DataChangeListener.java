package net.artux.mathc.ui;

import net.artux.mathc.data.Solution;

public interface DataChangeListener {

    void updateSolution(Solution solution);
    void error(Exception e);

}
