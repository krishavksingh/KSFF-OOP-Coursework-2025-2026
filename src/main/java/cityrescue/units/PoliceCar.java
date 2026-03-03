package cityrescue.units;
import cityrescue.Incident;
import cityrescue.Unit;
import cityrescue.enums.IncidentType;
import cityrescue.enums.UnitType;

public class PoliceCar extends Unit {

    public PoliceCar(int stationId, int unitId, int x, int y) {
        super(stationId, unitId, x, y);
        type = UnitType.POLICE_CAR;
    
    }
    @Override
    public boolean canHandle(Incident incident){
        return incident.getType()== IncidentType.CRIME;
    }
    @Override
    public int getRequiredWorkTicks(){
        return 3;
    }
    
}
