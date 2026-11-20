package com.lab.jpa.projetosanquim.mapper;

import com.lab.jpa.projetosanquim.domain.model.Avaliacao;
import com.lab.jpa.projetosanquim.dto.AvaliacaoRequestDTO;
import com.lab.jpa.projetosanquim.dto.AvaliacaoResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(config = MapperConfiguration.class, uses = {TurmaDisciplinaMapper.class})
public interface AvaliacaoMapper {

    AvaliacaoResponseDTO toResponse(Avaliacao entity);

    List<AvaliacaoResponseDTO> toResponseList(List<Avaliacao> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "turmaDisciplina", ignore = true)
    Avaliacao toEntity(AvaliacaoRequestDTO request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "turmaDisciplina", ignore = true)
    void atualizar(AvaliacaoRequestDTO request, @MappingTarget Avaliacao entity);
}
