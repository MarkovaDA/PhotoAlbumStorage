package darya.markova.photostorage.dto.photo;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public class PhotoDTO {

    /**
     * Метаданные фотографии.
     */
    private PhotoMetadataDTO metadata;

    /**
     * Данные изображения.
     */
    private MultipartFile payload;

    public PhotoMetadataDTO getMetadata() {
        return metadata;
    }

    public void setMetadata(PhotoMetadataDTO metadata) {
        this.metadata = metadata;
    }

    public Long getId() {
        return metadata != null ? metadata.getId() : null;
    }

    public void setId(Long id) {
        if (metadata == null) {
            metadata = new PhotoMetadataDTO();
        }
        metadata.setId(id);
    }

    public UUID getPayloadId() {
        return metadata != null ? metadata.getPayloadId() : null;
    }

    public void setPayloadId(UUID payloadId) {
        if (metadata == null) {
            metadata = new PhotoMetadataDTO();
        }
        metadata.setPayloadId(payloadId);
    }

    public String getOwnerLogin() {
        return metadata != null ? metadata.getOwnerLogin() : null;
    }

    public void setOwnerLogin(String ownerLogin) {
        if (metadata == null) {
            metadata = new PhotoMetadataDTO();
        }
        metadata.setOwnerLogin(ownerLogin);
    }

    public Long getAlbumId() {
        return metadata != null ? metadata.getAlbumId() : null;
    }

    public void setAlbumId(Long albumId) {
        if (metadata == null) {
            metadata = new PhotoMetadataDTO();
        }
        metadata.setAlbumId(albumId);
    }

    public MultipartFile getPayload() {
        return payload;
    }

    public void setPayload(MultipartFile payload) {
        this.payload = payload;
    }
}
