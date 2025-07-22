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
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@RequestMapping(value = "/jukeBox/",
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.ALL_VALUE})
@RestController
@AllArgsConstructor
@Slf4j
public class JukeBoxController {
    private static final String MANDATORY_FIELDS_ERROR_MESSAGE =
            "Album Name and Price Fields are Mandatory.";
    private static final String INVALID_ALBUM_NAME_ERROR_MESSAGE =
            "Album Name should be minimum 5 characters.";
    private static final String INVALID_PRICE_ERROR_MESSAGE =
            "Price value should be between 100 to 1000.";
    private static final String INVALID_MUSICIAN_NAME_ERROR_MESSAGE =
            "Album Name and Price Fields are Mandatory.";

    private final JukeBoxService jukeBoxService;
    private final JukeBoxResponseAssembler jukeBoxResponseAssembler;

    @PostMapping("/createOrUpdateMusicAlbum/")
    public MusicAlbumResponseDto createOrUpdateMusicAlbum(
            @RequestBody MusicAlbumRequestDto musicAlbumRequestDto) {

        /* Below method validate the request body
         1. Check for mandatory fields.
         2. Verify certain fields to have required values. */
        ValidatedRequestBody(musicAlbumRequestDto);

        /* Method to check the request album already exist
         1. If yes, then perform update.
         2. If no, then create. */
        Optional<MusicAlbum> musicAlbums =
                jukeBoxService.checkIfAlbumAlreadyExist(musicAlbumRequestDto.getAlbumName());
        if (!musicAlbums.isPresent()) {
            log.info("Request to Create Music Album {}.", musicAlbumRequestDto);
            MusicAlbum savedMusicAlbum = jukeBoxService.createMusicAlbum(musicAlbumRequestDto);
            return jukeBoxResponseAssembler.toMusicDto(savedMusicAlbum);
        } else {
            log.info("Request to Update Existing Music Album {}.", musicAlbumRequestDto);
            MusicAlbum updatedMusicAlbum = jukeBoxService.updateMusicAlbum(musicAlbums.get(), musicAlbumRequestDto);
            return jukeBoxResponseAssembler.toMusicDto(updatedMusicAlbum);
        }
    }

    private void ValidatedRequestBody(MusicAlbumRequestDto musicAlbumRequestDto) {
        if (!isAllMandatoryFieldsPresent(musicAlbumRequestDto)) {
            throw new BadRequestException(MANDATORY_FIELDS_ERROR_MESSAGE);
        }
        if (!isRequestedAlbumNameValid(musicAlbumRequestDto)) {
            throw new BadRequestException(INVALID_ALBUM_NAME_ERROR_MESSAGE);
        }
        if (!isRequestedPriceValid(musicAlbumRequestDto)) {
            throw new BadRequestException(INVALID_PRICE_ERROR_MESSAGE);
        }
    }


    private boolean isAllMandatoryFieldsPresent(MusicAlbumRequestDto musicAlbumRequestDto) {
        return null != musicAlbumRequestDto.getAlbumName() && null != musicAlbumRequestDto.getDateOfRelease();
    }

    private boolean isRequestedAlbumNameValid(MusicAlbumRequestDto musicAlbumRequestDto) {
        return musicAlbumRequestDto.getAlbumName().length() >= 5;
    }

    private boolean isRequestedPriceValid(MusicAlbumRequestDto musicAlbumRequestDto) {
        return (musicAlbumRequestDto.getPrice() >= 100 && musicAlbumRequestDto.getPrice() <= 1000);
    }

    @GetMapping("/searchMusicAlbumsByReleaseDate")
    public List<MusicAlbumResponseDto> retrieveMusicAlbumsByReleaseDate() {
        log.info("Search Request to Retrieve MusicAlbums By Release Date");
        List<MusicAlbum> musicAlbums = jukeBoxService.getMusicAlbums();
        musicAlbums.sort(Comparator.comparing(MusicAlbum::getDateOfRelease));
        return jukeBoxResponseAssembler.assembleMusicAlbumsDto(musicAlbums);
    }

    @GetMapping("/searchMusicAlbumsByMusician")
    public List<MusicAlbumResponseDto> retrieveMusicAlbumsByMusician(
            @RequestParam String musicianName) {
        log.info("Search Request to Retrieve MusicAlbums For Musician {}.", musicianName);
        List<MusicAlbum> musicAlbums = jukeBoxService.getMusicAlbumsByMusician(musicianName);
        return jukeBoxResponseAssembler.assembleMusicAlbumsDto(musicAlbums);
    }

    @PostMapping("createOrUpdateMusician/")
    public MusicianResponseDto createOrUpdateMusician(@RequestBody MusicianRequestDto musicianRequestDto) {

        // Verify musician name to be present and its length should be minimum of 3 characters.
        if (null == musicianRequestDto.getName() || musicianRequestDto.getName().length() < 3) {
            throw new BadRequestException(INVALID_MUSICIAN_NAME_ERROR_MESSAGE);
        }

        /* Method to check the request musician already exist
            1. If yes, then perform update.
            2. If no, then create. */
        Optional<Musician> musicians =
                jukeBoxService.checkIfMusicianAlreadyExist(musicianRequestDto.getName());

        if (!musicians.isPresent()) {
            log.info("Request to Create Musician {}.", musicianRequestDto);
            Musician savedMusician = jukeBoxService.createMusician(musicianRequestDto);
            return jukeBoxResponseAssembler.toMusicianDto(savedMusician);
        } else {
            log.info("Request to Update Existing Musician {}.", musicianRequestDto);
            Musician updatedMusician = jukeBoxService.updateMusician(musicians.get(), musicianRequestDto);
            return jukeBoxResponseAssembler.toMusicianDto(updatedMusician);
        }
    }

    @GetMapping("/searchMusicianByMusicAlbumName")
    public List<MusicianResponseDto> retrieveMusicianByAlbumName(
            @RequestParam String albumName) {
        log.info("Search Request to Retrieve MusicAlbums By Album Name {}.", albumName);
        List<Musician> musicAlbums = jukeBoxService.getMusicianByAlbumName(albumName);
        return jukeBoxResponseAssembler.buildMusicianDto(musicAlbums);
    }
}

