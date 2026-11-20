package com.lab.jpa.projetosanquim.mapper;

import com.lab.jpa.projetosanquim.domain.model.Professor;
import com.lab.jpa.projetosanquim.dto.ProfessorRequestDTO;
import com.lab.jpa.projetosanquim.dto.ProfessorResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(config = MapperConfiguration.class, uses = {UsuarioMapper.class})
public interface ProfessorMapper {

    @Mapping(target = "dadosUsuario", source = "usuario")
    ProfessorResponseDTO toResponse(Professor entity);

    List<ProfessorResponseDTO> toResponseList(List<Professor> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuario", source = "dadosUsuario")
    Professor toEntity(ProfessorRequestDTO request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuario", source = "dadosUsuario")
    void atualizar(ProfessorRequestDTO request, @MappingTarget Professor entity);
}
