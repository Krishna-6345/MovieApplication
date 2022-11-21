package com.niit.authentication.controller;

import com.niit.authentication.model.UserImageUpload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Slf4j
@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/addProfilePic")
public class UserImageUploadController {

    private final Path path = Paths.get("..\\MovieService\\UserAuthenticationService\\ProfileImages\\");
    @PostMapping(value = "/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addProfilePicture(@ModelAttribute UserImageUpload userImageUpload) throws IOException {

        Files.createDirectories(path);
        System.out.println("Instance started");
        if(userImageUpload.getImageFile()!=null)
        {
            System.out.println("Entered");
            System.out.println("Image name :" + userImageUpload.getImageFile().getOriginalFilename());
            String localPath = System.getProperty("MovieService\\UserAuthenticationService.dir");
            try{
                Files.copy(userImageUpload.getImageFile().getInputStream(),path.resolve(userImageUpload.getImageFile().getOriginalFilename() + ".jpg"), StandardCopyOption.REPLACE_EXISTING);
            }
            catch (IOException io)
            {
                io.printStackTrace();
            }
        }

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("http://localhost:4200/dashboard");
        return new ResponseEntity<>(redirectView, HttpStatus.OK);
    }

    @GetMapping(value = "/getProfilePic/{userName}",produces=MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getUserImage(@PathVariable String userName) throws IOException{
        String localPath = System.getProperty("..\\MovieService\\UserAuthenticationService.dir");
        HttpHeaders headers = new HttpHeaders();
        Path path = Paths.get("..\\MovieService\\UserAuthenticationService\\ProfileImages\\"+ userName +".jpg");
        byte[] media = Files.readAllBytes(path);
        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(media,HttpStatus.OK);
        return responseEntity;
    }
}
