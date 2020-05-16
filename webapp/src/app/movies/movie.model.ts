import {Entity} from "../shared/Entity";

export class Movie implements Entity
{
  id: number;
  name: string;
  genre: string;
  rating: number;
}
