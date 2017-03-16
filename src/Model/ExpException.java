package Model;

/**
 * Created by Dani on 11/2/2016.
 */
public class ExpException extends Exception {

    String mes;

    public ExpException(String s){

        mes = s;

    }

    public String getMes(){

        return this.mes;

    }

}
