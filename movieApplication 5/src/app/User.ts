import { Movie } from "./Movie";

export class User{
    constructor(
        public userName:string,
        public password:string,
        public gender:string,
        public emailId:string,
        public movies: Movie,
        public watched: Movie,
      ){}

}