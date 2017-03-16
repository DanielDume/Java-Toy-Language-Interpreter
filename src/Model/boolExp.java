package Model;

/**
 * Created by Dani on 12/8/2016.
 */
public class boolExp extends Exp implements java.io.Serializable{
    Exp exp1,exp2;
    String operator;

    public boolExp(Exp exp1, Exp exp2, String operator) {
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.operator = operator;
    }

    @Override
    public int eval(MyIDictionary<String, Integer> table, MyIHeap<Integer, Integer> heap) throws ExpException {
        switch (operator){

            case "<" : return (exp1.eval(table,heap) < exp2.eval(table,heap))? 1 : 0;
            case "<=" : return (exp1.eval(table,heap) <= exp2.eval(table,heap))? 1 : 0;
            case "==" : return (exp1.eval(table,heap) == exp2.eval(table,heap))? 1 : 0;
            case "!=" : return (exp1.eval(table,heap) != exp2.eval(table,heap))? 1 : 0;
            case ">" : return (exp1.eval(table,heap) > exp2.eval(table,heap))? 1 : 0;
            case ">=" : return (exp1.eval(table,heap) >= exp2.eval(table,heap))? 1 : 0;

        }
        return 0;
    }

    @Override
    public String toStr() {
        return "(" + exp1.toStr() + operator + exp2.toStr() + ")";
    }
    @Override
    public String toString() {
        return "(" + exp1.toStr() + operator + exp2.toStr() + ")";
    }
}

