import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.function.Consumer;

public class DataModel {

    private Solution solution;

    public Expression getPostFixEx(Expression expression) {
        // Перебираем expressionList
        solution = new Solution(expression);
        while (!solution.done)
            solution.tick();
        return new Expression(solution.getResultExpression());
    }

    public Solution prepareSolution(String s){
        solution = new Solution(Expression.parseExpression(s));
        return solution;
    }

    public Solution getSolution(){
        return solution;
    }

    public void clear(){
        solution = null;
    }
}
