package by.soykin.nook.nookback.jpa.repository;

import by.soykin.nook.nookback.jpa.entities.Nook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NookRepository extends JpaRepository<Nook, String> {
}