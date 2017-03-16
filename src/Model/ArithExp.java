package Model;

/**
 * Created by Dani on 10/24/2016.
 */
public class ArithExp extends Exp implements java.io.Serializable {

    Exp e1;
    Exp e2;
    char operator;

    public ArithExp(Exp e1, Exp e2, char operator){

        this.e1 = e1;
        this.e2 = e2;
        this.operator = operator;

    }

    @Override
    public int eval(MyIDictionary<String, Integer> table, MyIHeap<Integer,Integer> heap) throws ExpException {
        switch (this.operator){

            case '+':
                return e1.eval(table, heap) + e2.eval(table, heap);
            //break;
            case '-':
                return e1.eval(table, heap) - e2.eval(table, heap);
            //break;
            case '*':
                return e1.eval(table, heap) * e2.eval(table,heap);
            //break;
            case '/':
                if (e2.eval(table, heap) == 0){

                    throw new ExpException("Division by 0");

                }
                return e1.eval(table, heap) / e2.eval(table, heap);
            //break;
            default:
                return 0;

        }

    }

    @Override
    public String toStr() {
        return e1.toStr() + operator + e2.toStr();
    }
    @Override
    public String toString() {
        return e1.toStr() + operator + e2.toStr();
    }
}
