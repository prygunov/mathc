import net.artux.mathc.data.CountSolution;
import net.artux.mathc.data.Solution;
import net.artux.mathc.data.SolutionException;
import net.artux.mathc.model.Expression;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class CountSolutionTest {


    @org.junit.Test
    public void simpleTest() throws SolutionException {
        String in = "a*b/(c+d)";
        Map<String, Double> values = new HashMap<>();
        values.put("a", 4d);
        values.put("b", 5d);
        values.put("c", 6d);
        values.put("d", 10d);

        Double result = 1.25d;

        Expression expression = TestHelper.parseExpression(in);
        Expression postData = ExpressionTest.result(expression);
        CountSolution countSolution = new CountSolution(postData, values);
        try {
            while (!countSolution.isDone())
                countSolution.tick();
        }catch (Exception ignored){}

        assertEquals(result, countSolution.getStack().peek());
    }

    @org.junit.Test
    public void complexTest() throws SolutionException {
        String in = "a^b*lg(c)";
        Map<String, Double> values = new HashMap<>();
        values.put("a", 4d);
        values.put("b", 0.5d);
        values.put("c", 10d);

        Double result = 2d;

        Expression expression =  TestHelper.parseExpression(in);
        Expression postData = ExpressionTest.result(expression);
        CountSolution countSolution = new CountSolution(postData, values);
        try {
            while (!countSolution.isDone())
                countSolution.tick();
        }catch (Exception ignored){}

        assertEquals(result, countSolution.getStack().peek());
    }

}