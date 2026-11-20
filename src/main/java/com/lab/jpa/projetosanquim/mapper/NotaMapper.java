package com.lab.jpa.projetosanquim.mapper;

import com.lab.jpa.projetosanquim.domain.model.Nota;
import com.lab.jpa.projetosanquim.dto.NotaRequestDTO;
import com.lab.jpa.projetosanquim.dto.NotaResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(config = MapperConfiguration.class)
public interface NotaMapper {

    @Mapping(target = "tituloAvaliacao", source = "avaliacao.titulo")
    @Mapping(target = "nomeAluno", source = "aluno.usuario.nome")
    NotaResponseDTO toResponse(Nota entity);

    List<NotaResponseDTO> toResponseList(List<Nota> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "avaliacao", ignore = true)
    @Mapping(target = "aluno", ignore = true)
    Nota toEntity(NotaRequestDTO request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "avaliacao", ignore = true)
    @Mapping(target = "aluno", ignore = true)
    void atualizar(NotaRequestDTO request, @MappingTarget Nota entity);
}
