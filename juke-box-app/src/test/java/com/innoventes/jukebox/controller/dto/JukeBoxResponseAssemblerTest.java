package com.innoventes.jukebox.controller.dto;

import com.innoventes.jukebox.controller.dto.responseDto.MusicAlbumResponseDto;
import com.innoventes.jukebox.controller.dto.responseDto.MusicianResponseDto;
import com.innoventes.jukebox.domain.MusicAlbum;
import com.innoventes.jukebox.domain.Musician;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.innoventes.jukebox.helper.JukeBoxHelper.buildMusicAlbums;
import static com.innoventes.jukebox.helper.JukeBoxHelper.buildMusicians;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class JukeBoxResponseAssemblerTest {
    @InjectMocks
    private JukeBoxResponseAssembler jukeBoxResponseAssembler;

    @Test
    public void buildMusicianDto_returnAssembledMusicAlbums() {
        List<Musician> musicianList = buildMusicians();
        //when
        List<MusicianResponseDto> musicianResponseDtoList = jukeBoxResponseAssembler.buildMusicianDto(musicianList);
        assertThat(musicianResponseDtoList).isNotEmpty();
        assertThat(musicianResponseDtoList).hasSize(2);
    }


    @Test
    public void assembleMusicAlbumsDto_returnAssembledMusicAlbums() {
        List<MusicAlbum> musicianList = buildMusicAlbums();
        //when
        List<MusicAlbumResponseDto> musicianResponseDtoList = jukeBoxResponseAssembler.assembleMusicAlbumsDto(musicianList);
        assertThat(musicianResponseDtoList).isNotEmpty();
        assertThat(musicianResponseDtoList).hasSize(2);
    }
}