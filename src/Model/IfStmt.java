package Model;

/**
 * Created by Dani on 11/1/2016.
 */
public class IfStmt implements IStmt,java.io.Serializable {

    Exp exp;
    IStmt thenS;
    IStmt elseS;

    public IfStmt(Exp exp, IStmt thenS, IStmt elseS) {

        this.exp = exp;
        this.thenS = thenS;
        this.elseS = elseS;

    }

    @Override
    public String toStr() {

        return "if (" + exp.toStr() + ") then (" + thenS.toStr() + ") else (" + elseS.toStr() + ")";

    }
    @Override
    public String toString() {

        return "if (" + exp.toStr() + ") then (" + thenS.toStr() + ") else (" + elseS.toStr() + ")";

    }

    @Override
    public PrgState execute(PrgState state) throws StmtException{

        MyIStack<IStmt> stk = state.getStack();
        try {
            if (exp.eval(state.getTable(),state.getHeap()) != 0) {

                stk.push(thenS);

            } else {

                stk.push(elseS);

            }
            return null;
        }
        catch (ExpException e){

            throw new StmtException(e.getMes());

        }

    }
}
