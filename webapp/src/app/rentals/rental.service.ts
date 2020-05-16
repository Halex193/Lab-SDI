import {HttpClient} from "@angular/common/http";
import {Injectable} from "@angular/core";
import {Service} from "../shared/Service";
import {Rental} from "./rental.model";

const URL = 'http://localhost:8080/api/rentals';

@Injectable()
export class RentalService extends Service<Rental>
{

  identify(baseURL: any, id: string): string
  {
    return URL + "/" + id
  }

  constructor(httpClient: HttpClient)
  {
    super(httpClient, URL);
  }

  getId(rental: Rental): string
  {
    return rental.movieId.toString() + "-" + rental.clientId.toString()
  }
}
