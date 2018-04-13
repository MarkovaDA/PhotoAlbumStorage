package darya.markova.photostorage.dto.photo;

import java.util.UUID;

public class PhotoMetadataDTO {

    /**
     * Уникальный идентификатор.
     */
    Long id;

    /**
     * Идентификатор содержимого фотографии.
     */
    UUID payloadId;

    /**
     * Идентификатор(логин) пользователя - владельца фотографии.
     */
    String ownerLogin;

    /**
     * Идентификатор альбома, в котором содержится данное фото.
     */
    Long albumId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getPayloadId() {
        return payloadId;
    }

    public void setPayloadId(UUID payloadId) {
        this.payloadId = payloadId;
    }

    public String getOwnerLogin() {
        return ownerLogin;
    }

    public void setOwnerLogin(String ownerLogin) {
        this.ownerLogin = ownerLogin;
    }

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }
}
