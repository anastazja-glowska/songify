package com.songify.domain.crud;


import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Transactional
interface AlbumRepository extends Repository<Album, Long> {

    Album save(Album album);


    @Query("""
            select a from Album a
           join fetch a.songs songs
           join fetch a.artists artists
            where a.id = :id """)
    Optional<Album> findAlbumByIdWithArtistsAndSongs(@Param("id") Long id);

    @Query("""
select a from Album a
 inner join a.artists
 where a.id = :id""")
    Set<Album> findAlbumsByArtistId(@Param("id") Long id);

    @Modifying
    @Query("delete from Album a where a.id in :id")
    int deleteByIdIn(Collection<Long> id);

    @Modifying
    @Query("delete from Album a where a.id = :id")
    void deleteById(Long id);



    @Modifying
    @Query("DELETE FROM Album a WHERE a.id IN :ids")
    void deleteAlbumsByIds(@Param("ids") Set<Long> ids);

    Optional<Album> findById(Long id);
//    @Modifying
//    @Query("delete from Album a where a.id in :id")
//    int deleteSongsByIdIn(Collection<Long> id);
}
