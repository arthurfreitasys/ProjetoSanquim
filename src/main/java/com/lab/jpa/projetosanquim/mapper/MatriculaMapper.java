package com.lab.jpa.projetosanquim.mapper;

import com.lab.jpa.projetosanquim.domain.model.Matricula;
import com.lab.jpa.projetosanquim.dto.MatriculaRequestDTO;
import com.lab.jpa.projetosanquim.dto.MatriculaResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(config = MapperConfiguration.class, uses = {TurmaMapper.class, AlunoMapper.class})
public interface MatriculaMapper {

    @Mapping(target = "nomeAluno", source = "aluno.usuario.nome")
    MatriculaResponseDTO toResponse(Matricula entity);

    List<MatriculaResponseDTO> toResponseList(List<Matricula> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "aluno", ignore = true)
    @Mapping(target = "turma", ignore = true)
    Matricula toEntity(MatriculaRequestDTO request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "aluno", ignore = true)
    @Mapping(target = "turma", ignore = true)
    void atualizar(MatriculaRequestDTO request, @MappingTarget Matricula entity);
}
