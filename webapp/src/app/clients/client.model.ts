import {Entity} from "../shared/Entity";

export class Client implements Entity
{
  id: number;
  name: string;

  public getIdString(): string
  {
    return this.id.toString()
  }
}
