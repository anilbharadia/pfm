package com.anil.pfm.mf.service.mapper;

import com.anil.pfm.domain.*;
import com.anil.pfm.mf.domain.MFPortfolio;
import com.anil.pfm.service.dto.MFPortfolioDTO;
import com.anil.pfm.service.mapper.EntityMapper;
import com.anil.pfm.service.mapper.PersonMapper;

import org.mapstruct.*;

/**
 * Mapper for the entity MFPortfolio and its DTO MFPortfolioDTO.
 */
@Mapper(componentModel = "spring", uses = {AMCMapper.class, PersonMapper.class})
public interface MFPortfolioMapper extends EntityMapper<MFPortfolioDTO, MFPortfolio> {

    @Mapping(source = "amc.id", target = "amcId")
    @Mapping(source = "owner.id", target = "ownerId")
    MFPortfolioDTO toDto(MFPortfolio mFPortfolio); 

    @Mapping(source = "amcId", target = "amc")
    @Mapping(source = "ownerId", target = "owner")
    MFPortfolio toEntity(MFPortfolioDTO mFPortfolioDTO);

    default MFPortfolio fromId(Long id) {
        if (id == null) {
            return null;
        }
        MFPortfolio mFPortfolio = new MFPortfolio();
        mFPortfolio.setId(id);
        return mFPortfolio;
    }
}
