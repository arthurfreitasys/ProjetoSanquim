package com.lab.jpa.projetosanquim.mapper;

import org.mapstruct.MapperConfig;
import org.mapstruct.ReportingPolicy;

//Faz configuração dos mappers
@MapperConfig(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface MapperConfiguration {}
