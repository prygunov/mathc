package net.artux.mathc.model;

import net.artux.mathc.Config;
import net.artux.mathc.OperationType;

public class ExpressionPart {

    ExpressionPart(ExpressionPart part){
        this.value = part.value;
        this.command = part.command;
    }

    public ExpressionPart(String value, boolean command){
        this.value = value;
        this.command = command;
    }

    private String value;
    private boolean command;

    public boolean isCommand() {
        return command;
    }

    public boolean isFunction(){
        if (Config.supportedOperations.containsKey(getValue()))
            return Config.supportedOperations.get(getValue()).getType() == OperationType.FUNCTION;
        else return false;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
