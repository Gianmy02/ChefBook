package it.sysman.chefbook.utils;

import it.sysman.chefbook.dto.AutoreDto;
import it.sysman.chefbook.entity.Autore;
import it.sysman.chefbook.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public abstract class AutoreMapper {
    public abstract AutoreDto autoreToAutoreDto(Autore autore);
    public abstract Autore autoreDtoToAutore(AutoreDto dto);
    public abstract List<AutoreDto> autoriToAutoriDto(List<Autore> autori);
    @Mapping(target = "username",source = "dto.email")
    public abstract User autoreDtoToUser(AutoreDto dto);
}
