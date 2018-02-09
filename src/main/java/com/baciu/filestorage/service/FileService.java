package com.baciu.filestorage.service;

import com.baciu.filestorage.converter.FileConverter;
import com.baciu.filestorage.dto.FileDTO;
import com.baciu.filestorage.entity.File;
import com.baciu.filestorage.entity.User;
import com.baciu.filestorage.repository.FileRepository;
import com.baciu.filestorage.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Set;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FileConverter fileConverter;

    public File addFile(MultipartFile multipartFile, String name, String description, Long userId) {
        try {
            uploadFile(multipartFile);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        File file = new File();
        User user = new User();
        user.setId(userId);
        file.setUser(user);
        file.setPath("uploads/" + multipartFile.getOriginalFilename());
        file.setName(name);
        file.setDescription(description);
        file.setSize(multipartFile.getSize());
        file.setUploadDate(new Date());

        return fileRepository.save(file);
    }

    public Set<FileDTO> getUserFiles(Long id) {
        User user = userRepository.findOne(id);
        return fileConverter.toDTO(user.getFiles());
    }

    public void deleteFile(Long id) throws Exception {
        if (!fileRepository.exists(id))
            throw new Exception("plik nie istnieje");

        File file = fileRepository.findOne(id);
        Path path = Paths.get("uploads/" + file.getName());
        Files.delete(path);

        fileRepository.delete(id);
    }

    public ByteArrayResource getFile(Long id) throws IOException {
        if (!fileRepository.exists(id))
            throw new IOException("Plik nie istnieje");

        File file = fileRepository.findOne(id);
        Path uploadPath = Paths.get("uploads");
        java.io.File uploadFile = new java.io.File(uploadPath + "/" + file.getName() );

        Path path = Paths.get(uploadFile.getAbsolutePath());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
        return resource;
    }

    public ByteArrayResource getImg() throws IOException {
        Path uploadPath = Paths.get("uploads");
        java.io.File uploadFile = new java.io.File(uploadPath + "/datav2.jpg");

        Path path = Paths.get(uploadFile.getAbsolutePath());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
        return resource;
    }

    private void uploadFile(MultipartFile file) throws IOException {
        Path path = Paths.get("uploads/" + file.getOriginalFilename());
        byte[] bytes = file.getBytes();
        Files.write(path, bytes);
    }
}
