package by.soykin.nook.nookback.jpa.repository;

import by.soykin.nook.nookback.jpa.entities.Nook;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface NookRepository extends JpaRepository<Nook, String> {


}