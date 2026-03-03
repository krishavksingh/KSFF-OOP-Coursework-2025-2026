package cityrescue.units;
import cityrescue.Incident;
import cityrescue.Unit;
import cityrescue.enums.IncidentType;
import cityrescue.enums.UnitType;

public class FireEngine extends Unit {

    public FireEngine(int stationId, int unitId, int x, int y) {
        super(stationId, unitId, x, y);
        type = UnitType.FIRE_ENGINE;
        
    }
    @Override
    public boolean canHandle(Incident incident){
        return incident.getType()==IncidentType.FIRE;
    }
    
}
