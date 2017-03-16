package Repository;

import Model.PrgState;
import javafx.collections.FXCollections;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dani on 11/1/2016.
 */
public class Repository implements IRepository {

    List<PrgState> l;
    String logFile;
    String serializeFile = "Program.sre";

    public Repository (PrgState p, String logFileLocation){

        this.l = new ArrayList<>();
        this.l.add(p);
        this.logFile = logFileLocation;
    }

    public FXCollections getHeapData(){

//        Set<Integer> keys = this.l.get(0).getHeap().keySet();  //get all keys
//
//        for(Integer i: keys)
//        {
//            result += i.toString() + " = " + this.heap.getVal(i).toString() + "\n";
//        }
//        if (result.length() > 0){
//
//            result = result.substring(0, result.length() - 1);
//
//        }
        return null;

    }

    public void serialize() {
        ObjectOutputStream out = null;
        try{
            out = new ObjectOutputStream(new FileOutputStream("programState.mobj"));
            out.writeObject(this.l);
        } catch (IOException e) {
            System.err.println("Error: " + e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    System.err.println("Error: " + e);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void deserialize() {
        ObjectInputStream in = null;
        try{
            in = new ObjectInputStream(new FileInputStream("programState.mobj"));
            this.l = (List<PrgState>)in.readObject();
        } catch (IOException e) {
            System.err.println("Error: " + e);
        } catch (ClassNotFoundException e) {
            System.err.println("Deserialization Error: " + e);
        } finally {
            if (in != null){
                try {
                    in.close();
                } catch (IOException e) {System.err.println("Error: " + e);}
            }
        }
    }

    @Override
    public List<PrgState> getPrgList() {
        return this.l;
    }

    @Override
    public void setPrgList(List<PrgState> l) {
        this.l = l;
    }

    @Override
    public PrgState getPrg() {

        return l.get(0);

    }

    @Override
    public String logPrgState(PrgState p) {

        try {
            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(this.logFile, true)));
            logFile.append(p.printAll());
            logFile.flush();
            logFile.close();
            return p.printAll();
        }
        catch (IOException e) {

            System.out.print(e.getMessage());
        }
        return null;
    }
}
