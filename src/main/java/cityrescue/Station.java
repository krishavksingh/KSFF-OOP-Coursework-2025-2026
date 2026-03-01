package cityrescue;

public class Station {
    private String name;
    private int xCoord;
    private int yCoord;
    private int id;
    private int maxUnits;

    public Station(String _name, int _xCoord, int _yCoord, int _id) {
        name = _name;
        xCoord = _xCoord;
        yCoord = _yCoord;
        id = _id;
        maxUnits = 5;
    }

    public String getName() {
        return name;
    }

    public int getXCoord() {
        return xCoord;
    }

    public int getYCoord() {
        return yCoord;
    }

    public int getId() {
        return id;
    }

    public int getMaxUnits() {
        return maxUnits;
    }

    public void setName(String _name) {
        name = _name;
    }

    public void setXCoord(int _xCoord) {
        xCoord = _xCoord;
    }

    public void setYCoord(int _yCoord) {
        yCoord = _yCoord;
    }

    public void setId(int _id) {
        id = _id;
    }

    public void setMaxUnits(int _maxUnits) {
        maxUnits = _maxUnits;
    }
}