package org.rsynek.timefold.reproducers.solver.issue190.domain;

import java.util.Map;

public class Location {

    private double latitude;
    private double longitude;

    private Map<Location, Long> drivingTimeMap;

    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public Map<Location, Long> getDrivingTimeMap() {
        return drivingTimeMap;
    }

    /**
     * Set the driving time map (in seconds).
     *
     * @param drivingTimeMap a map containing driving time from here to other locations
     */
    public void setDrivingTimeMap(Map<Location, Long> drivingTimeMap) {
        this.drivingTimeMap = drivingTimeMap;
    }

    /**
     * Driving time to the given location in seconds.
     *
     * @param location other location
     * @return driving time in seconds
     */
    public long getDrivingTimeTo(Location location) {
        return drivingTimeMap.get(location);
    }

    @Override
    public String toString() {
        return "Location{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
