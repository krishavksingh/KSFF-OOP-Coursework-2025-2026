package cityrescue;

import cityrescue.enums.*;
import cityrescue.exceptions.*;
import cityrescue.units.*;

/**
 * CityRescueImpl (Starter)
 *
 * Your task is to implement the full specification.
 * You may add additional classes in any package(s) you like.
 */
public class CityRescueImpl implements CityRescue {
    final int MAX_STATIONS = 20;
    final int MAX_UNITS = 50;
    final int MAX_INCIDENTS = 200;

    CityMap map; 
    Station[] stations;
    Unit[] units;
    Incident[] incidents;
    
    int station_num;
    int unit_num;
    int incident_num;
    int nextStationId;
    int nextUnitId;
    int nextIncidentId;
    
    
    // TODO: add fields (counters, tick, etc.)

    @Override
    public void initialise(int width, int height) throws InvalidGridException {
        if (width > 0 && height > 0) {
            map = new CityMap(width, height);

            stations = new Station[MAX_STATIONS];
            units = new Unit[MAX_UNITS];
            incidents = new Incident[MAX_INCIDENTS];

            station_num = 0;
            unit_num = 0;
            incident_num = 0;

            nextStationId = 1;
            nextUnitId = 1;
            nextIncidentId = 1;

        
        }
            else throw new InvalidGridException("Width/Height is lower than zero");
    }

    @Override
    public int[] getGridSize() {
        return map.getSize();
    }

    @Override
    public void addObstacle(int x, int y) throws InvalidLocationException {
        try{
            map.setBlocked(x, y);
        }
        catch (Exception e){
            throw new InvalidLocationException("Out of bounds");
        }
        
        
    }

    @Override
    public void removeObstacle(int x, int y) throws InvalidLocationException {
        try{
            map.setUnblocked(x, y);
        }
        catch (Exception e){
            throw new InvalidLocationException("Out of bounds");
        }
        
    }

    @Override
    public int addStation(String name, int x, int y) throws InvalidNameException, InvalidLocationException {
        int [] grid = getGridSize();
        if (x > grid[0] || y > grid[1]){
            throw new InvalidLocationException("Grid location out of bounds.");
        }
        if (name.equals(""))
        {
            throw new InvalidNameException("Name cannot be blank");
            
        }
        
        station_num += 1;
        int stationID = nextStationId;
        
        Station station = new Station(name, x, y, nextStationId);
        stations[nextStationId-1] = station;
        
        nextStationId += 1;

        return stationID;
    }

    @Override
    public void removeStation(int stationId) throws IDNotRecognisedException, IllegalStateException {
        if (stations[stationId-1] == null)
        {
            throw new IDNotRecognisedException("Station ID is invalid"); // TODO illegalState
        }
        stations[stationId-1] = null;
        station_num -= 1;

                      
    }

    @Override
    public void setStationCapacity(int stationId, int maxUnits) throws IDNotRecognisedException, InvalidCapacityException {
        if (stations[stationId-1] == null)
        {
            throw new IDNotRecognisedException("Station ID is invalid"); // TODO Invalid Capacity
        }
        stations[stationId-1].maxUnits = maxUnits; 
        
    }

    @Override
    public int[] getStationIds() {
        int[] stationIDs = new int[station_num];
        for (int i = 0; i < stationIDs.length; i++) {
            stationIDs[i] = stations[i].id; // use getters
            
        }
        return stationIDs;
    }

    @Override
    public int addUnit(int stationId, UnitType type) throws IDNotRecognisedException, InvalidUnitException, IllegalStateException {
        Station homeStation = stations[stationId-1];
        if (homeStation == null)
        {
            throw new IDNotRecognisedException("Station ID is invalid"); // TODO InvalidUnit, illegalstate
        }
        

        int numUnitsAtStat = 0;
        for (Unit unit: units) {
            if (unit != null){
                if (unit.x == homeStation.xCoord && unit.y == homeStation.yCoord){
                    numUnitsAtStat += 1;
                }
            }
        }
        if (homeStation.maxUnits - numUnitsAtStat == 0){
            throw new IllegalStateException("The Station has reached max units.");      
        }

        
        int unitID = nextUnitId;
        Unit newUnit;
        int stationX = stations[stationId-1].xCoord;
        int stationY = stations[stationId-1].yCoord;

        if (type == UnitType.AMBULANCE) 
        {
            newUnit = new Ambulance(stationId, nextUnitId, stationX, stationY);
        }
        else if (type == UnitType.FIRE_ENGINE) 
        {
            newUnit = new FireEngine(stationId, nextUnitId, stationX, stationY);
        }
        else if (type == UnitType.POLICE_CAR) 
        {
            newUnit = new PoliceCar(stationId, nextUnitId, stationX, stationY);
        }
        else {
            throw new InvalidUnitException("Unit type cannot be null.");
        }
        
        units[nextUnitId-1] = newUnit;
        nextUnitId += 1;
        unit_num += 1;
        return unitID;
        


    }

