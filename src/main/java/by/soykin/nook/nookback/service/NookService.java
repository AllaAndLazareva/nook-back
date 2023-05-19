package by.soykin.nook.nookback.service;

import by.soykin.nook.nookback.jpa.entities.Nook;
import org.springframework.stereotype.Service;

@Service
public interface NookService {

     void save(Nook nook);
}
