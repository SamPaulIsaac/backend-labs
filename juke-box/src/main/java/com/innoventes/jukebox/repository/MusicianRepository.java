package com.innoventes.jukebox.repository;

import com.innoventes.jukebox.domain.Musician;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MusicianRepository extends JpaRepository<Musician, Long> {
    Optional<Musician> findByName(String name);

    @Query(value = "select M from Musician AS M " +
            "JOIN M.musicAlbum AS MA " +
            "WHERE MA.albumName = ?1 " +
            "ORDER BY M.name ")
    List<Musician> findByMusicianOrderByMusicianName(String albumName);
}