    @Override
    public void decommissionUnit(int unitId) throws IDNotRecognisedException, IllegalStateException {
        if (units[unitId-1] == null)
        {
            throw new IDNotRecognisedException("Unit ID is invalid"); // TODO IllegalState
        }
        if(units[unitId-1].status!=UnitStatus.EN_ROUTE||units[unitId-1].status!=UnitStatus.AT_SCENE) units[unitId-1] = null;
        else throw new IllegalStateException("Unit cannot be En route or At scene."); 
    }

    @Override
    public void transferUnit(int unitId, int newStationId) throws IDNotRecognisedException, IllegalStateException {
        if (stations[newStationId-1] == null)
        {
            throw new IDNotRecognisedException("Station ID is invalid"); 
        }
        if (units[unitId-1] == null)
        {
            throw new IDNotRecognisedException("Unit ID is invalid"); // TODO IllegalState
        }

        units[unitId-1].x_dest = stations[newStationId-1].xCoord;
        units[unitId-1].y_dest = stations[newStationId-1].yCoord;
    }

    @Override
    public void setUnitOutOfService(int unitId, boolean outOfService) throws IDNotRecognisedException, IllegalStateException {
        if (units[unitId-1] == null)
        {
            throw new IDNotRecognisedException("Unit ID is invalid"); 
        }
        if (outOfService) {
            if (units[unitId-1].status == UnitStatus.IDLE){
                units[unitId-1].status = UnitStatus.OUT_OF_SERVICE;
            }    
            else{
                throw new IllegalStateException("Unit must be Idle before being out of service.");
            }     
        }
        else {
            units[unitId-1].status = UnitStatus.IDLE;
        }
    }

    @Override
    public int[] getUnitIds() {
        int[] unitIds = new int[unit_num-1];
        for (int i = 0; i < unitIds.length; i++) {
            unitIds[i] = units[i].unitID;
        }
        return unitIds;
    }

    @Override
    public String viewUnit(int unitId) throws IDNotRecognisedException {
        if (units[unitId-1] == null)
        {
            throw new IDNotRecognisedException("Unit ID is invalid"); 
        }
        Unit unit = units[unitId-1];

        String incident;
        if (unit.incidentId == -1) incident = "-";
        else incident = ((Integer)unit.incidentId).toString();
        String view = String.format("U#%d TYPE=%s HOME=%d LOC=(%d,%d) STATUS=%s INCIDENT=%s WORK=%d", unit.unitID, unit.type, unit.stationID, unit.x, unit.y, unit.status, incident, unit.worktick);
        return view;
    }

    @Override
    public int reportIncident(IncidentType type, int severity, int x, int y) throws InvalidSeverityException, InvalidLocationException {
        if (severity < 1 || severity > 5) {
            throw new InvalidSeverityException("Severity must be between 1 and 5.");
        }
        
        int[] grid = getGridSize();
        if (x < 0 || y < 0 || x >= grid[0] || y >= grid[1]) {
            throw new InvalidLocationException("Incident location out of bounds.");
        }
        int incidentId = nextIncidentId;
        Incident incident = new Incident(type, severity, x, y, incidentId);
        incidents[incidentId - 1] = incident;
        incident_num += 1;
        nextIncidentId += 1;
        return incidentId;
    }

    @Override
    public void cancelIncident(int incidentId) throws IDNotRecognisedException, IllegalStateException {
        if (incidentId <= 0 || incidentId >= nextIncidentId || incidents[incidentId - 1] == null) {
            throw new IDNotRecognisedException("Incident ID is invalid");
        }
        Incident incident = incidents[incidentId - 1];
        if (incident.status != IncidentStatus.REPORTED && incident.status != IncidentStatus.DISPATCHED) {
            throw new IllegalStateException("Incident cannot be cancelled in its current state");
        }
        for (int i = 0; i < units.length; i++) {
            Unit unit = units[i];
            if (unit != null) {
                if (unit.incidentId == incidentId) {
                    units[i].incidentId = -1;
                    units[i].status = UnitStatus.IDLE;

                }
            }
            
        }
        incident.status = IncidentStatus.CANCELLED;
        incidents[incidentId - 1] = incident;
    }

    @Override
    public void escalateIncident(int incidentId, int newSeverity) throws IDNotRecognisedException, InvalidSeverityException, IllegalStateException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int[] getIncidentIds() {
        int[] incidentIds = new int[incident_num-1];
        for (int i = 0; i < incidentIds.length; i++) {
            incidentIds[i] = units[i].unitID;
        }
        return incidentIds;
    }

    @Override
    public String viewIncident(int incidentId) throws IDNotRecognisedException {
        if (incidents[incidentId-1] == null)
        {
            throw new IDNotRecognisedException("Unit ID is invalid"); 
        }
        Incident incident = incidents[incidentId-1];

        String view = String.format("I#%d TYPE=%s SEV=%d LOC=(%d,%d) STATUS=%s UNIT=%d", incident.ID, incident.type, incident.severity, incident.x, incident.y, incident.status, -1);
        return view;
    }

    @Override
    public void dispatch() {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void tick() {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public String getStatus() {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
