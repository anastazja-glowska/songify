package com.songify.domain.crud;

import com.songify.domain.crud.dto.ArtistDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Transactional
interface ArtistRepository extends Repository<Artist, Long> {

    Artist save(Artist artist);

    Set<Artist> findAll(Pageable pageable);

    Optional<Artist> findById(Long artistId);


    @Modifying
    @Query("delete from Artist a where a.id = :id")
    int deleteById(Long id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM artist_albums WHERE artists_id = :artistId AND albums_id = :albumId", nativeQuery = true)
    void removeAlbumFromArtist(@Param("artistId") Long artistId, @Param("albumId") Long albumId);



}
