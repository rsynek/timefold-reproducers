package org.rsynek.timefold.reproducers.issue117;

import ai.timefold.solver.core.api.solver.Solver;
import ai.timefold.solver.core.api.solver.SolverFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.rsynek.timefold.reproducers.issue117.domain.Customer;
import org.rsynek.timefold.reproducers.issue117.domain.Depot;
import org.rsynek.timefold.reproducers.issue117.domain.Vehicle;
import org.rsynek.timefold.reproducers.issue117.domain.VehicleRoutePlan;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

@QuarkusTest
public class IssueReproducerTest {
    @Inject
    SolverFactory<VehicleRoutePlan> solverFactory;

    @Test
    void reproduce() {
        Solver<VehicleRoutePlan> solver = solverFactory.buildSolver();
        solver.solve(createProblem());
    }

    private VehicleRoutePlan createProblem() {
        Customer customer1 = new Customer(2L, LocalTime.of(8, 0), LocalTime.of(18, 0), Duration.ofHours(1L));
        customer1.setArrivalTime(LocalTime.of(8, 40));
        Customer customer2 = new Customer(3L, LocalTime.of(8, 0), LocalTime.of(9, 0), Duration.ofHours(1L));
        customer2.setArrivalTime(LocalTime.of(8 + 1 + 1, 30));
        Depot depot = new Depot(1L);
        Vehicle vehicleA = new Vehicle(1L, depot, LocalTime.of(7, 0));

        return new VehicleRoutePlan(List.of(depot), List.of(vehicleA), List.of(customer1, customer2));
    }
}
