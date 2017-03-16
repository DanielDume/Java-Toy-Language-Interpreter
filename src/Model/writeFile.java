package Model;

import java.io.BufferedWriter;

/**
 * Created by Dani on 1/25/2017.
 */
public class writeFile implements IStmt {

    String filename;
    Exp exp;

    public writeFile(String filename, Exp exp) {
        this.filename = filename;
        this.exp = exp;
    }

    @Override
    public String toStr() {
        return "writeFile(" + this.filename + "," + this.exp.toStr();
    }

    @Override
    public String toString() {
        return "writeFile(" + this.filename + "," + this.exp.toStr();
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException {

        MyIStack<IStmt> stk = state.getStack();
        MyIDictionary<Integer,File> fileTable = state.getFileTable();
        MyIDictionary<String,Integer> symTable = state.getTable();
        MyIHeap<Integer, Integer> heap = state.getHeap();
        MyIDictionary<String, Tuple<Integer,BufferedWriter>> writeFileTable = state.getWriteFileTable();
        try {

            Integer res = this.exp.eval(symTable, heap);
            if (writeFileTable.isDefined(this.filename) && writeFileTable.getVal(filename).getFirst() == state.getId()){
                writeFileTable.getVal(filename).getSecond().write(res.toString());
            }
            else{
                throw new StmtException("File not opened in this thread!");
            }

        }
        catch (Exception e){
            throw new StmtException("File not opened in this thread!");
        }
        return null;
    }
}
