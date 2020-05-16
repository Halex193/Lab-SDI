import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {ClientsComponent} from "./clients/clients.component";
import {MoviesComponent} from "./movies/movies.component";
import {RentalsComponent} from "./rentals/rentals.component";
import {ReportsComponent} from "./reports/reports.component";


const routes: Routes = [
  {path: "clients", component: ClientsComponent},
  {path: "movies", component: MoviesComponent},
  {path: "rentals", component: RentalsComponent},
  {path: "reports", component: ReportsComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule
{
}
