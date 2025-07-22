package com.innoventes.jukebox.repository;

import com.innoventes.jukebox.domain.MusicAlbum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MusicAlbumRepository extends JpaRepository<MusicAlbum, Long> {

    Optional<MusicAlbum> findByAlbumName(String albumName);

    @Query(value = "select MA from MusicAlbum AS MA " +
            "JOIN MA.musicianList AS ML " +
            "WHERE ML.name = ?1 " +
            "ORDER BY MA.price ")
    List<MusicAlbum> findByMusicianOrderByPrice(String musicianName);
}
