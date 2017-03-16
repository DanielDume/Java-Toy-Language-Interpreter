package Model;

import java.io.BufferedWriter;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Dani on 1/25/2017.
 */
public class closeWFile implements IStmt {

    String filename;

    public closeWFile(String filename) {
        this.filename = filename;
    }

    @Override
    public String toStr() {
        return "closeWFile(" + filename + ")";
    }

    @Override
    public String toString() {
        return "closeWFile(" + filename + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException {

        MyIDictionary<String, Tuple<Integer,BufferedWriter>> writeFileTable = state.getWriteFileTable();
        Lock l = new ReentrantLock();
        l.lock();
        try {
            if (writeFileTable.isDefined(this.filename) && writeFileTable.getVal(filename).getFirst() == state.getId()) {

                writeFileTable.getVal(filename).getSecond().close();
                writeFileTable.remove(filename);

            }
            else
                throw new StmtException("File not open in current thread");
        }
        catch (Exception e){
            throw new StmtException(e.getMessage());
        }
        finally {
            l.unlock();
        }
        return null;
    }
}
