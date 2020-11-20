package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteFormMapper {

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
    @Select("DELETE FROM NOTES WHERE noteid = #{noteid} AND userid=#{userid}")
    NoteForm deleteNote(Integer noteid, Integer userid);

    /**
     * Insert a note
     * @param note
     * @return
     */
    @Insert("INSERT INTO NOTES (noteId, notetitle, notedescription, userid) VALUES (#{noteId}, #{notetitle}, #{notedescription}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int insertNote(NoteForm note);

//    /**
//     * Get all notes in the Universe
//     * @return
//     */
//    @Select("SELECT * FROM NOTES")
////    @Results({ @Result(property = "noteId", column = "noteid",id=true), @Result(property = "notetitle", column = "notetitle"), @Result(property = "notedescription", column = "notedescription"), @Result(property = "userid", column = "userid")})
//    List<NoteForm> getNoteUniverse();

    /**
     * Update a note that already exists in the db
     * @param note
     * @return
     */
    @Update("UPDATE NOTES SET notetitle=#{notetitle}, notedescription=#{notedescription} WHERE noteId=#{noteId} AND userid=#{userid}")
    int updateNote(NoteForm note);
}
