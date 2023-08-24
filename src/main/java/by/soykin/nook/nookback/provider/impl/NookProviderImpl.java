package by.soykin.nook.nookback.provider.impl;

import by.soykin.nook.nookback.jpa.entities.Nook;
import by.soykin.nook.nookback.jpa.repository.NookRepository;
import by.soykin.nook.nookback.mapper.Mapper;
import by.soykin.nook.nookback.model.NookModel;
import by.soykin.nook.nookback.provider.ImageProvider;
import by.soykin.nook.nookback.provider.NookProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NookProviderImpl implements NookProvider {
    private final Mapper<Nook, NookModel> mapper;
    private final NookRepository nookRepository;




//    @Override
//    public List<NookModel> findAll() {
//
//        List<Object> timeOfEditing = nookRepository.getAll(PageRequest.of(0, 36, Sort.by("timeOfEditing").descending())).getContent();
//        return timeOfEditing.stream()
//               .map(element->(Nook)element)                                /*косяк!!!!*/
//                .map(mapper::toModel)
//                .collect(Collectors.toList());
//
//    }


    public List<NookModel> getAll(Integer offset, Integer limit) {
        List<NookModel> collect = nookRepository
                .findAll(PageRequest.of(offset, limit, Sort.Direction.DESC, "timeOfEditing"))
                .stream()
                .map(entity -> {
                    try {
                        return mapper.toModel(entity);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                //.sorted(Comparator.comparing(NookModel::getTimeOfEditing))
                .collect(Collectors.toList());

        return collect;
    }
//
//    Page<Nook> findAllWithPagination(Integer pageNumber, Integer pageSize, String sort){
//
//    }
}
