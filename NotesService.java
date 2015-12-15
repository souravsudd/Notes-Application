package com.gotprint.services.notes.service;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gotprint.services.notes.bo.ErrorObject;
import com.gotprint.services.notes.domain.Note;
import com.gotprint.services.notes.exception.ValidationException;
import com.gotprint.services.notes.repositories.NotesRepository;
import com.gotprint.services.notes.repositories.UsersRepository;

@Service
public class NotesService
{

    private static Logger logger = LoggerFactory.getLogger(NotesService.class);

    @Autowired
    NotesRepository notesRepository;

    @Autowired
    UsersRepository usersRepository;

    /*************************************
     * createNote- Create new user Note
     * 
     * @param note
     * @return
     * @throws ValidationException
     *************************************/
    @Transactional
    public Note createNote(Note note, String email) throws ValidationException
    {
        logger.debug("Processing Create Note");
        logger.debug("User email {}", email);
        logger.debug("Input Note {}", note);

        Long userId = usersRepository.getUserId(email);

        if (userId == null)
        {
            ErrorObject error = new ErrorObject();
            error.setCode("INVALID_USER");
            error.setField("email");
            error.setMessage("No user exist for the email Id:" + email);
            throw new ValidationException("Invalid User", error);
        }

        Note fetchedNote = notesRepository.findByTitleAndUserId(
            note.getTitle().trim(), userId);

        if (fetchedNote != null)
        {
            ErrorObject error = new ErrorObject();
            error.setCode("Duplicate_Record");
            error.setField("title");
            error.setMessage("User Note Already exist for the title:" + note.getTitle().trim());
            throw new ValidationException("Duplicate Record", error);
        }

        note.setUserId(userId);

        notesRepository.save(note);

        return note;
    }

    /*******************************************
     * getNoteByTitle - Find Note By Title
     * 
     * @param noteTitle
     * @param userId
     * @return
     * @throws ValidationException
     ******************************************/
    @Transactional
    public Note getNoteByTitle(String noteTitle, String email)
            throws ValidationException
    {

        Long userId = usersRepository.getUserId(email);

        if (userId == null)
        {
            ErrorObject error = new ErrorObject();
            error.setCode("INVALID_USER");
            error.setField("email");
            error.setMessage("No user exist for the email Id:" + email);
            throw new ValidationException("Invalid User", error);
        }

        Note note = notesRepository.findByTitleAndUserId(noteTitle.trim(),
            userId);

        // TODO: Have to check business requirement, whether throw error or send
        // zero records.
        if (note == null)
        {
            ErrorObject error = new ErrorObject();
            error.setCode("INVALID_NOTE");
            error.setField("requestParam.noteTitle");
            error.setMessage("No user note exists having title:" + noteTitle);
            throw new ValidationException("Invalid Note", error);
        }

        return note;
    }

    /***********************************************
     * findAllUserNotes- Find All user Notes
     * 
     * @param userId
     * @return
     * @throws ValidationException
     ************************************************/
    @Transactional
    public List<Note> findAllUserNotes(String email) throws ValidationException
    {

        Long userId = usersRepository.getUserId(email);

        if (userId == null)
        {
            ErrorObject error = new ErrorObject();
            error.setCode("INVALID_USER");
            error.setField("email");
            error.setMessage("No user exist for the email Id:" + email);
            throw new ValidationException("Invalid User", error);
        }

        List<Note> notes = notesRepository.getAllUserNotes(userId);

        return notes;
    }

    /**********************************************
     * updateUserNote - Update User Note
     * 
     * @param requestNote
     * @param userId
     * @return
     * @throws ValidationException
     ***********************************************/
    @Transactional
    public Note updateUserNote(String noteTitle, Note requestNote, String email)
            throws ValidationException
    {

        Long userId = usersRepository.getUserId(email);

        if (userId == null)
        {
            ErrorObject error = new ErrorObject();
            error.setCode("INVALID_USER");
            error.setField("email");
            error.setMessage("No user exist for the email Id:" + email);
            throw new ValidationException("Invalid User", error);
        }

        Note fetchedNote = notesRepository.findByTitleAndUserId(
            noteTitle, userId);

        // TODO: Have to check business requirement, whether throw error when no update record found
        if (fetchedNote == null)
        {
            ErrorObject error = new ErrorObject();
            error.setCode("INVALID_NOTE");
            error.setField("requestParam.noteTitle");
            error.setMessage(
                "No user note exists having title:" + noteTitle);
            throw new ValidationException("Invalid Note", error);
        }

        fetchedNote.setTitle(requestNote.getTitle());
        fetchedNote.setNoteText(requestNote.getNoteText());

        notesRepository.save(fetchedNote);

        return fetchedNote;
    }

    /************************************************************
     * deleteNote- delete userNote
     * 
     * @param requestNote
     * @param email
     * @throws ValidationException
     *************************************************************/
    @Transactional
    public void deleteNote(String noteTitle, String email)
            throws ValidationException
    {
        Long userId = usersRepository.getUserId(email);

        if (userId == null)
        {
            ErrorObject error = new ErrorObject();
            error.setCode("INVALID_USER");
            error.setField("email");
            error.setMessage("No user exist for the email Id:" + email);
            throw new ValidationException("Invalid User", error);
        }

        notesRepository.deleteTitleAndUserId(noteTitle, userId);
    }
}
