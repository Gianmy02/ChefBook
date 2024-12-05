package it.sysman.chefbook.utils;

import it.sysman.chefbook.dto.RicettaDto;
import it.sysman.chefbook.entity.Ricetta;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public abstract class RicettaMapper {
    public abstract RicettaDto ricettaToRicettaDto(Ricetta ricetta);
    public abstract Ricetta ricettaDtoToRicetta(RicettaDto ricettaDto);
    public abstract List<RicettaDto> ricetteToRicetteDto(List<Ricetta> ricette);
}
