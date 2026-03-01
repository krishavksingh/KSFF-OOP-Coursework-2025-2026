import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import cityrescue.*;
import cityrescue.enums.*;
import cityrescue.exceptions.*;

public class PrivateStationTest {

    CityRescueImpl rescue;

    @BeforeEach
    public void setUp() throws InvalidGridException {
        rescue = new CityRescueImpl();
        rescue.initialise(10, 10);
    }

    @Test
    public void testAddStationValid() throws Exception {
        int id = rescue.addStation("A", 2, 3);
        int[] ids = rescue.getStationIds();
        assertTrue(ids.length == 1);
        assertEquals(id, ids[0]);
    }

    @Test
    public void testAddStationInvalidName() {
        assertThrows(InvalidNameException.class, () -> {
            rescue.addStation("", 1, 1);
        });
    }

    @Test
    public void testAddStationOutOfBounds() {
        assertThrows(InvalidLocationException.class, () -> {
            rescue.addStation("A", 10, 10);
        });
        assertThrows(InvalidLocationException.class, () -> {
            rescue.addStation("A", -1, 5);
        });
    }

    @Test
    public void testSetStationCapacityValid() throws Exception {
        int id = rescue.addStation("A", 0, 0);
        rescue.setStationCapacity(id, 5);
        // Implicit check: no exception thrown
    }

    @Test
    public void testSetStationCapacityInvalid() throws Exception {
        int id = rescue.addStation("A", 0, 0);
        assertThrows(InvalidCapacityException.class, () -> {
            rescue.setStationCapacity(id, -1);
        });
    }

    @Test
    public void testRemoveStationWithUnits() throws Exception {
        int id = rescue.addStation("A", 1, 1);
        rescue.setStationCapacity(id, 1);
        rescue.addUnit(id, UnitType.AMBULANCE);
        assertThrows(IllegalStateException.class, () -> {
            rescue.removeStation(id);
        });
    }
}
