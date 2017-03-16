package Model;

/**
 * Created by Dani on 12/7/2016.
 */
public class newStmt implements IStmt,java.io.Serializable {

    String varName;
    Exp exp;

    public newStmt(String varName, Exp exp) {
        this.varName = varName;
        this.exp = exp;
    }


    @Override
    public String toStr() {
        return "new(" + varName + "," + exp.toStr() + ")";
    }

    @Override
    public String toString() {
        return "new(" + varName + "," + exp.toStr() + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException {

        MyIStack<IStmt> stk = state.getStack();
        MyIDictionary<String, Integer> table = state.getTable();
        MyIHeap<Integer, Integer> heap = state.getHeap();
        AssignStmt as = new AssignStmt(varName, new ConstExp(heap.getValidAdress()));
        as.execute(state);
        try {

            heap.add(heap.getValidAdress(), exp.eval(table, heap));

        }
        catch (ExpException e){

            throw new StmtException(e.getMes());

        }
        return null;
    }
}
