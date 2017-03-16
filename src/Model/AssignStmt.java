package Model;

/**
 * Created by Dani on 11/1/2016.
 */
public class AssignStmt implements IStmt,java.io.Serializable {

    String name;
    Exp exp;

    public AssignStmt(String name, Exp exp){

        this.name = name;
        this.exp = exp;

    }

    @Override
    public String toStr() {

        return name + " = " + exp.toStr();

    }
    @Override
    public String toString() {

        return name + " = " + exp.toStr();

    }

    @Override
    public PrgState execute(PrgState state) throws StmtException {

        MyIStack<IStmt> stk = state.getStack();
        MyIDictionary<String, Integer> table = state.getTable();
        MyIHeap<Integer, Integer> heap = state.getHeap();
        try {
            int temp = exp.eval(table,heap);
            if (table.isDefined(this.name)) {

                table.update(this.name, temp);

            } else {

                table.add(this.name, temp);

            }
            return null;
        }

        catch (ExpException e){

            throw new StmtException(e.getMes());

        }

    }
}
