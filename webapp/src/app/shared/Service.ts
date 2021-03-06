import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {Entity} from "./Entity";

export abstract class Service<T extends Entity>
{
  protected constructor(private httpClient: HttpClient, private baseURL)
  {
  }

  getItems(): Observable<T[]>
  {
    return this.httpClient.get<T[]>(this.baseURL, {withCredentials: true});
  }

  getItem(id: string): Observable<T>
  {
    return this.httpClient.get<T>(this.identify(this.baseURL, id),{withCredentials: true});
  }

  addItem(item: T): Observable<any>
  {
    return this.httpClient.post<T>(this.baseURL, item,{withCredentials: true});
  }

  updateItem(item: T): Observable<any>
  {
    const url = this.identify(this.baseURL, this.getId(item))
    const headers = new HttpHeaders({'Content-Type': 'application/json; charset=utf-8'});
    return this.httpClient.put<T>(url, item, {headers: headers, withCredentials: true});
  }

  deleteItem(id: string): Observable<any>
  {
    const url = this.identify(this.baseURL, id)
    return this.httpClient.delete(url,{withCredentials: true});
  }

  abstract identify(baseURL, id: string): string

  abstract getId(item: T): string
}
