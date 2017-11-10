package com.anil.pfm.service.mapper;

import com.anil.pfm.domain.*;
import com.anil.pfm.service.dto.ExpenseCategoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ExpenseCategory and its DTO ExpenseCategoryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ExpenseCategoryMapper extends EntityMapper<ExpenseCategoryDTO, ExpenseCategory> {

    @Mapping(source = "parent.id", target = "parentId")
    ExpenseCategoryDTO toDto(ExpenseCategory expenseCategory); 

    @Mapping(source = "parentId", target = "parent")
    @Mapping(target = "subCategories", ignore = true)
    ExpenseCategory toEntity(ExpenseCategoryDTO expenseCategoryDTO);

    default ExpenseCategory fromId(Long id) {
        if (id == null) {
            return null;
        }
        ExpenseCategory expenseCategory = new ExpenseCategory();
        expenseCategory.setId(id);
        return expenseCategory;
    }
}
