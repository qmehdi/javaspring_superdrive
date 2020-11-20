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

        String fileUploadSuccess = null;
        String fileIsBlank = null;

        // Check if the file already exists in the db
        File singleFile = getSingleFile(file.getFilename(), file.getUserid());

        if (singleFile != null) {

            // File already exists, hence return an error
            return fileUploadSuccess = "false";
        } else {
            filemapper.insertFile(file);
            return fileUploadSuccess = "true";
        }
    }

    // Delete file from db
    public void deleteFileFromDB(Integer fileid, Integer userid) {

        filemapper.deleteFile(fileid, userid);
    }

    // Get all files from db
    public List<File> getAllFilesFromDB(Integer userid) {

        return filemapper.getAllFiles(userid);
    }

    // Get a single file
    public File getSingleFile(String filename, Integer userid) {

        return filemapper.getFile(filename, userid);
    }
}
