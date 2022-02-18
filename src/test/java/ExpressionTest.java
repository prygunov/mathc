import net.artux.mathc.data.DataModel;
import net.artux.mathc.data.Solution;
import net.artux.mathc.data.SolutionException;
import net.artux.mathc.model.Expression;

import static org.junit.Assert.*;

public class ExpressionTest {

    @org.junit.Test
    public void testPostfix() {
        String in = "5*8*(2+9)+(7-5+8-9*(5*5)+5)";
        String post = "58*29+*75-8+955**-5++";

        Expression expression = Expression.parseExpression(in);
        Expression postData = result(expression);

        assertEquals(post, postData.toString());
    }

    @org.junit.Test
    public void testSecondPostfix() {
        String in = "5*8/(2+9)";
        String post = "58*29+/";

        Expression expression = Expression.parseExpression(in);
        Expression postData = result(expression);

        assertEquals(post, postData.toString());
    }

    @org.junit.Test
    public void testThirdPostfix() {
        String in = "a+b*sin(c-d/e)";
        String post = "abcde/-sin*+";

        Expression expression = Expression.parseExpression(in);
        Expression postData = result(expression);

        assertEquals(post, postData.toString());
    }

    @org.junit.Test
    public void testFourthPostfix() {
        String in = "exp(sin(a+b/(c-d)))";
        String post = "abcd-/+sinexp";

        Expression expression = Expression.parseExpression(in);
        Expression postData = result(expression);

        assertEquals(post, postData.toString());
    }

    public static Expression result(Expression expression) {
        Solution solution = new Solution(expression);
        try {
            while (!solution.isDone())
                solution.tick();
        } catch (SolutionException e) {
            e.printStackTrace();
        }
        return new Expression(solution.getResultExpression());
    }
}