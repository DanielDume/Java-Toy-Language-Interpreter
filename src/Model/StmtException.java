package Model;

/**
 * Created by Dani on 11/2/2016.
 */
public class StmtException extends Exception{

        String mes;

        public StmtException(String s){

            mes = s;

        }

        public String getMes(){

            return this.mes;

        }

}
