package sample;

import Model.IStmt;
import Model.PrgState;
import Model.Tuple;
import Repository.IRepository;
import Service.ProgramStateService;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import Model.*;

import java.awt.Label;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * Created by Dani on 1/19/2017.
 */
public class ControllerGUI implements Utils.Observer<PrgState>{
    @FXML
    private javafx.scene.control.Label prgStateCnt;
    //@FXML
    //private Label prgStatesCnt;
    @FXML
    private TableView<Map.Entry<Integer, Integer>> heapTableView;
    @FXML
    private ListView<String> outListView;
    @FXML
    private TableView<Tuple<Integer, String>> fileTableView;
    @FXML
    private ListView<PrgState> prgStateListView;
    @FXML
    private javafx.scene.control.Label prgIDLabel;
    @FXML
    private TableView<Tuple<String, Integer>> symTableView;
    @FXML
    private ListView<IStmt> exeStackListView;
    @FXML
    private TableView<Tuple<String, Integer>> writeFileView;
    @FXML
    private TableView<Tuple<String,Integer>> newListView;
    @FXML
    javafx.scene.control.Label labelCurrentNumber;
    @FXML
    javafx.scene.control.Label selectedPrg;

    private IRepository repo;
    private ObservableList<PrgState> prgStateModel;
    private ObservableList<String> outListModel;
    private ObservableList<Map.Entry<Integer, Integer>> heapTableModel;
    private ObservableList<Tuple<Integer, String>> fileTableModel;
    private ObservableList<IStmt> exeStackModel;
    private ExecutorService executor;
    private ProgramStateService prgStateService;
    private ObservableList<Tuple<String, Integer>> symTableModel;
    private ObservableList<Tuple<String,Integer>> writeFileModel;
    private ObservableList<Tuple<String,Integer>> newListModel;


    public ControllerGUI() {
        this.prgIDLabel = new javafx.scene.control.Label();
    }

    @FXML
    private void initialize() {
    }



