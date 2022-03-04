package net.artux.mathc.model;

import net.artux.mathc.Config;
import net.artux.mathc.OperationType;

public class ExpressionPart {

    private String value;
    private boolean command;

    public ExpressionPart(String value, boolean command){
        this.value = value;
        this.command = command;
    }

    public boolean isCommand() {
        return command;
    }

    public boolean isBracket() {
        return value.equals("(") || value.equals(")");
    }

    public boolean isLeftBracket() {
        return value.equals("(");
    }

    public boolean isRightBracket() {
        return value.equals(")");
    }

    public boolean isFunction(){
        if (Config.supportedOperations.containsKey(getValue()))
            return Config.supportedOperations.get(getValue()).getType() == OperationType.FUNCTION;
        else return false;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
