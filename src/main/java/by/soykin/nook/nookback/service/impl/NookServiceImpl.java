package by.soykin.nook.nookback.service.impl;

import by.soykin.nook.nookback.jpa.entities.Nook;
import by.soykin.nook.nookback.jpa.repository.NookRepository;
import by.soykin.nook.nookback.service.NookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NookServiceImpl implements NookService {
    @Autowired
    NookRepository nookRepository;

    @Override
    public void save(Nook nook) {
        nookRepository.save(nook);
    }
}
