package org.rsynek.timefold.reproducers.solver.issue190.solver;

import ai.timefold.solver.core.api.score.buildin.hardsoftlong.HardSoftLongScore;
import ai.timefold.solver.core.api.score.stream.Constraint;
import ai.timefold.solver.core.api.score.stream.ConstraintFactory;
import ai.timefold.solver.core.api.score.stream.ConstraintProvider;
import ai.timefold.solver.core.api.score.stream.Joiners;
import org.rsynek.timefold.reproducers.solver.issue190.domain.Visit;
import org.rsynek.timefold.reproducers.solver.issue190.domain.Vehicle;

import java.util.function.Function;

public class VehicleRoutingConstraintProvider implements ConstraintProvider {

    @Override
    public Constraint[] defineConstraints(ConstraintFactory factory) {
        return new Constraint[]{
                maxTravelTimePerVisitHardLimit(factory),
                serviceFinishedAfterDueTime(factory)
        };
    }

    // ************************************************************************
    // Hard constraints
    // ************************************************************************

    protected Constraint maxTravelTimePerVisitHardLimit(ConstraintFactory factory) {
        return factory.forEach(Vehicle.class)
                .filter(vehicle -> vehicle.getMaxTravelTimePerVisitHardLimit() != null)
                .join(Visit.class,
                        Joiners.equal(Function.identity(), Visit::getVehicle),
                        Joiners.lessThan(vehicleShift -> vehicleShift.getMaxTravelTimePerVisitHardLimit().toSeconds(),
                                visit -> visit.getDrivingTimeSecondsFromPreviousStandstill()))
                .penalizeLong(HardSoftLongScore.ONE_HARD,
                        (vehicleShift, visit) -> visit.getDrivingTimeSecondsFromPreviousStandstill()
                                - vehicleShift.getMaxTravelTimePerVisitHardLimit().toSeconds())
                .asConstraint("maxTravelTimePerVisitHardLimit");
    }

    // This works:
/*    protected Constraint maxTravelTimePerVisitHardLimit(ConstraintFactory factory) {
        return factory.forEach(Vehicle.class)
                .filter(vehicle -> vehicle.getMaxTravelTimePerVisitHardLimit() != null)
                .join(Visit.class,
                        Joiners.equal(Function.identity(), Visit::getVehicle),
                        Joiners.filtering((vehicleShift,
                                           visit) -> visit.getDrivingTimeSecondsFromPreviousStandstill() > vehicleShift
                                .getMaxTravelTimePerVisitHardLimit().toSeconds()))
                .penalizeLong(HardSoftLongScore.ONE_HARD,
                        (vehicleShift, visit) -> visit.getDrivingTimeSecondsFromPreviousStandstill()
                                - vehicleShift.getMaxTravelTimePerVisitHardLimit().toSeconds())
                .asConstraint("maxTravelTimePerVisitHardLimit");
    }*/


    protected Constraint serviceFinishedAfterDueTime(ConstraintFactory factory) {
        return factory.forEach(Visit.class)
                .filter(Visit::isServiceFinishedAfterDueTime)
                .penalizeLong(HardSoftLongScore.ONE_HARD,
                        Visit::getServiceFinishedDelayInMinutes)
                .asConstraint("serviceFinishedAfterDueTime");
    }
}
