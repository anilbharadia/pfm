package com.anil.pfm.mf.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.anil.pfm.mf.domain.MFInvestment;
import com.anil.pfm.mf.service.dto.CreateMFInvestmentVM;
import com.anil.pfm.mf.service.dto.MFInvestmentDTO;
import com.anil.pfm.service.mapper.EntityMapper;
import com.anil.pfm.service.mapper.GoalMapper;
import com.anil.pfm.service.mapper.MyAccountMapper;
import com.anil.pfm.service.mapper.TransactionMapper;
import com.anil.pfm.tx.service.dto.CreateTransactionVM;

/**
 * Mapper for the entity MFInvestment and its DTO MFInvestmentDTO.
 */
@Mapper(componentModel = "spring", uses = { MutualFundMapper.class, MFPortfolioMapper.class, GoalMapper.class,
		TransactionMapper.class, MyAccountMapper.class })
public interface MFInvestmentMapper extends EntityMapper<MFInvestmentDTO, MFInvestment> {

	@Mapping(source = "fund.id", target = "fundId")
	@Mapping(source = "folio.id", target = "folioId")
	@Mapping(source = "goal.id", target = "goalId")
	MFInvestmentDTO toDto(MFInvestment mfInvestment);
	
	@Mapping(source = "fund.id", target = "fundId")
	@Mapping(source = "folio.id", target = "folioId")
	@Mapping(source = "goal.id", target = "goalId")
	@Mapping(source = "fromAccount.id", target = "fromAccountId")
	CreateMFInvestmentVM toCreateMFInvestmentVM(MFInvestment mfInvestment);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "transaction", ignore = true)
	@Mapping(target = "fromAccount", source = "fromAccountId")
	@Mapping(source = "fundId", target = "fund")
	@Mapping(source = "folioId", target = "folio")
	@Mapping(source = "goalId", target = "goal")
	MFInvestment toEntity(CreateMFInvestmentVM vm);

	@Mapping(target = "transferAccountId", ignore = true)
	@Mapping(target = "incomeCategoryId", ignore = true)
	@Mapping(target = "expenseCategoryId", ignore = true)
	@Mapping(target="accountId", source="fromAccount.id")
	@Mapping(target="txTypeId", expression="java(com.anil.pfm.domain.TransactionType.INVESTMENT)")
	@Mapping(target="date", source = "purchaseDate")
	@Mapping(target = "desc", expression = "java(\"MF - \" + mfInvestment.getFund().getAmc().getName() + \" \" + mfInvestment.getFund().getName())")
	CreateTransactionVM toCreateTransactionVM(MFInvestment mfInvestment);

	default MFInvestment fromId(Long id) {
		if (id == null) {
			return null;
		}
		MFInvestment mFInvestment = new MFInvestment();
		mFInvestment.setId(id);
		return mFInvestment;
	}

}
