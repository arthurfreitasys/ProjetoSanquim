package com.lab.jpa.projetosanquim.mapper;

import com.lab.jpa.projetosanquim.domain.model.Gestor;
import com.lab.jpa.projetosanquim.dto.GestorRequestDTO;
import com.lab.jpa.projetosanquim.dto.GestorResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(config = MapperConfiguration.class, uses = {UsuarioMapper.class})
public interface GestorMapper {

    @Mapping(target = "dadosUsuario", source = "usuario")
    GestorResponseDTO toResponse(Gestor entity);

    List<GestorResponseDTO> toResponseList(List<Gestor> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuario", source = "usuario")
    Gestor toEntity(GestorRequestDTO request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuario", source = "usuario")
    void atualizar(GestorRequestDTO request, @MappingTarget Gestor entity);
}
