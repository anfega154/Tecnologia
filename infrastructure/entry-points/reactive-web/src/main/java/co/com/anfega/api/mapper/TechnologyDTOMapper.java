package co.com.anfega.api.mapper;

import co.com.anfega.api.dto.CreateTechnologyDTO;
import co.com.anfega.api.dto.TechnologyDTO;
import co.com.anfega.model.tecnology.Technology;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TechnologyDTOMapper {
    TechnologyDTO toResponse(Technology technology);
    Technology toModel(CreateTechnologyDTO createTechnologyDTO);
}
