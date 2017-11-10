package com.anil.pfm.service.mapper;

import com.anil.pfm.domain.*;
import com.anil.pfm.service.dto.IncomeCategoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity IncomeCategory and its DTO IncomeCategoryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface IncomeCategoryMapper extends EntityMapper<IncomeCategoryDTO, IncomeCategory> {

    @Mapping(source = "parent.id", target = "parentId")
    IncomeCategoryDTO toDto(IncomeCategory incomeCategory); 

    @Mapping(source = "parentId", target = "parent")
    @Mapping(target = "subCategories", ignore = true)
    IncomeCategory toEntity(IncomeCategoryDTO incomeCategoryDTO);

    default IncomeCategory fromId(Long id) {
        if (id == null) {
            return null;
        }
        IncomeCategory incomeCategory = new IncomeCategory();
        incomeCategory.setId(id);
        return incomeCategory;
    }
}
