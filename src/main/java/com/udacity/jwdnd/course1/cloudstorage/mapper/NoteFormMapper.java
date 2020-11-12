package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface NoteFormMapper {

    /**
     * Returns a single note for the logged-in user
     * @param notetitle
     * @param userid
     * @return
     */
    @Select("SELECT * FROM NOTES WHERE notetitle = #{notetitle} and userid = #{userid}")
    NoteForm getNote(String notetitle, Integer userid);

    /**
     * Returns list of notes for the logged-in user
     * @param userid
     * @return
     */
    @Select("SELECT * FROM NOTES WHERE userid = #{userid}")
    List<NoteForm> getAllNotes(Integer userid);

    /**
     * Deletes a given a note
     * @param noteid
     * @param userid
     * @return
     */
    @Select("DELETE FROM NOTES WHERE noteid = #{noteid}")
    NoteForm deleteNote(Integer noteid);

    /**
     * Insert a note
     * @param note
     * @return
     */
    @Insert("INSERT INTO NOTES (noteId, notetitle, notedescription, userid) VALUES (#{noteId}, #{notetitle}, #{notedescription}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int insertNote(NoteForm note);

    /**
     * Get all notes in the Universe
     * @return
     */
    @Select("SELECT * FROM NOTES")
    List<NoteForm> getNoteUniverse();
}
