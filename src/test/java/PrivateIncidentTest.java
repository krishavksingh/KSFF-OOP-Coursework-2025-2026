import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import cityrescue.*;
import cityrescue.enums.*;
import cityrescue.exceptions.*;

public class PrivateIncidentTest {

    CityRescueImpl rescue;

    @BeforeEach
    public void setUp() throws InvalidGridException {
        rescue = new CityRescueImpl();
        rescue.initialise(10, 10);
    }

    @Test
    public void testReportIncidentValid() throws Exception {
        int id = rescue.reportIncident(IncidentType.FIRE, 3, 2, 2);
        int[] ids = rescue.getIncidentIds();
        assertTrue(ids.length == 1);
        assertEquals(id, ids[0]);
    }

    @Test
    public void testReportIncidentInvalidSeverity() {
        assertThrows(InvalidSeverityException.class, () -> {
            rescue.reportIncident(IncidentType.MEDICAL, 0, 1, 1);
        });
        assertThrows(InvalidSeverityException.class, () -> {
            rescue.reportIncident(IncidentType.MEDICAL, 6, 1, 1);
        });
    }

    @Test
    public void testReportIncidentOutOfBounds() {
        assertThrows(InvalidLocationException.class, () -> {
            rescue.reportIncident(IncidentType.CRIME, 3, 10, 10);
        });
        assertThrows(InvalidLocationException.class, () -> {
            rescue.reportIncident(IncidentType.CRIME, 3, -1, 0);
        });
    }

    @Test
    public void testCancelIncident() throws Exception {
        int id = rescue.reportIncident(IncidentType.FIRE, 2, 1, 1);
        rescue.cancelIncident(id);
        assertThrows(IllegalStateException.class, () -> {
            rescue.cancelIncident(id);
        });
    }
}