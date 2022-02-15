import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Expression {

    private List<ExpressionPart> content;

    public Expression(List<ExpressionPart> content) {
        this.content = content;
    }

    public List<ExpressionPart> getContent() {
        return content;
    }

    public static String[] operations = {"+", "-", "*", "/", "^", "sin", "cos", "(", ")", "exp"};

    public static Expression parseExpression(String s){ // перевод строки в выражение
        List<ExpressionPart> parts = new ArrayList<>();
        StringBuilder partBuilder = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            partBuilder.append(s.charAt(i));
            if (Arrays.stream(operations).anyMatch(s1 -> s1.contains(partBuilder.toString()))) {
                if (Arrays.stream(operations).anyMatch(s1 -> s1.equals(partBuilder.toString()))) {
                    parts.add(new ExpressionPart(partBuilder.toString(), true));
                    partBuilder.setLength(0);
                }
            }else{
                if (partBuilder.length()>1){
                    i -= partBuilder.length() - 1;
                    partBuilder.setLength(1);
                }
                parts.add(new ExpressionPart(partBuilder.toString(), false));
                partBuilder.setLength(0);
            }
        }
        return new Expression(parts);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        getContent().forEach(expressionPart -> builder.append(expressionPart.getValue()));
        return builder.toString();
    }
}
