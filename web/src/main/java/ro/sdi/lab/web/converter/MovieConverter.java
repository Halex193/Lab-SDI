package ro.sdi.lab.web.converter;

import org.springframework.stereotype.Component;

import ro.sdi.lab.core.model.Movie;
import ro.sdi.lab.web.dto.MovieDto;

@Component
public class MovieConverter implements Converter<Movie, MovieDto>
{
    @Override
    public Movie toModel(MovieDto movieDto)
    {
        return new Movie(
                movieDto.getId(),
                movieDto.getName(),
                movieDto.getGenre(),
                movieDto.getRating()
        );
    }

    @Override
    public MovieDto toDto(Movie movie)
    {
        return MovieDto.builder()
                       .id(movie.getId())
                       .name(movie.getName())
                       .genre(movie.getGenre())
                       .rating(movie.getRating())
                       .build();
    }
}
