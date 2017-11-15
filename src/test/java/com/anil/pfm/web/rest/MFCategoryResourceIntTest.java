package com.anil.pfm.web.rest;

import com.anil.pfm.PfmApp;

import com.anil.pfm.domain.MFCategory;
import com.anil.pfm.repository.MFCategoryRepository;
import com.anil.pfm.service.MFCategoryService;
import com.anil.pfm.service.dto.MFCategoryDTO;
import com.anil.pfm.service.mapper.MFCategoryMapper;
import com.anil.pfm.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MFCategoryResource REST controller.
 *
 * @see MFCategoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PfmApp.class)
public class MFCategoryResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private MFCategoryRepository mFCategoryRepository;

    @Autowired
    private MFCategoryMapper mFCategoryMapper;

    @Autowired
    private MFCategoryService mFCategoryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMFCategoryMockMvc;

    private MFCategory mFCategory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MFCategoryResource mFCategoryResource = new MFCategoryResource(mFCategoryService);
        this.restMFCategoryMockMvc = MockMvcBuilders.standaloneSetup(mFCategoryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MFCategory createEntity(EntityManager em) {
        MFCategory mFCategory = new MFCategory()
            .name(DEFAULT_NAME);
        return mFCategory;
    }

    @Before
    public void initTest() {
        mFCategory = createEntity(em);
    }

    @Test
    @Transactional
    public void createMFCategory() throws Exception {
        int databaseSizeBeforeCreate = mFCategoryRepository.findAll().size();

        // Create the MFCategory
        MFCategoryDTO mFCategoryDTO = mFCategoryMapper.toDto(mFCategory);
        restMFCategoryMockMvc.perform(post("/api/m-f-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mFCategoryDTO)))
            .andExpect(status().isCreated());

        // Validate the MFCategory in the database
        List<MFCategory> mFCategoryList = mFCategoryRepository.findAll();
        assertThat(mFCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        MFCategory testMFCategory = mFCategoryList.get(mFCategoryList.size() - 1);
        assertThat(testMFCategory.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createMFCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mFCategoryRepository.findAll().size();

        // Create the MFCategory with an existing ID
        mFCategory.setId(1L);
        MFCategoryDTO mFCategoryDTO = mFCategoryMapper.toDto(mFCategory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMFCategoryMockMvc.perform(post("/api/m-f-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mFCategoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MFCategory in the database
        List<MFCategory> mFCategoryList = mFCategoryRepository.findAll();
        assertThat(mFCategoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = mFCategoryRepository.findAll().size();
        // set the field null
        mFCategory.setName(null);

        // Create the MFCategory, which fails.
        MFCategoryDTO mFCategoryDTO = mFCategoryMapper.toDto(mFCategory);

        restMFCategoryMockMvc.perform(post("/api/m-f-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mFCategoryDTO)))
            .andExpect(status().isBadRequest());

        List<MFCategory> mFCategoryList = mFCategoryRepository.findAll();
        assertThat(mFCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMFCategories() throws Exception {
        // Initialize the database
        mFCategoryRepository.saveAndFlush(mFCategory);

        // Get all the mFCategoryList
        restMFCategoryMockMvc.perform(get("/api/m-f-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mFCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getMFCategory() throws Exception {
        // Initialize the database
        mFCategoryRepository.saveAndFlush(mFCategory);

        // Get the mFCategory
        restMFCategoryMockMvc.perform(get("/api/m-f-categories/{id}", mFCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mFCategory.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMFCategory() throws Exception {
        // Get the mFCategory
        restMFCategoryMockMvc.perform(get("/api/m-f-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMFCategory() throws Exception {
        // Initialize the database
        mFCategoryRepository.saveAndFlush(mFCategory);
        int databaseSizeBeforeUpdate = mFCategoryRepository.findAll().size();

        // Update the mFCategory
        MFCategory updatedMFCategory = mFCategoryRepository.findOne(mFCategory.getId());
        updatedMFCategory
            .name(UPDATED_NAME);
        MFCategoryDTO mFCategoryDTO = mFCategoryMapper.toDto(updatedMFCategory);

        restMFCategoryMockMvc.perform(put("/api/m-f-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mFCategoryDTO)))
            .andExpect(status().isOk());

        // Validate the MFCategory in the database
        List<MFCategory> mFCategoryList = mFCategoryRepository.findAll();
        assertThat(mFCategoryList).hasSize(databaseSizeBeforeUpdate);
        MFCategory testMFCategory = mFCategoryList.get(mFCategoryList.size() - 1);
        assertThat(testMFCategory.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingMFCategory() throws Exception {
        int databaseSizeBeforeUpdate = mFCategoryRepository.findAll().size();

        // Create the MFCategory
        MFCategoryDTO mFCategoryDTO = mFCategoryMapper.toDto(mFCategory);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMFCategoryMockMvc.perform(put("/api/m-f-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mFCategoryDTO)))
            .andExpect(status().isCreated());

        // Validate the MFCategory in the database
        List<MFCategory> mFCategoryList = mFCategoryRepository.findAll();
        assertThat(mFCategoryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMFCategory() throws Exception {
        // Initialize the database
        mFCategoryRepository.saveAndFlush(mFCategory);
        int databaseSizeBeforeDelete = mFCategoryRepository.findAll().size();

        // Get the mFCategory
        restMFCategoryMockMvc.perform(delete("/api/m-f-categories/{id}", mFCategory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MFCategory> mFCategoryList = mFCategoryRepository.findAll();
        assertThat(mFCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MFCategory.class);
        MFCategory mFCategory1 = new MFCategory();
        mFCategory1.setId(1L);
        MFCategory mFCategory2 = new MFCategory();
        mFCategory2.setId(mFCategory1.getId());
        assertThat(mFCategory1).isEqualTo(mFCategory2);
        mFCategory2.setId(2L);
        assertThat(mFCategory1).isNotEqualTo(mFCategory2);
        mFCategory1.setId(null);
        assertThat(mFCategory1).isNotEqualTo(mFCategory2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MFCategoryDTO.class);
        MFCategoryDTO mFCategoryDTO1 = new MFCategoryDTO();
        mFCategoryDTO1.setId(1L);
        MFCategoryDTO mFCategoryDTO2 = new MFCategoryDTO();
        assertThat(mFCategoryDTO1).isNotEqualTo(mFCategoryDTO2);
        mFCategoryDTO2.setId(mFCategoryDTO1.getId());
        assertThat(mFCategoryDTO1).isEqualTo(mFCategoryDTO2);
        mFCategoryDTO2.setId(2L);
        assertThat(mFCategoryDTO1).isNotEqualTo(mFCategoryDTO2);
        mFCategoryDTO1.setId(null);
        assertThat(mFCategoryDTO1).isNotEqualTo(mFCategoryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(mFCategoryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(mFCategoryMapper.fromId(null)).isNull();
    }
}
