package dev.naimsulejmani.najaspad;

public interface NotepadService {
    NotepadDto saveNotepad(NotepadDto notepadDto);

    NotepadDto getNotepad(String id);

    NotepadDto getNotepad(String id, String password);
}
