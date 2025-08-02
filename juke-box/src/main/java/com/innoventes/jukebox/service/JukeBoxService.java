package com.innoventes.jukebox.service;

import com.innoventes.jukebox.controller.dto.requestDto.MusicAlbumRequestDto;
import com.innoventes.jukebox.controller.dto.requestDto.MusicianRequestDto;
import com.innoventes.jukebox.domain.MusicAlbum;
import com.innoventes.jukebox.domain.Musician;
import com.innoventes.jukebox.exception.BadRequestException;
import com.innoventes.jukebox.repository.MusicAlbumRepository;
import com.innoventes.jukebox.repository.MusicianRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JukeBoxService {

    private final MusicAlbumRepository musicAlbumRepository;
    private final MusicianRepository musicianRepository;

    // === Music Album ===

    public Optional<MusicAlbum> checkIfAlbumAlreadyExist(String albumName) {
        return musicAlbumRepository.findByAlbumName(albumName);
    }

    public List<MusicAlbum> getMusicAlbums() {
        return musicAlbumRepository.findAll();
    }

    public List<MusicAlbum> getMusicAlbumsByMusician(String musicianName) {
        return musicAlbumRepository.findByMusicianOrderByPrice(musicianName);
    }

    public MusicAlbum createMusicAlbum(MusicAlbumRequestDto request) {
        MusicAlbum album = MusicAlbum.builder()
                .albumName(request.getAlbumName())
                .dateOfRelease(request.getDateOfRelease())
                .description(request.getDescription())
                .genre(request.getGenre())
                .price(request.getPrice())
                .build();

        album.setMusicianList(
                mapMusicianRequestListToEntities(album, request.getMusicianRequestDtoList())
        );

        return musicAlbumRepository.save(album);
    }

    public MusicAlbum updateMusicAlbum(MusicAlbum album, MusicAlbumRequestDto request) {
        album.setDateOfRelease(request.getDateOfRelease());
        album.setDescription(request.getDescription());
        album.setGenre(request.getGenre());
        album.setPrice(request.getPrice());

        album.getMusicianList().clear();
        album.getMusicianList().addAll(
                mapMusicianRequestListToEntities(album, request.getMusicianRequestDtoList())
        );

        return musicAlbumRepository.save(album);
    }

    // === Musician ===

    public Optional<Musician> checkIfMusicianAlreadyExist(String name) {
        return musicianRepository.findByName(name);
    }

    public Musician createMusician(MusicianRequestDto request) {
        MusicAlbum album = musicAlbumRepository.findByAlbumName(
                request.getMusicAlbumRequestDto().getAlbumName()
        ).orElseThrow(() -> new BadRequestException("Requested Music Album doesn't exist."));

        return musicianRepository.save(
                Musician.builder()
                        .name(request.getName())
                        .musicianType(request.getMusicianType())
                        .musicAlbum(album)
                        .build()
        );
    }

    public Musician updateMusician(Musician musician, MusicianRequestDto request) {
        musician.setName(request.getName());
        musician.setMusicianType(request.getMusicianType());
        return musicianRepository.save(musician);
    }

    public List<Musician> getMusicianByAlbumName(String albumName) {
        return musicianRepository.findByMusicianOrderByMusicianName(albumName);
    }

    // === Private Mappers ===

    private List<Musician> mapMusicianRequestListToEntities(MusicAlbum album, List<MusicianRequestDto> requests) {
        return requests.stream()
                .map(req -> Musician.builder()
                        .name(req.getName())
                        .musicianType(req.getMusicianType())
                        .musicAlbum(album)
                        .build())
                .collect(Collectors.toList());
    }


}
