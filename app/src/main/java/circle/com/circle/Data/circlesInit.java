package circle.com.circle.Data;

import java.util.ArrayList;

/**
 * Created by skmishra on 12/14/2016.
 */
public class circlesInit {

    String name;
    String display;
    String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    boolean hasSubs;

    public boolean isHasSubs() {

        return hasSubs;
    }

    public void setHasSubs(boolean hasSubs) {
        this.hasSubs = hasSubs;
    }

    ArrayList<circlesInit> mArray=new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public ArrayList<circlesInit> getmArray() {
        return mArray;
    }

    public void setmArray(ArrayList<circlesInit> mArray) {
        this.mArray = mArray;
    }

    @Override
    public String toString() {
        return "\name:"+name
                +"\ndisplay:"+display;
    }
}
