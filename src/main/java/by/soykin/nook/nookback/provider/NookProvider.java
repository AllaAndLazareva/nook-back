package by.soykin.nook.nookback.provider;

import by.soykin.nook.nookback.jpa.entities.Nook;
import by.soykin.nook.nookback.model.NookModel;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NookProvider {
//
List<NookModel> getAll(Integer offset, Integer limit);


}
