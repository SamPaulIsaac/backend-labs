package com.innoventes.jukebox.controller;

import com.innoventes.jukebox.controller.dto.JukeBoxResponseAssembler;
import com.innoventes.jukebox.controller.dto.requestDto.MusicAlbumRequestDto;
import com.innoventes.jukebox.controller.dto.requestDto.MusicianRequestDto;
import com.innoventes.jukebox.controller.dto.responseDto.MusicAlbumResponseDto;
import com.innoventes.jukebox.controller.dto.responseDto.MusicianResponseDto;
import com.innoventes.jukebox.domain.MusicAlbum;
import com.innoventes.jukebox.domain.Musician;
import com.innoventes.jukebox.exception.BadRequestException;
import com.innoventes.jukebox.service.JukeBoxService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(
        value = "/jukeBox",
        produces = {MediaType.APPLICATION_JSON_VALUE}
)
@RequiredArgsConstructor
public class JukeBoxController {

    private static final String MANDATORY_FIELDS_ERROR = "Album name and release date are required.";
    private static final String ALBUM_NAME_TOO_SHORT_ERROR = "Album name must be at least 5 characters.";
    private static final String PRICE_RANGE_ERROR = "Price must be between 100 and 1000.";
    private static final String MUSICIAN_NAME_INVALID = "Musician name must be at least 3 characters.";

    
    private final JukeBoxService jukeBoxService;
    private final JukeBoxResponseAssembler jukeBoxResponseAssembler;

    @PostMapping("/createOrUpdateMusicAlbum")
    public MusicAlbumResponseDto createOrUpdateMusicAlbum(
            @RequestBody MusicAlbumRequestDto request) {

        validateMusicAlbumRequest(request);

        Optional<MusicAlbum> existingAlbum = jukeBoxService.checkIfAlbumAlreadyExist(request.getAlbumName());
        MusicAlbum album;

        if (existingAlbum.isPresent()) {
            log.info("Updating existing music album: {}", request.getAlbumName());
            album = jukeBoxService.updateMusicAlbum(existingAlbum.get(), request);
        } else {
            log.info("Creating new music album: {}", request.getAlbumName());
            album = jukeBoxService.createMusicAlbum(request);
        }

        return jukeBoxResponseAssembler.toMusicDto(album);
    }

    @PostMapping("/createOrUpdateMusician")
    public MusicianResponseDto createOrUpdateMusician(@RequestBody MusicianRequestDto request) {
        validateMusicianRequest(request);

        Optional<Musician> existingMusician = jukeBoxService.checkIfMusicianAlreadyExist(request.getName());
        Musician musician;

        if (existingMusician.isPresent()) {
            log.info("Updating existing musician: {}", request.getName());
            musician = jukeBoxService.updateMusician(existingMusician.get(), request);
        } else {
            log.info("Creating new musician: {}", request.getName());
            musician = jukeBoxService.createMusician(request);
        }

        return jukeBoxResponseAssembler.toMusicianDto(musician);
    }

    @GetMapping("/searchMusicAlbumsByReleaseDate")
    public List<MusicAlbumResponseDto> getAlbumsSortedByReleaseDate() {
        log.info("Fetching all music albums sorted by release date");
        List<MusicAlbum> albums = jukeBoxService.getMusicAlbums();
        albums.sort(Comparator.comparing(MusicAlbum::getDateOfRelease));
        return jukeBoxResponseAssembler.assembleMusicAlbumsDto(albums);
    }

    @GetMapping("/searchMusicAlbumsByMusician")
    public List<MusicAlbumResponseDto> getAlbumsByMusician(@RequestParam String musicianName) {
        log.info("Fetching albums by musician: {}", musicianName);
        List<MusicAlbum> albums = jukeBoxService.getMusicAlbumsByMusician(musicianName);
        return jukeBoxResponseAssembler.assembleMusicAlbumsDto(albums);
    }

    @GetMapping("/searchMusicianByMusicAlbumName")
    public List<MusicianResponseDto> getMusiciansByAlbumName(@RequestParam String albumName) {
        log.info("Fetching musicians by album name: {}", albumName);
        List<Musician> musicians = jukeBoxService.getMusicianByAlbumName(albumName);
        return jukeBoxResponseAssembler.buildMusicianDto(musicians);
    }

    // --- Private validation methods ---

    public void validateMusicAlbumRequest(MusicAlbumRequestDto request) {
        if (request.getAlbumName() == null || request.getDateOfRelease() == null) {
            throw new BadRequestException(MANDATORY_FIELDS_ERROR);
        }
        if (request.getAlbumName().length() < 5) {
            throw new BadRequestException(ALBUM_NAME_TOO_SHORT_ERROR);
        }
        if (request.getPrice() < 100 || request.getPrice() > 1000) {
            throw new BadRequestException(PRICE_RANGE_ERROR);
        }
    }

    public void validateMusicianRequest(MusicianRequestDto request) {
        if (request.getName() == null || request.getName().length() < 3) {
            throw new BadRequestException(MUSICIAN_NAME_INVALID);
        }
    }

}
