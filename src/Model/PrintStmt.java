package Model;

/**
 * Created by Dani on 10/24/2016.
 */
public class PrintStmt implements IStmt,java.io.Serializable {

    Exp expr;

    public PrintStmt(Exp expr){

        this.expr = expr;

    }

    @Override
    public String toStr() {

        return "print(" + expr.toStr() + ")";

    }
    @Override
    public String toString() {

        return "print(" + expr.toStr() + ")";

    }

    @Override
    public PrgState execute(PrgState state) throws StmtException{

        MyIList l = state.getOutput();
        MyIDictionary d = state.getTable();
        MyIHeap<Integer, Integer> heap = state.getHeap();
        try {
            l.add(expr.eval(d,heap));
            return null;
        }
        catch (ExpException e){

            throw new StmtException(e.getMes());

        }

    }
}
