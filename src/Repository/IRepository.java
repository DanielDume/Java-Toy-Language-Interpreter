package Repository;

import Model.PrgState;

import java.util.List;

/**
 * Created by Dani on 11/1/2016.
 */
public interface IRepository {
    PrgState getPrg();
    String logPrgState(PrgState p);
    void serialize();
    void deserialize();
    List<PrgState>  getPrgList();
    void setPrgList(List<PrgState> l);
}
