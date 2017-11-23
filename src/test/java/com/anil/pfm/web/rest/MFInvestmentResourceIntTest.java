package com.anil.pfm.web.rest;

import com.anil.pfm.PfmApp;
import com.anil.pfm.mf.domain.MFInvestment;
import com.anil.pfm.mf.domain.MutualFund;
import com.anil.pfm.mf.repository.MFInvestmentRepository;
import com.anil.pfm.mf.repository.MutualFundRepository;
import com.anil.pfm.mf.service.MFInvestmentService;
import com.anil.pfm.mf.service.mapper.MFInvestmentMapper;
import com.anil.pfm.mf.web.rest.MFInvestmentResource;
import com.anil.pfm.service.dto.MFInvestmentDTO;
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

/**
 * Test class for the MFInvestmentResource REST controller.
 *
 * @see MFInvestmentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PfmApp.class)
public class MFInvestmentResourceIntTest {

    private static final Instant DEFAULT_PURCHASE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PURCHASE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_NAV_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_NAV_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_NAV = new BigDecimal(1);
    private static final BigDecimal UPDATED_NAV = new BigDecimal(2);

    private static final BigDecimal DEFAULT_UNIT = new BigDecimal(1);
    private static final BigDecimal UPDATED_UNIT = new BigDecimal(2);

    @Autowired
    private MutualFundRepository mutualFundRepository;
    
    @Autowired
    private MFInvestmentRepository mfInvestmentRepository;

    @Autowired
    private MFInvestmentMapper mFInvestmentMapper;

    @Autowired
    private MFInvestmentService mFInvestmentService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMFInvestmentMockMvc;

    private MFInvestment mfInvestment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MFInvestmentResource mFInvestmentResource = new MFInvestmentResource(mFInvestmentService);
        this.restMFInvestmentMockMvc = MockMvcBuilders.standaloneSetup(mFInvestmentResource)
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
    public static MFInvestment createEntity(EntityManager em) {
        MFInvestment mfInvestment = new MFInvestment()
            .purchaseDate(DEFAULT_PURCHASE_DATE)
            .navDate(DEFAULT_NAV_DATE)
            .amount(DEFAULT_AMOUNT)
            .nav(DEFAULT_NAV)
            .unit(DEFAULT_UNIT);
        
        return mfInvestment;
    }

    @Before
    public void initTest() {
        mfInvestment = createEntity(em);
    }

    @Test
    @Transactional
    public void createMFInvestment() throws Exception {
        int databaseSizeBeforeCreate = mfInvestmentRepository.findAll().size();

        MutualFund fund = mutualFundRepository.saveAndFlush(MutualFundResourceIntTest.createEntity(em));
        mfInvestment.setFund(fund);
        
        // Create the MFInvestment
        MFInvestmentDTO mFInvestmentDTO = mFInvestmentMapper.toDto(mfInvestment);
        restMFInvestmentMockMvc.perform(post("/api/m-f-investments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mFInvestmentDTO)))
            .andExpect(status().isCreated());

        // Validate the MFInvestment in the database
        List<MFInvestment> mFInvestmentList = mfInvestmentRepository.findAll();
        assertThat(mFInvestmentList).hasSize(databaseSizeBeforeCreate + 1);
        MFInvestment testMFInvestment = mFInvestmentList.get(mFInvestmentList.size() - 1);
        assertThat(testMFInvestment.getPurchaseDate()).isEqualTo(DEFAULT_PURCHASE_DATE);
        assertThat(testMFInvestment.getNavDate()).isEqualTo(DEFAULT_NAV_DATE);
        assertThat(testMFInvestment.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testMFInvestment.getNav()).isEqualTo(DEFAULT_NAV);
        assertThat(testMFInvestment.getUnit()).isEqualTo(DEFAULT_UNIT);
    }

    @Test
    @Transactional
    public void createMFInvestmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mfInvestmentRepository.findAll().size();

        // Create the MFInvestment with an existing ID
        mfInvestment.setId(1L);
        MFInvestmentDTO mFInvestmentDTO = mFInvestmentMapper.toDto(mfInvestment);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMFInvestmentMockMvc.perform(post("/api/m-f-investments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mFInvestmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MFInvestment in the database
        List<MFInvestment> mFInvestmentList = mfInvestmentRepository.findAll();
        assertThat(mFInvestmentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkPurchaseDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = mfInvestmentRepository.findAll().size();
        // set the field null
        mfInvestment.setPurchaseDate(null);

        // Create the MFInvestment, which fails.
        MFInvestmentDTO mFInvestmentDTO = mFInvestmentMapper.toDto(mfInvestment);

        restMFInvestmentMockMvc.perform(post("/api/m-f-investments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mFInvestmentDTO)))
            .andExpect(status().isBadRequest());

        List<MFInvestment> mFInvestmentList = mfInvestmentRepository.findAll();
        assertThat(mFInvestmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNavDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = mfInvestmentRepository.findAll().size();
        // set the field null
        mfInvestment.setNavDate(null);

        // Create the MFInvestment, which fails.
        MFInvestmentDTO mFInvestmentDTO = mFInvestmentMapper.toDto(mfInvestment);

        restMFInvestmentMockMvc.perform(post("/api/m-f-investments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mFInvestmentDTO)))
            .andExpect(status().isBadRequest());

        List<MFInvestment> mFInvestmentList = mfInvestmentRepository.findAll();
        assertThat(mFInvestmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = mfInvestmentRepository.findAll().size();
        // set the field null
        mfInvestment.setAmount(null);

        // Create the MFInvestment, which fails.
        MFInvestmentDTO mFInvestmentDTO = mFInvestmentMapper.toDto(mfInvestment);

        restMFInvestmentMockMvc.perform(post("/api/m-f-investments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mFInvestmentDTO)))
            .andExpect(status().isBadRequest());

        List<MFInvestment> mFInvestmentList = mfInvestmentRepository.findAll();
        assertThat(mFInvestmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMFInvestments() throws Exception {
        // Initialize the database
    	
    	MutualFund fund = mutualFundRepository.saveAndFlush(MutualFundResourceIntTest.createEntity(em));
        mfInvestment.setFund(fund);
    	
        mfInvestmentRepository.saveAndFlush(mfInvestment);

        // Get all the mFInvestmentList
        restMFInvestmentMockMvc.perform(get("/api/m-f-investments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mfInvestment.getId().intValue())))
            .andExpect(jsonPath("$.[*].purchaseDate").value(hasItem(DEFAULT_PURCHASE_DATE.toString())))
            .andExpect(jsonPath("$.[*].navDate").value(hasItem(DEFAULT_NAV_DATE.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].nav").value(hasItem(DEFAULT_NAV.intValue())))
            .andExpect(jsonPath("$.[*].unit").value(hasItem(DEFAULT_UNIT.intValue())));
    }

    @Test
    @Transactional
    public void getMFInvestment() throws Exception {
        // Initialize the database
    	
    	MutualFund fund = mutualFundRepository.saveAndFlush(MutualFundResourceIntTest.createEntity(em));
        mfInvestment.setFund(fund);
    	
        mfInvestmentRepository.saveAndFlush(mfInvestment);

        // Get the mFInvestment
        restMFInvestmentMockMvc.perform(get("/api/m-f-investments/{id}", mfInvestment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mfInvestment.getId().intValue()))
            .andExpect(jsonPath("$.purchaseDate").value(DEFAULT_PURCHASE_DATE.toString()))
            .andExpect(jsonPath("$.navDate").value(DEFAULT_NAV_DATE.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.nav").value(DEFAULT_NAV.intValue()))
            .andExpect(jsonPath("$.unit").value(DEFAULT_UNIT.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMFInvestment() throws Exception {
        // Get the mFInvestment
        restMFInvestmentMockMvc.perform(get("/api/m-f-investments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMFInvestment() throws Exception {
        // Initialize the database
    	
    	MutualFund fund = mutualFundRepository.saveAndFlush(MutualFundResourceIntTest.createEntity(em));
        mfInvestment.setFund(fund);
    	
        mfInvestmentRepository.saveAndFlush(mfInvestment);
        int databaseSizeBeforeUpdate = mfInvestmentRepository.findAll().size();

        // Update the mFInvestment
        MFInvestment updatedMFInvestment = mfInvestmentRepository.findOne(mfInvestment.getId());
        updatedMFInvestment
            .purchaseDate(UPDATED_PURCHASE_DATE)
            .navDate(UPDATED_NAV_DATE)
            .amount(UPDATED_AMOUNT)
            .nav(UPDATED_NAV)
            .unit(UPDATED_UNIT);
        MFInvestmentDTO mFInvestmentDTO = mFInvestmentMapper.toDto(updatedMFInvestment);

        restMFInvestmentMockMvc.perform(put("/api/m-f-investments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mFInvestmentDTO)))
            .andExpect(status().isOk());

        // Validate the MFInvestment in the database
        List<MFInvestment> mFInvestmentList = mfInvestmentRepository.findAll();
        assertThat(mFInvestmentList).hasSize(databaseSizeBeforeUpdate);
        MFInvestment testMFInvestment = mFInvestmentList.get(mFInvestmentList.size() - 1);
        assertThat(testMFInvestment.getPurchaseDate()).isEqualTo(UPDATED_PURCHASE_DATE);
        assertThat(testMFInvestment.getNavDate()).isEqualTo(UPDATED_NAV_DATE);
        assertThat(testMFInvestment.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testMFInvestment.getNav()).isEqualTo(UPDATED_NAV);
        assertThat(testMFInvestment.getUnit()).isEqualTo(UPDATED_UNIT);
    }

    @Test
    @Transactional
    public void deleteMFInvestment() throws Exception {
        // Initialize the database
    	
    	MutualFund fund = mutualFundRepository.saveAndFlush(MutualFundResourceIntTest.createEntity(em));
        mfInvestment.setFund(fund);
    	
        mfInvestmentRepository.saveAndFlush(mfInvestment);
        int databaseSizeBeforeDelete = mfInvestmentRepository.findAll().size();

        // Get the mFInvestment
        restMFInvestmentMockMvc.perform(delete("/api/m-f-investments/{id}", mfInvestment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MFInvestment> mFInvestmentList = mfInvestmentRepository.findAll();
        assertThat(mFInvestmentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MFInvestment.class);
        MFInvestment mFInvestment1 = new MFInvestment();
        mFInvestment1.setId(1L);
        MFInvestment mFInvestment2 = new MFInvestment();
        mFInvestment2.setId(mFInvestment1.getId());
        assertThat(mFInvestment1).isEqualTo(mFInvestment2);
        mFInvestment2.setId(2L);
        assertThat(mFInvestment1).isNotEqualTo(mFInvestment2);
        mFInvestment1.setId(null);
        assertThat(mFInvestment1).isNotEqualTo(mFInvestment2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MFInvestmentDTO.class);
        MFInvestmentDTO mFInvestmentDTO1 = new MFInvestmentDTO();
        mFInvestmentDTO1.setId(1L);
        MFInvestmentDTO mFInvestmentDTO2 = new MFInvestmentDTO();
        assertThat(mFInvestmentDTO1).isNotEqualTo(mFInvestmentDTO2);
        mFInvestmentDTO2.setId(mFInvestmentDTO1.getId());
        assertThat(mFInvestmentDTO1).isEqualTo(mFInvestmentDTO2);
        mFInvestmentDTO2.setId(2L);
        assertThat(mFInvestmentDTO1).isNotEqualTo(mFInvestmentDTO2);
        mFInvestmentDTO1.setId(null);
        assertThat(mFInvestmentDTO1).isNotEqualTo(mFInvestmentDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(mFInvestmentMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(mFInvestmentMapper.fromId(null)).isNull();
    }
}
