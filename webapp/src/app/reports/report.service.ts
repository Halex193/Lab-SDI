import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {RentedMovieStatistic} from "./rentedmoviestatistic.model";
import {ClientGenre} from "./clientgenre.model";
import {Injectable} from "@angular/core";

@Injectable()
export class ReportService
{
  baseURL = 'http://localhost:8080/api/statistics';

  constructor(private httpClient: HttpClient)
  {
  }

  getRentedMovieStatistics(): Observable<RentedMovieStatistic[]>
  {
    return this.httpClient.get<RentedMovieStatistic[]>(this.baseURL + "/movies");
  }

  getClientGenres(): Observable<ClientGenre[]>
  {
    return this.httpClient.get<ClientGenre[]>(this.baseURL + "/genres");
  }

  logEntities()
  {
    this.httpClient.post(this.baseURL + "/log", "").subscribe();
  }
}
