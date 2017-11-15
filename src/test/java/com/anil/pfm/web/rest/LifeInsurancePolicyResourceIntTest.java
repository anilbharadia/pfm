package com.anil.pfm.web.rest;

import com.anil.pfm.PfmApp;

import com.anil.pfm.domain.LifeInsurancePolicy;
import com.anil.pfm.repository.LifeInsurancePolicyRepository;
import com.anil.pfm.service.LifeInsurancePolicyService;
import com.anil.pfm.service.dto.LifeInsurancePolicyDTO;
import com.anil.pfm.service.mapper.LifeInsurancePolicyMapper;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the LifeInsurancePolicyResource REST controller.
 *
 * @see LifeInsurancePolicyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PfmApp.class)
public class LifeInsurancePolicyResourceIntTest {

    private static final String DEFAULT_POLICY_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_POLICY_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_TOTAL_PREMIUM_PAID = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_PREMIUM_PAID = new BigDecimal(2);

    private static final BigDecimal DEFAULT_PREMIUM = new BigDecimal(1);
    private static final BigDecimal UPDATED_PREMIUM = new BigDecimal(2);

    private static final BigDecimal DEFAULT_SUM_ASSURED = new BigDecimal(1);
    private static final BigDecimal UPDATED_SUM_ASSURED = new BigDecimal(2);

    @Autowired
    private LifeInsurancePolicyRepository lifeInsurancePolicyRepository;

    @Autowired
    private LifeInsurancePolicyMapper lifeInsurancePolicyMapper;

    @Autowired
    private LifeInsurancePolicyService lifeInsurancePolicyService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLifeInsurancePolicyMockMvc;

    private LifeInsurancePolicy lifeInsurancePolicy;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LifeInsurancePolicyResource lifeInsurancePolicyResource = new LifeInsurancePolicyResource(lifeInsurancePolicyService);
        this.restLifeInsurancePolicyMockMvc = MockMvcBuilders.standaloneSetup(lifeInsurancePolicyResource)
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
    public static LifeInsurancePolicy createEntity(EntityManager em) {
        LifeInsurancePolicy lifeInsurancePolicy = new LifeInsurancePolicy()
            .policyNumber(DEFAULT_POLICY_NUMBER)
            .name(DEFAULT_NAME)
            .totalPremiumPaid(DEFAULT_TOTAL_PREMIUM_PAID)
            .premium(DEFAULT_PREMIUM)
            .sumAssured(DEFAULT_SUM_ASSURED);
        return lifeInsurancePolicy;
    }

    @Before
    public void initTest() {
        lifeInsurancePolicy = createEntity(em);
    }

