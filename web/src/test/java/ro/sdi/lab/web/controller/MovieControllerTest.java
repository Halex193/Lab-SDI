package ro.sdi.lab.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import ro.sdi.lab.core.model.Movie;
import ro.sdi.lab.core.service.MovieService;
import ro.sdi.lab.web.converter.MovieConverter;
import ro.sdi.lab.web.dto.MovieDto;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MovieControllerTest
{
    private MockMvc mockMvc;

    @InjectMocks
    private MovieController movieController;

    @Mock
    private MovieService movieService;

    @Mock
    private MovieConverter movieConverter;

    private Movie movie1;
    private Movie movie2;
    private MovieDto movieDto1;
    private MovieDto movieDto2;


    @Before
    public void setup() throws Exception
    {
        initMocks(this);
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(movieController)
                .build();
        initData();
    }

    @Test
    public void getStudents() throws Exception
    {
        List<Movie> movies = Arrays.asList(movie1, movie2);
        List<MovieDto> movieDtos = Arrays.asList(movieDto1, movieDto2);
        when(movieService.getMovies()).thenReturn(movies);
        when(movieConverter.toDtos(movies)).thenReturn(movieDtos);

        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders.get("/movies"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", anyOf(is("Movie1"), is("Movie2"))));

        String result = resultActions.andReturn().getResponse().getContentAsString();

        verify(movieService, times(1)).getMovies();
        verify(movieConverter, times(1)).toDtos(movies);
        verifyNoMoreInteractions(movieService, movieConverter);
    }

    @Test
    public void updateStudent() throws Exception
    {
        when(movieConverter.toModel(movieDto1)).thenReturn(movie1);
        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders
                                 .put("/movies/{id}", movie1.getId())
                                 .contentType(MediaType.APPLICATION_JSON_UTF8)
                                 .content(toJsonString(movieDto1))
                )
                .andExpect(status().isOk());

        verify(movieService, times(1)).updateMovie(
                movie1.getId(),
                movieDto1.getName(),
                movieDto1.getGenre(),
                movieDto1.getRating()
        );
        verify(movieConverter, times(1)).toModel(movieDto1);
        verifyNoMoreInteractions(movieService, movieConverter);
    }

    @Test
    public void addMovie() throws Exception
    {
        when(movieConverter.toModel(movieDto1)).thenReturn(movie1);
        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders
                                 .post("/movies", movie1.getId())
                                 .contentType(MediaType.APPLICATION_JSON_UTF8)
                                 .content(toJsonString(movieDto1))
                )
                .andExpect(status().isOk());

        verify(movieService, times(1)).addMovie(
                movie1.getId(),
                movieDto1.getName(),
                movieDto1.getGenre(),
                movieDto1.getRating()
        );
        verify(movieConverter, times(1)).toModel(movieDto1);
        verifyNoMoreInteractions(movieService, movieConverter);
    }

    @Test
    public void deleteMovie() throws Exception
    {
        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders.delete("/movies/{id}", movie1.getId()))
                .andExpect(status().isOk());

        verify(movieService, times(1)).deleteMovie(movie1.getId());
        verifyNoMoreInteractions(movieService, movieConverter);
    }

    private String toJsonString(MovieDto movieDto)
    {
        try
        {
            return new ObjectMapper().writeValueAsString(movieDto);
        }
        catch (JsonProcessingException e)
        {
            throw new RuntimeException(e);
        }
    }

    private void initData()
    {
        movie1 = new Movie(1, "Movie1", "Genre1", 80);
        movie2 = new Movie(1, "Movie2", "Genre2", 50);

        movieDto1 = createDto(movie1);
        movieDto2 = createDto(movie2);

    }

    private MovieDto createDto(Movie movie)
    {
        return MovieDto.builder()
                       .id(movie.getId())
                       .name(movie.getName())
                       .genre(movie.getGenre())
                       .rating(movie.getRating())
                       .build();
    }
}