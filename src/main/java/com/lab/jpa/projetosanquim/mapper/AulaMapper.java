package com.lab.jpa.projetosanquim.mapper;

import com.lab.jpa.projetosanquim.domain.model.Aula;
import com.lab.jpa.projetosanquim.dto.AulaRequestDTO;
import com.lab.jpa.projetosanquim.dto.AulaResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(config = MapperConfiguration.class, uses = {TurmaDisciplinaMapper.class})
public interface AulaMapper {

    AulaResponseDTO toResponse(Aula entity);

    List<AulaResponseDTO> toResponseList(List<Aula> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "turmaDisciplina", ignore = true)
    Aula toEntity(AulaRequestDTO request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "turmaDisciplina", ignore = true)
    void atualizar(AulaRequestDTO request, @MappingTarget Aula entity);
}
