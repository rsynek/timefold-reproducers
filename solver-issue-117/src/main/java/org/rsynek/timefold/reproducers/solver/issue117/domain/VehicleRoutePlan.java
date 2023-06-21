package org.rsynek.timefold.reproducers.solver.issue117.domain;

import ai.timefold.solver.core.api.domain.solution.PlanningEntityCollectionProperty;
import ai.timefold.solver.core.api.domain.solution.PlanningScore;
import ai.timefold.solver.core.api.domain.solution.PlanningSolution;
import ai.timefold.solver.core.api.domain.solution.ProblemFactCollectionProperty;
import ai.timefold.solver.core.api.domain.valuerange.ValueRangeProvider;
import ai.timefold.solver.core.api.score.buildin.hardsoftlong.HardSoftLongScore;

import java.util.List;

@PlanningSolution
public class VehicleRoutePlan {

    private List<Depot> depots;


    private List<Vehicle> vehicles;

    private List<Customer> customers;

    private HardSoftLongScore score;

    public VehicleRoutePlan() {
    }

    public VehicleRoutePlan(List<Depot> depots,
                            List<Vehicle> vehicles,
                            List<Customer> customers) {
        this.depots = depots;
        this.vehicles = vehicles;
        this.customers = customers;
    }

    @ProblemFactCollectionProperty
    public List<Depot> getDepots() {
        return depots;
    }

    public void setDepots(List<Depot> depots) {
        this.depots = depots;
    }

    @PlanningEntityCollectionProperty
    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    @ProblemFactCollectionProperty
    @ValueRangeProvider
    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    @PlanningScore
    public HardSoftLongScore getScore() {
        return score;
    }

    public void setScore(HardSoftLongScore score) {
        this.score = score;
    }
}
