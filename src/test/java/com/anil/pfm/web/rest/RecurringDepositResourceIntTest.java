package com.anil.pfm.web.rest;

import com.anil.pfm.PfmApp;

import com.anil.pfm.domain.RecurringDeposit;
import com.anil.pfm.repository.RecurringDepositRepository;
import com.anil.pfm.service.RecurringDepositService;
import com.anil.pfm.service.dto.RecurringDepositDTO;
import com.anil.pfm.service.mapper.RecurringDepositMapper;
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

import com.anil.pfm.domain.enumeration.RDStatus;
/**
 * Test class for the RecurringDepositResource REST controller.
 *
 * @see RecurringDepositResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PfmApp.class)
public class RecurringDepositResourceIntTest {

    private static final String DEFAULT_ACCOUNT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NUMBER = "BBBBBBBBBB";

    private static final Instant DEFAULT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_INSTALLMENT_DATE_DAY = 1;
    private static final Integer UPDATED_INSTALLMENT_DATE_DAY = 2;

    private static final BigDecimal DEFAULT_CURRENT_BALANCE = new BigDecimal(1);
    private static final BigDecimal UPDATED_CURRENT_BALANCE = new BigDecimal(2);

    private static final RDStatus DEFAULT_STATUS = RDStatus.OPEN;
    private static final RDStatus UPDATED_STATUS = RDStatus.CLOSED;

    private static final BigDecimal DEFAULT_INTEREST_RATE = new BigDecimal(1);
    private static final BigDecimal UPDATED_INTEREST_RATE = new BigDecimal(2);

    @Autowired
    private RecurringDepositRepository recurringDepositRepository;

    @Autowired
    private RecurringDepositMapper recurringDepositMapper;

    @Autowired
    private RecurringDepositService recurringDepositService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRecurringDepositMockMvc;

    private RecurringDeposit recurringDeposit;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RecurringDepositResource recurringDepositResource = new RecurringDepositResource(recurringDepositService);
        this.restRecurringDepositMockMvc = MockMvcBuilders.standaloneSetup(recurringDepositResource)
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
    public static RecurringDeposit createEntity(EntityManager em) {
        RecurringDeposit recurringDeposit = new RecurringDeposit()
            .accountNumber(DEFAULT_ACCOUNT_NUMBER)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .installmentDateDay(DEFAULT_INSTALLMENT_DATE_DAY)
            .currentBalance(DEFAULT_CURRENT_BALANCE)
            .status(DEFAULT_STATUS)
            .interestRate(DEFAULT_INTEREST_RATE);
        return recurringDeposit;
    }

    @Before
    public void initTest() {
        recurringDeposit = createEntity(em);
    }

    @Test
    @Transactional
    public void createRecurringDeposit() throws Exception {
        int databaseSizeBeforeCreate = recurringDepositRepository.findAll().size();

        // Create the RecurringDeposit
        RecurringDepositDTO recurringDepositDTO = recurringDepositMapper.toDto(recurringDeposit);
        restRecurringDepositMockMvc.perform(post("/api/recurring-deposits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recurringDepositDTO)))
            .andExpect(status().isCreated());

        // Validate the RecurringDeposit in the database
        List<RecurringDeposit> recurringDepositList = recurringDepositRepository.findAll();
        assertThat(recurringDepositList).hasSize(databaseSizeBeforeCreate + 1);
        RecurringDeposit testRecurringDeposit = recurringDepositList.get(recurringDepositList.size() - 1);
        assertThat(testRecurringDeposit.getAccountNumber()).isEqualTo(DEFAULT_ACCOUNT_NUMBER);
        assertThat(testRecurringDeposit.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testRecurringDeposit.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testRecurringDeposit.getInstallmentDateDay()).isEqualTo(DEFAULT_INSTALLMENT_DATE_DAY);
        assertThat(testRecurringDeposit.getCurrentBalance()).isEqualTo(DEFAULT_CURRENT_BALANCE);
        assertThat(testRecurringDeposit.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testRecurringDeposit.getInterestRate()).isEqualTo(DEFAULT_INTEREST_RATE);
    }

    @Test
    @Transactional
    public void createRecurringDepositWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = recurringDepositRepository.findAll().size();

        // Create the RecurringDeposit with an existing ID
        recurringDeposit.setId(1L);
        RecurringDepositDTO recurringDepositDTO = recurringDepositMapper.toDto(recurringDeposit);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRecurringDepositMockMvc.perform(post("/api/recurring-deposits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recurringDepositDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RecurringDeposit in the database
        List<RecurringDeposit> recurringDepositList = recurringDepositRepository.findAll();
        assertThat(recurringDepositList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = recurringDepositRepository.findAll().size();
        // set the field null
        recurringDeposit.setStartDate(null);

        // Create the RecurringDeposit, which fails.
        RecurringDepositDTO recurringDepositDTO = recurringDepositMapper.toDto(recurringDeposit);

        restRecurringDepositMockMvc.perform(post("/api/recurring-deposits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recurringDepositDTO)))
            .andExpect(status().isBadRequest());

        List<RecurringDeposit> recurringDepositList = recurringDepositRepository.findAll();
        assertThat(recurringDepositList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = recurringDepositRepository.findAll().size();
        // set the field null
        recurringDeposit.setEndDate(null);

        // Create the RecurringDeposit, which fails.
        RecurringDepositDTO recurringDepositDTO = recurringDepositMapper.toDto(recurringDeposit);

        restRecurringDepositMockMvc.perform(post("/api/recurring-deposits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recurringDepositDTO)))
            .andExpect(status().isBadRequest());

        List<RecurringDeposit> recurringDepositList = recurringDepositRepository.findAll();
        assertThat(recurringDepositList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInstallmentDateDayIsRequired() throws Exception {
        int databaseSizeBeforeTest = recurringDepositRepository.findAll().size();
        // set the field null
        recurringDeposit.setInstallmentDateDay(null);

        // Create the RecurringDeposit, which fails.
        RecurringDepositDTO recurringDepositDTO = recurringDepositMapper.toDto(recurringDeposit);

        restRecurringDepositMockMvc.perform(post("/api/recurring-deposits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recurringDepositDTO)))
            .andExpect(status().isBadRequest());

        List<RecurringDeposit> recurringDepositList = recurringDepositRepository.findAll();
        assertThat(recurringDepositList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCurrentBalanceIsRequired() throws Exception {
        int databaseSizeBeforeTest = recurringDepositRepository.findAll().size();
        // set the field null
        recurringDeposit.setCurrentBalance(null);

        // Create the RecurringDeposit, which fails.
        RecurringDepositDTO recurringDepositDTO = recurringDepositMapper.toDto(recurringDeposit);

        restRecurringDepositMockMvc.perform(post("/api/recurring-deposits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recurringDepositDTO)))
            .andExpect(status().isBadRequest());

        List<RecurringDeposit> recurringDepositList = recurringDepositRepository.findAll();
        assertThat(recurringDepositList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = recurringDepositRepository.findAll().size();
        // set the field null
        recurringDeposit.setStatus(null);

        // Create the RecurringDeposit, which fails.
        RecurringDepositDTO recurringDepositDTO = recurringDepositMapper.toDto(recurringDeposit);

        restRecurringDepositMockMvc.perform(post("/api/recurring-deposits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recurringDepositDTO)))
            .andExpect(status().isBadRequest());

        List<RecurringDeposit> recurringDepositList = recurringDepositRepository.findAll();
        assertThat(recurringDepositList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRecurringDeposits() throws Exception {
        // Initialize the database
        recurringDepositRepository.saveAndFlush(recurringDeposit);

        // Get all the recurringDepositList
        restRecurringDepositMockMvc.perform(get("/api/recurring-deposits?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recurringDeposit.getId().intValue())))
            .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].installmentDateDay").value(hasItem(DEFAULT_INSTALLMENT_DATE_DAY)))
            .andExpect(jsonPath("$.[*].currentBalance").value(hasItem(DEFAULT_CURRENT_BALANCE.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].interestRate").value(hasItem(DEFAULT_INTEREST_RATE.intValue())));
    }

    @Test
    @Transactional
    public void getRecurringDeposit() throws Exception {
        // Initialize the database
        recurringDepositRepository.saveAndFlush(recurringDeposit);

        // Get the recurringDeposit
        restRecurringDepositMockMvc.perform(get("/api/recurring-deposits/{id}", recurringDeposit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(recurringDeposit.getId().intValue()))
            .andExpect(jsonPath("$.accountNumber").value(DEFAULT_ACCOUNT_NUMBER.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.installmentDateDay").value(DEFAULT_INSTALLMENT_DATE_DAY))
            .andExpect(jsonPath("$.currentBalance").value(DEFAULT_CURRENT_BALANCE.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.interestRate").value(DEFAULT_INTEREST_RATE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingRecurringDeposit() throws Exception {
        // Get the recurringDeposit
        restRecurringDepositMockMvc.perform(get("/api/recurring-deposits/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRecurringDeposit() throws Exception {
        // Initialize the database
        recurringDepositRepository.saveAndFlush(recurringDeposit);
        int databaseSizeBeforeUpdate = recurringDepositRepository.findAll().size();

        // Update the recurringDeposit
        RecurringDeposit updatedRecurringDeposit = recurringDepositRepository.findOne(recurringDeposit.getId());
        updatedRecurringDeposit
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .installmentDateDay(UPDATED_INSTALLMENT_DATE_DAY)
            .currentBalance(UPDATED_CURRENT_BALANCE)
            .status(UPDATED_STATUS)
            .interestRate(UPDATED_INTEREST_RATE);
        RecurringDepositDTO recurringDepositDTO = recurringDepositMapper.toDto(updatedRecurringDeposit);

        restRecurringDepositMockMvc.perform(put("/api/recurring-deposits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recurringDepositDTO)))
            .andExpect(status().isOk());

        // Validate the RecurringDeposit in the database
        List<RecurringDeposit> recurringDepositList = recurringDepositRepository.findAll();
        assertThat(recurringDepositList).hasSize(databaseSizeBeforeUpdate);
        RecurringDeposit testRecurringDeposit = recurringDepositList.get(recurringDepositList.size() - 1);
        assertThat(testRecurringDeposit.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testRecurringDeposit.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testRecurringDeposit.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testRecurringDeposit.getInstallmentDateDay()).isEqualTo(UPDATED_INSTALLMENT_DATE_DAY);
        assertThat(testRecurringDeposit.getCurrentBalance()).isEqualTo(UPDATED_CURRENT_BALANCE);
        assertThat(testRecurringDeposit.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testRecurringDeposit.getInterestRate()).isEqualTo(UPDATED_INTEREST_RATE);
    }

    @Test
    @Transactional
    public void updateNonExistingRecurringDeposit() throws Exception {
        int databaseSizeBeforeUpdate = recurringDepositRepository.findAll().size();

        // Create the RecurringDeposit
        RecurringDepositDTO recurringDepositDTO = recurringDepositMapper.toDto(recurringDeposit);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRecurringDepositMockMvc.perform(put("/api/recurring-deposits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recurringDepositDTO)))
            .andExpect(status().isCreated());

        // Validate the RecurringDeposit in the database
        List<RecurringDeposit> recurringDepositList = recurringDepositRepository.findAll();
        assertThat(recurringDepositList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRecurringDeposit() throws Exception {
        // Initialize the database
        recurringDepositRepository.saveAndFlush(recurringDeposit);
        int databaseSizeBeforeDelete = recurringDepositRepository.findAll().size();

        // Get the recurringDeposit
        restRecurringDepositMockMvc.perform(delete("/api/recurring-deposits/{id}", recurringDeposit.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RecurringDeposit> recurringDepositList = recurringDepositRepository.findAll();
        assertThat(recurringDepositList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RecurringDeposit.class);
        RecurringDeposit recurringDeposit1 = new RecurringDeposit();
        recurringDeposit1.setId(1L);
        RecurringDeposit recurringDeposit2 = new RecurringDeposit();
        recurringDeposit2.setId(recurringDeposit1.getId());
        assertThat(recurringDeposit1).isEqualTo(recurringDeposit2);
        recurringDeposit2.setId(2L);
        assertThat(recurringDeposit1).isNotEqualTo(recurringDeposit2);
        recurringDeposit1.setId(null);
        assertThat(recurringDeposit1).isNotEqualTo(recurringDeposit2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RecurringDepositDTO.class);
        RecurringDepositDTO recurringDepositDTO1 = new RecurringDepositDTO();
        recurringDepositDTO1.setId(1L);
        RecurringDepositDTO recurringDepositDTO2 = new RecurringDepositDTO();
        assertThat(recurringDepositDTO1).isNotEqualTo(recurringDepositDTO2);
        recurringDepositDTO2.setId(recurringDepositDTO1.getId());
        assertThat(recurringDepositDTO1).isEqualTo(recurringDepositDTO2);
        recurringDepositDTO2.setId(2L);
        assertThat(recurringDepositDTO1).isNotEqualTo(recurringDepositDTO2);
        recurringDepositDTO1.setId(null);
        assertThat(recurringDepositDTO1).isNotEqualTo(recurringDepositDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(recurringDepositMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(recurringDepositMapper.fromId(null)).isNull();
    }
}
