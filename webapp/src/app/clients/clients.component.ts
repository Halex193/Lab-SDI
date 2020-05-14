import {Component} from '@angular/core';
import {ClientService} from "./client.service";
import {Router} from "@angular/router";
import {ItemComponent} from "../shared/ItemComponent";
import {Client} from "./client.model";

@Component({
  selector: 'app-clients',
  templateUrl: './clients.component.html',
  styleUrls: ['./clients.component.css']
})
export class ClientsComponent extends ItemComponent<Client>
{

  constructor(clientService: ClientService, router: Router)
  {
    super(clientService, router, "Client");
    this.formItem = new Client()
  }

  getId(client: Client)
  {
    return client.id.toString()
  }

  copyItem(client: Client)
  {
    this.formItem.id = client.id
    this.formItem.name = client.name
  }
}
