package sample;


import Model.*;
import Repository.IRepository;
import Repository.Repository;
import Service.ProgramStateService;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    public static IRepository getnewrepository(IStmt prg)
    {
        MyIStack<IStmt> exestack = new MyStack<IStmt>();
        MyIDictionary<String,Integer> symtable= new MyDictionary<String,Integer>();
        MyIList<Integer> list=new MyList<Integer>();
        MyIDictionary<Integer,File> fileTable = new MyDictionary<>();
        MyIHeap<Integer,Integer> heap = new MyHeap<Integer,Integer>();
        MyIDictionary<String, Tuple<Integer,BufferedWriter>> writeFileTable = new MyDictionary<String, Tuple<Integer,BufferedWriter>>();
        MyLatchTable<Integer,Integer> latchTable = new MyLatchTable<Integer,Integer>();

        PrgState prgstate = new PrgState(exestack,symtable,list,fileTable,heap,writeFileTable,latchTable,prg,1);
        IRepository repo = new Repository(prgstate,"D:/log.txt");

        return repo;

    }


    public static void main(String [] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {

        IStmt ex1 = new CompStmt(new ForkStmt(new AssignStmt("a", new ConstExp(10))),new CompStmt(new PrintStmt(new ConstExp(12)), new PrintStmt(new VarExp("a"))));
        IStmt ex2 = new CompStmt(new CompStmt(new CompStmt(new CompStmt(new CompStmt(new CompStmt(new CompStmt(new AssignStmt("a", new ConstExp(10)),new CompStmt(new newStmt("b", new ConstExp(10)),new ForkStmt(new CompStmt(new PrintStmt(new readHeapExp("b")), new CompStmt(new AssignStmt("a",new ConstExp(99)),new PrintStmt(new VarExp("a"))))))),new PrintStmt(new VarExp("a"))), new AssignStmt("c", new ConstExp(101))),new AssignStmt("d", new ConstExp(20))),new newStmt("e",new ConstExp(1234))), new PrintStmt(new VarExp("a"))),new PrintStmt(new VarExp("a")));
        IStmt ex3 = new CompStmt(new newStmt("a",new ConstExp(1)), new newStmt("b", new ConstExp(100)));
        IStmt ex4 = new CompStmt(new AssignStmt("a", new ConstExp(1)), new whileStmt(new boolExp(new VarExp("a"), new ConstExp(10), "<="),new CompStmt(new AssignStmt("a", new ArithExp(new VarExp("a"),new ConstExp(1),'+')),new PrintStmt(new VarExp("a")))));
        IStmt ex5 = new CompStmt(new AssignStmt("a",new ConstExp(1)), new CompStmt(new newStmt("b", new ConstExp(2)), new CompStmt(new openRFile("f","D:/t.txt"), new CompStmt(new readFile(new VarExp("f"), "c"), new PrintStmt(new readHeapExp("b"))))));
        IStmt ex6 = new CompStmt(new CompStmt(new openWFile("D:/write.txt"), new writeFile("D:/write.txt", new ConstExp(100))), new closeWFile("D:/write.txt"));
        IStmt ex7 = new CompStmt(new newLatch("a",new ConstExp(2)), new CompStmt(new countDown("a"), new await("a")));
        IStmt preFork = new CompStmt(new newStmt("v1",new ConstExp(2)), new CompStmt(new newStmt("v2", new ConstExp(3)), new CompStmt(new newStmt("v3", new ConstExp(4)), new newLatch("cnt", new readHeapExp("v2")))));
        IStmt fork1 = new ForkStmt(new CompStmt(new writeHeapStmt("v1", new ArithExp(new readHeapExp("v1"), new ConstExp(10), '*')), new CompStmt(new PrintStmt(new readHeapExp("v1")), new countDown("cnt"))));
        IStmt fork2 = new ForkStmt(new CompStmt(new writeHeapStmt("v2", new ArithExp(new readHeapExp("v2"), new ConstExp(10), '*')), new CompStmt(new PrintStmt(new readHeapExp("v2")), new countDown("cnt"))));
        IStmt fork3 = new ForkStmt(new CompStmt(new writeHeapStmt("v3", new ArithExp(new readHeapExp("v3"), new ConstExp(10), '*')), new CompStmt(new PrintStmt(new readHeapExp("v3")), new countDown("qwert"))));
        IStmt main = new CompStmt(preFork, new CompStmt(fork1, new CompStmt(fork2, new CompStmt(fork3, new CompStmt(new await("cnt"), new CompStmt(new PrintStmt(new VarExp("cnt")), new CompStmt(new countDown("cnt"), new PrintStmt(new VarExp("cnt")))))))));

        List<IStmt> menu = new ArrayList<IStmt>();
        menu.add(ex1);
        menu.add(ex2);
        menu.add(ex3);
        menu.add(ex4);
        menu.add(ex5);
        menu.add(ex6);
        menu.add(ex7);
        menu.add(preFork);
        menu.add(main);
        
        VBox root=new VBox(5);
        root.getChildren().add(new Label("Choose a program: "));

        //create the listview
        ObservableList<IStmt> observableStmtList= FXCollections.observableArrayList(menu);
        ListView<IStmt> programlist=new ListView<IStmt>(observableStmtList);
        programlist.setCellFactory(new Callback<ListView<IStmt>, ListCell<IStmt>>() {
            @Override
            public ListCell<IStmt> call(ListView<IStmt> param) {
                ListCell<IStmt> listCell = new ListCell<IStmt>(){
                    @Override
                    protected void updateItem(IStmt e , boolean empty)
                    {
                        super.updateItem(e,empty);
                        if(e==null || empty)
                            setText("");
                        else
                            setText(e.toString());
                    }
                };
                return listCell;
            }
        });

        root.getChildren().add(programlist);

        Scene scene = new Scene(root,1500,400);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Examples");
        primaryStage.show();

        programlist.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<IStmt>() {
            @Override
            public void changed(ObservableValue<? extends IStmt> observable, IStmt oldValue, IStmt newValue) {

                try
                {

                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(Main.class.getResource("StandardRunWindow.fxml"));

                    VBox root= loader.load();


                    ProgramStateService prgStateService = new ProgramStateService(getnewrepository(newValue));
                    ControllerGUI ctrl=loader.getController();
                    ctrl.setService(prgStateService);
                    prgStateService.addObserver(ctrl);

                    Stage dialogStage = new Stage();
                    dialogStage.setTitle("Run dialog");
                    dialogStage.initModality(Modality.APPLICATION_MODAL);
                    dialogStage.setScene(new Scene(root));
                    dialogStage.show();
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
            }
        });

    }

}

