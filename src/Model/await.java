package Model;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Dani on 1/26/2017.
 */
public class await implements IStmt {
    String varName;

    public await(String varName) {
        this.varName = varName;
    }

    @Override

    public String toString() {
        return "await(" + varName + ")";
    }

    @Override
    public String toStr() {
        return "await(" + varName + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException {

        MyIStack<IStmt> stk = state.getStack();
        MyIDictionary<String, Integer> table = state.getTable();
        MyIHeap<Integer, Integer> heap = state.getHeap();
        MyLatchTable<Integer,Integer> latchTable = state.getLatchTable();
        MyIList<Integer> out = state.getOutput();

//        if ( ! table.isDefined(varName)){
//            throw new StmtException("Latch not defined");
//        }
//        Lock l = new ReentrantLock();
//        l.lock();
//        try{
//            if ( ! latchTable.isDefined(table.getVal(varName)) ){
//                throw new StmtException("Latch not defined");
//            }
//            latchTable.update(table.getVal(varName), latchTable.getVal(table.getVal(varName)) - 1);
//            out.add(state.getId());
//        }
//        catch (Exception e){
//            throw new StmtException(e.getMessage())
//        }
//        finally {
//            l.unlock();
//        }
        if ( ! table.isDefined(varName)){
            throw new StmtException("Latch not defined");
        }
        Lock l = new ReentrantLock();
        l.lock();
        try{
            if ( ! latchTable.isDefined(table.getVal(varName)) ){
                throw new StmtException("Latch not defined");
            }
            if (latchTable.getVal(table.getVal(varName)) != 0){
                stk.push(this);
            }
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
