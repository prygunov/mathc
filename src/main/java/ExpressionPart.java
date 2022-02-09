public class ExpressionPart {

    ExpressionPart(ExpressionPart part){
        this.value = part.value;
        this.command = part.command;
    }

    ExpressionPart(String value, boolean command){
        this.value = value;
        this.command = command;
    }

    private String value;
    private boolean command;

    public boolean isCommand() {
        return command;
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
