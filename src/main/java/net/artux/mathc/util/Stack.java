package net.artux.mathc.util;

import java.util.Vector;

public class Stack<T> extends Vector<T> {

    private int peekIndex = -1;

    public Stack() {
    }

    public void push(T t) {
        if (elementCount == 0) {
            add(t);
            peekIndex = 0;
        }else if (elementCount-1 == peekIndex){
            add(t);
            peekIndex++;
        }else if (elementCount > peekIndex -1){
            set(++peekIndex, t);
        }
    }

    @Override
    public boolean isEmpty() {
        return peekIndex < 0;
    }

    public T pop() {
        if (peekIndex <0)
            throw new ArrayIndexOutOfBoundsException("Stack is empty");
        T t = get(peekIndex);
        peekIndex--;
        return t;
    }

    public T peek(){
        return get(peekIndex);
    }

    public int getPeekIndex() {
        return peekIndex;
    }

    public int getElementCount(){
        return elementCount;
    }
}
