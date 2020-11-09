package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class FileStorageService {

    FileMapper filemapper;

    public FileStorageService(FileMapper filemapper) {
        this.filemapper = filemapper;
    }

    // Insert file into db
    public void insertFileIntoDB(File file) {

        filemapper.insertFile(file);
    }

    // Delete file from db
    public void deleteFileFromDB(String filename, Integer userid) {

        filemapper.deleteFile(filename, userid);
    }

    // Get all files from db
    public List<File> getAllFilesFromDB(Integer userid) {

        return filemapper.getAllFiles(userid);
    }

    // Get File Universe
    public List<File> getFileUniverse() {
        return filemapper.getFileUniverse();
    }

    // Get a single file
    public File getSingleFile(String filename, Integer userid) {

        return filemapper.getFile(filename, userid);
    }
}
