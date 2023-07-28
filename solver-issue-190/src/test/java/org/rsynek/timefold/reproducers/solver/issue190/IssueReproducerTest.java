package org.rsynek.timefold.reproducers.solver.issue190;

import ai.timefold.solver.core.api.score.stream.ConstraintProvider;
import ai.timefold.solver.core.api.solver.Solver;
import ai.timefold.solver.core.api.solver.SolverFactory;
import ai.timefold.solver.core.config.solver.SolverConfig;
import ai.timefold.solver.core.config.solver.termination.TerminationConfig;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.rsynek.timefold.reproducers.solver.issue190.domain.HaversineDistanceCalculator;
import org.rsynek.timefold.reproducers.solver.issue190.domain.Location;
import org.rsynek.timefold.reproducers.solver.issue190.domain.Vehicle;
import org.rsynek.timefold.reproducers.solver.issue190.domain.VehicleRoutePlan;
import org.rsynek.timefold.reproducers.solver.issue190.domain.Visit;
import org.rsynek.timefold.reproducers.solver.issue190.solver.VehicleRoutingConstraintProvider;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

public class IssueReproducerTest {

    private static final Location LOCATION_1 = new Location(49.288087, 16.562172);
    private static final Location LOCATION_2 = new Location(49.190922, 16.624466);
    private static final Location LOCATION_3 = new Location(49.1767533245638, 16.50422914190477);

    @BeforeAll
    static void initDistanceMaps() {
        new HaversineDistanceCalculator().initDistanceMaps(Arrays.asList(LOCATION_1, LOCATION_2, LOCATION_3));
    }

    private Solver<VehicleRoutePlan> solver;

    public IssueReproducerTest() {
        SolverConfig solverConfig = new SolverConfig()
                .withSolutionClass(VehicleRoutePlan.class)
                .withEntityClasses(Vehicle.class)
                .withConstraintProviderClass(VehicleRoutingConstraintProvider.class)
                .withTerminationConfig(new TerminationConfig().withBestScoreLimit("0hard/*soft"));

        SolverFactory<VehicleRoutePlan> solverFactory = SolverFactory.create(solverConfig);
        this.solver = solverFactory.buildSolver();
    }

    @Test
    void reproduce() {
        solver.solve(createProblem());
    }

    private VehicleRoutePlan createProblem() {
        Visit visit1 = new Visit(2L, LocalTime.of(8, 0), LocalTime.of(18, 0), Duration.ofHours(1L), LOCATION_2);
        Visit visit2 = new Visit(3L, LocalTime.of(8, 0), LocalTime.of(9, 0), Duration.ofHours(1L), LOCATION_3);

        Vehicle vehicleA = new Vehicle(1L, LOCATION_1, LocalTime.of(7, 0), Duration.ofHours(1L));

        return new VehicleRoutePlan(List.of(vehicleA), List.of(visit1, visit2));
    }
}
