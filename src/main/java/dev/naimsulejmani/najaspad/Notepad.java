package dev.naimsulejmani.najaspad.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

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
