package Controller;

import Model.IStmt;
import Model.MyIStack;
import Model.PrgState;
import Model.StmtException;
import Repository.IRepository;
import javafx.collections.FXCollections;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * Created by Dani on 11/1/2016.
 */
public class Controller {

    IRepository repo;
    ExecutorService executor;

    public int getNrPrgStates(){

        return this.repo.getPrgList().size();

    }

    public FXCollections getHeapData(){

        //return this.repo.getHeapData();
        return null;

    }

    protected Map<Integer,Integer> conservativeGarbageCollector(Collection<Integer> symTableValues, Map<Integer,Integer> heap){

        return heap.entrySet().stream().filter(e -> symTableValues.contains(e.getKey())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    }

    public Controller (IRepository r){

        this.repo = r;

    }

    public void serializeRepo(){

        this.repo.serialize();

    }
    public void deserializeRepo(){

        this.repo.deserialize();

    }

    PrgState oneStep(PrgState state) throws StmtException{

        MyIStack<IStmt> stk = state.getStack();
        if(stk.size() == 0) return null;
        IStmt crtStmt = stk.pop();
        try {
            return crtStmt.execute(state);
        }
        catch (StmtException e){
            throw new StmtException(e.getMes());
        }

    }

    List<PrgState> removeCompletedPrg (List<PrgState> inPrgList){

        return inPrgList.stream().filter(p->p.isNotCompleted()).collect(Collectors.toList());

    }

    public void oneStepForAllPrg(List<PrgState> prgList){

        prgList.forEach(prg->repo.logPrgState(prg));
        List<Callable<PrgState>> callList = prgList.stream().map((PrgState p)->(Callable<PrgState>)(()->{return p.oneStep();})).collect(Collectors.toList());
        //List<Callable<PrgState>> callList = prgList.stream().map((PrgState p)->(()->{return p.oneStep();})).collect(Collectors.toList());
        List<PrgState> newPrgList;
        try{

            newPrgList = executor.invokeAll(callList).stream()
                    .map(future->{
                        try{
                            return future.get();
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                        return null;
                    }).filter(p->p != null).collect(Collectors.toList());
            prgList.addAll(newPrgList);

        }
        catch (InterruptedException e){

            //throw new StmtException(e.getMessage());

        }
        prgList.forEach(prg->repo.logPrgState(prg));
        repo.setPrgList(prgList);

    }

    public void allStep(){

        executor = Executors.newFixedThreadPool(2);
        while (true){

            List<PrgState> prgList = removeCompletedPrg(repo.getPrgList());
            if (prgList.size() == 0){
                break;
            }
            oneStepForAllPrg(prgList);
        }
        executor.shutdownNow();

    }

    public void allStep2(){
        try {
            serializeRepo();
            deserializeRepo();
            PrgState p = this.repo.getPrg();
            this.repo.logPrgState(this.repo.getPrgList().get(0));
            MyIStack printStk;
            String result;
            /*System.out.print("STACK: ");
            System.out.print(p.printStack());
            System.out.print("TABLE: ");
            System.out.print(p.printTable());
            System.out.print("\n");
            System.out.print("OUTPUT: ");
            System.out.print(p.printOutput());
            System.out.print("\n");*/
            while (p.getStack().size() > 0) {
                oneStep(p);
                p.getHeap().setContent(conservativeGarbageCollector(p.getTable().getContent().values(), p.getHeap().getContent()));
                System.out.print(this.repo.logPrgState(this.repo.getPrgList().get(0)));
                /*System.out.print("STACK: ");
                System.out.print(p.printStack());
                System.out.print("TABLE: ");
                System.out.print(p.printTable());
                System.out.print("\n");
                System.out.print("OUTPUT: ");
                System.out.print(p.printOutput());
                System.out.print("\n");*/

            }
        }
        catch (StmtException e){

            System.out.print(e.getMes());

        }

    }

    public IRepository getRepo() {
        return repo;
    }
}
