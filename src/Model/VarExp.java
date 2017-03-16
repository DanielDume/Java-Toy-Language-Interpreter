package Model;

/**
 * Created by Dani on 10/24/2016.
 */
public class VarExp extends Exp implements java.io.Serializable{

    String name;

    public VarExp (String name){

        this.name = name;

    }

    @Override
    public String toStr() {

        return name;

    }
    @Override
    public String toString() {

        return name;

    }

    @Override
    public int eval(MyIDictionary<String, Integer> table, MyIHeap<Integer,Integer> heap) throws ExpException{

        if(!table.isDefined(name)){

            throw new ExpException("The variable " + name + "is not defined.");

        }
        return table.getVal(name);

    }
}
