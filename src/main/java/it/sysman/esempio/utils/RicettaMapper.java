package it.sysman.esempio.utils;

import it.sysman.esempio.dto.RicettaDto;
import it.sysman.esempio.entity.Ricetta;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
public abstract class RicettaMapper {
    public abstract RicettaDto ricettaToRicettaDto(Ricetta ricetta);
    public abstract Ricetta ricettaDtoToRicetta(RicettaDto ricettaDto);
    public abstract List<RicettaDto> ricetteToRicetteDto(List<Ricetta> ricette);
}
