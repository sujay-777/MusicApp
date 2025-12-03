package com.musicapp.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musicapp.backend.dto.SongListResponse;
import com.musicapp.backend.dto.SongRequest;
import com.musicapp.backend.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/songs")
@RequiredArgsConstructor
public class SongController {

    private final SongService songService;

    @PostMapping
    public ResponseEntity<?> addSong(@RequestPart("request") String requestString,
                                     @RequestPart("audio") MultipartFile audioFile,
                                     @RequestPart("image") MultipartFile imageFile) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            SongRequest songRequest = objectMapper.readValue(requestString, SongRequest.class);
            songRequest.setImageFile(imageFile);
            songRequest.setAudioFile(audioFile);
            return ResponseEntity.status(HttpStatus.CREATED).body(songService.addSong(songRequest));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> listSongs() {
        try {
            return ResponseEntity.ok(songService.getAllSongs());
        } catch (Exception e) {
            return ResponseEntity.ok(new SongListResponse(false, null));
        }
    }
}