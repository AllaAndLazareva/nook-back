package by.soykin.nook.nookback.mapper;

import java.io.IOException;

public interface Mapper <Entity, Model>{

    Entity toEntity(Model model);
    Model toModel(Entity entity) throws IOException;
}
