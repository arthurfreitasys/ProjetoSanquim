package com.lab.jpa.projetosanquim.mapper;

import com.lab.jpa.projetosanquim.domain.model.TurmaDisciplina;
import com.lab.jpa.projetosanquim.dto.TurmaDisciplinaRequestDTO;
import com.lab.jpa.projetosanquim.dto.TurmaDisciplinaResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(config = MapperConfiguration.class, uses = {TurmaMapper.class, ProfessorMapper.class, DisciplinaMapper.class})
public interface TurmaDisciplinaMapper {

    @Mapping(target = "nomeDisciplina", source = "disciplina.nome")
    @Mapping(target = "nomeProfessor", source = "professor.usuario.nome")
    TurmaDisciplinaResponseDTO toResponse(TurmaDisciplina entity);

    List<TurmaDisciplinaResponseDTO> toResponseList(List<TurmaDisciplina> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "turma", ignore = true)
    @Mapping(target = "disciplina", ignore = true)
    @Mapping(target = "professor", ignore = true)
    TurmaDisciplina toEntity(TurmaDisciplinaRequestDTO request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "turma", ignore = true)
    @Mapping(target = "disciplina", ignore = true)
    @Mapping(target = "professor", ignore = true)
    void atualizar(TurmaDisciplinaRequestDTO request, @MappingTarget TurmaDisciplina entity);
}
