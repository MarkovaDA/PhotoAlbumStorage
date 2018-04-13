package darya.markova.photostorage.document;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document()
public class Photo {

    String ownerLogin;
    Date createDate;

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * Пользовательское название фотографии
     */
    String title;

    /**
     * Идентификатор альбома, в котором содержится данное фото.
     */
    Long albumId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
