package dev.naimsulejmani.najaspad;

public interface NotepadMapper {
    NotepadDto notepadToNotepadDto(Notepad notepad);

    Notepad notepadDtoToNotepad(NotepadDto notepadDto);
}
