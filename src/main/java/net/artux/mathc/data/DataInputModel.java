package net.artux.mathc.data;

import net.artux.mathc.Operation;
import net.artux.mathc.model.Expression;
import net.artux.mathc.validation.ValidationException;

import java.util.Collection;

public interface DataInputModel {

    void add(char symbol);
    void add(Operation operation);
    boolean accept() throws ValidationException;
    void removeLast();
    void clear();

    Expression getExpression();
    char[] getSymbols();
    Collection<Operation> getOperations();

}
