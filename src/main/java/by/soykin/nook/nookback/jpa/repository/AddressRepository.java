package by.soykin.nook.nookback.jpa.repository;

import by.soykin.nook.nookback.jpa.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, String> {

}