package cityrescue;

import cityrescue.enums.*;

public class Unit {
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

    // Constructor
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

    public UnitType getType() {
        return type;
    }

    public UnitStatus getStatus() {
        return status;
    }

    public int getStationID() {
        return stationID;
    }

    public int getUnitID() {
        return unitID;
    }

    public int getIncidentId() {
        return incidentId;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getX_dest() {
        return x_dest;
    }

    public int getY_dest() {
        return y_dest;
    }

    public int getWorktick() {
        return worktick;
    }

   
    public void setType(UnitType type) {
        this.type = type;
    }

    public void setStatus(UnitStatus status) {
        this.status = status;
    }

    public void setStationID(int stationID) {
        this.stationID = stationID;
    }

    public void setUnitID(int unitID) {
        this.unitID = unitID;
    }

    public void setIncidentId(int incidentId) {
        this.incidentId = incidentId;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX_dest(int x_dest) {
        this.x_dest = x_dest;
    }

    public void setY_dest(int y_dest) {
        this.y_dest = y_dest;
    }

    public void setWorktick(int worktick) {
        this.worktick = worktick;
    }
}