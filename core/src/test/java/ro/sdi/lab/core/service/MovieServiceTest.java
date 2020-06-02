package ro.sdi.lab.core.service;


import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import java.util.List;

import ro.sdi.lab.core.ITConfig;
import ro.sdi.lab.core.model.Movie;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ITConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
                         DirtiesContextTestExecutionListener.class,
                         TransactionalTestExecutionListener.class,
                         DbUnitTestExecutionListener.class})
@DatabaseSetup("/META-INF/dbtest/db-data.xml")
public class MovieServiceTest
{
    @Autowired
    private MovieService movieService;

    @Test
    public void testGet() throws Exception
    {
        List<Movie> students = movieService.getMovies();
        assertEquals("There should be three clients", 3, students.size());
    }

    @Test
    public void testAdd() throws Exception
    {
        movieService.addMovie(7, "moviename", "genre", 80);
        assertEquals(4, movieService.getMovies().size());
    }

    @Test
    public void testDelete() throws Exception
    {
        movieService.deleteMovie(1);
        assertEquals(2, movieService.getMovies().size());
    }

    @Test
    public void testUpdate() throws Exception
    {
        movieService.updateMovie(1, "newname", "new genre", 50);
        assertEquals(3, movieService.getMovies().size());
        assertTrue(movieService.getMovies().stream().anyMatch(movie -> movie.getName().equals("newname")));

    }
}