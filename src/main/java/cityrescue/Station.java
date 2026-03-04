package cityrescue;

/**
 * Represents a rescue station with a location and capacity.
 */
public class Station {
    private String name;
    private int xCoord;
    private int yCoord;
    private int id;
    private int maxUnits;

    /**
     * Creates a station with a name, coordinates and ID.
     */
    public Station(String _name, int _xCoord, int _yCoord, int _id) {
        name = _name;
        xCoord = _xCoord;
        yCoord = _yCoord;
        id = _id;
        maxUnits = 5;
    }

    /** Gets the station name. */
    public String getName() {
        return name;
    }

    /** Gets the X coordinate of the station. */
    public int getXCoord() {
        return xCoord;
    }

    /** Gets the Y coordinate of the station. */
    public int getYCoord() {
        return yCoord;
    }

    /** Gets the station ID. */
    public int getId() {
        return id;
    }

    /** Gets the maximum number of units allowed. */
    public int getMaxUnits() {
        return maxUnits;
    }

    /** Sets the station name. */
    public void setName(String _name) {
        name = _name;
    }

    /** Sets the X coordinate. */
    public void setXCoord(int _xCoord) {
        xCoord = _xCoord;
    }

    /** Sets the Y coordinate. */
    public void setYCoord(int _yCoord) {
        yCoord = _yCoord;
    }

    /** Sets the station ID. */
    public void setId(int _id) {
        id = _id;
    }

    /** Sets the maximum number of units allowed. */
    public void setMaxUnits(int _maxUnits) {
        maxUnits = _maxUnits;
    }
}