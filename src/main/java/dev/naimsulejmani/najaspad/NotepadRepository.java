package dev.naimsulejmani.najaspad;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotepadRepository extends JpaRepository<Notepad, String> {
}
