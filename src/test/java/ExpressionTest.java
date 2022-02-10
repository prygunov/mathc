import static org.junit.Assert.*;

public class ExpressionTest {

    DataModel dataModel = new DataModel();

    @org.junit.Test
    public void testPostfix() {
        String in = "5*8*(2+9)+(7-5+8-9*(5*5)+5)";
        String post = "58*29+*75-8+955**-5++";

        Expression expression = Expression.parseExpression(in);
        Expression postData = dataModel.getPostFixEx(expression);

        assertEquals(post, postData.toString());
    }

    @org.junit.Test
    public void testSecondPostfix() {
        String in = "5*8/(2+9)";
        String post = "58*29+/";

        Expression expression = Expression.parseExpression(in);
        Expression postData = dataModel.getPostFixEx(expression);

        assertEquals(post, postData.toString());
    }

    @org.junit.Test
    public void testThirdPostfix() {
        String in = "a+b*sin(c-d/e)";
        String post = "abcde/-sin*+";

        Expression expression = Expression.parseExpression(in);
        Expression postData = dataModel.getPostFixEx(expression);

        assertEquals(post, postData.toString());
    }

    @org.junit.Test
    public void testFourthPostfix() {
        String in = "exp(sin(a+b/(c-d)))";
        String post = "abcd-/+sinexp";

        Expression expression = Expression.parseExpression(in);
        Expression postData = dataModel.getPostFixEx(expression);

        assertEquals(post, postData.toString());
    }
}