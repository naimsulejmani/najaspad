package dev.naimsulejmani.najaspad;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class NotepadController {
    private final NotepadService service;

    @GetMapping("/{id}")
    public String getNotepad(@PathVariable String id, Model model, HttpServletRequest request) {
        var notepadDto = service.getNotepad(id);
        model.addAttribute("notepad", notepadDto);
        String url = request.getRequestURL().toString();
        model.addAttribute("shareUrl", url);

        // If the notepad has a password, don't show content until unlocked
        HttpSession session = request.getSession(false);
        boolean sessionUnlocked = false;
        if (session != null) {
            Object flag = session.getAttribute("unlocked-" + id);
            sessionUnlocked = flag instanceof Boolean && (Boolean) flag;
        }
        boolean unlocked = sessionUnlocked || !notepadDto.isPasswordProtected();
        model.addAttribute("unlocked", unlocked);

        return "home";
    }

    @GetMapping()
    public String getNotepad() {
        //generate random string
        String id = UUID.randomUUID().toString();

        return "redirect:/" + id;
    }

    // Save/update the notepad (only available when editor is shown — i.e. unlocked)
    @PostMapping("/{id}")
    public String saveNotepad(@PathVariable String id,
                              @RequestParam(required = false) String content,
                              @RequestParam(required = false) String password,
                              HttpServletRequest request) {
        NotepadDto dto = new NotepadDto();
        dto.setId(id);
        dto.setContent(content);
        dto.setPassword(password);

        service.saveNotepad(dto);

        // Keep the user unlocked after saving - they already had access to save
        // This prevents requiring password re-entry after every save
        HttpSession session = request.getSession(true);
        session.setAttribute("unlocked-" + id, Boolean.TRUE);

        return "redirect:/" + id; // redirect back to the note
    }

    // Unlock a password-protected notepad — user submits password via POST
    @PostMapping("/{id}/open")
    public String openNotepad(@PathVariable String id,
                              @RequestParam String password,
                              Model model,
                              HttpServletRequest request) {
        try {
            var notepadDto = service.getNotepad(id, password);

            // Mark session as unlocked for this id so subsequent GETs show the editor
            HttpSession session = request.getSession(true);
            session.setAttribute("unlocked-" + id, Boolean.TRUE);

            // Put notepad and other attributes into the model and render the page immediately
            model.addAttribute("notepad", notepadDto);
            model.addAttribute("unlocked", true);
            String url = request.getRequestURL().toString().replaceFirst("/open$", "");
            model.addAttribute("shareUrl", url);

            return "home";
        } catch (RuntimeException ex) {
            // failed to unlock: show prompt again with an error
            var notepadDto = service.getNotepad(id);
            model.addAttribute("notepad", notepadDto);
            model.addAttribute("unlocked", false);
            model.addAttribute("error", "Password is incorrect");
            model.addAttribute("shareUrl", request.getRequestURL().toString().replaceFirst("/open$", ""));
            return "home";
        }
    }
}
