package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteFormMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteStorageService {

    private final NoteFormMapper noteFormMapper;

    // Constructor
    public NoteStorageService(NoteFormMapper noteFormMapper) {

        this.noteFormMapper = noteFormMapper;
    }

    // This method creates the note row in the NOTES db
    public int insertNoteIntoDB(NoteForm noteForm) {
        return noteFormMapper.insertNote(noteForm);
    }

    // Get all notes for the logged-in user
    public List<NoteForm> getAllNotesFromDB(Integer userid) {
        return noteFormMapper.getAllNotes(userid);
    }

    // Get notes universe
    public List<NoteForm> getNotesUniverse() {
        return noteFormMapper.getNoteUniverse();
    }
}
