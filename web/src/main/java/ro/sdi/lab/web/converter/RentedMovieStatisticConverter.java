package ro.sdi.lab.web.converter;

import org.springframework.stereotype.Component;

import ro.sdi.lab.core.model.dto.RentedMovieStatistic;
import ro.sdi.lab.web.dto.RentedMovieStatisticDto;

@Component
public class RentedMovieStatisticConverter
        implements Converter<RentedMovieStatistic, RentedMovieStatisticDto>
{
    @Override
    public RentedMovieStatistic toModel(RentedMovieStatisticDto rentedMovieStatisticDto)
    {
        return new RentedMovieStatistic(
                rentedMovieStatisticDto.getMovieName(),
                rentedMovieStatisticDto.getNumberOfRentals()
        );
    }

    @Override
    public RentedMovieStatisticDto toDto(RentedMovieStatistic rentedMovieStatistic)
    {
        return RentedMovieStatisticDto.builder()
                                      .movieName(rentedMovieStatistic.getMovieName())
                                      .numberOfRentals(rentedMovieStatistic.getNumberOfRentals())
                                      .build();
    }
}
