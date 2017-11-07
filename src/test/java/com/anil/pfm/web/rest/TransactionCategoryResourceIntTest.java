package com.anil.pfm.web.rest;

import com.anil.pfm.PfmApp;

import com.anil.pfm.domain.TransactionCategory;
import com.anil.pfm.repository.TransactionCategoryRepository;
import com.anil.pfm.service.TransactionCategoryService;
import com.anil.pfm.service.dto.TransactionCategoryDTO;
import com.anil.pfm.service.mapper.TransactionCategoryMapper;
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
 * Test class for the TransactionCategoryResource REST controller.
 *
 * @see TransactionCategoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PfmApp.class)
public class TransactionCategoryResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private TransactionCategoryRepository transactionCategoryRepository;

    @Autowired
    private TransactionCategoryMapper transactionCategoryMapper;

    @Autowired
    private TransactionCategoryService transactionCategoryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTransactionCategoryMockMvc;

    private TransactionCategory transactionCategory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TransactionCategoryResource transactionCategoryResource = new TransactionCategoryResource(transactionCategoryService);
        this.restTransactionCategoryMockMvc = MockMvcBuilders.standaloneSetup(transactionCategoryResource)
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
    public static TransactionCategory createEntity(EntityManager em) {
        TransactionCategory transactionCategory = new TransactionCategory()
            .name(DEFAULT_NAME);
        return transactionCategory;
    }

    @Before
    public void initTest() {
        transactionCategory = createEntity(em);
    }

    @Test
    @Transactional
    public void createTransactionCategory() throws Exception {
        int databaseSizeBeforeCreate = transactionCategoryRepository.findAll().size();

        // Create the TransactionCategory
        TransactionCategoryDTO transactionCategoryDTO = transactionCategoryMapper.toDto(transactionCategory);
        restTransactionCategoryMockMvc.perform(post("/api/transaction-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionCategoryDTO)))
            .andExpect(status().isCreated());

        // Validate the TransactionCategory in the database
        List<TransactionCategory> transactionCategoryList = transactionCategoryRepository.findAll();
        assertThat(transactionCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        TransactionCategory testTransactionCategory = transactionCategoryList.get(transactionCategoryList.size() - 1);
        assertThat(testTransactionCategory.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createTransactionCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = transactionCategoryRepository.findAll().size();

        // Create the TransactionCategory with an existing ID
        transactionCategory.setId(1L);
        TransactionCategoryDTO transactionCategoryDTO = transactionCategoryMapper.toDto(transactionCategory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransactionCategoryMockMvc.perform(post("/api/transaction-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionCategoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TransactionCategory in the database
        List<TransactionCategory> transactionCategoryList = transactionCategoryRepository.findAll();
        assertThat(transactionCategoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionCategoryRepository.findAll().size();
        // set the field null
        transactionCategory.setName(null);

        // Create the TransactionCategory, which fails.
        TransactionCategoryDTO transactionCategoryDTO = transactionCategoryMapper.toDto(transactionCategory);

        restTransactionCategoryMockMvc.perform(post("/api/transaction-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionCategoryDTO)))
            .andExpect(status().isBadRequest());

        List<TransactionCategory> transactionCategoryList = transactionCategoryRepository.findAll();
        assertThat(transactionCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTransactionCategories() throws Exception {
        // Initialize the database
        transactionCategoryRepository.saveAndFlush(transactionCategory);

        // Get all the transactionCategoryList
        restTransactionCategoryMockMvc.perform(get("/api/transaction-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transactionCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getTransactionCategory() throws Exception {
        // Initialize the database
        transactionCategoryRepository.saveAndFlush(transactionCategory);

        // Get the transactionCategory
        restTransactionCategoryMockMvc.perform(get("/api/transaction-categories/{id}", transactionCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(transactionCategory.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTransactionCategory() throws Exception {
        // Get the transactionCategory
        restTransactionCategoryMockMvc.perform(get("/api/transaction-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTransactionCategory() throws Exception {
        // Initialize the database
        transactionCategoryRepository.saveAndFlush(transactionCategory);
        int databaseSizeBeforeUpdate = transactionCategoryRepository.findAll().size();

        // Update the transactionCategory
        TransactionCategory updatedTransactionCategory = transactionCategoryRepository.findOne(transactionCategory.getId());
        updatedTransactionCategory
            .name(UPDATED_NAME);
        TransactionCategoryDTO transactionCategoryDTO = transactionCategoryMapper.toDto(updatedTransactionCategory);

        restTransactionCategoryMockMvc.perform(put("/api/transaction-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionCategoryDTO)))
            .andExpect(status().isOk());

        // Validate the TransactionCategory in the database
        List<TransactionCategory> transactionCategoryList = transactionCategoryRepository.findAll();
        assertThat(transactionCategoryList).hasSize(databaseSizeBeforeUpdate);
        TransactionCategory testTransactionCategory = transactionCategoryList.get(transactionCategoryList.size() - 1);
        assertThat(testTransactionCategory.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingTransactionCategory() throws Exception {
        int databaseSizeBeforeUpdate = transactionCategoryRepository.findAll().size();

        // Create the TransactionCategory
        TransactionCategoryDTO transactionCategoryDTO = transactionCategoryMapper.toDto(transactionCategory);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTransactionCategoryMockMvc.perform(put("/api/transaction-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionCategoryDTO)))
            .andExpect(status().isCreated());

        // Validate the TransactionCategory in the database
        List<TransactionCategory> transactionCategoryList = transactionCategoryRepository.findAll();
        assertThat(transactionCategoryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTransactionCategory() throws Exception {
        // Initialize the database
        transactionCategoryRepository.saveAndFlush(transactionCategory);
        int databaseSizeBeforeDelete = transactionCategoryRepository.findAll().size();

        // Get the transactionCategory
        restTransactionCategoryMockMvc.perform(delete("/api/transaction-categories/{id}", transactionCategory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TransactionCategory> transactionCategoryList = transactionCategoryRepository.findAll();
        assertThat(transactionCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransactionCategory.class);
        TransactionCategory transactionCategory1 = new TransactionCategory();
        transactionCategory1.setId(1L);
        TransactionCategory transactionCategory2 = new TransactionCategory();
        transactionCategory2.setId(transactionCategory1.getId());
        assertThat(transactionCategory1).isEqualTo(transactionCategory2);
        transactionCategory2.setId(2L);
        assertThat(transactionCategory1).isNotEqualTo(transactionCategory2);
        transactionCategory1.setId(null);
        assertThat(transactionCategory1).isNotEqualTo(transactionCategory2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransactionCategoryDTO.class);
        TransactionCategoryDTO transactionCategoryDTO1 = new TransactionCategoryDTO();
        transactionCategoryDTO1.setId(1L);
        TransactionCategoryDTO transactionCategoryDTO2 = new TransactionCategoryDTO();
        assertThat(transactionCategoryDTO1).isNotEqualTo(transactionCategoryDTO2);
        transactionCategoryDTO2.setId(transactionCategoryDTO1.getId());
        assertThat(transactionCategoryDTO1).isEqualTo(transactionCategoryDTO2);
        transactionCategoryDTO2.setId(2L);
        assertThat(transactionCategoryDTO1).isNotEqualTo(transactionCategoryDTO2);
        transactionCategoryDTO1.setId(null);
        assertThat(transactionCategoryDTO1).isNotEqualTo(transactionCategoryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(transactionCategoryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(transactionCategoryMapper.fromId(null)).isNull();
    }
}
