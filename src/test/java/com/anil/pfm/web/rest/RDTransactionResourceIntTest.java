package com.anil.pfm.web.rest;

import com.anil.pfm.PfmApp;

import com.anil.pfm.domain.RDTransaction;
import com.anil.pfm.repository.RDTransactionRepository;
import com.anil.pfm.service.RDTransactionService;
import com.anil.pfm.service.dto.RDTransactionDTO;
import com.anil.pfm.service.mapper.RDTransactionMapper;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.anil.pfm.domain.enumeration.RDTransactionType;
/**
 * Test class for the RDTransactionResource REST controller.
 *
 * @see RDTransactionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PfmApp.class)
public class RDTransactionResourceIntTest {

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final RDTransactionType DEFAULT_TYPE = RDTransactionType.INSTALLMENT;
    private static final RDTransactionType UPDATED_TYPE = RDTransactionType.INTEREST;

    @Autowired
    private RDTransactionRepository rDTransactionRepository;

    @Autowired
    private RDTransactionMapper rDTransactionMapper;

    @Autowired
    private RDTransactionService rDTransactionService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRDTransactionMockMvc;

    private RDTransaction rDTransaction;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RDTransactionResource rDTransactionResource = new RDTransactionResource(rDTransactionService);
        this.restRDTransactionMockMvc = MockMvcBuilders.standaloneSetup(rDTransactionResource)
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
    public static RDTransaction createEntity(EntityManager em) {
        RDTransaction rDTransaction = new RDTransaction()
            .date(DEFAULT_DATE)
            .type(DEFAULT_TYPE);
        return rDTransaction;
    }

    @Before
    public void initTest() {
        rDTransaction = createEntity(em);
    }

    @Test
    @Transactional
    public void createRDTransaction() throws Exception {
        int databaseSizeBeforeCreate = rDTransactionRepository.findAll().size();

        // Create the RDTransaction
        RDTransactionDTO rDTransactionDTO = rDTransactionMapper.toDto(rDTransaction);
        restRDTransactionMockMvc.perform(post("/api/r-d-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rDTransactionDTO)))
            .andExpect(status().isCreated());

        // Validate the RDTransaction in the database
        List<RDTransaction> rDTransactionList = rDTransactionRepository.findAll();
        assertThat(rDTransactionList).hasSize(databaseSizeBeforeCreate + 1);
        RDTransaction testRDTransaction = rDTransactionList.get(rDTransactionList.size() - 1);
        assertThat(testRDTransaction.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testRDTransaction.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createRDTransactionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rDTransactionRepository.findAll().size();

        // Create the RDTransaction with an existing ID
        rDTransaction.setId(1L);
        RDTransactionDTO rDTransactionDTO = rDTransactionMapper.toDto(rDTransaction);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRDTransactionMockMvc.perform(post("/api/r-d-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rDTransactionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RDTransaction in the database
        List<RDTransaction> rDTransactionList = rDTransactionRepository.findAll();
        assertThat(rDTransactionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = rDTransactionRepository.findAll().size();
        // set the field null
        rDTransaction.setDate(null);

        // Create the RDTransaction, which fails.
        RDTransactionDTO rDTransactionDTO = rDTransactionMapper.toDto(rDTransaction);

        restRDTransactionMockMvc.perform(post("/api/r-d-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rDTransactionDTO)))
            .andExpect(status().isBadRequest());

        List<RDTransaction> rDTransactionList = rDTransactionRepository.findAll();
        assertThat(rDTransactionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRDTransactions() throws Exception {
        // Initialize the database
        rDTransactionRepository.saveAndFlush(rDTransaction);

        // Get all the rDTransactionList
        restRDTransactionMockMvc.perform(get("/api/r-d-transactions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rDTransaction.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getRDTransaction() throws Exception {
        // Initialize the database
        rDTransactionRepository.saveAndFlush(rDTransaction);

        // Get the rDTransaction
        restRDTransactionMockMvc.perform(get("/api/r-d-transactions/{id}", rDTransaction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rDTransaction.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRDTransaction() throws Exception {
        // Get the rDTransaction
        restRDTransactionMockMvc.perform(get("/api/r-d-transactions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRDTransaction() throws Exception {
        // Initialize the database
        rDTransactionRepository.saveAndFlush(rDTransaction);
        int databaseSizeBeforeUpdate = rDTransactionRepository.findAll().size();

        // Update the rDTransaction
        RDTransaction updatedRDTransaction = rDTransactionRepository.findOne(rDTransaction.getId());
        updatedRDTransaction
            .date(UPDATED_DATE)
            .type(UPDATED_TYPE);
        RDTransactionDTO rDTransactionDTO = rDTransactionMapper.toDto(updatedRDTransaction);

        restRDTransactionMockMvc.perform(put("/api/r-d-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rDTransactionDTO)))
            .andExpect(status().isOk());

        // Validate the RDTransaction in the database
        List<RDTransaction> rDTransactionList = rDTransactionRepository.findAll();
        assertThat(rDTransactionList).hasSize(databaseSizeBeforeUpdate);
        RDTransaction testRDTransaction = rDTransactionList.get(rDTransactionList.size() - 1);
        assertThat(testRDTransaction.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testRDTransaction.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingRDTransaction() throws Exception {
        int databaseSizeBeforeUpdate = rDTransactionRepository.findAll().size();

        // Create the RDTransaction
        RDTransactionDTO rDTransactionDTO = rDTransactionMapper.toDto(rDTransaction);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRDTransactionMockMvc.perform(put("/api/r-d-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rDTransactionDTO)))
            .andExpect(status().isCreated());

        // Validate the RDTransaction in the database
        List<RDTransaction> rDTransactionList = rDTransactionRepository.findAll();
        assertThat(rDTransactionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRDTransaction() throws Exception {
        // Initialize the database
        rDTransactionRepository.saveAndFlush(rDTransaction);
        int databaseSizeBeforeDelete = rDTransactionRepository.findAll().size();

        // Get the rDTransaction
        restRDTransactionMockMvc.perform(delete("/api/r-d-transactions/{id}", rDTransaction.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RDTransaction> rDTransactionList = rDTransactionRepository.findAll();
        assertThat(rDTransactionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RDTransaction.class);
        RDTransaction rDTransaction1 = new RDTransaction();
        rDTransaction1.setId(1L);
        RDTransaction rDTransaction2 = new RDTransaction();
        rDTransaction2.setId(rDTransaction1.getId());
        assertThat(rDTransaction1).isEqualTo(rDTransaction2);
        rDTransaction2.setId(2L);
        assertThat(rDTransaction1).isNotEqualTo(rDTransaction2);
        rDTransaction1.setId(null);
        assertThat(rDTransaction1).isNotEqualTo(rDTransaction2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RDTransactionDTO.class);
        RDTransactionDTO rDTransactionDTO1 = new RDTransactionDTO();
        rDTransactionDTO1.setId(1L);
        RDTransactionDTO rDTransactionDTO2 = new RDTransactionDTO();
        assertThat(rDTransactionDTO1).isNotEqualTo(rDTransactionDTO2);
        rDTransactionDTO2.setId(rDTransactionDTO1.getId());
        assertThat(rDTransactionDTO1).isEqualTo(rDTransactionDTO2);
        rDTransactionDTO2.setId(2L);
        assertThat(rDTransactionDTO1).isNotEqualTo(rDTransactionDTO2);
        rDTransactionDTO1.setId(null);
        assertThat(rDTransactionDTO1).isNotEqualTo(rDTransactionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(rDTransactionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(rDTransactionMapper.fromId(null)).isNull();
    }
}
