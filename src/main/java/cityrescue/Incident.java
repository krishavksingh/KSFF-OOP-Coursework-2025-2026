package cityrescue;

import cityrescue.enums.*;

/**
 * Represents an emergency incident in the system.
 */
public class Incident {
    private IncidentType type;
    private IncidentStatus status;
    private int severity;
    private int x;
    private int y;
    private int id;

    /**
     * Creates an incident with a type, severity, location in (X,Y) format and ID.
     */
    public Incident(IncidentType _type, int _severity, int _x, int _y, int _id) {
        type = _type;
        status = IncidentStatus.REPORTED;
        severity = _severity;
        x = _x;
        y = _y;
        id = _id;
    }

    /** Gets the incident type. */
    public IncidentType getType() {
        return type;
    }

    /** Gets the incident status. */
    public IncidentStatus getStatus() {
        return status;
    }

    /** Gets the severity level. */
    public int getSeverity() {
        return severity;
    }

    /** Gets the X coordinate of the incident. */
    public int getX() {
        return x;
    }

    /** Gets the Y coordinate of the incident. */
    public int getY() {
        return y;
    }

    /** Gets the incident ID. */
    public int getId() {
        return id;
    }

    /** Sets the incident type. */
    public void setType(IncidentType _type) {
        type = _type;
    }

    /** Sets the incident status. */
    public void setStatus(IncidentStatus _status) {
        status = _status;
    }

    /** Sets the severity level. */
    public void setSeverity(int _severity) {
        severity = _severity;
    }

    /** Sets the X coordinate. */
    public void setX(int _x) {
        x = _x;
    }

    /** Sets the Y coordinate. */
    public void setY(int _y) {
        y = _y;
    }

    /** Sets the incident ID. */
    public void setId(int _id) {
        id = _id;
    }
}