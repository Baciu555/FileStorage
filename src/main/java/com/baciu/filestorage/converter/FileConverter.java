package com.baciu.filestorage.converter;

import com.baciu.filestorage.entity.File;
import com.baciu.filestorage.dto.FileDTO;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class FileConverter {

    public File toEntity(FileDTO fileDTO) {
        File file = new File();
        file.setId(fileDTO.getId());
        file.setName(fileDTO.getName());
        file.setPath(fileDTO.getPath());
        file.setDescription(fileDTO.getDescription());
        file.setUploadDate(fileDTO.getUploadDate());
        file.setSize(fileDTO.getSize());
        return file;
    }

    public FileDTO toDTO(File file) {
        FileDTO fileDTO = new FileDTO();
        fileDTO.setId(file.getId());
        fileDTO.setName(file.getName());
        fileDTO.setPath(file.getPath());
        fileDTO.setDescription(file.getDescription());
        fileDTO.setUploadDate(file.getUploadDate());
        fileDTO.setSize(file.getSize());
        return fileDTO;
    }

    public Set<FileDTO> toDTO(Set<File> files) {
        Set<FileDTO> filesDTO = new HashSet<>(0);
        for (File file : files)
            filesDTO.add(toDTO(file));
        return filesDTO;
    }

    public Set<File> toEntity(Set<FileDTO> filesDTO) {
        Set<File> files = new HashSet<>(0);
        for (FileDTO file : filesDTO)
            files.add(toEntity(file));
        return files;
    }

}
