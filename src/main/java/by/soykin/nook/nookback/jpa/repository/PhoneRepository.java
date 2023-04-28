package by.soykin.nook.nookback.jpa.repository;

import by.soykin.nook.nookback.jpa.entities.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, String> {
}
