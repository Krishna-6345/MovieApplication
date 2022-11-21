export class Movie{
    constructor(
        public movieId: number,
        public overView: String,
        public popularity: number,
        public releaseDate: String,
        public title: String,
        public voteAverage: number,
        public posterPath: String,
        public watched: boolean 
    ){}
}
