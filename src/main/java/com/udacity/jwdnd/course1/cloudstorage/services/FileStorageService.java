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
    public String insertFileIntoDB(File file) {

        // fileUploadSuccess = null;
        // fileUploadFailure = null;

        // Get userid from File object

        // Get filename from file object

        // call filemapper.singlefile , pas

//        if above return something it means we have a file with same name
            // return String message that file exists, return an error return "fileUploadFailure"
//else if returns null
        // call filemapper.insertFile(file) and return success msg;
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
