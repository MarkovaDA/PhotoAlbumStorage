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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
        //все эти параметры завернуть в одну сущность
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
        }//id=5ad1aedac0b6980326afc571
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getAlbumPhotos(@RequestParam("albumId")Long albumId) {
        List<PhotoDTO> images = new ArrayList<>();

        Iterator<GridFSDBFile> iterator =
                this.photoService.getPhotosInAlbum(albumId).iterator();
        GridFSDBFile fetchedFile;
        String fileURL;
        String originFileTitle;
        while(iterator.hasNext()) {
            fetchedFile = iterator.next();
            fileURL = "/api/photo/get?id=" + fetchedFile.getId().toString();
            originFileTitle = fetchedFile.getMetaData().get("title").toString();
            images.add(new PhotoDTO(originFileTitle, fileURL));
        }
        return new ResponseEntity(images, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "/get")
    public byte[] getPhoto(@RequestParam("id")String fileName) throws IOException {
        //найти фотографию по идентификатору
        GridFSDBFile photo = photoService.getPhotoByFileName(fileName);

        if (photo != null) {
            InputStream in = photo.getInputStream();
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int nRead;
            int length = (int)photo.getLength();
            byte[] data = new byte[length];
            while ((nRead = in.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            buffer.flush();
            byte[] imagenEnBytes = buffer.toByteArray();
            return imagenEnBytes;
        }
        return null;
    }
}
