package com.lab.jpa.projetosanquim.mapper;

import com.lab.jpa.projetosanquim.domain.model.Frequencia;
import com.lab.jpa.projetosanquim.dto.FrequenciaRequestDTO;
import com.lab.jpa.projetosanquim.dto.FrequenciaResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(config = MapperConfiguration.class)
public interface FrequenciaMapper {

    @Mapping(target = "nomeAluno", source = "aluno.usuario.nome")
    @Mapping(target = "aulaID", source = "aula.id")
    FrequenciaResponseDTO toResponse(Frequencia entity);

    List<FrequenciaResponseDTO> toResponseList(List<Frequencia> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "aluno", ignore = true)
    @Mapping(target = "aula", ignore = true)
    Frequencia toEntity(FrequenciaRequestDTO request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "aluno", ignore = true)
    @Mapping(target = "aula", ignore = true)
    void atualizar(FrequenciaRequestDTO request, @MappingTarget Frequencia entity);
}
