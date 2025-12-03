package com.musicapp.backend.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.musicapp.backend.document.Song;
import com.musicapp.backend.dto.SongListResponse;
import com.musicapp.backend.dto.SongRequest;
import com.musicapp.backend.repository.SongRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@AllArgsConstructor
@Service
public class SongService {

    private final SongRepository songRepository;
    private final Cloudinary cloudinary;


    public Song addSong(SongRequest request) throws IOException {
        Map<String,Object> audioUploadResult = cloudinary.uploader().upload(request.getAudiofile().getBytes(), ObjectUtils.asMap("resource_type", "video"));
        Map<String,Object> imageUploadResult = cloudinary.uploader().upload(request.getImageFile().getBytes(), ObjectUtils.asMap("resource_type", "image"));

        double durationSeconds = (Double)audioUploadResult.get("duration");
        String duration = formatDuration(durationSeconds);

        Song newSong = Song.builder()
                .name(request.getName())
                .desc(request.getDesc())
                .image(imageUploadResult.get("secure_url").toString())
                .album(request.getAlbum())
                .file(audioUploadResult.get("secure_url").toString())
                .duration(duration)
                .build();

        return songRepository.save(newSong);

    }

    // this is for formatting the duration of the song
    private String formatDuration(double durationSeconds){
        if (durationSeconds == 0) {
            return "0:00";
        }
        int min = (int)(durationSeconds / 60);
        int sec = (int)(durationSeconds % 60);
        return String.format("%d:%02d", min, sec);
    }

    public boolean deleteSong(String id){
        Song song = songRepository.findById(id).orElseThrow(() -> new RuntimeException("Song not found") );
        if(song == null) return false;
        songRepository.deleteById(id);
        return true;
    }

    public SongListResponse getAllSongs(){
        return new SongListResponse(true, songRepository.findAll());
    }


}
