package View;

import Controller.Controller;

/**
 * Created by Dani on 11/17/2016.
 */
public class RunExample extends Command{

    private Controller ctr;
    public RunExample(String key, String desc, Controller ctr){
        super(key, desc);
        this.ctr=ctr;
    }
    @Override
    public void execute() {
        try{
            ctr.allStep();
            //System.out.print("ASDFG");
        }
        catch (Exception e) {

            System.out.print(e.getMessage());
            System.exit(0);

        } //here you must treat the exceptions that can not be solved in the controller
    }

}
