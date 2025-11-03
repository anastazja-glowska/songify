package com.songify.domain.crud;

class SongifyCrudFacadeConfiguration {


    public static SongifyCrudFasade createSongifyCrud(final SongRepository songRepository,
                                                      final GenreRepository genreRepository,
                                                      final ArtistRepository artistRepository,
                                                      final AlbumRepository albumRepository) {
        SongRetrierver songRetriever = new SongRetrierver(songRepository);
        SongUpdater songUpdater = new SongUpdater(songRetriever, songRepository);
        AlbumAdder albumAdder = new AlbumAdder(songRetriever, albumRepository);
        ArtistRetriever artistRetriever = new ArtistRetriever(artistRepository);
        AlbumRetriever albumRetriever = new AlbumRetriever(albumRepository);
        GenreDeleter genreDeleter = new GenreDeleter(genreRepository);
        SongDeleter songDeleter = new SongDeleter(songRepository, songRetriever, genreDeleter, songUpdater);
        GenreRetrever genreRetrever = new GenreRetrever(genreRepository);
        GenreAssigner genreAssigner = new GenreAssigner(genreRetrever, songRetriever);
        SongAdder songAdder = new SongAdder(songRepository, genreAssigner);
        ArtistAdder artistAdder = new ArtistAdder(artistRepository, albumAdder, songAdder);
        GenreAdder genreAdder = new GenreAdder(genreRepository);
        AlbumDeleter albumDeleter = new AlbumDeleter(albumRepository);
        ArtistDeleter artistDeleter = new ArtistDeleter(artistRepository, albumRetriever, artistRetriever, songDeleter,
                albumDeleter, albumRepository, songRepository);
        ArtistAssigner artistAssigner = new ArtistAssigner(artistRetriever, albumRetriever);
        ArtistUpdater artistUpdater = new ArtistUpdater(artistRetriever);
        return new SongifyCrudFasade(
                songAdder,
                songRetriever,
                songDeleter,
                songUpdater,
                artistAdder,
                genreAdder,
                albumAdder,
                artistRetriever,
                albumRetriever,
                artistDeleter,
                artistAssigner,
                artistUpdater,
                genreRetrever,
                genreAssigner
        );
    }
}
