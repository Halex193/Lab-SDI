import {Component} from '@angular/core';
import {ItemComponent} from "../shared/ItemComponent";
import {Movie} from "./movie.model";
import {Router} from "@angular/router";
import {MovieService} from "./movie.service";

@Component({
  selector: 'app-movies',
  templateUrl: './movies.component.html',
  styleUrls: ['../shared/common.css']
})
export class MoviesComponent extends ItemComponent<Movie>
{

  constructor(movieService: MovieService, router: Router)
  {
    super(movieService, router, "Movie");
    this.formItem = new Movie()
  }

  getId(movie: Movie)
  {
    return movie.id.toString()
  }

  copyItem(item: Movie)
  {
    this.formItem.id = item.id
    this.formItem.name = item.name
    this.formItem.genre = item.genre
    this.formItem.rating = item.rating
  }

  filterAndSortItems(movies: Movie[]): Movie[]
  {
    if (this.filter2)
      movies = movies.filter((movie) => movie.name.includes(this.filter2))
    if (this.sort2)
    {
      if (this.sort2 == 'id')
      {
        if (this.order2 == 'asc')
          movies.sort((movie1, movie2) => movie1.id - movie2.id)
        else
          movies.sort((movie1, movie2) => movie2.id - movie1.id)
      }
      if (this.sort2 == 'name')
      {
        if (this.order2 == 'asc')
          movies.sort((movie1, movie2) => movie1.name.localeCompare(movie2.name))
        else
          movies.sort((movie1, movie2) => movie2.name.localeCompare(movie1.name))
      }
      if (this.sort2 == 'genre')
      {
        if (this.order2 == 'asc')
          movies.sort((movie1, movie2) => movie1.genre.localeCompare(movie2.genre))
        else
          movies.sort((movie1, movie2) => movie2.genre.localeCompare(movie1.genre))
      }
      if (this.sort2 == 'rating')
      {
        if (this.order2 == 'asc')
          movies.sort((movie1, movie2) => movie1.rating - movie2.rating)
        else
          movies.sort((movie1, movie2) => movie2.rating - movie1.rating)
      }
    }
    return movies;
  }
}
