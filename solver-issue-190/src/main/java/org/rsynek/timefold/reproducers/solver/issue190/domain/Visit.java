package org.rsynek.timefold.reproducers.solver.issue190.domain;

import ai.timefold.solver.core.api.domain.entity.PlanningEntity;
import ai.timefold.solver.core.api.domain.lookup.PlanningId;
import ai.timefold.solver.core.api.domain.variable.InverseRelationShadowVariable;
import ai.timefold.solver.core.api.domain.variable.NextElementShadowVariable;
import ai.timefold.solver.core.api.domain.variable.PreviousElementShadowVariable;
import ai.timefold.solver.core.api.domain.variable.ShadowVariable;
import org.rsynek.timefold.reproducers.solver.issue190.solver.ArrivalTimeUpdatingVariableListener;

import java.time.Duration;
import java.time.LocalTime;

@PlanningEntity
public class Visit {

    private long id;
    private LocalTime readyTime;
    private LocalTime dueTime;
    private Duration serviceDuration;
    private Location location;

    // shadow variables
    private Vehicle vehicle;
    private Visit previousVisit;
    private Visit nextVisit;
    private LocalTime arrivalTime;

    public Visit() {
    }

    public Visit(long id, LocalTime readyTime, LocalTime dueTime, Duration serviceDuration, Location location) {
        this.id = id;
        this.readyTime = readyTime;
        this.dueTime = dueTime;
        this.serviceDuration = serviceDuration;
        this.location = location;
    }

    @PlanningId
    public long getId() {
        return id;
    }

    public LocalTime getReadyTime() {
        return readyTime;
    }

    public LocalTime getDueTime() {
        return dueTime;
    }

    public Duration getServiceDuration() {
        return serviceDuration;
    }

    @InverseRelationShadowVariable(sourceVariableName = "visits")
    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    @PreviousElementShadowVariable(sourceVariableName = "visits")
    public Visit getPreviousVisit() {
        return previousVisit;
    }

    public void setPreviousVisit(Visit previousVisit) {
        this.previousVisit = previousVisit;
    }

    @NextElementShadowVariable(sourceVariableName = "visits")
    public Visit getNextVisit() {
        return nextVisit;
    }

    public void setNextVisit(Visit nextVisit) {
        this.nextVisit = nextVisit;
    }

    @ShadowVariable(variableListenerClass = ArrivalTimeUpdatingVariableListener.class, sourceVariableName = "vehicle")
    @ShadowVariable(variableListenerClass = ArrivalTimeUpdatingVariableListener.class, sourceVariableName = "previousVisit")
    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Location getLocation() {
        return location;
    }

    // ************************************************************************
    // Complex methods
    // ************************************************************************


    public LocalTime getDepartureTime() {
        if (arrivalTime == null) {
            return null;
        }
        LocalTime startServiceTime = arrivalTime.isBefore(readyTime) ? readyTime : arrivalTime;
        return startServiceTime.plus(serviceDuration);
    }

    public boolean isServiceFinishedAfterDueTime() {
        return arrivalTime != null
                && arrivalTime.plus(serviceDuration).isAfter(dueTime);
    }


    public long getServiceFinishedDelayInMinutes() {
        if (arrivalTime == null) {
            return 0;
        }
        return Duration.between(dueTime, arrivalTime.plus(serviceDuration)).toMinutes();
    }

    public long getDrivingTimeSecondsFromPreviousStandstill() {
        if (vehicle == null) {
            throw new IllegalStateException(
                    "This method must not be called when the shadow variables are not initialized yet.");
        }
        if (previousVisit == null) {
            return vehicle.getStartLocation().getDrivingTimeTo(location);
        }
        return previousVisit.getLocation().getDrivingTimeTo(location);
    }
    @Override
    public String toString() {
        return "Visit{" +
                "id=" + id +
                '}';
    }
}
