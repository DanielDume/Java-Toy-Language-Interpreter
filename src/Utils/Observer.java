package Utils;

/**
 * Created by Dani on 1/19/2017.
 */
public interface Observer<T>
{

    void update(Observable<T> observable);
}

