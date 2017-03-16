package View;

import Controller.Controller;

/**
 * Created by Dani on 12/8/2016.
 */
public class serializeCommand extends Command {
    private Controller c;

    public serializeCommand(String key, String description, Controller c) {
        super(key, description);
        this.c = c;
    }

    @Override
    public void execute() {
        try {
            c.serializeRepo();
        }
        catch (Exception e){

            System.out.print("Serialization error");

        }

    }
}
