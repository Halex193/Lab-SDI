package ro.sdi.lab.web.converter;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import jdk.jshell.spi.ExecutionControl;
import ro.sdi.lab.core.model.Rental;
import ro.sdi.lab.web.controller.RentalController;
import ro.sdi.lab.web.dto.RentalDto;

@Component
public class RentalConverter implements Converter<Rental, RentalDto>
{
    @Override
    public Rental toModel(RentalDto rentalDto)
    {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public RentalDto toDto(Rental rental)
    {
        return RentalDto.builder()
                        .movieId(rental.getMovie().getId())
                        .clientId(rental.getClient().getId())
                        .time(RentalController.formatter.format(rental.getTime()))
                        .build();
    }
}
