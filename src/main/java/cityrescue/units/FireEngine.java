package cityrescue.units;
import cityrescue.Incident;
import cityrescue.Unit;
import cityrescue.enums.IncidentType;
import cityrescue.enums.UnitType;
/**
* Represents a FireEngine unit which responds to FIRE incidents only
*/
public class FireEngine extends Unit {

    /**
     * Creates a FireEngine unit with a station ID, unit ID and starting position.
     */
    public FireEngine(int stationId, int unitId, int x, int y) {
        super(stationId, unitId, x, y);
        type = UnitType.FIRE_ENGINE;
        
    }
    @Override
    /**
     * Checks whether this unit can handle the given incident.
     */
    public boolean canHandle(Incident incident){
        return incident.getType()==IncidentType.FIRE;
    }

    @Override
    /**
    * Gets the number of work ticks required to resolve the incident.
    */
    public int getRequiredWorkTicks(){
        return 4;
    }
    
}
