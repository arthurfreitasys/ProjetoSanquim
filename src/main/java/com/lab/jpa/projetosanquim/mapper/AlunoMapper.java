package com.lab.jpa.projetosanquim.mapper;

import com.lab.jpa.projetosanquim.domain.model.Aluno;
import com.lab.jpa.projetosanquim.dto.AlunoRequestDTO;
import com.lab.jpa.projetosanquim.dto.AlunoResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(config = MapperConfiguration.class, uses = {UsuarioMapper.class})
public interface AlunoMapper {

    @Mapping(target = "dadosUsuario", source = "usuario")
    AlunoResponseDTO toResponse(Aluno entity);

    List<AlunoResponseDTO> toResponseList(List<Aluno> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ra", ignore = true)
    @Mapping(target = "usuario", source = "dadosUsuario")
    Aluno toEntity(AlunoRequestDTO request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ra", ignore = true)
    @Mapping(target = "usuario", source = "dadosUsuario")
    void atualizar(AlunoRequestDTO request, @MappingTarget Aluno entity);
}
