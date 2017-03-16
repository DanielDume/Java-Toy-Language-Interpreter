package Model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Dani on 1/26/2017.
 */
public class MyLatchTable<T1,T2> implements ILatchTable<T1,T2>{

    HashMap<T1,T2> dict;
    int validAdress;

    public MyLatchTable() {
        this.dict = new HashMap<>();
        this.validAdress = 1;
    }

    public MyLatchTable(HashMap<T1, T2> dict) {
        this.dict = dict;
    }

    @Override
    public String toString() {
        String result = "";
        Set<T1> keys = this.dict.keySet();  //get all keys
        for(T1 i: keys)
        {
            result += i.toString() + " = " + this.dict.get(i).toString() + "\n";
        }
        if (result.length() > 0){

            result = result.substring(0, result.length() - 1);

        }
        //return "ASDF";
        return result;
    }


    @Override
    public T2 getVal(T1 key) {
        return this.dict.get(key);
    }

    @Override
    public void add(T1 key, T2 val) {

        this.dict.put(key, val);
        this.validAdress = this.validAdress + 1;

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
    public int getValidAdress() {
        return this.validAdress;
    }

    @Override
    public Map<T1, T2> getContent() {

        return dict;

    }

    @Override
    public void setContent(Map<T1, T2> newHeap) {

        dict = (HashMap<T1,T2>)newHeap;

    }

}
