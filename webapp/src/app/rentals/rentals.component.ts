import {Component} from '@angular/core';
import {ItemComponent} from "../shared/ItemComponent";
import {RentalService} from "./rental.service";
import {Router} from "@angular/router";
import {Rental} from "./rental.model";

@Component({
  selector: 'app-rentals',
  templateUrl: './rentals.component.html',
  styleUrls: ['../shared/common.css']
})
export class RentalsComponent extends ItemComponent<Rental>
{
  constructor(rentalService: RentalService, router: Router)
  {
    super(rentalService, router, "Rental");
    this.formItem = new Rental()
  }

  getId(rental: Rental)
  {
    return rental.movieId.toString() + "-" + rental.clientId.toString()
  }

  copyItem(item: Rental)
  {
    this.formItem.movieId = item.movieId
    this.formItem.clientId = item.clientId
    this.formItem.time = item.time
  }

  filterAndSortItems(rentals: Rental[]): Rental[]
  {
    if (this.filter2)
      rentals = rentals.filter((rental) => rental.time.includes(this.filter2))
    if (this.sort2)
    {
      if (this.sort2 == 'movieId')
      {
        if (this.order2 == 'asc')
          rentals.sort((rental1, rental2) => rental1.movieId - rental2.movieId)
        else
          rentals.sort((rental1, rental2) => rental2.movieId - rental1.movieId)
      }
      if (this.sort2 == 'clientId')
      {
        if (this.order2 == 'asc')
          rentals.sort((rental1, rental2) => rental1.clientId - rental2.clientId)
        else
          rentals.sort((rental1, rental2) => rental2.clientId - rental1.clientId)
      }
      if (this.sort2 == 'time')
      {
        if (this.order2 == 'asc')
          rentals.sort((rental1, rental2) => rental1.time.localeCompare(rental2.time))
        else
          rentals.sort((rental1, rental2) => rental2.time.localeCompare(rental1.time))
      }
    }
    return rentals;
  }
}
