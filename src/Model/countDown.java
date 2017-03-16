package Model;

import javax.swing.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Dani on 1/26/2017.
 */
public class countDown implements IStmt {
    String varName;

    public countDown(String varName) {
        this.varName = varName;
    }

    @Override
    public String toString() {
        return "countDown(" + varName + ")";
    }

    @Override
    public String toStr() {
        return "countDown(" + varName + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException {

        //System.out.println("ENTERED Countdown");
        MyIStack<IStmt> stk = state.getStack();
        MyIDictionary<String, Integer> table = state.getTable();
        MyIHeap<Integer, Integer> heap = state.getHeap();
        MyLatchTable<Integer,Integer> latchTable = state.getLatchTable();
        MyIList<Integer> out = state.getOutput();

        if ( ! table.isDefined(varName)){
            //System.out.print("HERE2");
            JOptionPane.showMessageDialog(null, "Latch not available", "Error",
                    JOptionPane.ERROR_MESSAGE);
            throw new StmtException("Latch not defined");
        }
        Lock l = new ReentrantLock();
        l.lock();
        try{
            if ( ! latchTable.isDefined(table.getVal(varName)) ){
                //System.out.print("HERE");
                JOptionPane.showMessageDialog(null, "Latch not available", "Error",
                        JOptionPane.ERROR_MESSAGE);
                throw new StmtException("Latch not defined");
            }
            if (latchTable.getVal(table.getVal(varName)) > 0) {
                latchTable.update(table.getVal(varName), latchTable.getVal(table.getVal(varName)) - 1);
                out.add(state.getId());
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
