package Model;

/**
 * Created by Dani on 10/24/2016.
 */
public interface IStmt {

    String toStr();
    PrgState execute(PrgState state) throws StmtException;

}
