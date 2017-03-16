package Model;

import java.util.Locale;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Dani on 1/26/2017.
 */
public class newLatch implements IStmt {
    String latchName;
    Exp latchCount;

    @Override
    public String toString() {
        return "newLatch(" + latchName +"," + latchCount.toStr() + ")";
    }

    @Override
    public String toStr() {
        return "newLatch(" + latchName +"," + latchCount.toStr() + ")";
    }

    public newLatch(String latchName, Exp latchCount) {
        this.latchName = latchName;
        this.latchCount = latchCount;
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException {

        MyIStack<IStmt> stk = state.getStack();
        MyIDictionary<String, Integer> table = state.getTable();
        MyIHeap<Integer, Integer> heap = state.getHeap();
        MyLatchTable<Integer,Integer> latchTable = state.getLatchTable();
        AssignStmt as = new AssignStmt(latchName, new ConstExp(latchTable.getValidAdress()));
        as.execute(state);
        Lock l = new ReentrantLock();
        l.lock();
        try {

            latchTable.add(latchTable.getValidAdress(), latchCount.eval(table, heap));

        }
        catch (ExpException e){

            throw new StmtException(e.getMes());

        }
        finally {
            l.unlock();
        }
        return null;
    }
}
