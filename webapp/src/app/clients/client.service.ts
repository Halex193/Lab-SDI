import {HttpClient} from "@angular/common/http";
import {Client} from "./client.model";
import {Injectable} from "@angular/core";
import {Service} from "../shared/Service";

const URL = 'http://localhost:8080/api/clients';

@Injectable()
export class ClientService extends Service<Client>
{

  identify(baseURL: any, id: string): string
  {
    return URL + "/" + id
  }

  constructor(httpClient: HttpClient)
  {
    super(httpClient, URL);
  }

  getId(client: Client): string
  {
    return client.id.toString()
  }
}
