package cityrescue;

import cityrescue.enums.*;
import cityrescue.exceptions.*;

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
    Incident[] incidents;
    
    int station_num;
    int nextStationId;
    int incident_num;
    // TODO: add fields (arrays for stations/units/incidents, counters, tick, etc.)

    @Override
    public void initialise(int width, int height) throws InvalidGridException {
        if (width > 0 && height > 0) {
            map = new CityMap(width, height);
            stations = new Station[MAX_STATIONS];
            incidents = new Incident[MAX_INCIDENTS];
            station_num = 0;
            incident_num = 0;
            nextStationId = 1;

        
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
        

        boolean nextIdFound = false;
        int count = 0;
        while (nextIdFound == false) {
            if (stations[count] == (null))
            {
                nextStationId = count + 1;
                nextIdFound = true;
                
            }
            count += 1;
                        
        }

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

        boolean nextIdFound = false;
        int count = 0;
        while (nextIdFound == false) {
            if (stations[count].equals(null))
            {
                nextStationId = count + 1;
                nextIdFound = true;
                
            }
            count += 1;
                        
        }
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
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void decommissionUnit(int unitId) throws IDNotRecognisedException, IllegalStateException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
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
