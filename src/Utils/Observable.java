package Utils;
/**
 * Created by Dani on 1/19/2017.
 */
public interface Observable<T> {
    void addObserver(Observer<T> o);
    void removeObserver(Observer<T> o);
    void notifyObservers();

}