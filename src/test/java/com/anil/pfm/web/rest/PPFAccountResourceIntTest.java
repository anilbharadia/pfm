package com.anil.pfm.web.rest;

import com.anil.pfm.PfmApp;

import com.anil.pfm.domain.PPFAccount;
import com.anil.pfm.repository.PPFAccountRepository;
import com.anil.pfm.service.PPFAccountService;
import com.anil.pfm.service.dto.PPFAccountDTO;
import com.anil.pfm.service.mapper.PPFAccountMapper;
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
 * Test class for the PPFAccountResource REST controller.
 *
 * @see PPFAccountResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PfmApp.class)
public class PPFAccountResourceIntTest {

    private static final String DEFAULT_ACCOUNT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NUMBER = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_BALANCE = new BigDecimal(1);
    private static final BigDecimal UPDATED_BALANCE = new BigDecimal(2);

    @Autowired
    private PPFAccountRepository pPFAccountRepository;

    @Autowired
    private PPFAccountMapper pPFAccountMapper;

    @Autowired
    private PPFAccountService pPFAccountService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPPFAccountMockMvc;

    private PPFAccount pPFAccount;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PPFAccountResource pPFAccountResource = new PPFAccountResource(pPFAccountService);
        this.restPPFAccountMockMvc = MockMvcBuilders.standaloneSetup(pPFAccountResource)
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
    public static PPFAccount createEntity(EntityManager em) {
        PPFAccount pPFAccount = new PPFAccount()
            .accountNumber(DEFAULT_ACCOUNT_NUMBER)
            .balance(DEFAULT_BALANCE);
        return pPFAccount;
    }

    @Before
    public void initTest() {
        pPFAccount = createEntity(em);
    }

