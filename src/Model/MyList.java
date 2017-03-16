package Model;

import java.util.Vector;

/**
 * Created by Dani on 10/24/2016.
 */
public class MyList<T> implements MyIList<T>,java.io.Serializable {

    Vector<T> list;

    public MyList (){

        this.list = new Vector<>();

    }

    public MyList(Vector<T> list) {
        this.list = list;
    }

    public void add(T element) {

        list.add(element);

    }

    @Override
    public T get(int index) {

        return this.list.get(index);

    }

    @Override
    public int size() {

        return this.list.size();

    }

    void getAll(){

        int i = 0;
        for ( i = 0; i < list.size(); i++ ){

            System.out.print(list.toArray()[i]);

        }

    }

}
