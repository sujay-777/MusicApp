package com.musicapp.backend.repository;

import com.musicapp.backend.document.Album;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AlbumRepository extends MongoRepository<Album, String> {
}
