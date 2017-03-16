package sample;


import Controller.Controller;
import Model.*;
import Repository.IRepository;
import Repository.Repository;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableIntegerArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainPrev extends Application {

    private TableView HeapTable = new TableView();
    private ObservableList<HeapGUI> HeapData =
            FXCollections.observableArrayList(
                    new HeapGUI(1,2),
                    new HeapGUI(2,3)
            );

    public void populateHeapData(Controller c){

        HeapData = FXCollections.observableArrayList(new HeapGUI(9,9));


    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        MyIStack<IStmt> s1 = new MyStack<>();
        MyIDictionary<String,Integer> table1 = new MyDictionary<>();
        MyIList<Integer> l1 = new MyList<>();
        MyIDictionary<Integer,File> fileTable1 = new MyDictionary<>();
        MyIHeap<Integer,Integer> heap1 = new MyHeap<>();
        IStmt ex1 = new CompStmt(new newStmt("a",new ConstExp(1)), new newStmt("b", new ConstExp(100)));
        PrgState p1 = new PrgState(s1,table1,l1,fileTable1,heap1,ex1,1);
        IRepository r1 = new Repository(p1,"D:/guiLog1.txt");
        Controller c1 = new Controller(r1);
        ObservableIntegerArray nrPrgStates = FXCollections.observableIntegerArray(c1.getNrPrgStates());
        Text nrPrg = new Text(Integer.toString(nrPrgStates.get(0)));
        HBox root = new HBox(5);

        TableColumn adress = new TableColumn("adress");
        adress.setCellValueFactory(
                new PropertyValueFactory<>("adress"));
        TableColumn value = new TableColumn("value");
        value.setCellValueFactory(
                new PropertyValueFactory<>("value"));

        HeapTable.setEditable(true);
        HeapTable.setItems(HeapData);
        HeapTable.getColumns().addAll(adress, value);

        final Button addButton = new Button("Add");
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                HeapData.add(new HeapGUI(
                        9,9
                ));
            }
        });

        root.getChildren().add(new Label("Nr of prgStates: "));
        root.getChildren().add(nrPrg);
        root.getChildren().add(HeapTable);
        root.getChildren().add(addButton);
        Scene scene = new Scene(root, 500, 500);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Examples");
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
