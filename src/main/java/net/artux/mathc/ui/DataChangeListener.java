package net.artux.mathc.ui;

import net.artux.mathc.model.Expression;
import net.artux.mathc.util.Stack;

public interface DataChangeListener {

    void updateInputExpression(Expression expression);
    void updatePostfix(String s);
    void updateResult(Double d);
    void updateStack(Stack stack);
    void error(Exception e);

}
