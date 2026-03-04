package cityrescue;

import cityrescue.enums.IncidentStatus;
import cityrescue.enums.IncidentType;
import cityrescue.enums.UnitStatus;
import cityrescue.enums.UnitType;
import cityrescue.exceptions.CapacityExceededException;
import cityrescue.exceptions.IDNotRecognisedException;
import cityrescue.exceptions.InvalidCapacityException;
import cityrescue.exceptions.InvalidGridException;
import cityrescue.exceptions.InvalidLocationException;
import cityrescue.exceptions.InvalidNameException;
import cityrescue.exceptions.InvalidSeverityException;
import cityrescue.exceptions.InvalidUnitException;
import cityrescue.units.Ambulance;
import cityrescue.units.FireEngine;
import cityrescue.units.PoliceCar;

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
        if (unit_num >= MAX_UNITS) {throw new CapacityExceededException("Maximum units (" + MAX_UNITS + ") exceeded.");}

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
        try{
        if (stations[stationId-1] == null)
        {
            throw new IDNotRecognisedException("Station ID is invalid");
        }}
        catch (ArrayIndexOutOfBoundsException e){
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
        try{
        if (stations[stationId-1] == null)
        {
            throw new IDNotRecognisedException("Station ID is invalid");
        }}
        catch (ArrayIndexOutOfBoundsException e){
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
        if (unit_num >= MAX_UNITS) {throw new CapacityExceededException("Maximum units (" + MAX_UNITS + ") exceeded.");}
        try{
            Station homeStation = stations[stationId-1];
            if (homeStation == null)
            {
                throw new IDNotRecognisedException("Station ID is invalid"); 
            }
            int numUnitsAtStat = 0;
            for (Unit unit: units) {
                if (unit != null){
                    if (unit.getStationID() == homeStation.getId()){
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
        catch (ArrayIndexOutOfBoundsException e){
            throw new IDNotRecognisedException("Unit ID is invalid");

        }
        
        

        
        


    }

    @Override
    public void decommissionUnit(int unitId) throws IDNotRecognisedException, IllegalStateException {
        try{
        if (units[unitId-1] == null)
        {
            throw new IDNotRecognisedException("Unit ID is invalid");
        }}
        catch (ArrayIndexOutOfBoundsException e){
            throw new IDNotRecognisedException("Unit ID is invalid");
            
        }
        if(units[unitId-1].status!=UnitStatus.EN_ROUTE && units[unitId-1].status!=UnitStatus.AT_SCENE) {
            units[unitId-1] = null;
            unit_num -= 1;
        }
        else throw new IllegalStateException("Unit cannot be En route or At scene."); 
    }

    @Override
    public void transferUnit(int unitId, int newStationId) throws IDNotRecognisedException, IllegalStateException {
        try{
        if (stations[newStationId-1] == null)
        {
            throw new IDNotRecognisedException("Station ID is invalid"); 
        }}
        catch(Exception e){ throw new IDNotRecognisedException("Station ID is invalid");}
        try{
        if (units[unitId-1] == null)
        {
            throw new IDNotRecognisedException("Unit ID is invalid"); 
        }}
        catch(Exception e)
        {
            throw new IDNotRecognisedException("Unit ID is invalid"); 
        }
        if (units[unitId-1].status != UnitStatus.IDLE) {
            throw new IllegalStateException("Unit is not idle.");
        }
        
        Station homeStation = stations[newStationId-1];
            int numUnitsAtStat = 0;
            for (Unit unit: units) {
                if (unit != null){
                    if (unit.getStationID() == homeStation.getId()){
                        numUnitsAtStat += 1;
                    }
                }
            }
            if (numUnitsAtStat >= homeStation.getMaxUnits()){
                throw new IllegalStateException("The Station has reached max units.");      
            }

        units[unitId-1].x_dest = stations[newStationId-1].getXCoord();
        units[unitId-1].y_dest = stations[newStationId-1].getYCoord();
        units[unitId-1].stationID = newStationId;
    }

    @Override
    public void setUnitOutOfService(int unitId, boolean outOfService) throws IDNotRecognisedException, IllegalStateException {
        try{
        if (units[unitId-1] == null)
        {
            throw new IDNotRecognisedException("Unit ID is invalid");
        }}
        catch (ArrayIndexOutOfBoundsException e){
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
        try{
        if (units[unitId-1] == null)
        {
            throw new IDNotRecognisedException("Unit ID is invalid");
        }}
        catch (ArrayIndexOutOfBoundsException e){
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
        if (incident_num >= MAX_INCIDENTS) {throw new CapacityExceededException("Maximum incidents (" + MAX_INCIDENTS + ") exceeded.");}
        
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
        try{
        if (incidentId <= 0 || incidentId >= nextIncidentId || incidents[incidentId - 1] == null) {
            throw new IDNotRecognisedException("Incident ID is invalid");
        }}
        catch(ArrayIndexOutOfBoundsException e){
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
        incident_num -= 1;
    }

    @Override
    public void escalateIncident(int incidentId, int newSeverity) throws IDNotRecognisedException, InvalidSeverityException, IllegalStateException {
        try{
        if (incidentId <= 0 || incidentId >= nextIncidentId || incidents[incidentId - 1] == null) {
            throw new IDNotRecognisedException("Incident ID is invalid");
        }}
        catch(ArrayIndexOutOfBoundsException e){
            throw new IDNotRecognisedException("Incident ID is invalid");
        }
        if (newSeverity < 1 || newSeverity > 5) {
            throw new InvalidSeverityException("Severity must be between 1 and 5");
        }
        Incident incident = incidents[incidentId -1];

        if (incident.getStatus() == IncidentStatus.RESOLVED || incident.getStatus() == IncidentStatus.CANCELLED) {
            throw new IllegalStateException("Cannot escalate resolved or cancelled incident");
        }
        incident.setSeverity(newSeverity);

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
        try{
        if (incidents[incidentId - 1] == null) {
            throw new IDNotRecognisedException("Incident ID is invalid");
        }}
        catch(ArrayIndexOutOfBoundsException e){
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
        for (int i = 0; i < nextIncidentId; i++) {
            Incident incident = incidents[i];

            if (incident == null || incident.getStatus() != IncidentStatus.REPORTED) continue;

            Unit bestUnit = null;
            int bestDistance = Integer.MAX_VALUE;
            //search all units
            for (int j = 0; j < nextUnitId; j++) {

                Unit unit = units[j];

                if (unit == null) continue;
            //rules
                if (unit.getStatus() != UnitStatus.IDLE) continue;

                if (!unit.canHandle(incident)) continue;
            //manhattan distance
                int distance = Math.abs(unit.getX() - incident.getX()) + Math.abs(unit.getY() - incident.getY());
                if (bestUnit == null) {bestUnit = unit; bestDistance = distance;}

                else {
                    if (distance < bestDistance) {bestUnit = unit; bestDistance = distance;}

                    else if (distance == bestDistance) {
                        //2nd tie break lowest unitID
                        if (unit.getUnitID() < bestUnit.getUnitID()){bestUnit = unit;}

                        else if (unit.getUnitID() == bestUnit.getUnitID()) {
                            //3rd tie break lowest stationID
                            if(unit.getStationID() < bestUnit.getStationID()){
                                bestUnit= unit;
                            }
                        }}
                }

                //assign if found
                // TODO CHECK
            }
            if (bestUnit!= null) {
                bestUnit.setStatus(UnitStatus.EN_ROUTE);
                bestUnit.setIncidentId(incident.getId());

                bestUnit.setX_dest(incident.getX());
                bestUnit.setY_dest(incident.getY());

                incident.setStatus(IncidentStatus.DISPATCHED);
            }
        }
        
    }
    
    private int tickCount = 0;
    @Override
    public void tick() {
        tickCount++;
        //move EN_ROUTE units
        for (int i = 0; i < nextUnitId; i++) {
            Unit unit = units[i];
            if (unit == null) continue;
            if (unit.getStatus() == UnitStatus.EN_ROUTE) {
                int x = unit.getX();
                int y = unit.getY();
                int xDest = unit.getX_dest();
                int yDest = unit.getY_dest();

                if (x < xDest) unit.setX(x+1);
                else if (x>xDest) unit.setX(x-1);
                else if (y< yDest) unit.setY(y+1);
                else if (y>yDest) unit.setY(y-1);

            }
        }
        //mark arrivals
        for (int i=0; i < nextUnitId; i++) {
            Unit unit = units[i];
            if (unit ==null) continue;
            if (unit.getStatus() == UnitStatus.EN_ROUTE){
                if (unit.getX()== unit.getX_dest() && unit.getY() == unit.getY_dest()) {
                    unit.setStatus(UnitStatus.AT_SCENE);
                    unit.setWorktick(0);

                    Incident incident = incidents[unit.getIncidentId()-1];
                    if (incident != null) {
                        incident.setStatus(IncidentStatus.IN_PROGRESS);
                    }
                }
            }
        }
        //process on scene work
        for (int i=0;i<nextUnitId; i++){
            Unit unit = units[i];
            if (unit==null) continue;
            if (unit.getStatus()== UnitStatus.AT_SCENE){
                unit.setWorktick(unit.getWorktick()+1);

            }
        }
        //reslove completed incidents
        for (int i=0; i<nextIncidentId; i++){
            Incident incident = incidents[i];
            if (incident == null) continue;

            for (int j=0;j<nextUnitId;j++){
                Unit unit = units[j];
                if (unit==null) continue;
                if (unit.getIncidentId()== incident.getId() && unit.getStatus()== UnitStatus.AT_SCENE){
                if (unit.getWorktick() >= unit.getRequiredWorkTicks()){
                    incident.setStatus(IncidentStatus.RESOLVED);
                    unit.setStatus(UnitStatus.IDLE);
                    unit.setIncidentId(-1);
                    unit.setWorktick(0);
                }

                }

            }

        }

        
    }

    @Override
    public String getStatus() {
        StringBuilder sb = new StringBuilder();
        //header
        sb.append(String.format("TICK=%d\n", tickCount));
        sb.append(String.format("STATIONS=%d UNITS=%d INCIDENTS=%d OBSTACLES=%d\n", nextStationId, nextUnitId, nextIncidentId, map.getObstacleCount()));

        //incidents
        sb.append("INCIDENTS ");
        for (int i = 0; i < nextIncidentId; i++){
            Incident inc = incidents[i];
            if (inc == null) continue;

            int assignedUnit = -1;
            for (int j=0; j < nextUnitId; j++){
                Unit u = units[j];
                if (u != null && u.getIncidentId() == inc.getId()) {assignedUnit = u.getUnitID(); break;}

            }
            sb.append(String.format("I#%d TYPE=%s SEV=%d LOC=(%d,%d) STATUS=%s UNIT=%s ", inc.getId(), inc.getType(), inc.getSeverity(), inc.getX(), inc.getY(), inc.getStatus(), (assignedUnit==-1? "-":assignedUnit)));
        }
        sb.append("\n");
        //units
        sb.append("UNITS\n");
        for (int i = 0; i < nextUnitId; i++){
            Unit u = units[i];
            if (u == null) continue;

            sb.append(String.format("U#%d TYPE=%s HOME=%d LOC=(%d,%d) STATUS=%s INCIDENT=%s%s\n", u.getUnitID(), u.getType(), u.getStationID(), u.getX(), u.getY(), u.getStatus(), (u.getIncidentId()==-1? "-":u.getIncidentId()), (u.getStatus()==UnitStatus.AT_SCENE? " WORK=" + u.getWorktick():"")));


        }
        return sb.toString();


        
    }
}
