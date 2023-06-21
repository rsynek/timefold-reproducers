package org.rsynek.timefold.reproducers.solver.issue117.solver;

import java.time.LocalTime;
import java.util.Objects;

import ai.timefold.solver.core.api.domain.variable.VariableListener;
import ai.timefold.solver.core.api.score.director.ScoreDirector;
import org.rsynek.timefold.reproducers.solver.issue117.domain.Customer;
import org.rsynek.timefold.reproducers.solver.issue117.domain.VehicleRoutePlan;

public class ArrivalTimeUpdatingVariableListener implements VariableListener<VehicleRoutePlan, Customer> {

    private static final String ARRIVAL_TIME_FIELD = "arrivalTime";

    @Override
    public void beforeVariableChanged(ScoreDirector<VehicleRoutePlan> scoreDirector, Customer customer) {

    }

    @Override
    public void afterVariableChanged(ScoreDirector<VehicleRoutePlan> scoreDirector, Customer customer) {
        if (customer.getVehicle() == null) {
            if (customer.getArrivalTime() != null) {
                scoreDirector.beforeVariableChanged(customer, ARRIVAL_TIME_FIELD);
                customer.setArrivalTime(null);
                scoreDirector.afterVariableChanged(customer, ARRIVAL_TIME_FIELD);
            }
            return;
        }

        Customer previousCustomer = customer.getPreviousCustomer();
        LocalTime departureTime =
                previousCustomer == null ? customer.getVehicle().getDepartureTime() : previousCustomer.getDepartureTime();

        Customer nextCustomer = customer;
        LocalTime arrivalTime = calculateArrivalTime(nextCustomer, departureTime);
        while (nextCustomer != null && !Objects.equals(nextCustomer.getArrivalTime(), arrivalTime)) {
            scoreDirector.beforeVariableChanged(nextCustomer, ARRIVAL_TIME_FIELD);
            nextCustomer.setArrivalTime(arrivalTime);
            scoreDirector.afterVariableChanged(nextCustomer, ARRIVAL_TIME_FIELD);
            departureTime = nextCustomer.getDepartureTime();
            nextCustomer = nextCustomer.getNextCustomer();
            arrivalTime = calculateArrivalTime(nextCustomer, departureTime);
        }
    }

    @Override
    public void beforeEntityAdded(ScoreDirector<VehicleRoutePlan> scoreDirector, Customer customer) {

    }

    @Override
    public void afterEntityAdded(ScoreDirector<VehicleRoutePlan> scoreDirector, Customer customer) {

    }

    @Override
    public void beforeEntityRemoved(ScoreDirector<VehicleRoutePlan> scoreDirector, Customer customer) {

    }

    @Override
    public void afterEntityRemoved(ScoreDirector<VehicleRoutePlan> scoreDirector, Customer customer) {

    }

    private LocalTime calculateArrivalTime(Customer customer, LocalTime previousDepartureTime) {
        if (customer == null || previousDepartureTime == null) {
            return null;
        }
        return previousDepartureTime.plusMinutes(5L);
    }
}
