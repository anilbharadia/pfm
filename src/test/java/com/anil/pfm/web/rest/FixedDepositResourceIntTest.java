package com.anil.pfm.web.rest;

import com.anil.pfm.PfmApp;

import com.anil.pfm.domain.FixedDeposit;
import com.anil.pfm.repository.FixedDepositRepository;
import com.anil.pfm.service.FixedDepositService;
import com.anil.pfm.service.dto.FixedDepositDTO;
import com.anil.pfm.service.mapper.FixedDepositMapper;
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

import com.anil.pfm.domain.enumeration.FDStatus;
/**
 * Test class for the FixedDepositResource REST controller.
 *
 * @see FixedDepositResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PfmApp.class)
public class FixedDepositResourceIntTest {

    private static final String DEFAULT_ACCOUNT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NUMBER = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    private static final Instant DEFAULT_OPEN_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_OPEN_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_MATURITY_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MATURITY_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final FDStatus DEFAULT_STATUS = FDStatus.OPEN;
    private static final FDStatus UPDATED_STATUS = FDStatus.CLOSED;

    @Autowired
    private FixedDepositRepository fixedDepositRepository;

    @Autowired
    private FixedDepositMapper fixedDepositMapper;

    @Autowired
    private FixedDepositService fixedDepositService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFixedDepositMockMvc;

    private FixedDeposit fixedDeposit;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FixedDepositResource fixedDepositResource = new FixedDepositResource(fixedDepositService);
        this.restFixedDepositMockMvc = MockMvcBuilders.standaloneSetup(fixedDepositResource)
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
    public static FixedDeposit createEntity(EntityManager em) {
        FixedDeposit fixedDeposit = new FixedDeposit()
            .accountNumber(DEFAULT_ACCOUNT_NUMBER)
            .amount(DEFAULT_AMOUNT)
            .openDate(DEFAULT_OPEN_DATE)
            .maturityDate(DEFAULT_MATURITY_DATE)
            .status(DEFAULT_STATUS);
        return fixedDeposit;
    }

    @Before
    public void initTest() {
        fixedDeposit = createEntity(em);
    }

    @Test
    @Transactional
    public void createFixedDeposit() throws Exception {
        int databaseSizeBeforeCreate = fixedDepositRepository.findAll().size();

        // Create the FixedDeposit
        FixedDepositDTO fixedDepositDTO = fixedDepositMapper.toDto(fixedDeposit);
        restFixedDepositMockMvc.perform(post("/api/fixed-deposits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fixedDepositDTO)))
            .andExpect(status().isCreated());

        // Validate the FixedDeposit in the database
        List<FixedDeposit> fixedDepositList = fixedDepositRepository.findAll();
        assertThat(fixedDepositList).hasSize(databaseSizeBeforeCreate + 1);
        FixedDeposit testFixedDeposit = fixedDepositList.get(fixedDepositList.size() - 1);
        assertThat(testFixedDeposit.getAccountNumber()).isEqualTo(DEFAULT_ACCOUNT_NUMBER);
        assertThat(testFixedDeposit.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testFixedDeposit.getOpenDate()).isEqualTo(DEFAULT_OPEN_DATE);
        assertThat(testFixedDeposit.getMaturityDate()).isEqualTo(DEFAULT_MATURITY_DATE);
        assertThat(testFixedDeposit.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createFixedDepositWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fixedDepositRepository.findAll().size();

        // Create the FixedDeposit with an existing ID
        fixedDeposit.setId(1L);
        FixedDepositDTO fixedDepositDTO = fixedDepositMapper.toDto(fixedDeposit);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFixedDepositMockMvc.perform(post("/api/fixed-deposits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fixedDepositDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FixedDeposit in the database
        List<FixedDeposit> fixedDepositList = fixedDepositRepository.findAll();
        assertThat(fixedDepositList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = fixedDepositRepository.findAll().size();
        // set the field null
        fixedDeposit.setAmount(null);

        // Create the FixedDeposit, which fails.
        FixedDepositDTO fixedDepositDTO = fixedDepositMapper.toDto(fixedDeposit);

        restFixedDepositMockMvc.perform(post("/api/fixed-deposits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fixedDepositDTO)))
            .andExpect(status().isBadRequest());

        List<FixedDeposit> fixedDepositList = fixedDepositRepository.findAll();
        assertThat(fixedDepositList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOpenDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = fixedDepositRepository.findAll().size();
        // set the field null
        fixedDeposit.setOpenDate(null);

        // Create the FixedDeposit, which fails.
        FixedDepositDTO fixedDepositDTO = fixedDepositMapper.toDto(fixedDeposit);

        restFixedDepositMockMvc.perform(post("/api/fixed-deposits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fixedDepositDTO)))
            .andExpect(status().isBadRequest());

        List<FixedDeposit> fixedDepositList = fixedDepositRepository.findAll();
        assertThat(fixedDepositList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMaturityDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = fixedDepositRepository.findAll().size();
        // set the field null
        fixedDeposit.setMaturityDate(null);

        // Create the FixedDeposit, which fails.
        FixedDepositDTO fixedDepositDTO = fixedDepositMapper.toDto(fixedDeposit);

        restFixedDepositMockMvc.perform(post("/api/fixed-deposits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fixedDepositDTO)))
            .andExpect(status().isBadRequest());

        List<FixedDeposit> fixedDepositList = fixedDepositRepository.findAll();
        assertThat(fixedDepositList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = fixedDepositRepository.findAll().size();
        // set the field null
        fixedDeposit.setStatus(null);

        // Create the FixedDeposit, which fails.
        FixedDepositDTO fixedDepositDTO = fixedDepositMapper.toDto(fixedDeposit);

        restFixedDepositMockMvc.perform(post("/api/fixed-deposits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fixedDepositDTO)))
            .andExpect(status().isBadRequest());

        List<FixedDeposit> fixedDepositList = fixedDepositRepository.findAll();
        assertThat(fixedDepositList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFixedDeposits() throws Exception {
        // Initialize the database
        fixedDepositRepository.saveAndFlush(fixedDeposit);

        // Get all the fixedDepositList
        restFixedDepositMockMvc.perform(get("/api/fixed-deposits?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fixedDeposit.getId().intValue())))
            .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].openDate").value(hasItem(DEFAULT_OPEN_DATE.toString())))
            .andExpect(jsonPath("$.[*].maturityDate").value(hasItem(DEFAULT_MATURITY_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getFixedDeposit() throws Exception {
        // Initialize the database
        fixedDepositRepository.saveAndFlush(fixedDeposit);

        // Get the fixedDeposit
        restFixedDepositMockMvc.perform(get("/api/fixed-deposits/{id}", fixedDeposit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fixedDeposit.getId().intValue()))
            .andExpect(jsonPath("$.accountNumber").value(DEFAULT_ACCOUNT_NUMBER.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.openDate").value(DEFAULT_OPEN_DATE.toString()))
            .andExpect(jsonPath("$.maturityDate").value(DEFAULT_MATURITY_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFixedDeposit() throws Exception {
        // Get the fixedDeposit
        restFixedDepositMockMvc.perform(get("/api/fixed-deposits/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFixedDeposit() throws Exception {
        // Initialize the database
        fixedDepositRepository.saveAndFlush(fixedDeposit);
        int databaseSizeBeforeUpdate = fixedDepositRepository.findAll().size();

        // Update the fixedDeposit
        FixedDeposit updatedFixedDeposit = fixedDepositRepository.findOne(fixedDeposit.getId());
        updatedFixedDeposit
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .amount(UPDATED_AMOUNT)
            .openDate(UPDATED_OPEN_DATE)
            .maturityDate(UPDATED_MATURITY_DATE)
            .status(UPDATED_STATUS);
        FixedDepositDTO fixedDepositDTO = fixedDepositMapper.toDto(updatedFixedDeposit);

        restFixedDepositMockMvc.perform(put("/api/fixed-deposits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fixedDepositDTO)))
            .andExpect(status().isOk());

        // Validate the FixedDeposit in the database
        List<FixedDeposit> fixedDepositList = fixedDepositRepository.findAll();
        assertThat(fixedDepositList).hasSize(databaseSizeBeforeUpdate);
        FixedDeposit testFixedDeposit = fixedDepositList.get(fixedDepositList.size() - 1);
        assertThat(testFixedDeposit.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testFixedDeposit.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testFixedDeposit.getOpenDate()).isEqualTo(UPDATED_OPEN_DATE);
        assertThat(testFixedDeposit.getMaturityDate()).isEqualTo(UPDATED_MATURITY_DATE);
        assertThat(testFixedDeposit.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingFixedDeposit() throws Exception {
        int databaseSizeBeforeUpdate = fixedDepositRepository.findAll().size();

        // Create the FixedDeposit
        FixedDepositDTO fixedDepositDTO = fixedDepositMapper.toDto(fixedDeposit);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFixedDepositMockMvc.perform(put("/api/fixed-deposits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fixedDepositDTO)))
            .andExpect(status().isCreated());

        // Validate the FixedDeposit in the database
        List<FixedDeposit> fixedDepositList = fixedDepositRepository.findAll();
        assertThat(fixedDepositList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFixedDeposit() throws Exception {
        // Initialize the database
        fixedDepositRepository.saveAndFlush(fixedDeposit);
        int databaseSizeBeforeDelete = fixedDepositRepository.findAll().size();

        // Get the fixedDeposit
        restFixedDepositMockMvc.perform(delete("/api/fixed-deposits/{id}", fixedDeposit.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FixedDeposit> fixedDepositList = fixedDepositRepository.findAll();
        assertThat(fixedDepositList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FixedDeposit.class);
        FixedDeposit fixedDeposit1 = new FixedDeposit();
        fixedDeposit1.setId(1L);
        FixedDeposit fixedDeposit2 = new FixedDeposit();
        fixedDeposit2.setId(fixedDeposit1.getId());
        assertThat(fixedDeposit1).isEqualTo(fixedDeposit2);
        fixedDeposit2.setId(2L);
        assertThat(fixedDeposit1).isNotEqualTo(fixedDeposit2);
        fixedDeposit1.setId(null);
        assertThat(fixedDeposit1).isNotEqualTo(fixedDeposit2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FixedDepositDTO.class);
        FixedDepositDTO fixedDepositDTO1 = new FixedDepositDTO();
        fixedDepositDTO1.setId(1L);
        FixedDepositDTO fixedDepositDTO2 = new FixedDepositDTO();
        assertThat(fixedDepositDTO1).isNotEqualTo(fixedDepositDTO2);
        fixedDepositDTO2.setId(fixedDepositDTO1.getId());
        assertThat(fixedDepositDTO1).isEqualTo(fixedDepositDTO2);
        fixedDepositDTO2.setId(2L);
        assertThat(fixedDepositDTO1).isNotEqualTo(fixedDepositDTO2);
        fixedDepositDTO1.setId(null);
        assertThat(fixedDepositDTO1).isNotEqualTo(fixedDepositDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        // assertThat(fixedDepositMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(fixedDepositMapper.fromId(null)).isNull();
    }
}
