package cityrescue.units;
import cityrescue.*;
import cityrescue.enums.UnitType;

public class FireEngine extends Unit {

    public FireEngine(int stationId, int unitId, int x, int y) {
        super(stationId, unitId, x, y);
        type = UnitType.FIRE_ENGINE;
        
    }
    
}
