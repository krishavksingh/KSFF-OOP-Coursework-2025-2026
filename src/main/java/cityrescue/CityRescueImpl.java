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
    
    
    // TODO: add fields (arrays for stations/units/incidents, counters, tick, etc.)

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
            throw new IDNotRecognisedException("Station ID is invalid"); // Do illegalState
        }
        stations[stationId-1] = null;
        station_num -= 1;

                      
    }

    @Override
    public void setStationCapacity(int stationId, int maxUnits) throws IDNotRecognisedException, InvalidCapacityException {
        if (stations[stationId-1] == null)
        {
            throw new IDNotRecognisedException("Station ID is invalid"); // do Invalid Capacity
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
            throw new IDNotRecognisedException("Station ID is invalid"); // do Invalid Capacity
        }

        int numUnitsAtStat = 0;
        for (Unit unit: units) {
            if (unit.x == homeStation.xCoord && unit.y == homeStation.yCoord){
                numUnitsAtStat += 1;
            }
        }
        if (homeStation.maxUnits - numUnitsAtStat == 0){
            throw new InvalidUnitException("The Station has reached max units.");      
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
        return unitID;
        


    }

    @Override
    public void decommissionUnit(int unitId) throws IDNotRecognisedException, IllegalStateException {
        if (units[unitId-1] == null)
        {
            throw new IDNotRecognisedException("Unit ID is invalid"); // do Invalid Capacity
        }
        units[unitId-1] = null;
    }

    @Override
    public void transferUnit(int unitId, int newStationId) throws IDNotRecognisedException, IllegalStateException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void setUnitOutOfService(int unitId, boolean outOfService) throws IDNotRecognisedException, IllegalStateException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int[] getUnitIds() {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public String viewUnit(int unitId) throws IDNotRecognisedException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int reportIncident(IncidentType type, int severity, int x, int y) throws InvalidSeverityException, InvalidLocationException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void cancelIncident(int incidentId) throws IDNotRecognisedException, IllegalStateException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void escalateIncident(int incidentId, int newSeverity) throws IDNotRecognisedException, InvalidSeverityException, IllegalStateException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int[] getIncidentIds() {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public String viewIncident(int incidentId) throws IDNotRecognisedException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
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
