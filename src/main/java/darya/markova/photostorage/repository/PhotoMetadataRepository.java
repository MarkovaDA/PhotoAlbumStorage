package darya.markova.photostorage.repository;

import darya.markova.photostorage.document.photo.PhotoMetadata;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoMetadataRepository extends MongoRepository<PhotoMetadata, Long> {
}
