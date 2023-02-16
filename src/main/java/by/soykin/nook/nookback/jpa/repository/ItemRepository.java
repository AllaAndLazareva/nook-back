package by.soykin.nook.nookback.jpa.repository;

import by.soykin.nook.nookback.jpa.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, String> {
}