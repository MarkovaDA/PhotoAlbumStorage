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
import java.io.IOException;
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

    public GridFSFile uploadPhotoToAlbum(String login, Long albumId, MultipartFile file) throws IOException {
        String fileName = file.getName();
        String uniqueFileName = login + albumId + fileName + System.currentTimeMillis();
        Photo photo = new Photo();
        photo.setAlbumId(albumId);
        photo.setTitle(fileName);
        photo.setOwnerLogin(login);
        return gridFsTemplate.store(file.getInputStream(), uniqueFileName, file.getContentType(), photo);
    }

    //список фотографий альбома
    public List<GridFSDBFile> getPhotosInAlbum(Long albumId) {
        return gridFsTemplate.find(new Query(Criteria.where("albumId").is(albumId)));
    }

    public GridFSDBFile getPhotoByFileName(String fileName) {
        return gridFsTemplate.findOne(new Query().addCriteria(Criteria.where("filename").is(fileName)));
    }
}