    @Test
    @Transactional
    public void createLifeInsurancePolicy() throws Exception {
        int databaseSizeBeforeCreate = lifeInsurancePolicyRepository.findAll().size();

        // Create the LifeInsurancePolicy
        LifeInsurancePolicyDTO lifeInsurancePolicyDTO = lifeInsurancePolicyMapper.toDto(lifeInsurancePolicy);
        restLifeInsurancePolicyMockMvc.perform(post("/api/life-insurance-policies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lifeInsurancePolicyDTO)))
            .andExpect(status().isCreated());

        // Validate the LifeInsurancePolicy in the database
        List<LifeInsurancePolicy> lifeInsurancePolicyList = lifeInsurancePolicyRepository.findAll();
        assertThat(lifeInsurancePolicyList).hasSize(databaseSizeBeforeCreate + 1);
        LifeInsurancePolicy testLifeInsurancePolicy = lifeInsurancePolicyList.get(lifeInsurancePolicyList.size() - 1);
        assertThat(testLifeInsurancePolicy.getPolicyNumber()).isEqualTo(DEFAULT_POLICY_NUMBER);
        assertThat(testLifeInsurancePolicy.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLifeInsurancePolicy.getTotalPremiumPaid()).isEqualTo(DEFAULT_TOTAL_PREMIUM_PAID);
        assertThat(testLifeInsurancePolicy.getPremium()).isEqualTo(DEFAULT_PREMIUM);
        assertThat(testLifeInsurancePolicy.getSumAssured()).isEqualTo(DEFAULT_SUM_ASSURED);
    }

    @Test
    @Transactional
    public void createLifeInsurancePolicyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lifeInsurancePolicyRepository.findAll().size();

        // Create the LifeInsurancePolicy with an existing ID
        lifeInsurancePolicy.setId(1L);
        LifeInsurancePolicyDTO lifeInsurancePolicyDTO = lifeInsurancePolicyMapper.toDto(lifeInsurancePolicy);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLifeInsurancePolicyMockMvc.perform(post("/api/life-insurance-policies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lifeInsurancePolicyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LifeInsurancePolicy in the database
        List<LifeInsurancePolicy> lifeInsurancePolicyList = lifeInsurancePolicyRepository.findAll();
        assertThat(lifeInsurancePolicyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkPolicyNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = lifeInsurancePolicyRepository.findAll().size();
        // set the field null
        lifeInsurancePolicy.setPolicyNumber(null);

        // Create the LifeInsurancePolicy, which fails.
        LifeInsurancePolicyDTO lifeInsurancePolicyDTO = lifeInsurancePolicyMapper.toDto(lifeInsurancePolicy);

        restLifeInsurancePolicyMockMvc.perform(post("/api/life-insurance-policies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lifeInsurancePolicyDTO)))
            .andExpect(status().isBadRequest());

        List<LifeInsurancePolicy> lifeInsurancePolicyList = lifeInsurancePolicyRepository.findAll();
        assertThat(lifeInsurancePolicyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = lifeInsurancePolicyRepository.findAll().size();
        // set the field null
        lifeInsurancePolicy.setName(null);

        // Create the LifeInsurancePolicy, which fails.
        LifeInsurancePolicyDTO lifeInsurancePolicyDTO = lifeInsurancePolicyMapper.toDto(lifeInsurancePolicy);

        restLifeInsurancePolicyMockMvc.perform(post("/api/life-insurance-policies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lifeInsurancePolicyDTO)))
            .andExpect(status().isBadRequest());

        List<LifeInsurancePolicy> lifeInsurancePolicyList = lifeInsurancePolicyRepository.findAll();
        assertThat(lifeInsurancePolicyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotalPremiumPaidIsRequired() throws Exception {
        int databaseSizeBeforeTest = lifeInsurancePolicyRepository.findAll().size();
        // set the field null
        lifeInsurancePolicy.setTotalPremiumPaid(null);

        // Create the LifeInsurancePolicy, which fails.
        LifeInsurancePolicyDTO lifeInsurancePolicyDTO = lifeInsurancePolicyMapper.toDto(lifeInsurancePolicy);

        restLifeInsurancePolicyMockMvc.perform(post("/api/life-insurance-policies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lifeInsurancePolicyDTO)))
            .andExpect(status().isBadRequest());

        List<LifeInsurancePolicy> lifeInsurancePolicyList = lifeInsurancePolicyRepository.findAll();
        assertThat(lifeInsurancePolicyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPremiumIsRequired() throws Exception {
        int databaseSizeBeforeTest = lifeInsurancePolicyRepository.findAll().size();
        // set the field null
        lifeInsurancePolicy.setPremium(null);

        // Create the LifeInsurancePolicy, which fails.
        LifeInsurancePolicyDTO lifeInsurancePolicyDTO = lifeInsurancePolicyMapper.toDto(lifeInsurancePolicy);

        restLifeInsurancePolicyMockMvc.perform(post("/api/life-insurance-policies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lifeInsurancePolicyDTO)))
            .andExpect(status().isBadRequest());

        List<LifeInsurancePolicy> lifeInsurancePolicyList = lifeInsurancePolicyRepository.findAll();
        assertThat(lifeInsurancePolicyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSumAssuredIsRequired() throws Exception {
        int databaseSizeBeforeTest = lifeInsurancePolicyRepository.findAll().size();
        // set the field null
        lifeInsurancePolicy.setSumAssured(null);

        // Create the LifeInsurancePolicy, which fails.
        LifeInsurancePolicyDTO lifeInsurancePolicyDTO = lifeInsurancePolicyMapper.toDto(lifeInsurancePolicy);

        restLifeInsurancePolicyMockMvc.perform(post("/api/life-insurance-policies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lifeInsurancePolicyDTO)))
            .andExpect(status().isBadRequest());

        List<LifeInsurancePolicy> lifeInsurancePolicyList = lifeInsurancePolicyRepository.findAll();
        assertThat(lifeInsurancePolicyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLifeInsurancePolicies() throws Exception {
        // Initialize the database
        lifeInsurancePolicyRepository.saveAndFlush(lifeInsurancePolicy);

        // Get all the lifeInsurancePolicyList
        restLifeInsurancePolicyMockMvc.perform(get("/api/life-insurance-policies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lifeInsurancePolicy.getId().intValue())))
            .andExpect(jsonPath("$.[*].policyNumber").value(hasItem(DEFAULT_POLICY_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].totalPremiumPaid").value(hasItem(DEFAULT_TOTAL_PREMIUM_PAID.intValue())))
            .andExpect(jsonPath("$.[*].premium").value(hasItem(DEFAULT_PREMIUM.intValue())))
            .andExpect(jsonPath("$.[*].sumAssured").value(hasItem(DEFAULT_SUM_ASSURED.intValue())));
    }

    @Test
    @Transactional
    public void getLifeInsurancePolicy() throws Exception {
        // Initialize the database
        lifeInsurancePolicyRepository.saveAndFlush(lifeInsurancePolicy);

        // Get the lifeInsurancePolicy
        restLifeInsurancePolicyMockMvc.perform(get("/api/life-insurance-policies/{id}", lifeInsurancePolicy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(lifeInsurancePolicy.getId().intValue()))
            .andExpect(jsonPath("$.policyNumber").value(DEFAULT_POLICY_NUMBER.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.totalPremiumPaid").value(DEFAULT_TOTAL_PREMIUM_PAID.intValue()))
            .andExpect(jsonPath("$.premium").value(DEFAULT_PREMIUM.intValue()))
            .andExpect(jsonPath("$.sumAssured").value(DEFAULT_SUM_ASSURED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingLifeInsurancePolicy() throws Exception {
        // Get the lifeInsurancePolicy
        restLifeInsurancePolicyMockMvc.perform(get("/api/life-insurance-policies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLifeInsurancePolicy() throws Exception {
        // Initialize the database
        lifeInsurancePolicyRepository.saveAndFlush(lifeInsurancePolicy);
        int databaseSizeBeforeUpdate = lifeInsurancePolicyRepository.findAll().size();

        // Update the lifeInsurancePolicy
        LifeInsurancePolicy updatedLifeInsurancePolicy = lifeInsurancePolicyRepository.findOne(lifeInsurancePolicy.getId());
        updatedLifeInsurancePolicy
            .policyNumber(UPDATED_POLICY_NUMBER)
            .name(UPDATED_NAME)
            .totalPremiumPaid(UPDATED_TOTAL_PREMIUM_PAID)
            .premium(UPDATED_PREMIUM)
            .sumAssured(UPDATED_SUM_ASSURED);
        LifeInsurancePolicyDTO lifeInsurancePolicyDTO = lifeInsurancePolicyMapper.toDto(updatedLifeInsurancePolicy);

        restLifeInsurancePolicyMockMvc.perform(put("/api/life-insurance-policies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lifeInsurancePolicyDTO)))
            .andExpect(status().isOk());

        // Validate the LifeInsurancePolicy in the database
        List<LifeInsurancePolicy> lifeInsurancePolicyList = lifeInsurancePolicyRepository.findAll();
        assertThat(lifeInsurancePolicyList).hasSize(databaseSizeBeforeUpdate);
        LifeInsurancePolicy testLifeInsurancePolicy = lifeInsurancePolicyList.get(lifeInsurancePolicyList.size() - 1);
        assertThat(testLifeInsurancePolicy.getPolicyNumber()).isEqualTo(UPDATED_POLICY_NUMBER);
        assertThat(testLifeInsurancePolicy.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLifeInsurancePolicy.getTotalPremiumPaid()).isEqualTo(UPDATED_TOTAL_PREMIUM_PAID);
        assertThat(testLifeInsurancePolicy.getPremium()).isEqualTo(UPDATED_PREMIUM);
        assertThat(testLifeInsurancePolicy.getSumAssured()).isEqualTo(UPDATED_SUM_ASSURED);
    }

    @Test
    @Transactional
    public void updateNonExistingLifeInsurancePolicy() throws Exception {
        int databaseSizeBeforeUpdate = lifeInsurancePolicyRepository.findAll().size();

        // Create the LifeInsurancePolicy
        LifeInsurancePolicyDTO lifeInsurancePolicyDTO = lifeInsurancePolicyMapper.toDto(lifeInsurancePolicy);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLifeInsurancePolicyMockMvc.perform(put("/api/life-insurance-policies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lifeInsurancePolicyDTO)))
            .andExpect(status().isCreated());

        // Validate the LifeInsurancePolicy in the database
        List<LifeInsurancePolicy> lifeInsurancePolicyList = lifeInsurancePolicyRepository.findAll();
        assertThat(lifeInsurancePolicyList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLifeInsurancePolicy() throws Exception {
        // Initialize the database
        lifeInsurancePolicyRepository.saveAndFlush(lifeInsurancePolicy);
        int databaseSizeBeforeDelete = lifeInsurancePolicyRepository.findAll().size();

        // Get the lifeInsurancePolicy
        restLifeInsurancePolicyMockMvc.perform(delete("/api/life-insurance-policies/{id}", lifeInsurancePolicy.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LifeInsurancePolicy> lifeInsurancePolicyList = lifeInsurancePolicyRepository.findAll();
        assertThat(lifeInsurancePolicyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LifeInsurancePolicy.class);
        LifeInsurancePolicy lifeInsurancePolicy1 = new LifeInsurancePolicy();
        lifeInsurancePolicy1.setId(1L);
        LifeInsurancePolicy lifeInsurancePolicy2 = new LifeInsurancePolicy();
        lifeInsurancePolicy2.setId(lifeInsurancePolicy1.getId());
        assertThat(lifeInsurancePolicy1).isEqualTo(lifeInsurancePolicy2);
        lifeInsurancePolicy2.setId(2L);
        assertThat(lifeInsurancePolicy1).isNotEqualTo(lifeInsurancePolicy2);
        lifeInsurancePolicy1.setId(null);
        assertThat(lifeInsurancePolicy1).isNotEqualTo(lifeInsurancePolicy2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LifeInsurancePolicyDTO.class);
        LifeInsurancePolicyDTO lifeInsurancePolicyDTO1 = new LifeInsurancePolicyDTO();
        lifeInsurancePolicyDTO1.setId(1L);
        LifeInsurancePolicyDTO lifeInsurancePolicyDTO2 = new LifeInsurancePolicyDTO();
        assertThat(lifeInsurancePolicyDTO1).isNotEqualTo(lifeInsurancePolicyDTO2);
        lifeInsurancePolicyDTO2.setId(lifeInsurancePolicyDTO1.getId());
        assertThat(lifeInsurancePolicyDTO1).isEqualTo(lifeInsurancePolicyDTO2);
        lifeInsurancePolicyDTO2.setId(2L);
        assertThat(lifeInsurancePolicyDTO1).isNotEqualTo(lifeInsurancePolicyDTO2);
        lifeInsurancePolicyDTO1.setId(null);
        assertThat(lifeInsurancePolicyDTO1).isNotEqualTo(lifeInsurancePolicyDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(lifeInsurancePolicyMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(lifeInsurancePolicyMapper.fromId(null)).isNull();
    }
}
