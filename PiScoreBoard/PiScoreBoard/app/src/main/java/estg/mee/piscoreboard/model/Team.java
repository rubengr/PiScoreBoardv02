package estg.mee.piscoreboard.model;

import java.io.Serializable;

/**
 * Created by Rúben Rodrigues on 01-05-2015.
 */
public class Team implements Serializable{
    private int id;
    private String name = new String();
    private String logotipo;
    private boolean uploadstate;

    public Team() {
        this.id = 0;
        this.name = null;
        this.logotipo = null;
        this.uploadstate = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogotipo() {
        return logotipo;
    }

    public void setLogotipo(String logotipo) {
        this.logotipo = logotipo;
    }

    public boolean isUploadstate() {
        return uploadstate;
    }

    public void setUploadstate(boolean uploadstate) {
        this.uploadstate = uploadstate;
    }
}
