package ro.sdi.lab24.sorting;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import ro.sdi.lab.common.model.Movie;
import ro.sdi.lab.common.model.Sort;
import ro.sdi.lab.server.model.serialization.database.MovieTableAdapter;
import ro.sdi.lab.server.repository.DatabaseRepository;
import ro.sdi.lab.server.repository.SortingRepository;
import ro.sdi.lab.server.sorting.SortingUtils;

class SortingUtilsTest
{
    SortingRepository<Integer, Movie> repository;

    @BeforeEach
    void setUp()
    {
        repository = new DatabaseRepository<>(new MovieTableAdapter());
    }

    @AfterEach
    void tearDown()
    {
        repository = null;
    }

    @Test
    void sort()
    {
        List<TestClass> dopes = List.of(
                new TestClass(1.9f),
                new TestClass(2f),
                new TestClass(3f),
                new TestClass(0.8f)
        );
        dopes = new ArrayList<>(dopes);
        Sort criteria = new Sort(Sort.Direction.DESC, "boat");
        SortingUtils.sort(dopes, criteria).forEach(System.out::println);
    }

    static class TestClass
    {
        private float boat;
        private TestClass2 d = new TestClass2();

        public TestClass(float boat)
        {
            this.boat = boat;
        }

        @Override
        public String toString()
        {
            return "TestClass{" +
                    "boat=" + boat +
                    '}';
        }
    }

    static class TestClass2 implements Comparable<TestClass>
    {

        @Override
        public int compareTo(TestClass dope)
        {
            return 0;
        }
    }
}