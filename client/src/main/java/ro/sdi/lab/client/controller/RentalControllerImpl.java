package ro.sdi.lab.client.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.sdi.lab.common.controller.RentalController;
import ro.sdi.lab.common.model.Rental;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@Service
public class RentalControllerImpl implements FutureRentalController {
    public static final Logger log = LoggerFactory.getLogger(RentalControllerImpl.class);

    @Autowired
    private ExecutorService executorService;

    @Autowired
    private RentalController rentalController;


    @Override
    public Future<Void> addRental(int movieId, int clientId, String time) {
        Callable<Void> callable = () -> {
            log.trace("Sending request: add rental movieid={}, clientid={}, time={}", movieId, clientId, time);
            rentalController.addRental(movieId, clientId, time);
            log.trace("Received response: added rental movieid={}, clientid={}, time={}", movieId, clientId, time);
            return null;
        };
        return executorService.submit(callable);
    }

    @Override
    public Future<Void> deleteRental(int movieId, int clientId) {
        Callable<Void> callable = () -> {
            log.trace("Sending request: delete rental movieid={}, clientid={}", movieId, clientId);
            rentalController.deleteRental(movieId, clientId);
            log.trace("Received response: deleted rental movieid={}, clientid={}", movieId, clientId);
            return null;
        };
        return executorService.submit(callable);
    }

    @Override
    public Future<Iterable<Rental>> getRentals() {
        Callable<Iterable<Rental>> callable = () -> {
            log.trace("Sending request: get all rentals");
            Iterable<Rental> rentals = rentalController.getRentals();
            log.trace("Received response: get all rentals");
            return rentals;
        };
        return executorService.submit(callable);
    }

    @Override
    public Future<Void> updateRental(int movieId, int clientId, String time) {
        Callable<Void> callable = () -> {
            log.trace("Sending request: update rental movieid={}, clientid={}, time={}", movieId, clientId, time);
            rentalController.updateRental(movieId, clientId, time);
            log.trace("Received response: updated rental movieid={}, clientid={}, time={}", movieId, clientId, time);
            return null;
        };
        return executorService.submit(callable);
    }

    @Override
    public Future<Iterable<Rental>> filterRentalsByMovieName(String name) {
        Callable<Iterable<Rental>> callable = () -> {
            log.trace("Sending request: filter rentals by name={}", name);
            Iterable<Rental> rentals = rentalController.filterRentalsByMovieName(name);
            log.trace("Received response: filtered rentals by name={}", name);
            return rentals;
        };
        return executorService.submit(callable);
    }
}
