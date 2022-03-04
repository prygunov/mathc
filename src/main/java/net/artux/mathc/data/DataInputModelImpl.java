package net.artux.mathc.data;

import net.artux.mathc.Config;
import net.artux.mathc.Operation;
import net.artux.mathc.model.Expression;
import net.artux.mathc.model.ExpressionPart;
import net.artux.mathc.validation.ValidationException;
import net.artux.mathc.validation.Validator;

import java.util.ArrayList;
import java.util.Collection;

public class DataInputModelImpl implements DataInputModel{

    private Expression exp;
    private final DataModelImpl dataModel;
    private static final char[] symbols = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', '(', ')'};
    private static final Collection<Operation> operations = Config.getSupportedOperations();

    public DataInputModelImpl(DataModelImpl dataModel){
        exp = new Expression(new ArrayList<>());
        this.dataModel = dataModel;
    }

    public void add(char symbol){
        ExpressionPart expressionPart = new ExpressionPart(String.valueOf(symbol), false);
        exp.getContent().add(expressionPart);
    }

    public void add(Operation operation){
        ExpressionPart expressionPart = new ExpressionPart(operation.getName(), true);
        exp.getContent().add(expressionPart);
        if (expressionPart.isFunction())
            add('(');
    }

    public Expression getExpression() {
        return exp;
    }

    public boolean accept() throws ValidationException {
        if (Validator.isValid(exp)) {
            dataModel.setExpression(exp);
            return true;
        } else return false;
    }

    public void clear(){
        exp = new Expression(new ArrayList<>());
    }

    public void removeLast(){
        if (exp.getContent().size()>0) {
            exp.getContent().remove(exp.getContent().size()-1);
        }
    }

    public char[] getSymbols() {
        return symbols;
    }

    public Collection<Operation> getOperations() {
        return operations;
    }
}
