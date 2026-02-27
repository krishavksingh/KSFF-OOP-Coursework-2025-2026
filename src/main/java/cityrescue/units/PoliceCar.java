package cityrescue.units;
import cityrescue.*;
import cityrescue.enums.UnitType;

public class PoliceCar extends Unit {

    public PoliceCar(int stationId, int unitId, int x, int y) {
        super(stationId, unitId, x, y);
        type = UnitType.POLICE_CAR;
    
    }
    
}
