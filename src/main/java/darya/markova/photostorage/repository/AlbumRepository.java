package darya.markova.photostorage.repository;

import darya.markova.photostorage.document.Album;
import darya.markova.photostorage.document.Photo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumRepository extends MongoRepository<Album, Long> {

}
