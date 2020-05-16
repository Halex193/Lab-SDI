import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HttpClientModule} from "@angular/common/http";
import {FormsModule} from "@angular/forms";
import { ClientsComponent } from './clients/clients.component';
import { MoviesComponent } from './movies/movies.component';
import { RentalsComponent } from './rentals/rentals.component';
import {ClientService} from "./clients/client.service";
import { ReportsComponent } from './reports/reports.component';
import {MovieService} from "./movies/movie.service";
import {RentalService} from "./rentals/rental.service";
import {ReportService} from "./reports/report.service";

@NgModule({
  declarations: [
    AppComponent,
    ClientsComponent,
    MoviesComponent,
    RentalsComponent,
    ReportsComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    AppRoutingModule,
  ],
  providers: [ClientService, MovieService, RentalService, ReportService],
  bootstrap: [AppComponent]
})
export class AppModule { }
