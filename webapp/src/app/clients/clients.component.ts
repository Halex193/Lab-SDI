import { Component, OnInit } from '@angular/core';
import {Client} from "./client.model";
import {ClientService} from "./client.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-clients',
  templateUrl: './clients.component.html',
  styleUrls: ['./clients.component.css']
})
export class ClientsComponent implements OnInit {
  errorMessage: string;
  clients: Array<Client>;
  selectedClient: Client;

  constructor(private clientService: ClientService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.getClients();
  }

  getClients() {
    this.clientService.getClients()
      .subscribe(
        clients => this.clients = clients,
        error => this.errorMessage = <any>error
      );
  }

  onSelect(client: Client): void {
    this.selectedClient = client;
  }

  deleteClient(client: Client) {
    console.log("deleting client: ", client);

    this.clientService.deleteClient(client.id)
      .subscribe(_ => {
        console.log("client deleted");

        this.getClients()
      });
  }
}
