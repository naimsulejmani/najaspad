package dev.naimsulejmani.najaspad;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotepadDto {
    private String id;

    private String content;

    private String password;

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt;

    public boolean isPasswordProtected() {
        return password != null && !password.isBlank();
    }
}
