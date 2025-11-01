package com.songify.domain.crud;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface SongRepository extends Repository<Song, Long> {

    Song save(Song song);

    @Query("""
select s from Song s
join fetch s.genre
""")
    List<Song> findAll (Pageable pageable);

    @Query("select s from Song s where s.id = :id")
    Optional<Song> findById(Long id);

    @Modifying
    @Query("delete from Song s where s.id =:id")
    void deleteById(Long id);


    @Modifying
    @Query("UPDATE Song s set s.name = :#{#newSong.name} where s.id = :id" )
    void updateById(Long id, Song newSong);

    @Query("select s from Song s where s.genre.id = :id")
    List<Song> findAllSongsByGenreId(@Param("id") Long id);

    @Modifying
    @Query("UPDATE Song s SET s.genre = NULL WHERE s.id = :id")
    void updateSongsGenreId(@Param("id") Long id);


    @Modifying
    @Query("delete from Song s where s.id in :ids")
    int deleteByIdIn(Collection<Long> ids);


    @Modifying
    @Query("DELETE FROM Song s WHERE s.id IN :ids")
    void deleteSongsByIds(@Param("ids") Set<Long> ids);

}
