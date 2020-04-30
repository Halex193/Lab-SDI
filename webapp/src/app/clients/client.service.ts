import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {map} from "rxjs/operators";
import {Client} from "./client.model";
import {Injectable} from "@angular/core";

export class ClientList
{
  clients: Client[]
}

@Injectable()
export class ClientService
{
  private URL = 'http://localhost:8080/api/clients';

  constructor(private httpClient: HttpClient)
  {
  }

  getClients(): Observable<Client[]>
  {
    return this.httpClient.get<ClientList>(this.URL).pipe(map(clientList => clientList.clients));
  }

  /*getClient(id: number): Observable<Client> {
    return this.getClients()
      .pipe(
        map(students => students.find(student => student.id === id))
      );
  }*/

  addClient(client: Client)
  {
    console.log("addClient", client);

    return this.httpClient.post<Client>(this.URL, client);
  }

  update(client)
  {
    const url = `${this.URL}/${client.id}`;
    return this.httpClient.put<Client>(url, client);
  }

  deleteClient(id: number)
  {
    const url = `${this.URL}/${id}`;
    return this.httpClient.delete(url);
  }

}
