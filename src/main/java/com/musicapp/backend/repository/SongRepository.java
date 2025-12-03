package com.musicapp.backend.repository;

import com.musicapp.backend.document.Song;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SongRepository extends MongoRepository<Song, String> {
}
