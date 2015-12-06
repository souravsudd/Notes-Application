package com.gotprint.services.notes.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "notes")
public class Note
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "noteId")
    Long noteId;
    
    @Column(name = "title") 
    String title;
    
    @Column(name ="noteText")
    String noteText;
    
    
    @Column(name = "crt_dt", insertable = false, updatable = false)
    private Timestamp createTime;
    
    
    @Column(name = "last_upd_dt", insertable = false, updatable = false)
    private Timestamp lastUpdateTime;
    
    // User Note Association need to maintained in separate table say User_Note,right now putting under
    // notes itself as it is very small Application.
    @Column(name ="userId")
    private Long userId;

    @JsonIgnore
    public Long getNoteId()
    {
        return noteId;
    }

    public void setNoteId(Long noteId)
    {
        this.noteId = noteId;
    }

    
    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getNoteText()
    {
        return noteText;
    }

    public void setNoteText(String noteText)
    {
        this.noteText = noteText;
    }
    
    @JsonIgnore
    public Timestamp getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime)
    {
        this.createTime = createTime;
    }
    
    @JsonIgnore
    public Timestamp getLastUpdateTime()
    {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Timestamp lastUpdateTime)
    {
        this.lastUpdateTime = lastUpdateTime;
    }
    
    @JsonIgnore
    public Long getUserId()
    {
        return userId;
    }

    @JsonProperty
    public void setUserId(Long userId)
    {
        this.userId = userId;
    }
    
   
    
}
