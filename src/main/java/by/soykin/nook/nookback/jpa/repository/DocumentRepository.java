package by.soykin.nook.nookback.jpa.repository;

import by.soykin.nook.nookback.jpa.entities.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {
}