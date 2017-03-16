package Model;

/**
 * Created by Dani on 12/8/2016.
 */
public class writeHeapStmt implements IStmt,java.io.Serializable {
    String varName;
    Exp exp;

    public writeHeapStmt(String varName, Exp exp) {
        this.varName = varName;
        this.exp = exp;
    }

    @Override
    public String toStr() {
        return "wH(" + varName + "," + exp.toStr() + ")";
    }
    public String toString() {
        return "wH(" + varName + "," + exp.toStr() + ")";
    }


    @Override
    public PrgState execute(PrgState state) throws StmtException {

        int adress;
        MyIDictionary<String,Integer> symTable = state.getTable();
        MyIHeap<Integer,Integer> heap = state.getHeap();
        try{
            if (!symTable.isDefined(varName)){
                throw new ExpException("The variable " + varName + "is not defined in the symTable.");
            }
            if (!heap.isDefined(symTable.getVal(varName))){
                throw new ExpException("Not a valid adress.");
            }
            heap.update(symTable.getVal(varName),exp.eval(symTable,heap));
        }
        catch (ExpException e){

            throw new StmtException(e.getMes());

        }
        return null;
    }
}
