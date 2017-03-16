package Model;

/**
 * Created by Dani on 10/24/2016.
 */
public interface MyIList<T> {

    void add(T element);
    T get(int index);
    int size();

}
