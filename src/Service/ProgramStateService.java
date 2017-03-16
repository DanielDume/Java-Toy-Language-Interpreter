package Service;
/**
 * Created by Dani on 1/19/2017.
 */

import Model.PrgState;
import Model.Tuple;
import Repository.IRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

public class ProgramStateService implements Utils.Observable<PrgState>
{

    protected List<Utils.Observer<PrgState>> observers = new ArrayList<Utils.Observer<PrgState>>();
    private IRepository repo;

    public ProgramStateService(IRepository repo)
    {
        this.repo=repo;
    }

    public IRepository getRepo()
    {
        return this.repo;
    }

    public List<PrgState> getAll()
    {
        List<PrgState> mlist= new ArrayList<PrgState>();
        mlist.addAll(this.repo.getPrgList());
        return mlist;
    }

    public List<String> getOutList()
    {
        List<String> mlist= new ArrayList<String>();
        for(int i=0 ; i<this.repo.getPrgList().get(0).getOutput().size(); ++i)
            mlist.add(String.valueOf(this.repo.getPrgList().get(0).getOutput().get(i)));
        return mlist;
    }

    public List<Tuple<Integer,String>> getFileList() throws Exception
    {
        List<Tuple<Integer,String>> mlist=new ArrayList<>();
        for(Integer x : this.repo.getPrgList().get(0).getFileTable().keySet())
            mlist.add(new Tuple(x,this.repo.getPrgList().get(0).getFileTable().getVal(x).getFileid().toString()));
        return mlist;

    }

    public List<Tuple<String, Integer>> getWriteFileList(){
        List<Tuple<String ,Integer>> wList = new ArrayList<>();
        for(String key : this.repo.getPrgList().get(0).getWriteFileTable().keySet()){
            wList.add(new Tuple<String, Integer>(key,this.repo.getPrgList().get(0).getWriteFileTable().getVal(key).getFirst()));
        }
        return wList;
    }

    public List<Tuple<String,Integer>> getLatchTable(){

        List<Tuple<String ,Integer>> newL = new ArrayList<>();
        for(Integer key : this.repo.getPrgList().get(0).getLatchTable().keySet()){
            newL.add(new Tuple<String, Integer>(key.toString(), this.repo.getPrgList().get(0).getLatchTable().getVal(key)));
        }
        return newL;

}

    public List<Map.Entry<Integer,Integer>> getHeapList()
    {
        //System.out.println(repo.getPrgList().get(0).getHeap().getmap().entrySet());
        return new ArrayList<Map.Entry<Integer, Integer>>(repo.getPrgList().get(0).getHeap().getContent().entrySet());
    }

    @Override
    public void addObserver(Utils.Observer <PrgState> o)
    {
        observers.add(o);
    }

    @Override
    public void removeObserver(Utils.Observer <PrgState> o)
    {
        observers.remove(o);
    }

    @Override
    public void notifyObservers()
    {
        for(Utils.Observer o:observers)
            o.update(this);
    }
}
