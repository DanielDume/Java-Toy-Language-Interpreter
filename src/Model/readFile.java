package Model;

/**
 * Created by Dani on 11/17/2016.
 */
public class readFile implements IStmt,java.io.Serializable {

    Exp varFileId;
    String varName;

    public readFile(Exp varFileId, String varName) {
        this.varFileId = varFileId;
        this.varName = varName;
    }

    public Exp getVarFileId() {
        return varFileId;
    }

    public void setVarFileId(Exp varFileId) {
        this.varFileId = varFileId;
    }

    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName = varName;
    }

    @Override
    public String toStr() {
        return "readFile("+this.varFileId.toStr()+", "+this.varName+")";
    }
    public String toString() {
        return "readFile("+this.varFileId.toStr()+", "+this.varName+")";
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException {
        MyIStack<IStmt> stk = state.getStack();
        MyIDictionary<Integer,File> fileTable = state.getFileTable();
        MyIDictionary<String,Integer> symTable = state.getTable();
        MyIHeap<Integer, Integer> heap = state.getHeap();
        String value;
        try {

            Integer id = varFileId.eval(symTable,heap);
            if(fileTable.getVal(id) == null)
                throw new StmtException("No such file available");
            //symTable.add(varName,);
            value = fileTable.getVal(id).getFileid().readLine();
            if(value.isEmpty())
                symTable.add(varName,0);
            else
                symTable.add(varName,Integer.parseInt(value));
            return null;
        }
        catch (Exception e){

            throw new StmtException("ERROR "+ e.getMessage());

        }

    }
}
