package Model;

import java.util.Stack;

/**
 * Created by Dani on 10/24/2016.
 */
public class MyStack<T> implements MyIStack<T>,java.io.Serializable {

    Stack<T> stack;

    public MyStack(){

        this.stack = new Stack<>();

    }

    public MyStack(Stack<T> stack) {
        this.stack = stack;
    }

    public T pop(){

        return this.stack.pop();

    }

    public void push (T element){

        this.stack.push(element);

    }

    @Override
    public int size() {

        return this.stack.size();

    }

    @Override
    public T get(int pos) {

        return this.stack.get(pos);

    }

    @Override
    public T peek() {
        return this.stack.peek();
    }

    public Stack<IStmt> toStack()

    {
        return (Stack<IStmt>) this.stack;
    }

}
