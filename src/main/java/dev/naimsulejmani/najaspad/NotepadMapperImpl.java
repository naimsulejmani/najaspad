package dev.naimsulejmani.najaspad;

import org.springframework.stereotype.Component;

@Component
public class NotepadMapperImpl implements NotepadMapper {
    @Override
    public NotepadDto notepadToNotepadDto(Notepad notepad) {
        NotepadDto dto = new NotepadDto();
        dto.setId(notepad.getId());
        dto.setContent(notepad.getContent());
        dto.setCreatedAt(notepad.getCreatedAt());
        dto.setUpdatedAt(notepad.getUpdatedAt());
        dto.setPassword(notepad.getPassword());
        return dto;
    }

    @Override
    public Notepad notepadDtoToNotepad(NotepadDto notepadDto) {
        Notepad notepad = new Notepad();
        notepad.setId(notepadDto.getId());
        notepad.setContent(notepadDto.getContent());
        notepad.setCreatedAt(notepadDto.getCreatedAt());
        notepad.setUpdatedAt(notepadDto.getUpdatedAt());
        notepad.setPassword(notepadDto.getPassword());
        return notepad;
    }
}
