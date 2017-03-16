package Model;

/**
 * Created by Dani on 12/8/2016.
 */
public class whileStmt implements IStmt,java.io.Serializable {
    Exp exp;
    IStmt statement;

    public whileStmt(Exp exp, IStmt statement) {
        this.exp = exp;
        this.statement = statement;
    }

    @Override
    public String toStr() {
        return "while(" + exp.toStr() + ")" + statement.toStr();
    }
    public String toString() {
        return "while(" + exp.toStr() + ")" + statement.toStr();
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException {

        MyIStack<IStmt> stk = state.getStack();
        MyIDictionary<String, Integer> table = state.getTable();
        MyIHeap<Integer, Integer> heap = state.getHeap();
        try {

            if (exp.eval(table, heap) != 0){

                stk.push(new whileStmt(exp,statement));
                stk.push(statement);

            }
        }
        catch (ExpException e){

            throw new StmtException(e.getMes());

        }
        return null;
    }
}
