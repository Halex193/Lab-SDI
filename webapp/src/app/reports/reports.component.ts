import { Component, OnInit } from '@angular/core';
import {Service} from "../shared/Service";
import {Router} from "@angular/router";
import {RentedMovieStatistic} from "./rentedmoviestatistic.model";
import {ClientGenre} from "./clientgenre.model";
import {ReportService} from "./report.service";

@Component({
  selector: 'app-reports',
  templateUrl: './reports.component.html',
  styleUrls: ['../shared/common.css']
})
export class ReportsComponent implements OnInit {

  errorMessage: string;
  rentedMovieStatistics: Array<RentedMovieStatistic>;
  clientGenres : Array<ClientGenre>

  constructor(protected service: ReportService, protected router: Router)
  {
  }

  ngOnInit(): void
  {
    this.getItems();
  }

  getItems()
  {
    this.service.getRentedMovieStatistics()
      .subscribe(
        items =>
        {
          this.rentedMovieStatistics = items;
          console.log("Received rented movie statistics: ", items)
        },
        () => this.errorMessage = "Unaunthorized"
      );

    this.service.getClientGenres()
      .subscribe(
        items =>
        {
          this.clientGenres = items;
          console.log("Received client genres: ", items)
        },
        () => this.errorMessage = "Unaunthorized"
      );
  }

  logEntities()
  {
    this.service.logEntities()
  }
}
