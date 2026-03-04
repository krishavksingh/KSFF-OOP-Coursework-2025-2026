package cityrescue;

import cityrescue.enums.UnitStatus;
import cityrescue.enums.UnitType;

/**
 * Represents a generic response unit.
 * The three unit types of Ambulance, PoliceCar and FireEngine extend from this class
 */
public abstract class Unit {

    protected UnitType type;
    protected UnitStatus status;
    protected int stationID;
    protected int unitID;
    protected int incidentId;
    protected int x;
    protected int y;
    protected int x_dest;
    protected int y_dest;
    protected int worktick;

    /**
     * Creates a unit with a station ID, unit ID and starting position.
     */
    public Unit(int stationId, int unitId, int _x, int _y) {
        status = UnitStatus.IDLE;
        stationID = stationId;
        unitID = unitId;
        x = _x;
        y = _y;
        x_dest = _x;
        y_dest = _y;
        incidentId = -1;
    }

    /** Gets the unit type. */
    public UnitType getType() {
        return type;
    }

    /** Gets the unit status. */
    public UnitStatus getStatus() {
        return status;
    }

    /** Gets the station ID. */
    public int getStationID() {
        return stationID;
    }

    /** Gets the unit ID. */
    public int getUnitID() {
        return unitID;
    }

    /** Gets the assigned incident ID. */
    public int getIncidentId() {
        return incidentId;
    }

    /** Gets the current X position. */
    public int getX() {
        return x;
    }

    /** Gets the current Y position. */
    public int getY() {
        return y;
    }

    /** Gets the destination X position. */
    public int getX_dest() {
        return x_dest;
    }

    /** Gets the destination Y position. */
    public int getY_dest() {
        return y_dest;
    }

    /** Gets the number of work ticks completed. */
    public int getWorktick() {
        return worktick;
    }

    /** Sets the unit type. */
    public void setType(UnitType type) {
        this.type = type;
    }

    /** Sets the unit status. */
    public void setStatus(UnitStatus status) {
        this.status = status;
    }

    /** Sets the station ID. */
    public void setStationID(int stationID) {
        this.stationID = stationID;
    }

    /** Sets the unit ID. */
    public void setUnitID(int unitID) {
        this.unitID = unitID;
    }

    /** Sets the incident ID. */
    public void setIncidentId(int incidentId) {
        this.incidentId = incidentId;
    }

    /** Sets the current X position. */
    public void setX(int x) {
        this.x = x;
    }

    /** Sets the current Y position. */
    public void setY(int y) {
        this.y = y;
    }

    /** Sets the destination X position. */
    public void setX_dest(int x_dest) {
        this.x_dest = x_dest;
    }

    /** Sets the destination Y position. */
    public void setY_dest(int y_dest) {
        this.y_dest = y_dest;
    }

    /** Sets the number of work ticks completed. */
    public void setWorktick(int worktick) {
        this.worktick = worktick;
    }

    /**
     * Checks whether this unit can handle the given incident.
     */
    public abstract boolean canHandle(Incident incident);

    /**
    * Gets the number of work ticks required to resolve the incident.
    */
    public abstract int getRequiredWorkTicks();


}