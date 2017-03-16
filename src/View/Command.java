package View;

/**
 * Created by Dani on 11/17/2016.
 */
public abstract class Command {

    private String key, description;
    public Command(String key, String description) {
        this.key = key;
        this.description = description;
    }
    public abstract void execute();
    public String getKey(){return key;}
    public String getDescription(){return description;}


}
