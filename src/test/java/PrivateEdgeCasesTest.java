
import cityrescue.CityRescueImpl;
import cityrescue.enums.*;
import cityrescue.exceptions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PrivateEdgeCasesTest {

    private CityRescueImpl system;

    @BeforeEach
    void SetUp() throws Exception {
        system = new CityRescueImpl();
        system.initialise(10, 10);
    }

    @Test
    /**
    * A test to check that a CapacityExceededException is thrown if a unit is added when station capacity is full.
    */
    void testAddMaximumUnits() throws Exception {
        int sid = system.addStation("A", 0, 0);
        system.setStationCapacity(sid, 50);
        for (int i = 0; i < 50; i++) {
            int uid = system.addUnit(sid, UnitType.AMBULANCE);
            assertEquals(i + 1, uid);
        }
        assertThrows(CapacityExceededException.class, () -> system.addUnit(sid, UnitType.AMBULANCE));
    }

    /**
    * A test to check that a unit moves correctly when an obstacle is presented.
    */
    @Test
    void testUnitMovementAroundObstacles() throws Exception {
        int s = system.addStation("A", 0, 0);
        system.setStationCapacity(s, 1);
        int u = system.addUnit(s, UnitType.FIRE_ENGINE);
        int inc = system.reportIncident(IncidentType.FIRE, 3, 3, 0);

       
        system.addObstacle(1, 0);
        system.dispatch();
        system.tick(); 
        String view = system.viewUnit(u);
        assertTrue(!view.contains("LOC=(1,0)"));
    }

  
    @Test
    /**
    * A test to check that the tie break works correctly
    */
    void testDispatchTieBreak() throws Exception {
        int s1 = system.addStation("A", 0, 0);
        int s2 = system.addStation("B", 2, 0);
        system.setStationCapacity(s1, 5);
        system.setStationCapacity(s2, 5);

        int u1 = system.addUnit(s1, UnitType.FIRE_ENGINE);
        int u2 = system.addUnit(s2, UnitType.FIRE_ENGINE);

        int inc = system.reportIncident(IncidentType.FIRE, 3, 1, 0);
        system.dispatch();

    
        String status = system.viewUnit(u1);
        assertTrue(status.contains("EN_ROUTE"));
    }

    
    @Test
    /**
    * A test to check that the system can handle multiple incidents and multiple units going around without any confusion
    */
    void testMultipleIncidentsMultipleUnits() throws Exception {
        int s1 = system.addStation("A", 0, 0);
        int s2 = system.addStation("B", 9, 9);
        system.setStationCapacity(s1, 5);
        system.setStationCapacity(s2, 5);

        int u1 = system.addUnit(s1, UnitType.FIRE_ENGINE);
        int u2 = system.addUnit(s2, UnitType.FIRE_ENGINE);

        int inc1 = system.reportIncident(IncidentType.FIRE, 3, 1, 0);
        int inc2 = system.reportIncident(IncidentType.FIRE, 3, 8, 9);

        system.dispatch();
        assertTrue(system.viewUnit(u1).contains("EN_ROUTE"));
        assertTrue(system.viewUnit(u2).contains("EN_ROUTE"));
    }


    @Test
    /**
    * Another test to check that the system can handle multiple incidents and multiple units going around without any confusion
    */
    void testIncidentResolvesWithMultipleUnits() throws Exception {
        int s = system.addStation("A", 0, 0);
        system.setStationCapacity(s, 5);
        int u1 = system.addUnit(s, UnitType.FIRE_ENGINE);
        int u2 = system.addUnit(s, UnitType.FIRE_ENGINE);

        int inc = system.reportIncident(IncidentType.FIRE, 1, 0, 1);
        system.dispatch();
        for (int i = 0; i < 20; i++) system.tick();

        String view = system.viewIncident(inc);
        assertTrue(view.contains("RESOLVED"));
        assertTrue(system.viewUnit(u1).contains("IDLE"));
        assertTrue(system.viewUnit(u2).contains("IDLE"));
    }


    @Test
    /**
    * A test to check that the system will not allow a unit to be transferred if out of service
    */
    void testTransferUnitOutOfServiceFails() throws Exception {
        int s1 = system.addStation("A", 0, 0);
        int s2 = system.addStation("B", 1, 1);
        system.setStationCapacity(s1,5);
        system.setStationCapacity(s2,5);

        int u = system.addUnit(s1, UnitType.AMBULANCE);
        system.setUnitOutOfService(u, true);
        assertThrows(IllegalStateException.class, () -> system.transferUnit(u, s2));
    }

    @Test
    /**
    * A test to check that you cannot decommision an en route unit
    */
    void testDecommissionEnRouteFails() throws Exception {
        int s = system.addStation("A", 0, 0);
        system.setStationCapacity(s,5);
        int u = system.addUnit(s, UnitType.FIRE_ENGINE);
        int inc = system.reportIncident(IncidentType.FIRE, 1, 1, 0);
        system.dispatch();
        system.tick();
        
        assertThrows(IllegalStateException.class, () -> system.decommissionUnit(u));
    }

    @Test
    /**
    * A test to check that the system doesn't allow a transfer of a unit if station is full
    */
    void testTransferToFullStationFails() throws Exception {
        int s1 = system.addStation("A",0,0);
        int s2 = system.addStation("B",1,1);
        system.setStationCapacity(s1,5);
        system.setStationCapacity(s2,1);

        int u1 = system.addUnit(s1, UnitType.AMBULANCE);
        int u2 = system.addUnit(s2, UnitType.FIRE_ENGINE);

        assertThrows(IllegalStateException.class, () -> system.transferUnit(u1, s2));
    }



    @Test
    /**
    * A test to check that a cancelled incident resets the unit that was assigned
    */
    void testCancelDispatchedIncidentResetsUnit() throws Exception {
        int s = system.addStation("A", 0, 0);
        system.setStationCapacity(s, 5);
        int u = system.addUnit(s, UnitType.FIRE_ENGINE);
        int inc = system.reportIncident(IncidentType.FIRE, 3, 1,0);

        system.dispatch();
        system.cancelIncident(inc);
        assertTrue(system.viewUnit(u).contains("IDLE"));
        assertTrue(system.viewIncident(inc).contains("CANCELLED"));
        
    }

    @Test
    /**
    * A test to check that the system doesn't allow an incident to be cancelled if in progress
    */
    void testCancelInProgressFail() throws Exception {
        int s = system.addStation("A",0,0);
        system.setStationCapacity(s,5);
        int u = system.addUnit(s, UnitType.FIRE_ENGINE);
        int inc = system.reportIncident(IncidentType.FIRE,1,0,1);
        system.dispatch();
        system.tick();
        system.tick();
        system.tick();
        
        assertThrows(IllegalStateException.class, () -> system.cancelIncident(inc));
    }

    @Test
    /**
    * A test to check that a cancelled incident cannot be escalated
    */
    void testEscalateCancelledFail() throws Exception {
        int inc = system.reportIncident(IncidentType.FIRE, 3,1,1);
        system.cancelIncident(inc);
        assertThrows(IllegalStateException.class, () -> system.escalateIncident(inc,5));
    }


    @Test
    /**
    * A test to check that the system will not assign a unit of wrong type to an incident
    */
    void wrongUnitCannotHandleIncident() throws Exception {
        int s = system.addStation("A",0,0);
        system.setStationCapacity(s,5);
        int u = system.addUnit(s,UnitType.POLICE_CAR);
        int inc = system.reportIncident(IncidentType.FIRE,3,1,0);
        system.dispatch();
        assertTrue(system.viewUnit(u).contains("IDLE"));
    }
}