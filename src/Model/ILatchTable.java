package Model;

import java.util.Map;
import java.util.Set;

/**
 * Created by Dani on 1/26/2017.
 */
public interface ILatchTable<T1,T2> {

    T2 getVal(T1 key);
    void add(T1 key, T2 val);
    boolean isDefined(T1 key);
    void update(T1 key, T2 val);
    Set<T1> keySet();
    void remove(T1 key);
    int getValidAdress();
    Map<T1,T2> getContent();
    void setContent(Map<T1, T2> newHeap);

}
