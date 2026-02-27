package cityrescue.units;
import cityrescue.*;
import cityrescue.enums.UnitType;
public class Ambulance extends Unit {

    public Ambulance(int stationId, int unitId, int x, int y) {
        super(stationId, unitId, x, y);
        type = UnitType.AMBULANCE;
        
    }
    
}
