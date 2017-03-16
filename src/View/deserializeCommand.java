package View;

import Controller.Controller;

/**
 * Created by Dani on 12/8/2016.
 */
public class deserializeCommand extends Command {

    private Controller c;

    public deserializeCommand(String key, String description, Controller c) {
        super(key, description);
        this.c = c;
    }

    @Override
    public void execute() {
        try {
            c.deserializeRepo();
        }
        catch (Exception e){

            System.out.print("Deserialization error");

        }

    }

}
