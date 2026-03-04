package cityrescue.units;
import cityrescue.Incident;
import cityrescue.Unit;
import cityrescue.enums.IncidentType;
import cityrescue.enums.UnitType;
/**
* Represents an Ambulance unit which responds to MEDICAL incidents only
*/
public class Ambulance extends Unit {

    /**
     * Creates an Ambulance unit with a station ID, unit ID and starting position.
     */
    public Ambulance(int stationId, int unitId, int x, int y) {
        super(stationId, unitId, x, y);
        type = UnitType.AMBULANCE;
        
    }
    @Override
    /**
     * Checks whether this unit can handle the given incident.
     */
    public boolean canHandle(Incident incident){
        return incident.getType() == IncidentType.MEDICAL;
    }
    
    @Override
    /**
    * Gets the number of work ticks required to resolve the incident.
    */
    public int getRequiredWorkTicks(){
        return 2;
    }
    
}
