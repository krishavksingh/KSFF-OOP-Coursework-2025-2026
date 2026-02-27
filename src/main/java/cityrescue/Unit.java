package cityrescue;

import cityrescue.enums.*;

public class Unit {
    protected UnitType type;
    protected UnitStatus status;
    protected int stationID;
    protected int unitID;
    protected int x;
    protected int y;
    protected int x_dest;
    protected int y_dest;

    public UnitStatus getStatus()
    {
        return status;
    }
    public Unit(int stationId, int unitId, int _x, int _y)
    {
        status = UnitStatus.IDLE;
        stationID = stationId;
        unitID = unitId;
        x = _x;
        y = _y;
        x_dest = _x;
        y_dest = _y;
    }
    
}
