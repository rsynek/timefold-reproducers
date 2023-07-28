package org.rsynek.timefold.reproducers.solver.issue190.domain;

import ai.timefold.solver.core.api.domain.entity.PlanningEntity;
import ai.timefold.solver.core.api.domain.lookup.PlanningId;
import ai.timefold.solver.core.api.domain.variable.PlanningListVariable;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@PlanningEntity
public class Vehicle {

    private long id;

    private Location startLocation;


    private List<Visit> visits;

    private LocalTime departureTime;

    private Duration maxTravelTimePerVisitHardLimit;

    public Vehicle() {
    }

    public Vehicle(long id, Location startLocation, LocalTime departureTime, Duration maxTravelTimePerVisitHardLimit) {
        this.id = id;
        this.startLocation = startLocation;
        this.visits = new ArrayList<>();
        this.departureTime = departureTime;
        this.maxTravelTimePerVisitHardLimit = maxTravelTimePerVisitHardLimit;
    }

    @PlanningId
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Location getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(Location startLocation) {
        this.startLocation = startLocation;
    }

    @PlanningListVariable
    public List<Visit> getVisits() {
        return visits;
    }

    public void setVisits(List<Visit> visits) {
        this.visits = visits;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public Duration getMaxTravelTimePerVisitHardLimit() {
        return maxTravelTimePerVisitHardLimit;
    }

    // ************************************************************************
    // Complex methods
    // ************************************************************************

    public long getTotalDistanceMeters() {
        if (visits.isEmpty()) {
            return 0;
        }

        return visits.size();
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                '}';
    }
}
