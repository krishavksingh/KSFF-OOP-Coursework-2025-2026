package cityrescue;

import cityrescue.enums.*;

public class Incident {
    IncidentType type;
    IncidentStatus status;
    int severity;
    int x;
    int y;
    int ID;

    public Incident(IncidentType type2, int severity, int _x, int _y, int incidentId) {
        type = type2;
        status = IncidentStatus.REPORTED;
        x = _x;
        y = _y;
        ID = incidentId;



    }

    public IncidentStatus getStatus()
    {
        return status;
    }
    
}
