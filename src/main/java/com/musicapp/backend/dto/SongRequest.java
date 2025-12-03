package com.musicapp.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class SongRequest {

    @Id
    private String id;
    private String name;
    private String desc;
    private String album;
    private MultipartFile audiofile;
    private MultipartFile imageFile;
    private String duration;
}
