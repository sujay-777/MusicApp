package com.musicapp.backend.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.musicapp.backend.document.Album;
import com.musicapp.backend.dto.AlbumListResponse;
import com.musicapp.backend.dto.AlbumRequest;
import com.musicapp.backend.repository.AlbumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AlbumService {

    private final AlbumRepository albumRepository;
    private final Cloudinary cloudinary;


    public Album addAlbum(AlbumRequest request) throws IOException {
        //converts the given image to the bytes and  uploads it to the cloud and then
//     returns it in the map data structrue
        Map<String, Object> imageUploadResult = cloudinary.uploader().upload(request.getImageFile().getBytes(), ObjectUtils
                .asMap("resource_type", "image"));
        Album newAlbum = Album.builder()
                .name(request.getName())
                .desc(request.getDesc())
                .bgColorl(request.getBgColor())
                .imageUrl(imageUploadResult.get("secure_url").toString())
                .build();
        return albumRepository.save(newAlbum);
    }

    public AlbumListResponse getAllAlbums(){
        return new AlbumListResponse(true, albumRepository.findAll());
    }

    public boolean removeAlbum(String id){
        Album existingAlbum = albumRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Album not Found."));
        albumRepository.delete(existingAlbum);
        return true;
    }


}