import static org.junit.Assert.*;

public class ExpressionTest {

    @org.junit.Test
    public void testPostfix() {
        String in = "5*8*(2+9)+(7-5+8-9*(5*5)+5)";
        String post = "58*29+*75-8+955**-5++";

        Expression expression = Expression.parseExpression(in);

        DataModel dataModel = new DataModel();
        Expression postData = dataModel.getPostFixEx(expression);

        assertEquals(post, postData.toString());
    }
}