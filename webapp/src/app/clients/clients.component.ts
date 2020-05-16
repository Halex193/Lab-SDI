import {Component} from '@angular/core';
import {ClientService} from "./client.service";
import {Router} from "@angular/router";
import {ItemComponent} from "../shared/ItemComponent";
import {Client} from "./client.model";

@Component({
  selector: 'app-clients',
  templateUrl: './clients.component.html',
  styleUrls: ['../shared/common.css']
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

  filterAndSortItems(clients: Client[]): Client[]
  {
    if (this.filter2)
      clients = clients.filter((client) => client.name.includes(this.filter2))
    if (this.sort2)
    {
      if (this.sort2 == 'id')
      {
        if (this.order2 == 'asc')
          clients.sort((client1, client2) => client1.id - client2.id)
        else
          clients.sort((client1, client2) => client2.id - client1.id)
      }
      if (this.sort2 == 'name')
      {
        if (this.order2 == 'asc')
          clients.sort((client1, client2) => client1.name.localeCompare(client2.name))
        else
          clients.sort((client1, client2) => client2.name.localeCompare(client1.name))
      }
    }
    return clients;
  }
}
