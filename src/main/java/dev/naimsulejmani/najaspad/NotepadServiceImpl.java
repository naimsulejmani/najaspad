package dev.naimsulejmani.najaspad;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotepadServiceImpl implements NotepadService {
    private final NotepadRepository repository;
    private final NotepadMapper mapper;

    @Override
    public NotepadDto saveNotepad(NotepadDto notepadDto) {
        Notepad notepad = mapper.notepadDtoToNotepad(notepadDto);
        notepad = repository.save(notepad);
        return mapper.notepadToNotepadDto(notepad);
    }

    @Override
    public NotepadDto getNotepad(String id) {
        Notepad notepad = repository.findById(id).orElse(null);

        if (notepad == null) {
            notepad = new Notepad();
            notepad.setId(id);
        }

        return mapper.notepadToNotepadDto(notepad);
    }

    @Override
    public NotepadDto getNotepad(String id, String password) {
        Notepad notepad = repository.findById(id).orElse(null);

        if (notepad == null) {
            throw new RuntimeException("Notepad not found");
        }
        if (!notepad.getPassword().equals(password)) {
            throw new RuntimeException("Password didnt match");
        }
        return mapper.notepadToNotepadDto(notepad);
    }
}
