package com.anil.pfm.mf.service.mapper;

import com.anil.pfm.domain.*;
import com.anil.pfm.mf.domain.MFCategory;
import com.anil.pfm.service.dto.MFCategoryDTO;
import com.anil.pfm.service.mapper.EntityMapper;

import org.mapstruct.*;

/**
 * Mapper for the entity MFCategory and its DTO MFCategoryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MFCategoryMapper extends EntityMapper<MFCategoryDTO, MFCategory> {

    @Mapping(source = "parent.id", target = "parentId")
    MFCategoryDTO toDto(MFCategory mFCategory); 

    @Mapping(source = "parentId", target = "parent")
    MFCategory toEntity(MFCategoryDTO mFCategoryDTO);

    default MFCategory fromId(Long id) {
        if (id == null) {
            return null;
        }
        MFCategory mFCategory = new MFCategory();
        mFCategory.setId(id);
        return mFCategory;
    }
}
