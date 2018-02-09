package com.baciu.filestorage.controller;

import com.baciu.filestorage.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@CrossOrigin(origins="*")
public class FileController {

    @Autowired
    private FileService fileService;

    @GetMapping("/files/{id}")
    public ResponseEntity<?> getFile(@PathVariable("id") Long id) throws IOException {
        System.out.println(id);
        ByteArrayResource resource = fileService.getFile(id);

        return ResponseEntity.ok()
                .contentLength(resource.contentLength())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }

    @GetMapping("files/users/{id}")
    public ResponseEntity<?> getUserFiles(@PathVariable("id") Long id) {
        return new ResponseEntity<>(fileService.getUserFiles(id), HttpStatus.OK);
    }

    @PostMapping("files/users/{id}")
    public ResponseEntity<?> addFile(@RequestParam("file") MultipartFile multipartFile, @PathVariable("id") Long id) {
        fileService.addFile(multipartFile, multipartFile.getOriginalFilename(), "Opis 123", id);

        return new ResponseEntity<>("dodano plik", HttpStatus.OK);
    }

    @DeleteMapping("files/{id}")
    public ResponseEntity<?> deleteFile(@PathVariable Long id) throws Exception {
        fileService.deleteFile(id);

        return new ResponseEntity<>("plik usuniety", HttpStatus.OK);
    }

    @GetMapping("datav2.jpg")
    public ResponseEntity<?> getImg() throws IOException {
        return new ResponseEntity<>(fileService.getImg(), HttpStatus.OK);
    }

}
