package Model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Dani on 1/25/2017.
 */
public class openWFile implements IStmt {

    String filename;

    public openWFile(String filename) {
        this.filename = filename;
    }

    @Override
    public String toString() {
        return "openFile(" + filename + ")";
    }

    @Override
    public String toStr() {
        return "openFile(" + filename + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException {

        MyIStack<IStmt> stk = state.getStack();
        MyIDictionary<String, Tuple<Integer,BufferedWriter>> writeFileTable = state.getWriteFileTable();
        if(writeFileTable.isDefined(filename)){

            if(writeFileTable.getVal(filename).getFirst() == state.getId()){
                throw new StmtException("The file is already open");
            }
            else{
                stk.push(this);
            }
        }
        else {
            Lock l = new ReentrantLock();
            l.lock();
            try{
                BufferedWriter writer = new BufferedWriter(new FileWriter(filename,true));
                writeFileTable.add(filename, new Tuple<Integer, BufferedWriter>(state.getId(),writer));
            }
            catch (Exception e){
                throw new StmtException(e.getMessage());
            }
            finally {
                l.unlock();
            }
        }



        return null;
    }
}
