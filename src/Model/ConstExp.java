package Model;

/**
 * Created by Dani on 10/24/2016.
 */
public class ConstExp extends Exp implements java.io.Serializable{

    int number;

    public ConstExp(int number){

        this.number = number;

    }

    @Override
    public String toStr() {

        return Integer.toString(number);

    }
    @Override
    public String toString() {

        return Integer.toString(number);

    }

    @Override
    public int eval(MyIDictionary<String, Integer> table, MyIHeap<Integer,Integer> heap) {

        return number;

    }

}
