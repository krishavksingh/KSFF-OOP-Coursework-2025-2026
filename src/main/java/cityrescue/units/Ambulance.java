package cityrescue.units;
import cityrescue.Incident;
import cityrescue.Unit;
import cityrescue.enums.IncidentType;
import cityrescue.enums.UnitType;
public class Ambulance extends Unit {

    public Ambulance(int stationId, int unitId, int x, int y) {
        super(stationId, unitId, x, y);
        type = UnitType.AMBULANCE;
        
    }
    @Override
    public boolean canHandle(Incident incident){
        return incident.getType() == IncidentType.MEDICAL;
    }
    
}