    public void setService(ProgramStateService prgStateService) {
        this.prgStateService = prgStateService;
        this.repo = this.prgStateService.getRepo();
        this.labelCurrentNumber.setText("Nr of prgStates : 1");
        this.selectedPrg.setText("Currently selected prgState ID : 1");
        this.prgStateCnt = new javafx.scene.control.Label("");
        this.prgStateCnt.textProperty().bind(new SimpleStringProperty("1234"));
        //this.prgStateCnt = new Label(new String("9"));
        //this.prgStateCnt = new Label(new String("9"));
        SimpleStringProperty sPr = new SimpleStringProperty("4");
        this.prgIDLabel.textProperty().bind(sPr);
        //this.prgIDLabel.setText("123");
        sPr.set("7890");
        //prgIDLable.textProperty().bind(new SimpleIntegerProperty(integer).asString());

        //prgstate model
        this.prgStateModel = FXCollections.observableArrayList();
        this.prgStateListView.setItems(this.prgStateModel);
        this.prgStateListView.setCellFactory(new Callback<ListView<PrgState>, ListCell<PrgState>>() {
            @Override
            public ListCell<PrgState> call(ListView<PrgState> param) {
                ListCell<PrgState> listCell = new ListCell<PrgState>() {
                    @Override
                    protected void updateItem(PrgState e, boolean empty) {
                        super.updateItem(e, empty);
                        if (e == null || empty)
                            setText("");
                        else
                            setText(String.valueOf(e.getId()));
                    }
                };
                return listCell;
            }


        });

        //heapTableView
        this.heapTableModel = FXCollections.observableArrayList();
        TableColumn<Map.Entry<Integer, Integer>, String> first = new TableColumn<>("Address");
        TableColumn<Map.Entry<Integer, Integer>, String> second = new TableColumn<>("Value");
        first.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<Integer, Integer>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<Integer, Integer>, String> param) {
                return new SimpleStringProperty(String.valueOf(param.getValue().getKey()));
            }
        });

        second.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<Integer, Integer>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<Integer, Integer>, String> param) {
                return new SimpleStringProperty(String.valueOf(param.getValue().getValue()));
            }
        });

        this.heapTableView.getColumns().setAll(first, second);
        this.heapTableView.setItems(this.heapTableModel);


        //fileTableView
        this.fileTableModel = FXCollections.observableArrayList();
        TableColumn<Tuple<Integer, String>, String> fd = new TableColumn<>("File descriptor:");
        TableColumn<Tuple<Integer, String>, String> fn = new TableColumn<>("File name");
        fd.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Tuple<Integer, String>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Tuple<Integer, String>, String> param) {
                return new SimpleStringProperty(String.valueOf(param.getValue().getFirst()));
            }
        });

        fn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Tuple<Integer, String>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Tuple<Integer, String>, String> param) {
                return new SimpleStringProperty(String.valueOf(param.getValue().getSecond()));
            }
        });

        this.fileTableView.getColumns().setAll(fd, fn);
        this.fileTableView.setItems(this.fileTableModel);

        TableColumn<Tuple<String, Integer>, String> symNameColumn = new TableColumn<>("Symbol name");
        TableColumn<Tuple<String, Integer>, String> symValueColumn = new TableColumn<>("Value");
        symNameColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Tuple<String, Integer>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Tuple<String, Integer>, String> param) {
                return new SimpleStringProperty(param.getValue().getFirst());
            }
        });
        symValueColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Tuple<String, Integer>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Tuple<String, Integer>, String> param) {
                return new SimpleStringProperty(String.valueOf(param.getValue().getSecond()));
            }
        });
        this.symTableView.getColumns().setAll(symNameColumn, symValueColumn);
        this.symTableModel = FXCollections.observableArrayList();
        this.symTableView.setItems(this.symTableModel);

        //newListView
        this.newListModel = FXCollections.observableArrayList();
        TableColumn<Tuple<String ,Integer>, String> stringColumn = new TableColumn<>("Latch Id");
        TableColumn<Tuple<String ,Integer>, String> integerColumn = new TableColumn<>("Latch Counter");
        stringColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Tuple<String, Integer>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Tuple<String, Integer>, String> param) {
                return new SimpleStringProperty(param.getValue().getFirst());
            }
        });
        integerColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Tuple<String, Integer>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Tuple<String, Integer>, String> param) {
                return new SimpleStringProperty(Integer.toString(param.getValue().getSecond()));
            }
        });
        this.newListView.getColumns().setAll(stringColumn,integerColumn);
        this.newListView.setItems(this.newListModel);

        this.writeFileModel = FXCollections.observableArrayList();
        TableColumn<Tuple<String,Integer>, String> fileColumn = new TableColumn<>("File");
        TableColumn<Tuple<String,Integer>, String> stateColumn = new TableColumn<>("Owner state");
        fileColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Tuple<String, Integer>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Tuple<String, Integer>, String> param) {
                return new SimpleStringProperty(param.getValue().getFirst());
            }
        });
        stateColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Tuple<String, Integer>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Tuple<String, Integer>, String> param) {
                return new SimpleStringProperty(Integer.toString(param.getValue().getSecond()));
            }
        });
        this.writeFileView.getColumns().setAll(fileColumn,stateColumn);
        this.writeFileView.setItems(this.writeFileModel);


        // outListView
        this.outListModel = FXCollections.observableArrayList();
        this.outListView.setItems(this.outListModel);

        this.prgStateListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<PrgState>() {
            @Override
            public void changed(ObservableValue<? extends PrgState> observable, PrgState oldValue, PrgState newValue) {
                System.out.print("Entered Change Fork");
                if (newValue != null)
                {
                    System.out.print(Integer.toString( newValue.getId()));
                    selectedPrg.setText("Currently selected prgState ID : " + Integer.toString( newValue.getId()));
                    //prgIDLable = new javafx.scene.control.Label("TEXTS");
                    java.util.List<PrgState> prgStates = prgStateService.getAll();
                    //PrgState current = prgStates.stream().filter(e -> e.getId().equals(Integer.valueOf(prgIDLable.getText()))).findFirst().get();
                    PrgState current = newValue;
                    //PrgState current = newValue;

                    //java.util.List<IStmt> list = (java.util.List)current.getStack().toStack().stream().collect(Collectors.toList());
                    java.util.List<IStmt> list = new java.util.ArrayList<IStmt>();
                    for (int j = 0; j < current.getStack().size(); j++){
                        list.add(((IStmt) current.getStack().get(j)));
                    }
                    Collections.reverse(list);
                    exeStackModel.setAll(list);
                    symTableModel.clear();
                    for (Object key : current.getTable().keySet()){

                        symTableModel.add(new Tuple<String,Integer>((String)key, ((Integer) current.getTable().getVal(key))));

                    }
                }
            }
        });

        // exeStack
        this.exeStackModel = FXCollections.observableArrayList();
        this.exeStackListView.setItems(this.exeStackModel);

        this.update(this.prgStateService);

    }

    Map<Integer, Integer> conservativeGarbageCollector(Collection<Integer> symTableValues, Map<Integer, Integer> heap) {
        return heap.entrySet()
                .stream()
                .filter(e -> symTableValues.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }


    public java.util.List<PrgState> removeCompletedPrg(java.util.List<PrgState> prgStateList) {
        return prgStateList.stream()
                .filter(p -> p.isNotCompleted())
                .collect(Collectors.toList());
    }


    public void setMain(PrgState state) {
        this.repo.getPrgList().clear();
        this.repo.getPrgList().add(state);
    }


    public void serialize() {
        this.repo.serialize();
    }

    public void deserializa() {
        this.repo.deserialize();
    }

    public void oneStepForAllPrg(java.util.List<PrgState> prgList) throws InterruptedException {
        /// Log the states before the execution
        prgList.forEach(prg -> {
            try {
                repo.logPrgState(prg);
            } catch (Exception e) {
                e.getMessage();
            }
        });

        java.util.List<Callable<PrgState>> callList = prgList.stream()
                .map((PrgState p) -> (Callable<PrgState>) (() -> {
                    return p.oneStep();
                }))
                .collect(Collectors.toList());

        java.util.List<PrgState> newPrgList = executor.invokeAll(callList).stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    return null;
                })
                .filter(p -> p != null)
                .collect(Collectors.toList());

        prgList.addAll(newPrgList);
        prgList.forEach(prg -> {
            try {
                repo.logPrgState(prg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        repo.setPrgList(prgList);
        this.prgStateService.notifyObservers();
    }


    public void allSteps() throws Exception {
        executor = Executors.newFixedThreadPool(2);
        while (true) {
            java.util.List<PrgState> prgList = removeCompletedPrg(this.prgStateService.getAll());
            if (prgList.size() == 0)
                break;
            oneStepForAllPrg(prgList);
        }
        executor.shutdownNow();
    }

    @FXML
    public void allStepsGUI() throws Exception {
        try{
        executor = Executors.newFixedThreadPool(2);
        while (true) {
            this.prgStateService.notifyObservers();
            java.util.List<PrgState> prglist = removeCompletedPrg(this.prgStateService.getAll());
            if (prglist.size() == 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Program end");
                alert.setHeaderText(null);
                alert.setContentText("Program is done, quit it");

                alert.showAndWait();
                //System.out.println("Finished");
                break;
            }
            oneStepForAllPrg(prglist);
            //System.out.println("One step");
            break;
        }
        executor.shutdownNow();
        }
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Exception");
            alert.setContentText(e.getMessage());

            alert.showAndWait();
        }
    }

    @Override
    public void update(Utils.Observable<PrgState> observable) {

        java.util.List<PrgState> prgstates = this.prgStateService.getAll();

        //this.prgStateCnt.setText(Integer.toString(prgstates.size()));
        //this.prgStateCnt = new javafx.scene.control.Label("ASDF");
        //SimpleStringProperty sPr = new SimpleStringProperty("4");
        //this.prgIDLabel.textProperty().bind(sPr);
        //this.prgIDLabel.setText("123");
        //sPr.set("7890");
        this.prgStateModel.setAll(prgstates);
        this.writeFileModel.setAll(this.prgStateService.getWriteFileList());
        this.outListModel.setAll(this.prgStateService.getOutList());
        this.heapTableModel.setAll(this.prgStateService.getHeapList());
        this.newListModel.setAll(this.prgStateService.getLatchTable());
        this.fileTableModel.clear();
        for (Object i : prgstates.get(0).getFileTable().keySet()){
            this.fileTableModel.add(new Tuple<>((Integer)i, prgstates.get(0).getFileTable().getVal((Integer) i).getFilename().toString()));
        }
        PrgState current = prgstates.get(0);
        java.util.List<IStmt> list = new java.util.ArrayList<IStmt>();
        for (int j = 0; j < current.getStack().size(); j++){
            list.add(((IStmt) current.getStack().get(j)));
        }
        //java.util.List<IStmt> list = current.getStack().toStack().stream().collect(Collectors.toList());
        Collections.reverse(list);
        this.exeStackModel.setAll(list);
        this.symTableModel.clear();
        for(Object K : current.getTable().keySet()){
            this.symTableModel.add(new Tuple<String,Integer>(((String) K), (Integer) current.getTable().getVal(K)));
        }
        this.labelCurrentNumber.setText("Nr of prgStates : " + this.repo.getPrgList().size());
        this.selectedPrg.setText("Currently selected prgState ID : 1") ;
        //newList

    }


}
