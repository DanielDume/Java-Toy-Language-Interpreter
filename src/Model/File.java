package Model;

import java.io.BufferedReader;

/**
 * Created by Dani on 11/17/2016.
 */
public class File implements java.io.Serializable {

    String filename;
    BufferedReader fileid;

    public File(String filename,BufferedReader fileid){

        this.filename = filename;
        this.fileid = fileid;

    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public BufferedReader getFileid() {
        return fileid;
    }

    @Override
    public String toString() {
        return "File{" +
                "filename='" + filename + '\'' +
                ", fileid=" + fileid +
                '}';
    }

    public void setFileid(BufferedReader fileid) {
        this.fileid = fileid;
    }
}
