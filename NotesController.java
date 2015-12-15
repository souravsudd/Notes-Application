package com.gotprint.services.notes.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;
import com.gotprint.services.notes.domain.Note;
import com.gotprint.services.notes.exception.ValidationException;
import com.gotprint.services.notes.service.NotesService;

@Controller
@RequestMapping(value = "/notes")
public class NotesController
{

    private static final Logger logger = LoggerFactory.getLogger(
        NotesController.class);

    @Autowired
    NotesService notesService;

    /***************************
     * 
     * @param note
     * @param user
     * @return
     * @throws ValidationException
     */
    @RequestMapping(method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    public ResponseEntity<String> createNotes(@RequestBody Note note,
        @AuthenticationPrincipal
    final UserDetails user) throws ValidationException
    {
        note = notesService.createNote(note, user.getUsername());

        Gson gson = new Gson();

        return new ResponseEntity<String>(
            gson.toJson("Note Created Successfully."), HttpStatus.CREATED);
    }

    /**********************************
     * 
     * @param noteTitle
     * @param user
     * @return
     * @throws ValidationException
     */
    @RequestMapping(value = "/getNoteByTitle", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    public ResponseEntity<Note> getNoteByTitle(
        @RequestParam("noteTitle") String noteTitle, @AuthenticationPrincipal
    final UserDetails user) throws ValidationException
    {
        {
            Note note = notesService.getNoteByTitle(noteTitle,
                user.getUsername());

            return new ResponseEntity<Note>(note, HttpStatus.OK);
        }
    }

    /*********************************
     * 
     * @param user
     * @return
     * @throws ValidationException
     */
    @RequestMapping(method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    public ResponseEntity<List<Note>> findAllUserNotes(@AuthenticationPrincipal
    final UserDetails user) throws ValidationException
    {
        {
            List<Note> notes = notesService.findAllUserNotes(
                user.getUsername());

            return new ResponseEntity<List<Note>>(notes, HttpStatus.OK);
        }
    }

    /***********************************************
     * 
     * @param noteTitle
     * @param user
     * @return
     * @throws ValidationException
     */
    @RequestMapping(method = RequestMethod.DELETE, produces = "application/json; charset=UTF-8")
    public ResponseEntity<String> deleteNoteByTitle(
        @RequestParam("noteTitle") String noteTitle, @AuthenticationPrincipal
    final UserDetails user) throws ValidationException
    {
        {
            notesService.deleteNote(noteTitle, user.getUsername());

            Gson gson = new Gson();

            return new ResponseEntity<String>(
                gson.toJson("Note Deleted Successfully"), HttpStatus.OK);
        }
    }

    /*****************************************************
     * 
     * @param noteTitle
     * @param note
     * @param user
     * @return
     * @throws ValidationException
     */
    @RequestMapping(method = RequestMethod.PUT, produces = "application/json; charset=UTF-8")
    public ResponseEntity<String> updateNotes(
        @RequestParam("noteTitle") String noteTitle, @RequestBody Note note,
        @AuthenticationPrincipal
    final UserDetails user) throws ValidationException
    {
        note = notesService.updateUserNote(noteTitle, note, user.getUsername());

        Gson gson = new Gson();
        return new ResponseEntity<String>(
            gson.toJson("Note Updated Successfully."), HttpStatus.CREATED);
    }
}
