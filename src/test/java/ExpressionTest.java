import net.artux.mathc.data.Solution;
import net.artux.mathc.data.SolutionException;
import net.artux.mathc.model.Expression;

import static org.junit.Assert.assertEquals;

public class ExpressionTest {

    @org.junit.Test
    public void testPostfix() throws SolutionException {
        String in = "5*8*(2+9)+(7-5+8-9*(5*5)+5)";
        String post = "58*29+*75-8+955**-5++";

        Expression expression = TestHelper.parseExpression(in);
        Expression postData = result(expression);

        assertEquals(post, postData.toString());
    }

    @org.junit.Test
    public void testSecondPostfix() throws SolutionException {
        String in = "5*8/(2+9)";
        String post = "58*29+/";

        Expression expression = TestHelper.parseExpression(in);
        Expression postData = result(expression);

        assertEquals(post, postData.toString());
    }

    @org.junit.Test
    public void testThirdPostfix() throws SolutionException {
        String in = "a+b*sin(c-d/e)";
        String post = "abcde/-sin*+";

        Expression expression = TestHelper.parseExpression(in);
        Expression postData = result(expression);

        assertEquals(post, postData.toString());
    }

    @org.junit.Test
    public void testFourthPostfix() throws SolutionException {
        String in = "exp(sin(a+b/(c-d)))";
        String post = "abcd-/+sinexp";

        Expression expression = TestHelper.parseExpression(in);
        Expression postData = result(expression);

        assertEquals(post, postData.toString());
    }

    @org.junit.Test
    public void testFifthPostfix() throws SolutionException {
        String in = "cos(f^sin(b))";
        String post = "fbsin^cos";

        Expression expression = TestHelper.parseExpression(in);
        Expression postData = result(expression);

        assertEquals(post, postData.toString());
    }

    @org.junit.Test
    public void testSixthPostfix() throws SolutionException {
        String in = "cos(e^h*d+c)";
        String post = "eh^d*c+cos";

        Expression expression = TestHelper.parseExpression(in);
        Expression postData = result(expression);

        assertEquals(post, postData.toString());
    }

    public static Expression result(Expression expression) throws SolutionException {
        Solution solution = new Solution(expression);
        try {
            while (!solution.isDone())
                solution.tick();
        } catch (SolutionException e) {
            e.printStackTrace();
        }
        return solution.getResultExpression();
    }
}