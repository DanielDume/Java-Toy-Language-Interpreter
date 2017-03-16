package Model;

import java.util.HashMap;
import java.util.Set;

/**
 * Created by Dani on 10/24/2016.
 */
public class MyDictionary<T1, T2> implements MyIDictionary<T1, T2>,java.io.Serializable {


    HashMap<T1, T2> dict;

    public MyDictionary(){

        this.dict = new HashMap<>();
    }

    public MyDictionary(HashMap<T1, T2> dict) {
        this.dict = dict;
    }

    public T2 getVal(T1 key) {

        return this.dict.get(key);

    }

    @Override
    public void add(T1 key, T2 val) {

        this.dict.put(key, val);

    }

    @Override
    public boolean isDefined(T1 key) {

        return this.dict.containsKey(key);

    }

    @Override
    public void update(T1 key, T2 val) {

        this.dict.put(key,val);

    }

    @Override
    public Set<T1> keySet() {

        return this.dict.keySet();

    }

    @Override
    public void remove(T1 key) {
        this.dict.remove(key);
    }

    @Override
    public HashMap<T1, T2> getContent() {
        return this.dict;
    }

}
