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
        if (x >= grid[0] || y >= grid[1] || x < 0 || y < 0){
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
            throw new IDNotRecognisedException("Station ID is invalid");
        }
        boolean unitFound = false;
        for (Unit unit: units) {
            if (unit != null) {
                if (unit.stationID == stationId){
                    unitFound = true;
                }
            }
        }
        if(unitFound) throw new IllegalStateException("Station still has units assigned.");
        stations[stationId-1] = null;
        station_num -= 1;

                      
    }

    @Override
    public void setStationCapacity(int stationId, int maxUnits) throws IDNotRecognisedException, InvalidCapacityException {
        if (stations[stationId-1] == null)
        {
            throw new IDNotRecognisedException("Station ID is invalid"); 
        }
        int unitsFound = 0;
        for (Unit unit: units) {
            if (unit != null) {
                if (unit.stationID == stationId){
                    unitsFound += 1;
                }
            }
        }
        if (0 < maxUnits && maxUnits >= unitsFound){ 
            stations[stationId-1].setMaxUnits(maxUnits); 
        }
        else throw new InvalidCapacityException("Max Units is below zero or above existing units");
        
    }

    @Override
    public int[] getStationIds() {
        int[] stationIDs = new int[station_num];
        int count = 0;
        for (int i = 0; i < stations.length; i++) {
            if (stations[i] != null){
                stationIDs[count] = stations[i].getId();
                count += 1;

            }
        }
        return stationIDs;
    }

    @Override
    public int addUnit(int stationId, UnitType type) throws IDNotRecognisedException, InvalidUnitException, IllegalStateException {
        Station homeStation = stations[stationId-1];
        if (homeStation == null)
        {
            throw new IDNotRecognisedException("Station ID is invalid"); // TODO clarify which is which
        }
        

        int numUnitsAtStat = 0;
        for (Unit unit: units) {
            if (unit != null){
                if (unit.x == homeStation.getXCoord() && unit.y == homeStation.getYCoord()){
                    numUnitsAtStat += 1;
                }
            }
        }
        if (numUnitsAtStat >= homeStation.getMaxUnits()){
            throw new IllegalStateException("The Station has reached max units.");      
        }

        
        int unitID = nextUnitId;
        Unit newUnit;
        int stationX = stations[stationId-1].getXCoord();
        int stationY = stations[stationId-1].getYCoord();

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
            throw new IDNotRecognisedException("Unit ID is invalid");
        }
        if(units[unitId-1].status!=UnitStatus.EN_ROUTE && units[unitId-1].status!=UnitStatus.AT_SCENE) units[unitId-1] = null;
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
            throw new IDNotRecognisedException("Unit ID is invalid"); 
        }
        if (units[unitId-1].status == UnitStatus.AT_SCENE || units[unitId-1].status == UnitStatus.EN_ROUTE) {
            throw new IllegalStateException("Unit is en route or at scene.");
        }

        units[unitId-1].x_dest = stations[newStationId-1].getXCoord();
        units[unitId-1].y_dest = stations[newStationId-1].getYCoord();
        units[unitId-1].stationID = newStationId;
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
        int[] unitIds = new int[unit_num];
        int count = 0;
        for (int i = 0; i < units.length; i++) {
            if (units[i] != null){
                unitIds[count] = units[i].unitID;
                count += 1;

            }
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
        else incident = ((Integer)unit.getIncidentId()).toString();
        String view = String.format("U#%d TYPE=%s HOME=%d LOC=(%d,%d) STATUS=%s INCIDENT=%s WORK=%d", unit.getUnitID(), unit.getType(), unit.getStationID(), unit.getX(), unit.getY(), unit.getStatus(), incident, unit.getWorktick());
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
        if (incident.getStatus() != IncidentStatus.REPORTED && incident.getStatus() != IncidentStatus.DISPATCHED) {
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
        incident.setStatus(IncidentStatus.CANCELLED);
        incidents[incidentId - 1] = incident;
    }

    @Override
    public void escalateIncident(int incidentId, int newSeverity) throws IDNotRecognisedException, InvalidSeverityException, IllegalStateException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int[] getIncidentIds() {
        int[] incidentIds = new int[incident_num];
        int count = 0;
        for (int i = 0; i < incidents.length; i++) {
            if (incidents[i] != null){
                incidentIds[count] = incidents[i].getId();
                count += 1;

            }
        }
        return incidentIds;
    }

    @Override
    public String viewIncident(int incidentId) throws IDNotRecognisedException {
        if (incidents[incidentId-1] == null)
        {
            throw new IDNotRecognisedException("Incident ID is invalid"); 
        }
        Incident incident = incidents[incidentId-1];
        int unitId = -1;
        for (Unit unit : units) {
            if (unit != null && unit.incidentId == incident.getId()) {
                unitId = unit.unitID;
            }
        }

        String view = String.format("I#%d TYPE=%s SEV=%d LOC=(%d,%d) STATUS=%s UNIT=%d", incident.getId(), incident.getType(), incident.getSeverity(), incident.getX(), incident.getY(), incident.getStatus(), unitId); 
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
