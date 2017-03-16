package Model;

import java.io.*;

/**
 * Created by Dani on 12/14/2016.
 */
public class ForkStmt implements IStmt, Serializable {
    IStmt stmt;

    public ForkStmt(IStmt stmt) {
        this.stmt = stmt;
    }

    @Override
    public String toStr() {
        return "fork(" + stmt.toStr() + ")";
    }
    public String toString() {
        return "fork(" + stmt.toStr() + ")";
    }

    public static Object deepClone(Object object) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException {

        MyIStack<IStmt> stk = state.getStack();
        MyIDictionary<String, Integer> table = state.getTable();
        MyIHeap<Integer, Integer> heap = state.getHeap();
        MyLatchTable<Integer,Integer> latchTable = state.getLatchTable();
        MyIList<Integer> out = state.getOutput();
        MyIDictionary<Integer,File> fileTable = state.getFileTable();
        //System.out.print("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        try {
            Integer id = state.getId();

            IStmt forkedStmt = this.stmt;
            //System.out.print("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
            MyIStack<IStmt> newStack = new MyStack<>();
            //System.out.print("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
            MyIDictionary<String, Integer> newTable = (MyIDictionary<String, Integer>) deepClone(table);
            PrgState forkedPrg = new PrgState(newStack, newTable, out, fileTable, heap,null, latchTable, forkedStmt, state.getCurrentSonId() * 10);
            //System.out.print(forkedPrg.printAll());
            state.setCurrentSonId(state.getCurrentSonId() + 1);
            //MyIStack<IStmt> exeStack, MyIDictionary<String, Integer> symTable, MyIList<Integer> out, MyIDictionary<Integer, File> fileTable, MyIHeap<Integer,Integer> heap, IStmt originalProgram, Integer id) {
            return forkedPrg;
        }
        catch (Exception e){

            throw new StmtException(e.getMessage());

        }

    }
}
