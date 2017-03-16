package View;

import Controller.Controller;
import Model.*;
import Repository.Repository;

/**
 * Created by Dani on 11/17/2016.
 */
public class Interpreter {

    public static void main(String[] args) {
        ConstExp e = new ConstExp(190), e2 = new ConstExp(10);
        MyList<Integer> l = new MyList<>();
        MyDictionary<String ,Integer> table = new MyDictionary<>();
        MyDictionary<Integer, File> fileTable = new MyDictionary<>();
        MyStack<IStmt> s = new MyStack<>();
        MyHeap<Integer,Integer> heap = new MyHeap<>();
        MyList<Integer> lFrk = new MyList<>();
        MyDictionary<String ,Integer> tableFrk = new MyDictionary<>();
        MyDictionary<Integer, File> fileTableFrk = new MyDictionary<>();
        MyStack<IStmt> sFrk = new MyStack<>();
        MyHeap<Integer,Integer> heapFrk = new MyHeap<>();
        MyList<Integer> l2 = new MyList<>();
        MyDictionary<String ,Integer> table2 = new MyDictionary<>();
        MyDictionary<Integer, File> fileTable2 = new MyDictionary<>();
        MyStack<IStmt> s2 = new MyStack<>();
        MyList<Integer> l3 = new MyList<>();
        MyDictionary<String ,Integer> table3 = new MyDictionary<>();
        MyDictionary<Integer, File> fileTable3 = new MyDictionary<>();
        MyStack<IStmt> s3 = new MyStack<>();
        AssignStmt st = new AssignStmt("a", new ConstExp(10));
        AssignStmt st2 = new AssignStmt("b", new ConstExp(100));
        IStmt ex1 = new CompStmt(new ForkStmt(new CompStmt(new AssignStmt("G",new ConstExp(1234)), new PrintStmt(new ConstExp(9876)))),new CompStmt(new openRFile("f","D:/t.txt"),new CompStmt(new CompStmt(new PrintStmt(new ConstExp(10)), new AssignStmt("a",new ConstExp(1234))), new newStmt("b",new ConstExp(100)))));
        CompStmt cst = new CompStmt(new AssignStmt("a", new ConstExp(10)), new AssignStmt("b", new ConstExp(100)));
        IStmt ex = new CompStmt(new AssignStmt("a", new ArithExp(new ConstExp(2), new ArithExp(new ConstExp(3), new ConstExp(5),'*'),'+')), new AssignStmt("b", new ArithExp(new VarExp("a"), new ConstExp(1), '+')));
        IStmt ex2 = new IfStmt(new boolExp(new ConstExp(100), new ConstExp(10), "<"),new AssignStmt("a", new ArithExp(new ConstExp(2), new ArithExp(new ConstExp(3), new ConstExp(5),'*'),'+')), new PrintStmt(new VarExp("b")));
        IStmt ex3 = new CompStmt(new CompStmt(new CompStmt(new CompStmt(new CompStmt(ex,ex2),new newStmt("c",new ConstExp(10))),new writeHeapStmt("c",new ConstExp(99))),new PrintStmt(new readHeapExp("c"))),new AssignStmt("c",new ConstExp(100)) );
        //IStmt ex4 = new CompStmt(new CompStmt(new CompStmt(new openRFile("a","D:/t.txt"), new readFile(new VarExp("a"),"b")), new readFile(new VarExp("a"),"b")),new closeRFile(new VarExp("a")));
        IStmt ex4 = new CompStmt(new newStmt("a",new ConstExp(1)), new newStmt("b", new ConstExp(100)));
        IStmt ex5 = new CompStmt(new AssignStmt("a", new ConstExp(1)), new whileStmt(new boolExp(new VarExp("a"), new ConstExp(10), "<="),new CompStmt(new AssignStmt("a", new ArithExp(new VarExp("a"),new ConstExp(1),'+')),new PrintStmt(new VarExp("a")))));
        IStmt exFrk = new CompStmt(new CompStmt(new PrintStmt(new ConstExp(2)),new ForkStmt(new CompStmt(new PrintStmt(new ConstExp(1)),new PrintStmt(new ConstExp(4))))), new PrintStmt(new ConstExp(3)));
        IStmt exFrk2 = new CompStmt(new CompStmt(new CompStmt(new CompStmt(new CompStmt(new CompStmt(new CompStmt(new AssignStmt("a", new ConstExp(10)),new CompStmt(new newStmt("b", new ConstExp(10)),new ForkStmt(new CompStmt(new PrintStmt(new readHeapExp("b")), new CompStmt(new AssignStmt("a",new ConstExp(99)),new PrintStmt(new VarExp("a"))))))),new PrintStmt(new VarExp("a"))), new AssignStmt("c", new ConstExp(101))),new AssignStmt("d", new ConstExp(20))),new newStmt("e",new ConstExp(1234))), new PrintStmt(new VarExp("a"))),new PrintStmt(new VarExp("a")));
        PrgState p = new PrgState(s,table,l,fileTable,heap,ex4,1);
        PrgState p2 = new PrgState(s2,table2,l2,fileTable2,heap,ex5,1);
        PrgState p3 = new PrgState(s3,table3,l3,fileTable3,heap,ex3,1);
        PrgState pFrk = new PrgState(sFrk,tableFrk,lFrk,fileTableFrk,heapFrk,exFrk2,1);
        Repository r = new Repository(p,"D:/test1.txt");
        Repository r2 = new Repository(p2,"D:/test2.txt");
        Repository r3 = new Repository(p3,"D:/test3.txt");
        Repository rFrk = new Repository(pFrk,"D:/testFrk.txt");
        Controller c = new Controller(r);
        Controller c2 = new Controller(r2);
        Controller c3 = new Controller(r3);
        Controller cFrk = new Controller(rFrk);
        //c.allStep();
        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExample("1", ex4.toStr(),c));
        //System.out.print();
        menu.addCommand(new RunExample("2",ex5.toStr(),c2));
        menu.addCommand(new RunExample("3",ex3.toStr(),c3));
        menu.addCommand(new RunExample("4",exFrk2.toStr(),cFrk));
        menu.addCommand(new serializeCommand("5","Serialize ex3",c3));
        menu.addCommand(new deserializeCommand("6","Deserialize ex3",c3));
        menu.show();
    }

}
