package com.innoventes.jukebox.service;

import com.innoventes.jukebox.controller.dto.requestDto.MusicAlbumRequestDto;
import com.innoventes.jukebox.controller.dto.requestDto.MusicianRequestDto;
import com.innoventes.jukebox.domain.MusicAlbum;
import com.innoventes.jukebox.domain.Musician;
import com.innoventes.jukebox.exception.BadRequestException;
import com.innoventes.jukebox.repository.MusicAlbumRepository;
import com.innoventes.jukebox.repository.MusicianRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static com.innoventes.jukebox.helper.JukeBoxHelper.buildMusicAlbumRequestDtoToSave;
import static com.innoventes.jukebox.helper.JukeBoxHelper.buildMusicAlbumRequestDtoToUpdate;
import static com.innoventes.jukebox.helper.JukeBoxHelper.buildMusicAlbums;
import static com.innoventes.jukebox.helper.JukeBoxHelper.buildMusicianRequestDtoAlongWithMusicAlbumToSave;
import static com.innoventes.jukebox.helper.JukeBoxHelper.buildMusicians;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class JukeBoxServiceTest {
    @Mock
    private MusicAlbumRepository musicAlbumRepository;
    @Mock
    private MusicianRepository musicianRepository;

    @InjectMocks
    private JukeBoxService jukeBoxService;

    @Test
    public void checkIfAlbumAlreadyExist_ReturnOptionalOfMusicAlbums() {
        List<MusicAlbum> buildMusicAlbums = buildMusicAlbums();
        when(musicAlbumRepository.findByAlbumName(buildMusicAlbums.get(0).getAlbumName()))
                .thenReturn(Optional.of(buildMusicAlbums.get(0)));
        Optional<MusicAlbum> musicAlbum = jukeBoxService.checkIfAlbumAlreadyExist(buildMusicAlbums.get(0).getAlbumName());
        assertThat(musicAlbum).isPresent();
        assertThat(musicAlbum.get()).isEqualTo(buildMusicAlbums.get(0));
    }

    @Test
    public void checkIfAlbumAlreadyExist_ReturnOptionalEmpty() {
        when(musicAlbumRepository.findByAlbumName(any()))
                .thenReturn(Optional.empty());
        Optional<MusicAlbum> musicAlbum = jukeBoxService.checkIfAlbumAlreadyExist(any());
        assertThat(musicAlbum).isEmpty();
    }

    @Test
    public void checkIfMusicianAlreadyExist_ReturnOptionalOfMusician() {
        List<Musician> buildMusician = buildMusicians();
        when(musicianRepository.findByName(buildMusician.get(0).getName()))
                .thenReturn(Optional.of(buildMusician.get(0)));
        Optional<Musician> musician = jukeBoxService.checkIfMusicianAlreadyExist(buildMusician.get(0).getName());
        assertThat(musician).isPresent();
        assertThat(musician.get()).isEqualTo(buildMusician.get(0));
    }

    @Test
    public void checkIfMusicianAlreadyExist_ReturnOptionalEmpty() {
        when(musicianRepository.findByName(any()))
                .thenReturn(Optional.empty());
        Optional<Musician> musician = jukeBoxService.checkIfMusicianAlreadyExist(any());
        assertThat(musician).isEmpty();
    }

    @Test
    public void getMusicAlbums_RetrievesAllTheAvailableMusicAlbums() {
        when(musicAlbumRepository.findAll()).thenReturn(buildMusicAlbums());
        List<MusicAlbum> musicAlbums = jukeBoxService.getMusicAlbums();
        assertThat(musicAlbums).isNotEmpty();
        assertThat(musicAlbums).hasSize(2);
        assertThat(musicAlbums).isEqualTo(buildMusicAlbums());
    }

    @Test
    public void getMusicAlbumsByMusician_RetrievesAllTheAvailableMusicAlbumsComposedByTheMusician() {
        List<MusicAlbum> buildMusicAlbums = buildMusicAlbums();
        when(musicAlbumRepository.findByMusicianOrderByPrice(buildMusicAlbums.get(0)
                .getMusicianList().get(0).getName())).thenReturn(buildMusicAlbums);
        List<MusicAlbum> musicAlbums = jukeBoxService.getMusicAlbumsByMusician(buildMusicAlbums.get(0)
                .getMusicianList().get(0).getName());
        assertThat(musicAlbums).isNotEmpty();
        assertThat(musicAlbums).hasSize(2);
        assertThat(musicAlbums).isEqualTo(buildMusicAlbums());
        assertThat(musicAlbums.get(0).getMusicianList().get(0).getName()).isEqualTo(buildMusicAlbums().get(0).getMusicianList().get(0).getName());
        assertThat(musicAlbums.get(0).getMusicianList().get(1).getName()).isEqualTo(buildMusicAlbums().get(0).getMusicianList().get(1).getName());
        assertThat(musicAlbums.get(1).getMusicianList().get(0).getName()).isEqualTo(buildMusicAlbums().get(0).getMusicianList().get(0).getName());
        assertThat(musicAlbums.get(1).getMusicianList().get(1).getName()).isEqualTo(buildMusicAlbums().get(1).getMusicianList().get(1).getName());

    }

    @Test
    public void getMusicianByAlbumName_RetrievesMusicianForTheRequestedAlbumName() {

        List<Musician> buildMusicians = buildMusicians();
        when(musicianRepository.findByMusicianOrderByMusicianName(buildMusicians.get(0)
                .getMusicAlbum().getAlbumName())).thenReturn(buildMusicians);
        List<Musician> musicians = jukeBoxService.getMusicianByAlbumName(buildMusicians.get(0)
                .getMusicAlbum().getAlbumName());
        assertThat(musicians).isNotEmpty();
        assertThat(musicians).hasSize(2);
    }

    @Test
    public void createMusicAlbum_CreatesNewMusicAlbum() {
        MusicAlbumRequestDto musicAlbumRequestDto = buildMusicAlbumRequestDtoToSave("Album 1", 550,
                "Fireflies");
        when(musicAlbumRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        MusicAlbum musicAlbum = jukeBoxService.createMusicAlbum(musicAlbumRequestDto);
        assertThat(musicAlbum).isNotNull();

    }

    @Test
    public void createMusician_CreatesNewMusician() {
        List<MusicAlbum> buildMusicAlbums = buildMusicAlbums();
        when(musicAlbumRepository.findByAlbumName(buildMusicAlbums.get(0).getAlbumName()))
                .thenReturn(Optional.of(buildMusicAlbums.get(0)));

        MusicianRequestDto musicianRequestDto = buildMusicianRequestDtoAlongWithMusicAlbumToSave("Timothy",
                "Guitarist");
        when(musicianRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        Musician musician = jukeBoxService.createMusician(musicianRequestDto);
        assertThat(musician).isNotNull();
    }

    @Test
    public void updateMusicAlbum_UpdateExistingMusicAlbum() {
        MusicAlbum musicAlbum = buildMusicAlbums().get(0);
        MusicAlbumRequestDto musicAlbumRequestDto =
                buildMusicAlbumRequestDtoToUpdate(musicAlbum.getAlbumName(), 700, musicAlbum.getDescription());
        when(musicAlbumRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);
        MusicAlbum updateMusicAlbum = jukeBoxService.updateMusicAlbum(musicAlbum, musicAlbumRequestDto);
        assertThat(updateMusicAlbum).isNotNull();
        assertThat(updateMusicAlbum.getPrice()).isEqualTo(700);

    }

    @Test
    public void createMusician_ThrowsBadRequestExceptionWhenRequestedAlbumDoesnotExist() {
        MusicianRequestDto musicianRequestDto = buildMusicianRequestDtoAlongWithMusicAlbumToSave("Timothy",
                "Guitarist");
        assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> jukeBoxService.createMusician(musicianRequestDto));
    }

    @Test
    public void updateMusician_UpdatesMusician() {
        Musician musician = buildMusicians().get(0);
        MusicianRequestDto musicianRequestDto = buildMusicianRequestDtoAlongWithMusicAlbumToSave("Matthew",
                "Guitarist");
        when(musicianRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);
        Musician updateMusician = jukeBoxService.updateMusician(musician, musicianRequestDto);
        assertThat(updateMusician).isNotNull();
        assertThat(updateMusician.getMusicianType()).isEqualTo(musicianRequestDto.getMusicianType());
    }
}
