package com.lab.jpa.projetosanquim.mapper;

import com.lab.jpa.projetosanquim.domain.model.Turma;
import com.lab.jpa.projetosanquim.dto.TurmaRequestDTO;
import com.lab.jpa.projetosanquim.dto.TurmaResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(config = MapperConfiguration.class)
public interface TurmaMapper {

    TurmaResponseDTO toResponse(Turma entity);

    List<TurmaResponseDTO> toResponseList(List<Turma> entities);

    @Mapping(target = "id", ignore = true)
    Turma toEntity(TurmaRequestDTO request);

    @Mapping(target = "id", ignore = true)
    void atualizar(TurmaRequestDTO request, @MappingTarget Turma entity);
}
