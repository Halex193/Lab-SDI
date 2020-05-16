import {Entity} from "../shared/Entity";

export class Rental implements Entity
{
  movieId: number;
  clientId: number;
  time: string;
}
