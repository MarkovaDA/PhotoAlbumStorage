package darya.markova.photostorage.dto;


public class PhotoDTO {
    String title;
    String description;
    Long albumId;
    String url; //сгенерировать url, по которому можно будет непосредственно получить изображение

    public String getUrl() {
        return url;
    }

    public PhotoDTO(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }
}
