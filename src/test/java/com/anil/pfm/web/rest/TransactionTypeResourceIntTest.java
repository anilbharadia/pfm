package com.anil.pfm.web.rest;

import com.anil.pfm.PfmApp;

import com.anil.pfm.domain.TransactionType;
import com.anil.pfm.repository.TransactionTypeRepository;
import com.anil.pfm.service.TransactionTypeService;
import com.anil.pfm.service.dto.TransactionTypeDTO;
import com.anil.pfm.service.mapper.TransactionTypeMapper;
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
 * Test class for the TransactionTypeResource REST controller.
 *
 * @see TransactionTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PfmApp.class)
public class TransactionTypeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private TransactionTypeRepository transactionTypeRepository;

    @Autowired
    private TransactionTypeMapper transactionTypeMapper;

    @Autowired
    private TransactionTypeService transactionTypeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTransactionTypeMockMvc;

    private TransactionType transactionType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TransactionTypeResource transactionTypeResource = new TransactionTypeResource(transactionTypeService);
        this.restTransactionTypeMockMvc = MockMvcBuilders.standaloneSetup(transactionTypeResource)
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
    public static TransactionType createEntity(EntityManager em) {
        TransactionType transactionType = new TransactionType()
            .name(DEFAULT_NAME);
        return transactionType;
    }

    @Before
    public void initTest() {
        transactionType = createEntity(em);
    }

    @Test
    @Transactional
    public void createTransactionType() throws Exception {
        int databaseSizeBeforeCreate = transactionTypeRepository.findAll().size();

        // Create the TransactionType
        TransactionTypeDTO transactionTypeDTO = transactionTypeMapper.toDto(transactionType);
        restTransactionTypeMockMvc.perform(post("/api/transaction-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the TransactionType in the database
        List<TransactionType> transactionTypeList = transactionTypeRepository.findAll();
        assertThat(transactionTypeList).hasSize(databaseSizeBeforeCreate + 1);
        TransactionType testTransactionType = transactionTypeList.get(transactionTypeList.size() - 1);
        assertThat(testTransactionType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createTransactionTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = transactionTypeRepository.findAll().size();

        // Create the TransactionType with an existing ID
        transactionType.setId(1L);
        TransactionTypeDTO transactionTypeDTO = transactionTypeMapper.toDto(transactionType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransactionTypeMockMvc.perform(post("/api/transaction-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TransactionType in the database
        List<TransactionType> transactionTypeList = transactionTypeRepository.findAll();
        assertThat(transactionTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionTypeRepository.findAll().size();
        // set the field null
        transactionType.setName(null);

        // Create the TransactionType, which fails.
        TransactionTypeDTO transactionTypeDTO = transactionTypeMapper.toDto(transactionType);

        restTransactionTypeMockMvc.perform(post("/api/transaction-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionTypeDTO)))
            .andExpect(status().isBadRequest());

        List<TransactionType> transactionTypeList = transactionTypeRepository.findAll();
        assertThat(transactionTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTransactionTypes() throws Exception {
        // Initialize the database
        transactionTypeRepository.saveAndFlush(transactionType);

        // Get all the transactionTypeList
        restTransactionTypeMockMvc.perform(get("/api/transaction-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transactionType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getTransactionType() throws Exception {
        // Initialize the database
        transactionTypeRepository.saveAndFlush(transactionType);

        // Get the transactionType
        restTransactionTypeMockMvc.perform(get("/api/transaction-types/{id}", transactionType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(transactionType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTransactionType() throws Exception {
        // Get the transactionType
        restTransactionTypeMockMvc.perform(get("/api/transaction-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTransactionType() throws Exception {
        // Initialize the database
        transactionTypeRepository.saveAndFlush(transactionType);
        int databaseSizeBeforeUpdate = transactionTypeRepository.findAll().size();

        // Update the transactionType
        TransactionType updatedTransactionType = transactionTypeRepository.findOne(transactionType.getId());
        updatedTransactionType
            .name(UPDATED_NAME);
        TransactionTypeDTO transactionTypeDTO = transactionTypeMapper.toDto(updatedTransactionType);

        restTransactionTypeMockMvc.perform(put("/api/transaction-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionTypeDTO)))
            .andExpect(status().isOk());

        // Validate the TransactionType in the database
        List<TransactionType> transactionTypeList = transactionTypeRepository.findAll();
        assertThat(transactionTypeList).hasSize(databaseSizeBeforeUpdate);
        TransactionType testTransactionType = transactionTypeList.get(transactionTypeList.size() - 1);
        assertThat(testTransactionType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingTransactionType() throws Exception {
        int databaseSizeBeforeUpdate = transactionTypeRepository.findAll().size();

        // Create the TransactionType
        TransactionTypeDTO transactionTypeDTO = transactionTypeMapper.toDto(transactionType);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTransactionTypeMockMvc.perform(put("/api/transaction-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the TransactionType in the database
        List<TransactionType> transactionTypeList = transactionTypeRepository.findAll();
        assertThat(transactionTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTransactionType() throws Exception {
        // Initialize the database
        transactionTypeRepository.saveAndFlush(transactionType);
        int databaseSizeBeforeDelete = transactionTypeRepository.findAll().size();

        // Get the transactionType
        restTransactionTypeMockMvc.perform(delete("/api/transaction-types/{id}", transactionType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TransactionType> transactionTypeList = transactionTypeRepository.findAll();
        assertThat(transactionTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransactionType.class);
        TransactionType transactionType1 = new TransactionType();
        transactionType1.setId(1L);
        TransactionType transactionType2 = new TransactionType();
        transactionType2.setId(transactionType1.getId());
        assertThat(transactionType1).isEqualTo(transactionType2);
        transactionType2.setId(2L);
        assertThat(transactionType1).isNotEqualTo(transactionType2);
        transactionType1.setId(null);
        assertThat(transactionType1).isNotEqualTo(transactionType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransactionTypeDTO.class);
        TransactionTypeDTO transactionTypeDTO1 = new TransactionTypeDTO();
        transactionTypeDTO1.setId(1L);
        TransactionTypeDTO transactionTypeDTO2 = new TransactionTypeDTO();
        assertThat(transactionTypeDTO1).isNotEqualTo(transactionTypeDTO2);
        transactionTypeDTO2.setId(transactionTypeDTO1.getId());
        assertThat(transactionTypeDTO1).isEqualTo(transactionTypeDTO2);
        transactionTypeDTO2.setId(2L);
        assertThat(transactionTypeDTO1).isNotEqualTo(transactionTypeDTO2);
        transactionTypeDTO1.setId(null);
        assertThat(transactionTypeDTO1).isNotEqualTo(transactionTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(transactionTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(transactionTypeMapper.fromId(null)).isNull();
    }
}
