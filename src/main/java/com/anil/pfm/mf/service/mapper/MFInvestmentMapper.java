package com.anil.pfm.service.mapper;

import com.anil.pfm.domain.*;
import com.anil.pfm.service.dto.MFInvestmentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MFInvestment and its DTO MFInvestmentDTO.
 */
@Mapper(componentModel = "spring", uses = {MutualFundMapper.class, MFPortfolioMapper.class, GoalMapper.class})
public interface MFInvestmentMapper extends EntityMapper<MFInvestmentDTO, MFInvestment> {

    @Mapping(source = "fund.id", target = "fundId")
    @Mapping(source = "folio.id", target = "folioId")
    @Mapping(source = "goal.id", target = "goalId")
    MFInvestmentDTO toDto(MFInvestment mFInvestment); 

    @Mapping(source = "fundId", target = "fund")
    @Mapping(source = "folioId", target = "folio")
    @Mapping(source = "goalId", target = "goal")
    MFInvestment toEntity(MFInvestmentDTO mFInvestmentDTO);

    default MFInvestment fromId(Long id) {
        if (id == null) {
            return null;
        }
        MFInvestment mFInvestment = new MFInvestment();
        mFInvestment.setId(id);
        return mFInvestment;
    }
}
