package org.rsynek.timefold.reproducers.solver.issue117.domain;

import ai.timefold.solver.core.api.domain.entity.PlanningEntity;
import ai.timefold.solver.core.api.domain.variable.InverseRelationShadowVariable;
import ai.timefold.solver.core.api.domain.variable.NextElementShadowVariable;
import ai.timefold.solver.core.api.domain.variable.PreviousElementShadowVariable;
import ai.timefold.solver.core.api.domain.variable.ShadowVariable;
import org.rsynek.timefold.reproducers.solver.issue117.solver.ArrivalTimeUpdatingVariableListener;

import java.time.Duration;
import java.time.LocalTime;

@PlanningEntity
public class Customer {

    private long id;
    private LocalTime readyTime;
    private LocalTime dueTime;
    private Duration serviceDuration;
    private Vehicle vehicle;
    private Customer previousCustomer;
    private Customer nextCustomer;
    private LocalTime arrivalTime;

    public Customer() {
    }

    public Customer(long id, LocalTime readyTime, LocalTime dueTime, Duration serviceDuration) {
        this.id = id;
        this.readyTime = readyTime;
        this.dueTime = dueTime;
        this.serviceDuration = serviceDuration;
    }

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

    @InverseRelationShadowVariable(sourceVariableName = "customers")
    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    @PreviousElementShadowVariable(sourceVariableName = "customers")
    public Customer getPreviousCustomer() {
        return previousCustomer;
    }

    public void setPreviousCustomer(Customer previousCustomer) {
        this.previousCustomer = previousCustomer;
    }

    @NextElementShadowVariable(sourceVariableName = "customers")
    public Customer getNextCustomer() {
        return nextCustomer;
    }

    public void setNextCustomer(Customer nextCustomer) {
        this.nextCustomer = nextCustomer;
    }

    @ShadowVariable(variableListenerClass = ArrivalTimeUpdatingVariableListener.class, sourceVariableName = "vehicle")
    @ShadowVariable(variableListenerClass = ArrivalTimeUpdatingVariableListener.class, sourceVariableName = "previousCustomer")
    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalTime arrivalTime) {
        this.arrivalTime = arrivalTime;
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

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                '}';
    }
}
