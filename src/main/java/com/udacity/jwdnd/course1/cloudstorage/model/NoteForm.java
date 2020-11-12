package com.udacity.jwdnd.course1.cloudstorage.model;

// POJO - This class is backing the submitted note object
public class NoteForm {

    private Integer noteId;
    private String notetitle;
    private String notedescription;
    private Integer userid;

    // Constructor
    public NoteForm(Integer noteId, String notetitle, String notedescription) {
        this.noteId = noteId;
        this.notetitle = notetitle;
        this.notedescription = notedescription;
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
