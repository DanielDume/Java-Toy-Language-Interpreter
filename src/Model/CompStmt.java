package Model;

/**
 * Created by Dani on 10/24/2016.
 */
public class CompStmt implements IStmt,java.io.Serializable {

    IStmt first;
    IStmt second;

    public CompStmt(IStmt first, IStmt second){

        this.first = first;
        this.second = second;

    }

    @Override
    public String toStr() {

        return "(" + first.toStr() + "; " + second.toStr() + ")";

    }

    @Override
    public PrgState execute(PrgState state) throws StmtException {

        MyIStack<IStmt> stk = state.getStack();
        stk.push(second);
        stk.push(first);
        return null;

    }

    @Override
    public String toString() {
        return "(" + first.toStr() + "; " + second.toStr() + ")";
    }
}
