package Model;

import java.util.Map;
import java.util.Set;

/**
 * Created by Dani on 10/24/2016.
 */
public interface MyIDictionary<T1,T2> {

    T2 getVal(T1 key);
    void add(T1 key, T2 val);
    boolean isDefined(T1 key);
    void update(T1 key, T2 val);
    Set<T1> keySet();
    void remove(T1 key);
    Map<T1,T2> getContent();

}
