package com.gotprint.services.notes.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.gotprint.services.notes.domain.Note;

public interface NotesRepository extends CrudRepository<Note, Long>
{
   public Note findByTitleAndUserId(@Param("title")String title,@Param("userId")Long userId);
    
   
   @Query(value="Select n from Note n where n.userId = :userId")
   public List<Note> getAllUserNotes(@Param("userId") Long userId);
   
   @Transactional
   @Modifying
   @Query(value="Delete from Note n where n.title = :title And  n.userId = :userId")
   public void  deleteTitleAndUserId(@Param("title")String title,@Param("userId")Long userId);
}
