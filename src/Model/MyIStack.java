package Model;

import java.util.Stack;

/**
 * Created by Dani on 10/24/2016.
 */
public interface MyIStack<T> {

    T pop();
    void push(T element);
    int size();
    T get(int pos);
    T peek();
    Stack<IStmt> toStack();

}
