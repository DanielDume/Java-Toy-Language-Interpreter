package Model;

/**
 * Created by Dani on 10/24/2016.
 */
abstract class Exp {

    public abstract int eval(MyIDictionary<String, Integer> table, MyIHeap<Integer,Integer> heap) throws ExpException;
    public abstract String toStr();

}
