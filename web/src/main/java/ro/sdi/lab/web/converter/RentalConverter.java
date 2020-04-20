package ro.sdi.lab.web.converter;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import ro.sdi.lab.core.model.Rental;
import ro.sdi.lab.web.controller.RentalController;
import ro.sdi.lab.web.dto.RentalDto;

@Component
public class RentalConverter implements Converter<Rental, RentalDto>
{
    @Override
    public Rental toModel(RentalDto rentalDto)
    {
        return new Rental(
                rentalDto.getMovieId(),
                rentalDto.getClientId(),
                LocalDateTime.parse(rentalDto.getTime(), RentalController.formatter)
        );
    }

    @Override
    public RentalDto toDto(Rental rental)
    {
        return RentalDto.builder()
                        .movieId(rental.getId().getMovieId())
                        .clientId(rental.getId().getClientId())
                        .time(RentalController.formatter.format(rental.getTime()))
                        .build();
    }
}
