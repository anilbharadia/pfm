package com.anil.pfm.web.rest;

import com.anil.pfm.PfmApp;

import com.anil.pfm.domain.PPFTransaction;
import com.anil.pfm.repository.PPFTransactionRepository;
import com.anil.pfm.service.PPFTransactionService;
import com.anil.pfm.service.dto.PPFTransactionDTO;
import com.anil.pfm.service.mapper.PPFTransactionMapper;
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
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.anil.pfm.domain.enumeration.PPFTransactionType;
/**
 * Test class for the PPFTransactionResource REST controller.
 *
 * @see PPFTransactionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PfmApp.class)
public class PPFTransactionResourceIntTest {

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_DESC = "AAAAAAAAAA";
    private static final String UPDATED_DESC = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    private static final PPFTransactionType DEFAULT_TYPE = PPFTransactionType.INSTALLMENT;
    private static final PPFTransactionType UPDATED_TYPE = PPFTransactionType.INTEREST;

    @Autowired
    private PPFTransactionRepository pPFTransactionRepository;

    @Autowired
    private PPFTransactionMapper pPFTransactionMapper;

    @Autowired
    private PPFTransactionService pPFTransactionService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPPFTransactionMockMvc;

    private PPFTransaction pPFTransaction;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PPFTransactionResource pPFTransactionResource = new PPFTransactionResource(pPFTransactionService);
        this.restPPFTransactionMockMvc = MockMvcBuilders.standaloneSetup(pPFTransactionResource)
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
    public static PPFTransaction createEntity(EntityManager em) {
        PPFTransaction pPFTransaction = new PPFTransaction()
            .date(DEFAULT_DATE)
            .desc(DEFAULT_DESC)
            .amount(DEFAULT_AMOUNT)
            .type(DEFAULT_TYPE);
        return pPFTransaction;
    }

    @Before
    public void initTest() {
        pPFTransaction = createEntity(em);
    }

    @Test
    @Transactional
    public void createPPFTransaction() throws Exception {
        int databaseSizeBeforeCreate = pPFTransactionRepository.findAll().size();

        // Create the PPFTransaction
        PPFTransactionDTO pPFTransactionDTO = pPFTransactionMapper.toDto(pPFTransaction);
        restPPFTransactionMockMvc.perform(post("/api/p-pf-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pPFTransactionDTO)))
            .andExpect(status().isCreated());

        // Validate the PPFTransaction in the database
        List<PPFTransaction> pPFTransactionList = pPFTransactionRepository.findAll();
        assertThat(pPFTransactionList).hasSize(databaseSizeBeforeCreate + 1);
        PPFTransaction testPPFTransaction = pPFTransactionList.get(pPFTransactionList.size() - 1);
        assertThat(testPPFTransaction.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testPPFTransaction.getDesc()).isEqualTo(DEFAULT_DESC);
        assertThat(testPPFTransaction.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testPPFTransaction.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createPPFTransactionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pPFTransactionRepository.findAll().size();

        // Create the PPFTransaction with an existing ID
        pPFTransaction.setId(1L);
        PPFTransactionDTO pPFTransactionDTO = pPFTransactionMapper.toDto(pPFTransaction);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPPFTransactionMockMvc.perform(post("/api/p-pf-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pPFTransactionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PPFTransaction in the database
        List<PPFTransaction> pPFTransactionList = pPFTransactionRepository.findAll();
        assertThat(pPFTransactionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = pPFTransactionRepository.findAll().size();
        // set the field null
        pPFTransaction.setDate(null);

        // Create the PPFTransaction, which fails.
        PPFTransactionDTO pPFTransactionDTO = pPFTransactionMapper.toDto(pPFTransaction);

        restPPFTransactionMockMvc.perform(post("/api/p-pf-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pPFTransactionDTO)))
            .andExpect(status().isBadRequest());

        List<PPFTransaction> pPFTransactionList = pPFTransactionRepository.findAll();
        assertThat(pPFTransactionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = pPFTransactionRepository.findAll().size();
        // set the field null
        pPFTransaction.setAmount(null);

        // Create the PPFTransaction, which fails.
        PPFTransactionDTO pPFTransactionDTO = pPFTransactionMapper.toDto(pPFTransaction);

        restPPFTransactionMockMvc.perform(post("/api/p-pf-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pPFTransactionDTO)))
            .andExpect(status().isBadRequest());

        List<PPFTransaction> pPFTransactionList = pPFTransactionRepository.findAll();
        assertThat(pPFTransactionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = pPFTransactionRepository.findAll().size();
        // set the field null
        pPFTransaction.setType(null);

        // Create the PPFTransaction, which fails.
        PPFTransactionDTO pPFTransactionDTO = pPFTransactionMapper.toDto(pPFTransaction);

        restPPFTransactionMockMvc.perform(post("/api/p-pf-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pPFTransactionDTO)))
            .andExpect(status().isBadRequest());

        List<PPFTransaction> pPFTransactionList = pPFTransactionRepository.findAll();
        assertThat(pPFTransactionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPPFTransactions() throws Exception {
        // Initialize the database
        pPFTransactionRepository.saveAndFlush(pPFTransaction);

        // Get all the pPFTransactionList
        restPPFTransactionMockMvc.perform(get("/api/p-pf-transactions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pPFTransaction.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].desc").value(hasItem(DEFAULT_DESC.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getPPFTransaction() throws Exception {
        // Initialize the database
        pPFTransactionRepository.saveAndFlush(pPFTransaction);

        // Get the pPFTransaction
        restPPFTransactionMockMvc.perform(get("/api/p-pf-transactions/{id}", pPFTransaction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pPFTransaction.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.desc").value(DEFAULT_DESC.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPPFTransaction() throws Exception {
        // Get the pPFTransaction
        restPPFTransactionMockMvc.perform(get("/api/p-pf-transactions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePPFTransaction() throws Exception {
        // Initialize the database
        pPFTransactionRepository.saveAndFlush(pPFTransaction);
        int databaseSizeBeforeUpdate = pPFTransactionRepository.findAll().size();

        // Update the pPFTransaction
        PPFTransaction updatedPPFTransaction = pPFTransactionRepository.findOne(pPFTransaction.getId());
        updatedPPFTransaction
            .date(UPDATED_DATE)
            .desc(UPDATED_DESC)
            .amount(UPDATED_AMOUNT)
            .type(UPDATED_TYPE);
        PPFTransactionDTO pPFTransactionDTO = pPFTransactionMapper.toDto(updatedPPFTransaction);

        restPPFTransactionMockMvc.perform(put("/api/p-pf-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pPFTransactionDTO)))
            .andExpect(status().isOk());

        // Validate the PPFTransaction in the database
        List<PPFTransaction> pPFTransactionList = pPFTransactionRepository.findAll();
        assertThat(pPFTransactionList).hasSize(databaseSizeBeforeUpdate);
        PPFTransaction testPPFTransaction = pPFTransactionList.get(pPFTransactionList.size() - 1);
        assertThat(testPPFTransaction.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testPPFTransaction.getDesc()).isEqualTo(UPDATED_DESC);
        assertThat(testPPFTransaction.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testPPFTransaction.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingPPFTransaction() throws Exception {
        int databaseSizeBeforeUpdate = pPFTransactionRepository.findAll().size();

        // Create the PPFTransaction
        PPFTransactionDTO pPFTransactionDTO = pPFTransactionMapper.toDto(pPFTransaction);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPPFTransactionMockMvc.perform(put("/api/p-pf-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pPFTransactionDTO)))
            .andExpect(status().isCreated());

        // Validate the PPFTransaction in the database
        List<PPFTransaction> pPFTransactionList = pPFTransactionRepository.findAll();
        assertThat(pPFTransactionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePPFTransaction() throws Exception {
        // Initialize the database
        pPFTransactionRepository.saveAndFlush(pPFTransaction);
        int databaseSizeBeforeDelete = pPFTransactionRepository.findAll().size();

        // Get the pPFTransaction
        restPPFTransactionMockMvc.perform(delete("/api/p-pf-transactions/{id}", pPFTransaction.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PPFTransaction> pPFTransactionList = pPFTransactionRepository.findAll();
        assertThat(pPFTransactionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PPFTransaction.class);
        PPFTransaction pPFTransaction1 = new PPFTransaction();
        pPFTransaction1.setId(1L);
        PPFTransaction pPFTransaction2 = new PPFTransaction();
        pPFTransaction2.setId(pPFTransaction1.getId());
        assertThat(pPFTransaction1).isEqualTo(pPFTransaction2);
        pPFTransaction2.setId(2L);
        assertThat(pPFTransaction1).isNotEqualTo(pPFTransaction2);
        pPFTransaction1.setId(null);
        assertThat(pPFTransaction1).isNotEqualTo(pPFTransaction2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PPFTransactionDTO.class);
        PPFTransactionDTO pPFTransactionDTO1 = new PPFTransactionDTO();
        pPFTransactionDTO1.setId(1L);
        PPFTransactionDTO pPFTransactionDTO2 = new PPFTransactionDTO();
        assertThat(pPFTransactionDTO1).isNotEqualTo(pPFTransactionDTO2);
        pPFTransactionDTO2.setId(pPFTransactionDTO1.getId());
        assertThat(pPFTransactionDTO1).isEqualTo(pPFTransactionDTO2);
        pPFTransactionDTO2.setId(2L);
        assertThat(pPFTransactionDTO1).isNotEqualTo(pPFTransactionDTO2);
        pPFTransactionDTO1.setId(null);
        assertThat(pPFTransactionDTO1).isNotEqualTo(pPFTransactionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(pPFTransactionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(pPFTransactionMapper.fromId(null)).isNull();
    }
}
