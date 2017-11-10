package com.anil.pfm.web.rest;

import com.anil.pfm.PfmApp;

import com.anil.pfm.domain.IncomeCategory;
import com.anil.pfm.repository.IncomeCategoryRepository;
import com.anil.pfm.service.IncomeCategoryService;
import com.anil.pfm.service.dto.IncomeCategoryDTO;
import com.anil.pfm.service.mapper.IncomeCategoryMapper;
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
 * Test class for the IncomeCategoryResource REST controller.
 *
 * @see IncomeCategoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PfmApp.class)
public class IncomeCategoryResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private IncomeCategoryRepository incomeCategoryRepository;

    @Autowired
    private IncomeCategoryMapper incomeCategoryMapper;

    @Autowired
    private IncomeCategoryService incomeCategoryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restIncomeCategoryMockMvc;

    private IncomeCategory incomeCategory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final IncomeCategoryResource incomeCategoryResource = new IncomeCategoryResource(incomeCategoryService);
        this.restIncomeCategoryMockMvc = MockMvcBuilders.standaloneSetup(incomeCategoryResource)
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
    public static IncomeCategory createEntity(EntityManager em) {
        IncomeCategory incomeCategory = new IncomeCategory()
            .name(DEFAULT_NAME);
        return incomeCategory;
    }

    @Before
    public void initTest() {
        incomeCategory = createEntity(em);
    }

    @Test
    @Transactional
    public void createIncomeCategory() throws Exception {
        int databaseSizeBeforeCreate = incomeCategoryRepository.findAll().size();

        // Create the IncomeCategory
        IncomeCategoryDTO incomeCategoryDTO = incomeCategoryMapper.toDto(incomeCategory);
        restIncomeCategoryMockMvc.perform(post("/api/income-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incomeCategoryDTO)))
            .andExpect(status().isCreated());

        // Validate the IncomeCategory in the database
        List<IncomeCategory> incomeCategoryList = incomeCategoryRepository.findAll();
        assertThat(incomeCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        IncomeCategory testIncomeCategory = incomeCategoryList.get(incomeCategoryList.size() - 1);
        assertThat(testIncomeCategory.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createIncomeCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = incomeCategoryRepository.findAll().size();

        // Create the IncomeCategory with an existing ID
        incomeCategory.setId(1L);
        IncomeCategoryDTO incomeCategoryDTO = incomeCategoryMapper.toDto(incomeCategory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIncomeCategoryMockMvc.perform(post("/api/income-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incomeCategoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the IncomeCategory in the database
        List<IncomeCategory> incomeCategoryList = incomeCategoryRepository.findAll();
        assertThat(incomeCategoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = incomeCategoryRepository.findAll().size();
        // set the field null
        incomeCategory.setName(null);

        // Create the IncomeCategory, which fails.
        IncomeCategoryDTO incomeCategoryDTO = incomeCategoryMapper.toDto(incomeCategory);

        restIncomeCategoryMockMvc.perform(post("/api/income-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incomeCategoryDTO)))
            .andExpect(status().isBadRequest());

        List<IncomeCategory> incomeCategoryList = incomeCategoryRepository.findAll();
        assertThat(incomeCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllIncomeCategories() throws Exception {
        // Initialize the database
        incomeCategoryRepository.saveAndFlush(incomeCategory);

        // Get all the incomeCategoryList
        restIncomeCategoryMockMvc.perform(get("/api/income-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(incomeCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getIncomeCategory() throws Exception {
        // Initialize the database
        incomeCategoryRepository.saveAndFlush(incomeCategory);

        // Get the incomeCategory
        restIncomeCategoryMockMvc.perform(get("/api/income-categories/{id}", incomeCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(incomeCategory.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingIncomeCategory() throws Exception {
        // Get the incomeCategory
        restIncomeCategoryMockMvc.perform(get("/api/income-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIncomeCategory() throws Exception {
        // Initialize the database
        incomeCategoryRepository.saveAndFlush(incomeCategory);
        int databaseSizeBeforeUpdate = incomeCategoryRepository.findAll().size();

        // Update the incomeCategory
        IncomeCategory updatedIncomeCategory = incomeCategoryRepository.findOne(incomeCategory.getId());
        updatedIncomeCategory
            .name(UPDATED_NAME);
        IncomeCategoryDTO incomeCategoryDTO = incomeCategoryMapper.toDto(updatedIncomeCategory);

        restIncomeCategoryMockMvc.perform(put("/api/income-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incomeCategoryDTO)))
            .andExpect(status().isOk());

        // Validate the IncomeCategory in the database
        List<IncomeCategory> incomeCategoryList = incomeCategoryRepository.findAll();
        assertThat(incomeCategoryList).hasSize(databaseSizeBeforeUpdate);
        IncomeCategory testIncomeCategory = incomeCategoryList.get(incomeCategoryList.size() - 1);
        assertThat(testIncomeCategory.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingIncomeCategory() throws Exception {
        int databaseSizeBeforeUpdate = incomeCategoryRepository.findAll().size();

        // Create the IncomeCategory
        IncomeCategoryDTO incomeCategoryDTO = incomeCategoryMapper.toDto(incomeCategory);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restIncomeCategoryMockMvc.perform(put("/api/income-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incomeCategoryDTO)))
            .andExpect(status().isCreated());

        // Validate the IncomeCategory in the database
        List<IncomeCategory> incomeCategoryList = incomeCategoryRepository.findAll();
        assertThat(incomeCategoryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteIncomeCategory() throws Exception {
        // Initialize the database
        incomeCategoryRepository.saveAndFlush(incomeCategory);
        int databaseSizeBeforeDelete = incomeCategoryRepository.findAll().size();

        // Get the incomeCategory
        restIncomeCategoryMockMvc.perform(delete("/api/income-categories/{id}", incomeCategory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<IncomeCategory> incomeCategoryList = incomeCategoryRepository.findAll();
        assertThat(incomeCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IncomeCategory.class);
        IncomeCategory incomeCategory1 = new IncomeCategory();
        incomeCategory1.setId(1L);
        IncomeCategory incomeCategory2 = new IncomeCategory();
        incomeCategory2.setId(incomeCategory1.getId());
        assertThat(incomeCategory1).isEqualTo(incomeCategory2);
        incomeCategory2.setId(2L);
        assertThat(incomeCategory1).isNotEqualTo(incomeCategory2);
        incomeCategory1.setId(null);
        assertThat(incomeCategory1).isNotEqualTo(incomeCategory2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IncomeCategoryDTO.class);
        IncomeCategoryDTO incomeCategoryDTO1 = new IncomeCategoryDTO();
        incomeCategoryDTO1.setId(1L);
        IncomeCategoryDTO incomeCategoryDTO2 = new IncomeCategoryDTO();
        assertThat(incomeCategoryDTO1).isNotEqualTo(incomeCategoryDTO2);
        incomeCategoryDTO2.setId(incomeCategoryDTO1.getId());
        assertThat(incomeCategoryDTO1).isEqualTo(incomeCategoryDTO2);
        incomeCategoryDTO2.setId(2L);
        assertThat(incomeCategoryDTO1).isNotEqualTo(incomeCategoryDTO2);
        incomeCategoryDTO1.setId(null);
        assertThat(incomeCategoryDTO1).isNotEqualTo(incomeCategoryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(incomeCategoryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(incomeCategoryMapper.fromId(null)).isNull();
    }
}
