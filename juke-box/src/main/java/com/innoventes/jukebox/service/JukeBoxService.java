package com.innoventes.jukebox.service;

import com.innoventes.jukebox.controller.dto.requestDto.MusicAlbumRequestDto;
import com.innoventes.jukebox.controller.dto.requestDto.MusicianRequestDto;
import com.innoventes.jukebox.domain.MusicAlbum;
import com.innoventes.jukebox.domain.Musician;
import com.innoventes.jukebox.exception.BadRequestException;
import com.innoventes.jukebox.repository.MusicAlbumRepository;
import com.innoventes.jukebox.repository.MusicianRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class JukeBoxService {
    private final MusicAlbumRepository musicAlbumRepository;
    private final MusicianRepository musicianRepository;

    public Optional<MusicAlbum> checkIfAlbumAlreadyExist(String albumName) {
        return musicAlbumRepository.findByAlbumName(albumName);
    }

    public List<MusicAlbum> getMusicAlbums() {
        return musicAlbumRepository.findAll();
    }

    public MusicAlbum createMusicAlbum(MusicAlbumRequestDto musicAlbumRequestDto) {
        MusicAlbum musicAlbumToBeSaved = MusicAlbum.builder()
                .albumName(musicAlbumRequestDto.getAlbumName())
                .dateOfRelease(musicAlbumRequestDto.getDateOfRelease())
                .description(musicAlbumRequestDto.getDescription())
                .genre(musicAlbumRequestDto.getGenre())
                .price(musicAlbumRequestDto.getPrice())
                .description(musicAlbumRequestDto.getDescription())
                .build();
        musicAlbumToBeSaved.setMusicianList(
                createMusiciansList(musicAlbumToBeSaved, musicAlbumRequestDto.getMusicianRequestDtoList()));
        return musicAlbumRepository.save(musicAlbumToBeSaved);
    }

    private List<Musician> createMusiciansList(MusicAlbum musicAlbumToBeUpdated,
                                               List<MusicianRequestDto> musicianRequestDtoList) {
        return musicianRequestDtoList.stream().map(
                musician -> createMusician(musicAlbumToBeUpdated, musician)).collect(Collectors.toList());
    }

    private Musician createMusician(MusicAlbum musicAlbumToBeUpdated, MusicianRequestDto musicianRequestDto) {
        return Musician.builder()
                .name(musicianRequestDto.getName())
                .musicianType(musicianRequestDto.getMusicianType())
                .musicAlbum(musicAlbumToBeUpdated)
                .build();
    }

    public MusicAlbum updateMusicAlbum(MusicAlbum musicAlbum, MusicAlbumRequestDto musicAlbumRequestDto) {
        musicAlbum.setDateOfRelease(musicAlbumRequestDto.getDateOfRelease());
        musicAlbum.setDescription(musicAlbumRequestDto.getDescription());
        musicAlbum.setGenre(musicAlbumRequestDto.getGenre());
        musicAlbum.setPrice(musicAlbumRequestDto.getPrice());
        musicAlbum.getMusicianList().clear();
        musicAlbum.getMusicianList().addAll(
                updateMusiciansList(musicAlbum, musicAlbumRequestDto.getMusicianRequestDtoList()));
        return musicAlbumRepository.save(musicAlbum);
    }

    private List<Musician> updateMusiciansList(MusicAlbum musicAlbum, List<MusicianRequestDto> musicianRequestDtoList) {
        return musicianRequestDtoList.stream().map(
                musician -> updateMusician(musicAlbum, musician)).collect(Collectors.toList());
    }

    private Musician updateMusician(MusicAlbum musicAlbum, MusicianRequestDto musicianRequestDto) {
        return Musician.builder()
                .name(musicianRequestDto.getName())
                .musicianType(musicianRequestDto.getMusicianType())
                .musicAlbum(musicAlbum)
                .build();
    }

    public List<MusicAlbum> getMusicAlbumsByMusician(String musician) {
        return musicAlbumRepository.findByMusicianOrderByPrice(musician);
    }

    public Optional<Musician> checkIfMusicianAlreadyExist(String name) {
        return musicianRepository.findByName(name);
    }

    public Musician createMusician(MusicianRequestDto musicianRequestDto) {
        if (checkIfAlbumAlreadyExist(musicianRequestDto.getMusicAlbumRequestDto().getAlbumName()).isPresent()) {
            return musicianRepository.save(Musician.builder()
                    .name(musicianRequestDto.getName())
                    .musicianType(musicianRequestDto.getMusicianType())
                    .musicAlbum(
                            checkIfAlbumAlreadyExist(musicianRequestDto.getMusicAlbumRequestDto().getAlbumName()).get())
                    .build());
        } else {
            throw new BadRequestException("Requested Music Album doesn't exist.");
        }
    }

    public Musician updateMusician(Musician musician, MusicianRequestDto musicianRequestDto) {
        musician.setName(musicianRequestDto.getName());
        musician.setMusicianType(musicianRequestDto.getMusicianType());
        return musicianRepository.save(musician);
    }

    public List<Musician> getMusicianByAlbumName(String albumName) {
        return musicianRepository.findByMusicianOrderByMusicianName(albumName);
    }
}
