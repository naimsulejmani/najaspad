package dev.naimsulejmani.najaspad;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "notepads")
public class Notepad {
    @Id
    private String id;

    @Column(name = "content", length = Integer.MAX_VALUE)
    private String content;

    @Column(name = "password", length = 50)
    private String password;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at", insertable = false)
    private LocalDateTime updatedAt;
}
