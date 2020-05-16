import {HttpClient} from "@angular/common/http";
import {Injectable} from "@angular/core";
import {Service} from "../shared/Service";
import {Movie} from "./movie.model";

const URL = 'http://localhost:8080/api/movies';

@Injectable()
export class MovieService extends Service<Movie>
{

  constructor(httpClient: HttpClient)
  {
    super(httpClient, URL);
  }

  identify(baseURL: any, id: string): string
  {
    return URL + "/" + id
  }

  getId(movie: Movie): string
  {
    return movie.id.toString()
  }
}
