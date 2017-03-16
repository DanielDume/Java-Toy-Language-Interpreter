package Model;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Created by Dani on 11/17/2016.
 */
public class openRFile implements IStmt,java.io.Serializable{

    String varFileId, filename;

    public openRFile(String varFileId, String filename) {
        this.varFileId = varFileId;
        this.filename = filename;
    }

    public String getVarFileId() {
        return varFileId;
    }

    public void setVarFileId(String varFileId) {
        this.varFileId = varFileId;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Override
    public String toStr() {
        return "openRfile("+varFileId+", "+this.filename+")";
    }
    @Override
    public String toString() {
        return "openRfile("+varFileId+", "+this.filename+")";
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException {

        MyIStack<IStmt> stk = state.getStack();
        MyIDictionary<Integer,File> fileTable = state.getFileTable();
        MyIDictionary<String,Integer> symTable = state.getTable();
        Integer validId = 0;
        for(Integer key: fileTable.keySet()){

            if (fileTable.getVal(key).getFilename() == this.filename){

                throw new StmtException("File already open");

            }
            validId = key;
        }
        validId = validId + 1;
        try{

            BufferedReader reader = new BufferedReader(new FileReader(this.filename));
            fileTable.add(validId,new File(filename,reader));
            //System.out.print(Integer.toString(validId));
            AssignStmt as = new AssignStmt(this.varFileId,new ConstExp(validId));
            as.execute(state);
            //symTable.add(this.varFileId,validId);
            System.out.print(symTable.getVal(this.varFileId));
            return null;

        }
        catch (Exception e){

            throw new StmtException(e.getMessage());

        }

        //return null;

    }
}
