package com.innoventes.jukebox.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "music_albums")
@ToString(exclude = "musicianList")
public class MusicAlbum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String albumName;

    private Date dateOfRelease;

    private String genre;

    private int price;

    private String description;

    @OneToMany(mappedBy = "musicAlbum", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Musician> musicianList;
}
