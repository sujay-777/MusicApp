package com.musicapp.backend.dto;

import com.musicapp.backend.document.Album;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AlbumListResponse {

    private boolean success;
    private List<Album> albums;
}
