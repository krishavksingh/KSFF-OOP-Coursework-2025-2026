import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import cityrescue.*;
import cityrescue.enums.*;
import cityrescue.exceptions.*;

public class PrivateUnitTest {

    CityRescueImpl rescue;

    @BeforeEach
    public void setUp() throws Exception {
        rescue = new CityRescueImpl();
        rescue.initialise(10, 10);
        rescue.addStation("A", 0, 0);
    }

    @Test
    public void testAddUnitValid() throws Exception {
        int unitId = rescue.addUnit(1, UnitType.AMBULANCE);
        int[] units = rescue.getUnitIds();
        assertEquals(1, units.length);
        assertEquals(unitId, units[0]);
    }

    @Test
    public void testAddUnitExceedCapacity() throws Exception {
        rescue.setStationCapacity(1, 1);
        rescue.addUnit(1, UnitType.AMBULANCE);
        assertThrows(IllegalStateException.class, () -> {
            rescue.addUnit(1, UnitType.FIRE_ENGINE);
        });
    }

    @Test
    public void testTransferUnit() throws Exception {
        rescue.addStation("A", 1, 1);
        int unitId = rescue.addUnit(1, UnitType.POLICE_CAR);
        rescue.transferUnit(unitId, 2);
        String view = rescue.viewUnit(unitId);
        assertTrue(view.contains("HOME=2"));
    }

    @Test
    public void testSetUnitOutOfService() throws Exception {
        int unitId = rescue.addUnit(1, UnitType.FIRE_ENGINE);
        rescue.setUnitOutOfService(unitId, true);
        String view = rescue.viewUnit(unitId);
        assertTrue(view.contains("OUT_OF_SERVICE"));
    }

    @Test
    public void testDecommissionUnit() throws Exception {
        int unitId = rescue.addUnit(1, UnitType.AMBULANCE);
        int[] ids = rescue.getUnitIds();
        System.out.println(ids);
        rescue.decommissionUnit(unitId);
        ids = rescue.getUnitIds();
        System.out.println(ids);
        
        assertEquals(0, ids.length);
    }
}