package cityrescue;

import cityrescue.enums.*;

public class Incident {
    private IncidentType type;
    private IncidentStatus status;
    private int severity;
    private int x;
    private int y;
    private int id;

    public Incident(IncidentType _type, int _severity, int _x, int _y, int _id) {
        type = _type;
        status = IncidentStatus.REPORTED;
        severity = _severity;
        x = _x;
        y = _y;
        id = _id;
    }

    public IncidentType getType() {
        return type;
    }

    public IncidentStatus getStatus() {
        return status;
    }

    public int getSeverity() {
        return severity;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getId() {
        return id;
    }

    public void setType(IncidentType _type) {
        type = _type;
    }

    public void setStatus(IncidentStatus _status) {
        status = _status;
    }

    public void setSeverity(int _severity) {
        severity = _severity;
    }

    public void setX(int _x) {
        x = _x;
    }

    public void setY(int _y) {
        y = _y;
    }

    public void setId(int _id) {
        id = _id;
    }
}