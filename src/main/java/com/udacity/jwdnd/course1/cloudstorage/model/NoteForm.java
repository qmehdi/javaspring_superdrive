package com.udacity.jwdnd.course1.cloudstorage.model;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;

// POJO - This class is backing the submitted note object
public class NoteForm {

    private Integer noteId;
    private String notetitle;
    private String notedescription;
    private Integer userid;

    // Empty Constructor - This is necessary to accommodate the fact that the noteId is not part of the second constructor.
    public NoteForm() {
    }

    // Constructor
    public NoteForm(String notetitle, String notedescription, Integer userid) {
        this.notetitle = notetitle;
        this.notedescription = notedescription;
        this.userid = userid;
    }

    public Integer getNoteId() {
        return noteId;
    }

    public void setNoteId(Integer noteId) {
        this.noteId = noteId;
    }

    public String getNotetitle() {
        return notetitle;
    }

    public void setNotetitle(String notetitle) {
        this.notetitle = notetitle;
    }

    public String getNotedescription() {
        return notedescription;
    }

    public void setNotedescription(String notedescription) {
        this.notedescription = notedescription;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }
}
