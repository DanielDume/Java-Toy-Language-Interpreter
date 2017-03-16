package Model;

/**
 * Created by Dani on 11/17/2016.
 */
public class closeRFile implements IStmt,java.io.Serializable {

    Exp varFIleId;

    public closeRFile(Exp varFIleId) {
        this.varFIleId = varFIleId;
    }

    public Exp getVarFIleId() {
        return varFIleId;
    }

    public void setVarFIleId(Exp varFIleId) {
        this.varFIleId = varFIleId;
    }

    @Override
    public String toStr() {
        return "closeRFile("+varFIleId.toStr()+")";
    }
    public String toString() {
        return "closeRFile("+varFIleId.toStr()+")";
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException {

        MyIStack<IStmt> stk = state.getStack();
        MyIDictionary<Integer,File> fileTable = state.getFileTable();
        MyIDictionary<String,Integer> symTable = state.getTable();
        MyIHeap<Integer,Integer> heap = state.getHeap();

        try{

            Integer id = varFIleId.eval(symTable,heap);
            fileTable.getVal(id).getFileid().close();
            fileTable.remove(id);

        }
        catch (Exception e){

            throw new StmtException(e.getMessage());

        }
        return null;

    }
}
