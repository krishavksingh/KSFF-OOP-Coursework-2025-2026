package cityrescue.units;
import cityrescue.Incident;
import cityrescue.Unit;
import cityrescue.enums.IncidentType;
import cityrescue.enums.UnitType;

/**
* Represents a PoliceCar unit which responds to CRIME incidents only
*/
public class PoliceCar extends Unit {

    /**
     * Creates a PoliceCar unit with a station ID, unit ID and starting position.
     */
    public PoliceCar(int stationId, int unitId, int x, int y) {
        super(stationId, unitId, x, y);
        type = UnitType.POLICE_CAR;
    
    }
    @Override
    /**
     * Checks whether this unit can handle the given incident.
     */
    public boolean canHandle(Incident incident){
        return incident.getType()== IncidentType.CRIME;
    }
    @Override
    /**
    * Gets the number of work ticks required to resolve the incident.
    */
    public int getRequiredWorkTicks(){
        return 3;
    }
    
}
