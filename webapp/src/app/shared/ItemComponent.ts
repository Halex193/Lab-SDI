import {OnInit} from "@angular/core";
import {Service} from "./Service";
import {Router} from "@angular/router";
import {Entity} from "./Entity";

export abstract class ItemComponent<T extends Entity> implements OnInit
{
  errorMessage: string;
  items: Array<T>;
  formItem: T;

  protected constructor(protected service: Service<T>, protected router: Router, private entityName: string)
  {
  }

  ngOnInit(): void
  {
    this.getItems();
  }

  getItems()
  {
    this.service.getItems()
      .subscribe(
        items =>
        {
          this.items = items;
          console.log("Received items: ", items)
        },
        () => this.errorMessage = "Items not available"
      );
  }

  onSelect(item: T): void
  {
    console.log("Selected:", item)
    this.copyItem(item)
  }

  delete(item: T)
  {
    console.log("Deleting:", item)
    this.service.deleteItem(this.getId(item))
      .subscribe(_ => this.getItems(), () => this.errorMessage = this.entityName + " does not exist in the database");
  }

  add()
  {
    console.log("Adding:", this.formItem)
    this.service.addItem(this.formItem)
      .subscribe(_ => this.getItems(), () => this.errorMessage = this.entityName + " already exists in the database");
  }

  update()
  {
    console.log("Updating:", this.formItem)
    this.service.updateItem(this.formItem)
      .subscribe(_ => this.getItems(), () => this.errorMessage = this.entityName + " does not exist in the database");
  }

  abstract getId(item: T): string

  abstract copyItem(item: T)
}
