package org.rsynek.timefold.reproducers.solver.issue117.domain;

import ai.timefold.solver.core.api.domain.entity.PlanningEntity;
import ai.timefold.solver.core.api.domain.lookup.PlanningId;
import ai.timefold.solver.core.api.domain.variable.PlanningListVariable;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@PlanningEntity
public class Vehicle {

    @PlanningId
    private long id;
    private Depot depot;

    @PlanningListVariable
    private List<Customer> customers;

    private LocalTime departureTime;

    public Vehicle() {
    }

    public Vehicle(long id, Depot depot, LocalTime departureTime) {
        this.id = id;
        this.depot = depot;
        this.customers = new ArrayList<>();
        this.departureTime = departureTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Depot getDepot() {
        return depot;
    }

    public void setDepot(Depot depot) {
        this.depot = depot;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

// ************************************************************************
// Complex methods
// ************************************************************************

    public long getTotalDistanceMeters() {
        if (customers.isEmpty()) {
            return 0;
        }

        return customers.size();
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                '}';
    }
}
