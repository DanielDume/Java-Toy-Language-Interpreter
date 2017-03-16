package Model;

import java.io.BufferedWriter;
import java.util.Set;

/**
 * Created by Dani on 10/24/2016.
 */
public class PrgState implements java.io.Serializable{

    MyIStack<IStmt> exeStack;
    MyIDictionary <String, Integer> symTable;
    MyIList<Integer> out;
    MyIDictionary<Integer, File> fileTable;
    MyIHeap<Integer,Integer> heap;
    MyLatchTable<Integer,Integer> latchTable;
    IStmt originalProgram;
    Integer id;
    Integer currentSonId;

    public MyLatchTable<Integer, Integer> getLatchTable() {
        return this.latchTable;
    }

    public void setLatchTable(MyLatchTable<Integer, Integer> latchTable) {
        this.latchTable = latchTable;
    }




    public MyIDictionary<String, Tuple<Integer, BufferedWriter>> getWriteFileTable() {
        return writeFileTable;
    }

    public void setWriteFileTable(MyIDictionary<String, Tuple<Integer, BufferedWriter>> writeFileTable) {
        this.writeFileTable = writeFileTable;
    }

    MyIDictionary<String, Tuple<Integer,BufferedWriter>> writeFileTable;

    public PrgState(MyIStack<IStmt> exeStack, MyIDictionary<String, Integer> symTable, MyIList<Integer> out, MyIDictionary<Integer, File> fileTable, MyIHeap<Integer,Integer> heap, MyIDictionary<String, Tuple<Integer,BufferedWriter>> writeFileTable,MyLatchTable<Integer,Integer> latchTable, IStmt originalProgram, Integer id) {
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.out = out;
        this.heap = heap;
        this.fileTable = fileTable;
        this.originalProgram = originalProgram;
        this.id = id;
        this.currentSonId = id;
        this.writeFileTable = writeFileTable;
        this.latchTable = latchTable;
        exeStack.push(originalProgram);

    }

    public boolean isNotCompleted(){

        //System.out.print("STACK SIZE : " + this.exeStack.size());
        return this.exeStack.size() > 0;

    }

    public PrgState(MyIStack<IStmt> stk, MyIDictionary<String, Integer> st, MyIList<Integer> output,MyIHeap<Integer,Integer> heap, IStmt program){

        exeStack = stk;
        symTable = st;
        out = output;
        this.fileTable = fileTable;
        this.heap = heap;
        originalProgram = program;
        exeStack.push(program);

    }

    public PrgState oneStep() throws StmtException{

        if (this.exeStack.size() == 0)
            throw new StmtException("Stack is empty");
        IStmt crtStmt = this.exeStack.pop();
        return crtStmt.execute(this);

    }

    public MyIHeap getHeap(){

        return this.heap;

    }

    public MyIDictionary<Integer, File> getFileTable() {
        return fileTable;
    }

    public void setFileTable(MyIDictionary<Integer, File> fileTable) {
        this.fileTable = fileTable;
    }

    public PrgState(MyIStack<IStmt> exeStack, MyIDictionary<String, Integer> symTable, MyIList<Integer> out, MyIDictionary<Integer, File> fileTable, MyIHeap<Integer,Integer> heap, IStmt originalProgram, Integer id) {
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.out = out;
        this.heap = heap;
        this.fileTable = fileTable;
        this.originalProgram = originalProgram;
        this.id = id;
        this.currentSonId = id;
        exeStack.push(originalProgram);

    }

    public MyIStack getStack(){

        return this.exeStack;

    }

    public MyIList getOutput(){

        return this.out;

    }

    public MyIDictionary getTable(){

        return this.symTable;

    }

    public String printStack(){

        String result = "";
        if (this.exeStack.size() == 0){

            return result + "\n";

        }
        result += this.exeStack.peek().toStr()+ "\n";
        if (this.exeStack.size() == 1){

            return result + "\n";

        }
        for (int i = this.exeStack.size() - 2; i >= 0 ; i--){

            result += this.exeStack.get(i).toStr() + "\n";

        }
        return result + "\n";

    }

    public String printTable(){

        String result = "";
        Set<String> keys = this.symTable.keySet();  //get all keys
        for(String i: keys)
        {
            result += i + " = " + this.symTable.getVal(i) + "\n";
        }
        if (result.length() > 0){

            result = result.substring(0, result.length() - 1);

        }
        return result;

    }

    public String printOutput(){

        String result = "";
        int i = 0;
        for(i = 0; i < this.out.size(); i++){

            result += this.out.get(i).toString() + "\n";

        }
        if (result.length() > 0){

            result = result.substring(0, result.length() - 1);

        }
        return result;
    }

    public String printFileTable(){

        String result = "";
        Set<Integer> keys = this.fileTable.keySet();  //get all keys
        for(Integer i: keys)
        {
            result += Integer.toString(i) + " = " + this.fileTable.getVal(i) + "\n";
        }
        if (result.length() > 0){

            result = result.substring(0, result.length());

        }
        return result;

    }

    public String printHeap(){

        String result = "";
        Set<Integer> keys = this.heap.keySet();  //get all keys
        for(Integer i: keys)
        {
            result += i.toString() + " = " + this.heap.getVal(i).toString() + "\n";
        }
        if (result.length() > 0){

            result = result.substring(0, result.length() - 1);

        }
        //return "ASDF";
        return result;

    }

    public String printLatchTable(){
        String result = "";
        Set<Integer> keys = this.latchTable.keySet();  //get all keys
        for(Integer i: keys)
        {
            result += i.toString() + " = " + this.latchTable.getVal(i).toString() + "\n";
        }
        if (result.length() > 0){

            result = result.substring(0, result.length() - 1);

        }
        //return "ASDF";
        return result;
    }

    public String printAll(){

        String result = "--------------------------------------\nID : " + this.id + "\n";
        result += "STACK: \n";
        result += printStack();
        result += "TABLE: \n";
        result += printTable();
        result += "\n";
        result += "OUTPUT: \n";
        result += printOutput();
        result += "\n";
        result += "FILES: \n";
        result += printFileTable();
        result += "\n";
        result += "HEAP: \n";
        result += printHeap();
        result += "\n";
        result += "LATCHES:";
        result += printLatchTable();
        result += "\n";
        return result;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCurrentSonId() {
        return currentSonId;
    }

    public void setCurrentSonId(Integer currentSonId) {
        this.currentSonId = currentSonId;
    }
}
