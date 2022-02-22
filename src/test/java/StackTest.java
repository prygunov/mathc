import net.artux.mathc.util.Stack;

import java.util.Enumeration;

import static org.junit.Assert.assertEquals;

public class StackTest {

    @org.junit.Test
    public void simpleTest() {
        Stack<String> stack = new Stack<>();
        stack.push("tom");
        stack.push("garry");
        stack.pop();

        Enumeration<String> e = stack.elements();
        while (e.hasMoreElements())
            System.out.println(e.nextElement());
        System.out.println(stack.peek());
        stack.push("garry2");
        System.out.println(stack.peek());
        e = stack.elements();
        while (e.hasMoreElements())
            System.out.println(e.nextElement());
    }

}