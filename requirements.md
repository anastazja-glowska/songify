SONGIFY: AN APPLICATION FOR MANAGING SONGS, ARTISTS, AND ALBUMS
You can add an artist (artist name)

1 You can add a music genre (genre name)

2 You can add an album (title, release date — but it must contain at least one song)

3 You can add a song (title, duration, release date, and the artist it belongs to)

4 You can delete an artist (this also deletes their songs and albums)

5 You can delete a music genre (but only if no song exists with that genre)

6You can delete an album (but only if no songs are assigned to it)

7 You can delete a song

8 You can edit an artist’s songs and their name

9 You can edit the name of a music genre

10 You can edit an album (add songs, add artists, change the album name)

11 You can edit a song (duration, artist, song title)

12 Songs can only be assigned to albums

13Artists can be assigned to albums (an album can have multiple artists, and an artist can have multiple albums)

14 Only one music genre can be assigned to a song

15 You can display all songs

16 You can display all genres

17 You can display all artists

18 You can display all albums

19 You can display all albums with their artists and songs

20 You can display specific music genres along with their songs

21You can display specific artists along with their albums

22 We want persistent data (data should be saved permanently)



HAPPY PATH (user tworzy album "Eminema" z piosenkami "Til i collapse", "Lose Yourself", o gatunku Rap)

given there is no songs, artists, albums and genres created before

1. when I go to /song then I can see no songs
2. when I post to /song with Song "Till i collapse" then Song "Til i collapse" is returned with id 1
3. when I post to /song with Song "Lose Yourself" then Song "Lose Yourself" is returned with id 2
4. when I go to /genre then I can see no genres
5. when I post to /genre with Genre "Rap" then Genre "Rap" is returned with id 1
6. when I go to /song/1 then I can see default genre
7. when I put to /song/1/genre/1 then Genre with id 1 ("Rap") is added to Song with id 1 ("Til i collapse")
8. when I go to /song/1 then I can see "Rap" genre
9. when I put to /song/2/genre/1 then Genre with id 1 ("Rap") is added to Song with id 2 ("Lose Yourself")
10. when I go to /album then I can see no albums
11. when I post to /album with Album "EminemAlbum1" then Album "EminemAlbum1" is returned with id 1
12. when I go to /album/1 then I can see no songs added to album
13. when I put to /album/1/song/1 then Song with id 1 ("Til i collapse") is added to Album with id 1 ("EminemAlbum1")
14. when I put to /album/1/song/2 then Song with id 2 ("Lose Yourself") is added to Album with id 1 ("EminemAlbum1")
15. when I go to /album/1/song then I can see 2 songs (id 1, id 2)
16. when I post to /artist with Artist "Eminem" then Artist "Eminem" is returned with id 1
17. when I put to /album/1/artist/2 then Artist with id 1 ("Eminem") is added to Album with id 1 ("EminemAlbum1")