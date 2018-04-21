package darya.markova.photostorage.controller;


import com.mongodb.gridfs.GridFSFile;
import com.mongodb.gridfs.GridFSDBFile;
import darya.markova.photostorage.dto.PhotoDTO;
import darya.markova.photostorage.dto.ResourceKeyDTO;
import darya.markova.photostorage.service.PhotoService;
import darya.markova.photostorage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/api/photo")
public class PhotoController {
    @Autowired
    PhotoService photoService;

    @Autowired
    UserService userService;

    @PostMapping(value = "/upload", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity uploadPhotoToAlbum(@RequestParam("albumId")Long albumId,
                                             @RequestParam("description")String description,
                                             @RequestParam("title")String title,
                                             @RequestBody MultipartFile file) {
        //!!!все эти параметры завернуть в одну сущность
        try {
            GridFSFile uploadedFile = this.photoService
                    .uploadPhotoToAlbum(userService.getCurrentAuthUser().getLogin(),
                            albumId,
                            title,
                            description,
                            file);
            return new ResponseEntity(new ResourceKeyDTO(uploadedFile.getId().toString()), HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getAlbumPhotos(@RequestParam("albumId")Long albumId) {
        List<PhotoDTO> images = new ArrayList<>();

        Iterator<GridFSDBFile> iterator =
                this.photoService.getUserPhotosInAlbum(albumId, this.userService.getCurrentAuthUser().getLogin()).iterator();
        GridFSDBFile fetchedFile;
        PhotoDTO fetchedPhoto;
        String imageTitle;
        Object imageDescription;
        while(iterator.hasNext()) {
            fetchedFile = iterator.next();
            imageTitle = fetchedFile.getMetaData().get("title").toString();
            imageDescription = fetchedFile.getMetaData().get("description");
            if (imageDescription != null) {
                fetchedPhoto = new PhotoDTO(imageTitle, imageDescription.toString());
            } else {
                fetchedPhoto = new PhotoDTO(imageTitle, "");
            }
            fetchedPhoto.setId(fetchedFile.getId().toString());
            fetchedPhoto.setCreationDate(fetchedFile.getUploadDate());
            images.add(fetchedPhoto);
        }
        return new ResponseEntity(images, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "/get") //отладить этот метод
    public byte[] getPhoto(@RequestParam("id")String fileId) throws IOException {
        //id=5ad1aedac0b6980326afc571
        GridFSDBFile photo = photoService.getImageById(fileId);
        //проверить,что данная фотография принадлежит текущему юзеру
        if (photo != null) {
            return photoService.getPhotoBytes(photo);
        }
        return null;
    }

    @PostMapping(value="/delete", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity deletePhotos(@RequestBody List<String> ids) {
        photoService.deleteImage(ids);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
