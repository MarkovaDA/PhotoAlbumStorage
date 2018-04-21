package darya.markova.photostorage.service;

import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import darya.markova.photostorage.document.Photo;
import darya.markova.photostorage.repository.PhotoMetadataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;


@Service
public class PhotoService {

    private final PhotoMetadataRepository photoMetadataRepository;
    private final GridFsTemplate gridFsTemplate;

    @Autowired
    public PhotoService(PhotoMetadataRepository photoMetadataRepository, GridFsTemplate gridFsTemplate) {
        this.photoMetadataRepository = photoMetadataRepository;
        this.gridFsTemplate = gridFsTemplate;
    }

    public GridFSFile uploadPhotoToAlbum(String login,
                                         Long albumId,
                                         String title,
                                         String description,
                                         MultipartFile file) throws IOException {
        String uniqueFileName = login + albumId + title + System.currentTimeMillis();
        Photo photo = new Photo();
        photo.setAlbumId(albumId);
        photo.setTitle(title);
        photo.setOwnerLogin(login);
        photo.setDescription(description);
        return gridFsTemplate.store(file.getInputStream(), uniqueFileName, file.getContentType(), photo);
    }

    //список фотографий альбома
    public List<GridFSDBFile> getUserPhotosInAlbum(Long albumId, String ownerLogin) {
        return gridFsTemplate.find(new Query(Criteria.where("metadata.albumId").is(albumId)
                .andOperator(Criteria.where("metadata.ownerLogin").is(ownerLogin))));
    }

    public GridFSDBFile getImageById(String fileId) {
        return gridFsTemplate.findOne(new Query().addCriteria(Criteria.where("_id").is(fileId)));
    }

    public void deleteImage(List<String> ids) {
       gridFsTemplate.delete(new Query(Criteria.where("_id").in(ids)));
        //gridFsTemplate.delete(new Query(Criteria.where("_id").is(fileId)));
    }

    public byte[] getPhotoBytes(GridFSDBFile file) throws IOException {
        InputStream in = file.getInputStream();
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        int length = (int)file.getLength();
        byte[] data = new byte[length];
        while ((nRead = in.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        buffer.flush();
        byte[] fileBytes = buffer.toByteArray();
        return fileBytes;
    }
}
