package com.lab.jpa.projetosanquim.mapper;

import com.lab.jpa.projetosanquim.domain.model.Usuario;
import com.lab.jpa.projetosanquim.dto.UsuarioRequestDTO;
import com.lab.jpa.projetosanquim.dto.UsuarioResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UsuarioMapper {

    UsuarioResponseDTO toDTO(Usuario entity);

    @Mapping(target = "id", ignore = true)
    Usuario toEntity(UsuarioRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(UsuarioRequestDTO dto, @MappingTarget Usuario entity);
}
