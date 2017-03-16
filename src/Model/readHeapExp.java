package Model;

/**
 * Created by Dani on 12/8/2016.
 */
public class readHeapExp extends Exp implements java.io.Serializable {

    String name;

    public readHeapExp (String name){

        this.name = name;

    }

    @Override
    public String toStr() {

        return "readHeap(" + name + ")";

    }
    @Override
    public String toString() {

        return "readHeap(" + name + ")";

    }
    @Override
    public int eval(MyIDictionary<String, Integer> table, MyIHeap<Integer, Integer> heap) throws ExpException {
        if(!table.isDefined(name)){

            throw new ExpException("The variable " + name + "is not defined.");

        }
        if(!heap.isDefined(table.getVal(name))){

            throw new ExpException("The variable " + name + "is not defined in the heap.");

        }
        return heap.getVal(table.getVal(name));
        //return 0;
    }
}
