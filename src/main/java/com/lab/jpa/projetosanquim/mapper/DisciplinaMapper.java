package com.lab.jpa.projetosanquim.mapper;

import com.lab.jpa.projetosanquim.domain.model.Disciplina;
import com.lab.jpa.projetosanquim.dto.DisciplinaRequestDTO;
import com.lab.jpa.projetosanquim.dto.DisciplinaResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(config = MapperConfiguration.class)
public interface DisciplinaMapper {

    DisciplinaResponseDTO toResponse(Disciplina entity);

    List<DisciplinaResponseDTO> toResponseList(List<Disciplina> entities);

    @Mapping(target = "id", ignore = true)
    Disciplina toEntity(DisciplinaRequestDTO request);

    @Mapping(target = "id", ignore = true)
    void atualizar(DisciplinaRequestDTO request, @MappingTarget Disciplina entity);
}