    @Test
    @Transactional
    public void createPPFAccount() throws Exception {
        int databaseSizeBeforeCreate = pPFAccountRepository.findAll().size();

        // Create the PPFAccount
        PPFAccountDTO pPFAccountDTO = pPFAccountMapper.toDto(pPFAccount);
        restPPFAccountMockMvc.perform(post("/api/p-pf-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pPFAccountDTO)))
            .andExpect(status().isCreated());

        // Validate the PPFAccount in the database
        List<PPFAccount> pPFAccountList = pPFAccountRepository.findAll();
        assertThat(pPFAccountList).hasSize(databaseSizeBeforeCreate + 1);
        PPFAccount testPPFAccount = pPFAccountList.get(pPFAccountList.size() - 1);
        assertThat(testPPFAccount.getAccountNumber()).isEqualTo(DEFAULT_ACCOUNT_NUMBER);
        assertThat(testPPFAccount.getBalance()).isEqualTo(DEFAULT_BALANCE);
    }

    @Test
    @Transactional
    public void createPPFAccountWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pPFAccountRepository.findAll().size();

        // Create the PPFAccount with an existing ID
        pPFAccount.setId(1L);
        PPFAccountDTO pPFAccountDTO = pPFAccountMapper.toDto(pPFAccount);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPPFAccountMockMvc.perform(post("/api/p-pf-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pPFAccountDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PPFAccount in the database
        List<PPFAccount> pPFAccountList = pPFAccountRepository.findAll();
        assertThat(pPFAccountList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkAccountNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = pPFAccountRepository.findAll().size();
        // set the field null
        pPFAccount.setAccountNumber(null);

        // Create the PPFAccount, which fails.
        PPFAccountDTO pPFAccountDTO = pPFAccountMapper.toDto(pPFAccount);

        restPPFAccountMockMvc.perform(post("/api/p-pf-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pPFAccountDTO)))
            .andExpect(status().isBadRequest());

        List<PPFAccount> pPFAccountList = pPFAccountRepository.findAll();
        assertThat(pPFAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBalanceIsRequired() throws Exception {
        int databaseSizeBeforeTest = pPFAccountRepository.findAll().size();
        // set the field null
        pPFAccount.setBalance(null);

        // Create the PPFAccount, which fails.
        PPFAccountDTO pPFAccountDTO = pPFAccountMapper.toDto(pPFAccount);

        restPPFAccountMockMvc.perform(post("/api/p-pf-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pPFAccountDTO)))
            .andExpect(status().isBadRequest());

        List<PPFAccount> pPFAccountList = pPFAccountRepository.findAll();
        assertThat(pPFAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPPFAccounts() throws Exception {
        // Initialize the database
        pPFAccountRepository.saveAndFlush(pPFAccount);

        // Get all the pPFAccountList
        restPPFAccountMockMvc.perform(get("/api/p-pf-accounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pPFAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].balance").value(hasItem(DEFAULT_BALANCE.intValue())));
    }

    @Test
    @Transactional
    public void getPPFAccount() throws Exception {
        // Initialize the database
        pPFAccountRepository.saveAndFlush(pPFAccount);

        // Get the pPFAccount
        restPPFAccountMockMvc.perform(get("/api/p-pf-accounts/{id}", pPFAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pPFAccount.getId().intValue()))
            .andExpect(jsonPath("$.accountNumber").value(DEFAULT_ACCOUNT_NUMBER.toString()))
            .andExpect(jsonPath("$.balance").value(DEFAULT_BALANCE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPPFAccount() throws Exception {
        // Get the pPFAccount
        restPPFAccountMockMvc.perform(get("/api/p-pf-accounts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePPFAccount() throws Exception {
        // Initialize the database
        pPFAccountRepository.saveAndFlush(pPFAccount);
        int databaseSizeBeforeUpdate = pPFAccountRepository.findAll().size();

        // Update the pPFAccount
        PPFAccount updatedPPFAccount = pPFAccountRepository.findOne(pPFAccount.getId());
        updatedPPFAccount
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .balance(UPDATED_BALANCE);
        PPFAccountDTO pPFAccountDTO = pPFAccountMapper.toDto(updatedPPFAccount);

        restPPFAccountMockMvc.perform(put("/api/p-pf-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pPFAccountDTO)))
            .andExpect(status().isOk());

        // Validate the PPFAccount in the database
        List<PPFAccount> pPFAccountList = pPFAccountRepository.findAll();
        assertThat(pPFAccountList).hasSize(databaseSizeBeforeUpdate);
        PPFAccount testPPFAccount = pPFAccountList.get(pPFAccountList.size() - 1);
        assertThat(testPPFAccount.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testPPFAccount.getBalance()).isEqualTo(UPDATED_BALANCE);
    }

    @Test
    @Transactional
    public void updateNonExistingPPFAccount() throws Exception {
        int databaseSizeBeforeUpdate = pPFAccountRepository.findAll().size();

        // Create the PPFAccount
        PPFAccountDTO pPFAccountDTO = pPFAccountMapper.toDto(pPFAccount);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPPFAccountMockMvc.perform(put("/api/p-pf-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pPFAccountDTO)))
            .andExpect(status().isCreated());

        // Validate the PPFAccount in the database
        List<PPFAccount> pPFAccountList = pPFAccountRepository.findAll();
        assertThat(pPFAccountList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePPFAccount() throws Exception {
        // Initialize the database
        pPFAccountRepository.saveAndFlush(pPFAccount);
        int databaseSizeBeforeDelete = pPFAccountRepository.findAll().size();

        // Get the pPFAccount
        restPPFAccountMockMvc.perform(delete("/api/p-pf-accounts/{id}", pPFAccount.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PPFAccount> pPFAccountList = pPFAccountRepository.findAll();
        assertThat(pPFAccountList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PPFAccount.class);
        PPFAccount pPFAccount1 = new PPFAccount();
        pPFAccount1.setId(1L);
        PPFAccount pPFAccount2 = new PPFAccount();
        pPFAccount2.setId(pPFAccount1.getId());
        assertThat(pPFAccount1).isEqualTo(pPFAccount2);
        pPFAccount2.setId(2L);
        assertThat(pPFAccount1).isNotEqualTo(pPFAccount2);
        pPFAccount1.setId(null);
        assertThat(pPFAccount1).isNotEqualTo(pPFAccount2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PPFAccountDTO.class);
        PPFAccountDTO pPFAccountDTO1 = new PPFAccountDTO();
        pPFAccountDTO1.setId(1L);
        PPFAccountDTO pPFAccountDTO2 = new PPFAccountDTO();
        assertThat(pPFAccountDTO1).isNotEqualTo(pPFAccountDTO2);
        pPFAccountDTO2.setId(pPFAccountDTO1.getId());
        assertThat(pPFAccountDTO1).isEqualTo(pPFAccountDTO2);
        pPFAccountDTO2.setId(2L);
        assertThat(pPFAccountDTO1).isNotEqualTo(pPFAccountDTO2);
        pPFAccountDTO1.setId(null);
        assertThat(pPFAccountDTO1).isNotEqualTo(pPFAccountDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        // assertThat(pPFAccountMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(pPFAccountMapper.fromId(null)).isNull();
    }
}
