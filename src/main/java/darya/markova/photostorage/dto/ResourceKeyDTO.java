package darya.markova.photostorage.dto;

public class ResourceKeyDTO {
    private String id;//идентификатор ресурса в системе mongo

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ResourceKeyDTO(String id) {
        this.id = id;
    }
}
