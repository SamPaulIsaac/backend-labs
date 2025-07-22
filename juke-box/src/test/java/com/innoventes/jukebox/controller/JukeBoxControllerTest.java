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
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.innoventes.jukebox.helper.JukeBoxHelper.buildMusicAlbumRequestDtoToSave;
import static com.innoventes.jukebox.helper.JukeBoxHelper.buildMusicAlbumRequestDtoToUpdate;
import static com.innoventes.jukebox.helper.JukeBoxHelper.buildMusicAlbumResponseDto;
import static com.innoventes.jukebox.helper.JukeBoxHelper.buildMusicAlbums;
import static com.innoventes.jukebox.helper.JukeBoxHelper.buildMusicianRequestDtoToSave;
import static com.innoventes.jukebox.helper.JukeBoxHelper.buildMusicianRequestDtoToUpdate;
import static com.innoventes.jukebox.helper.JukeBoxHelper.buildMusicianResponseDto;
import static com.innoventes.jukebox.helper.JukeBoxHelper.buildMusicians;
import static com.innoventes.jukebox.helper.JukeBoxHelper.updatedMusicAlbums;
import static com.innoventes.jukebox.helper.JukeBoxHelper.updatedMusician;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class JukeBoxControllerTest {

    @Mock
    private JukeBoxService jukeBoxService;
    @Mock
    private JukeBoxResponseAssembler jukeBoxResponseAssembler;
    @InjectMocks
    private JukeBoxController jukeBoxController;

    @Test
    public void createOrUpdateMusicAlbum_createsMusicAlbum() {
        MusicAlbumRequestDto musicAlbumRequestDto
                = buildMusicAlbumRequestDtoToSave("album1", 550, "Mega Three Stallion");

        MusicAlbumResponseDto expectedMusicAlbumResponseDto =
                buildMusicAlbumResponseDto("album1", 550, "Mega Three Stallion");

        when(jukeBoxService.checkIfAlbumAlreadyExist(musicAlbumRequestDto.getAlbumName()))
                .thenReturn(Optional.empty());
        when(jukeBoxService.createMusicAlbum(musicAlbumRequestDto))
                .thenReturn(buildMusicAlbums().get(0));
        when(jukeBoxResponseAssembler.toMusicDto(buildMusicAlbums().get(0)))
                .thenReturn(expectedMusicAlbumResponseDto);


        MusicAlbumResponseDto musicAlbumResponseDto = jukeBoxController.createOrUpdateMusicAlbum(musicAlbumRequestDto);
        assertThat(musicAlbumResponseDto).isNotNull();
        assertThat(musicAlbumResponseDto).isEqualTo(expectedMusicAlbumResponseDto);

    }

    @Test
    public void createOrUpdateMusician_createsMusician() {
        MusicianRequestDto musicianRequestDto
                = buildMusicianRequestDtoToSave("Tom", "Vocalist");

        MusicianResponseDto expectedMusicianResponseDto =
                buildMusicianResponseDto("Tom", "Vocalist");

        when(jukeBoxService.checkIfMusicianAlreadyExist(musicianRequestDto.getName()))
                .thenReturn(Optional.empty());
        when(jukeBoxService.createMusician(musicianRequestDto))
                .thenReturn(buildMusicians().get(0));
        when(jukeBoxResponseAssembler.toMusicianDto(buildMusicians().get(0)))
                .thenReturn(expectedMusicianResponseDto);


        MusicianResponseDto musician = jukeBoxController.createOrUpdateMusician(musicianRequestDto);
        assertThat(musician).isNotNull();
        assertThat(musician).isEqualTo(expectedMusicianResponseDto);

    }

    @Test
    public void createOrUpdateMusicAlbum_updateExistingMusicAlbum() {
        MusicAlbumRequestDto musicAlbumRequestDto
                = buildMusicAlbumRequestDtoToUpdate("album 1", 700, "Mega Three Stallion");

        MusicAlbumResponseDto expectedMusicAlbumResponseDto =
                buildMusicAlbumResponseDto("album 1", 700, "Mega Three Stallion");

        when(jukeBoxService.checkIfAlbumAlreadyExist(musicAlbumRequestDto.getAlbumName()))
                .thenReturn(buildMusicAlbums().stream()
                        .filter(albumName -> albumName.getAlbumName().equals(musicAlbumRequestDto.getAlbumName()))
                        .findFirst());

        when(jukeBoxService.updateMusicAlbum(any(), any()))
                .thenReturn(updatedMusicAlbums(700));
        when(jukeBoxResponseAssembler.toMusicDto(updatedMusicAlbums(700)))
                .thenReturn(expectedMusicAlbumResponseDto);


        MusicAlbumResponseDto musicAlbumResponseDto = jukeBoxController.createOrUpdateMusicAlbum(musicAlbumRequestDto);
        assertThat(musicAlbumResponseDto).isNotNull();
        assertThat(musicAlbumResponseDto).isEqualTo(expectedMusicAlbumResponseDto);

    }

    @Test
    public void createOrUpdateMusician_updateExistingMusician() {
        MusicianRequestDto musicianRequestDto
                = buildMusicianRequestDtoToUpdate("Matthew", "Lead Guitarist");

        MusicianResponseDto expectedMusicianResponseDto =
                buildMusicianResponseDto("Matthew", "Lead Guitarist");

        when(jukeBoxService.checkIfMusicianAlreadyExist(musicianRequestDto.getName()))
                .thenReturn(buildMusicians().stream()
                        .filter(musician -> musician.getName().equals(musicianRequestDto.getName()))
                        .findFirst());

        when(jukeBoxService.updateMusician(any(), any()))
                .thenReturn(updatedMusician("Matthew", "Lead Guitarist"));
        when(jukeBoxResponseAssembler.toMusicianDto(updatedMusician("Matthew", "Lead Guitarist")))
                .thenReturn(expectedMusicianResponseDto);


        MusicianResponseDto musicianResponseDto = jukeBoxController.createOrUpdateMusician(musicianRequestDto);
        assertThat(musicianResponseDto).isNotNull();
        assertThat(musicianResponseDto).isEqualTo(expectedMusicianResponseDto);

    }

    @Test
    public void retrieveMusicAlbumsByReleaseDate_RetrievesMusicAlbumsByReleaseDateInAscendingOrder() {
        List<MusicAlbum> musicAlbums = buildMusicAlbums();

        MusicAlbumResponseDto musicAlbum1 = buildMusicAlbumResponseDto(musicAlbums.get(0).getAlbumName(),
                musicAlbums.get(0).getPrice(), musicAlbums.get(0).getDescription());
        MusicAlbumResponseDto musicAlbum2 = buildMusicAlbumResponseDto(musicAlbums.get(1).getAlbumName(),
                musicAlbums.get(1).getPrice(), musicAlbums.get(1).getDescription());
        List<MusicAlbumResponseDto> expectedMusicianResponseDto = Arrays.asList(musicAlbum1, musicAlbum2);

        when(jukeBoxService.getMusicAlbums()).thenReturn(musicAlbums);
        when(jukeBoxResponseAssembler.assembleMusicAlbumsDto(musicAlbums)).thenReturn(expectedMusicianResponseDto);

        List<MusicAlbumResponseDto> musicAlbumResponseDtos = jukeBoxController.retrieveMusicAlbumsByReleaseDate();
        assertThat(musicAlbumResponseDtos).isNotEmpty();
        assertThat(musicAlbumResponseDtos).hasSize(2);
        assertThat(musicAlbumResponseDtos).containsExactlyElementsOf(expectedMusicianResponseDto);
    }

    @Test
    public void retrieveMusicAlbumsByMusician_RetrievesMusicAlbumsByMusicianSorted() {

        String musicianName = "Matthew";
        List<MusicAlbum> musicAlbums = buildMusicAlbums();

        MusicAlbumResponseDto musicAlbum1 = buildMusicAlbumResponseDto(musicAlbums.get(0).getAlbumName(),
                musicAlbums.get(0).getPrice(), musicAlbums.get(0).getDescription());
        MusicAlbumResponseDto musicAlbum2 = buildMusicAlbumResponseDto(musicAlbums.get(1).getAlbumName(),
                musicAlbums.get(1).getPrice(), musicAlbums.get(1).getDescription());
        List<MusicAlbumResponseDto> expectedMusicianResponseDto = Arrays.asList(musicAlbum1, musicAlbum2);

        when(jukeBoxService.getMusicAlbumsByMusician(musicianName)).thenReturn(musicAlbums);
        when(jukeBoxResponseAssembler.assembleMusicAlbumsDto(musicAlbums)).thenReturn(expectedMusicianResponseDto);

        List<MusicAlbumResponseDto> musicAlbumResponseDtos =
                jukeBoxController.retrieveMusicAlbumsByMusician(musicianName);
        assertThat(musicAlbumResponseDtos).isNotEmpty();
        assertThat(musicAlbumResponseDtos).hasSize(2);
        assertThat(musicAlbumResponseDtos).containsAll(expectedMusicianResponseDto);
    }

    @Test
    public void retrieveMusicianByAlbumName_RetrievesMusicianByAlbumName() {
        String albumName = "album 1";
        List<Musician> musicians = buildMusicians();

        MusicianResponseDto musician1 = buildMusicianResponseDto(musicians.get(0).getName(),
                musicians.get(0).getMusicianType());
        MusicianResponseDto musician2 = buildMusicianResponseDto(musicians.get(1).getName(),
                musicians.get(1).getMusicianType());
        List<MusicianResponseDto> expectedMusicianResponseDtoList = Arrays.asList(musician1, musician2);

        when(jukeBoxService.getMusicianByAlbumName(albumName)).thenReturn(musicians);
        when(jukeBoxResponseAssembler.buildMusicianDto(musicians)).thenReturn(expectedMusicianResponseDtoList);

        List<MusicianResponseDto> musicianResponseDtos =
                jukeBoxController.retrieveMusicianByAlbumName(albumName);

        assertThat(musicianResponseDtos).isNotEmpty();
        assertThat(musicianResponseDtos).hasSize(2);
        assertThat(musicianResponseDtos).containsAll(expectedMusicianResponseDtoList);
    }

    @Test
    public void createOrUpdateMusicAlbum_ReturnBadRequestExceptionWhenMandatoryFieldsAreMissing() {
        MusicAlbumRequestDto musicAlbumRequestDto = MusicAlbumRequestDto.builder().build();
        assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> jukeBoxController.createOrUpdateMusicAlbum(musicAlbumRequestDto));
    }

    @Test
    public void createOrUpdateMusicAlbum_ReturnBadRequestExceptionForWhenAlbumNameIsInvalid() {
        MusicAlbumRequestDto musicAlbumRequestDto = MusicAlbumRequestDto.builder()
                .albumName("A1")
                .dateOfRelease(Date.valueOf(LocalDate.ofYearDay(2008, 25)))
                .price(500)
                .build();
        assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> jukeBoxController.createOrUpdateMusicAlbum(musicAlbumRequestDto));
    }

    @Test
    public void createOrUpdateMusicAlbum_ReturnBadRequestExceptionWhenPriceIsInvalid() {
        MusicAlbumRequestDto musicAlbumRequestDto = MusicAlbumRequestDto.builder()
                .albumName("Album 1")
                .dateOfRelease(Date.valueOf(LocalDate.ofYearDay(2008, 25)))
                .price(25)
                .build();
        assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> jukeBoxController.createOrUpdateMusicAlbum(musicAlbumRequestDto));
    }


    @Test
    public void createOrUpdateMusician_ReturnBadRequestExceptionWhenMandatoryFieldsAreMissing() {
        MusicianRequestDto musicianRequestDto = MusicianRequestDto.builder().build();
        assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> jukeBoxController.createOrUpdateMusician(musicianRequestDto));
    }
}
