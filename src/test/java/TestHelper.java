import net.artux.mathc.Config;
import net.artux.mathc.Operation;
import net.artux.mathc.model.Expression;
import net.artux.mathc.model.ExpressionPart;

import java.util.ArrayList;
import java.util.List;

public class TestHelper {

    public static List<String> operations = new ArrayList<>();
    static {
        for (Operation o :
                Config.getSupportedOperations()) {
            operations.add(o.getName());
        }
    }

    public static Expression parseExpression(String s){ // перевод строки в выражение
        List<ExpressionPart> parts = new ArrayList<>();
        StringBuilder partBuilder = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            partBuilder.append(s.charAt(i));
            if (operations.stream().anyMatch(s1 -> s1.contains(partBuilder.toString()))) {
                if (operations.stream().anyMatch(s1 -> s1.equals(partBuilder.toString()))) {
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

}
