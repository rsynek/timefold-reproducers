package org.rsynek.timefold.reproducers.solver.issue190.domain;

import ai.timefold.solver.core.api.domain.solution.PlanningEntityCollectionProperty;
import ai.timefold.solver.core.api.domain.solution.PlanningScore;
import ai.timefold.solver.core.api.domain.solution.PlanningSolution;
import ai.timefold.solver.core.api.domain.solution.ProblemFactCollectionProperty;
import ai.timefold.solver.core.api.domain.valuerange.ValueRangeProvider;
import ai.timefold.solver.core.api.score.buildin.hardsoftlong.HardSoftLongScore;

import java.util.List;

@PlanningSolution
public class VehicleRoutePlan {

    private List<Vehicle> vehicles;

    private List<Visit> visits;

    private HardSoftLongScore score;

    public VehicleRoutePlan() {
    }

    public VehicleRoutePlan(List<Vehicle> vehicles, List<Visit> visits) {
        this.vehicles = vehicles;
        this.visits = visits;
    }

    @PlanningEntityCollectionProperty
    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    @ProblemFactCollectionProperty
    @ValueRangeProvider
    public List<Visit> getCustomers() {
        return visits;
    }

    @PlanningScore
    public HardSoftLongScore getScore() {
        return score;
    }

    public void setScore(HardSoftLongScore score) {
        this.score = score;
    }
}
