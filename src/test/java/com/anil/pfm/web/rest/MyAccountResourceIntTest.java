package com.anil.pfm.web.rest;

import com.anil.pfm.PfmApp;
import com.anil.pfm.repository.MyAccountRepository;
import com.anil.pfm.service.MyAccountService;
import com.anil.pfm.service.dto.MyAccountDTO;
import com.anil.pfm.service.mapper.MyAccountMapper;
import com.anil.pfm.tx.domain.MyAccount;
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
 * Test class for the MyAccountResource REST controller.
 *
 * @see MyAccountResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PfmApp.class)
public class MyAccountResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_BALANCE = new BigDecimal(1);
    private static final BigDecimal UPDATED_BALANCE = new BigDecimal(2);

    @Autowired
    private MyAccountRepository myAccountRepository;

    @Autowired
    private MyAccountMapper myAccountMapper;

    @Autowired
    private MyAccountService myAccountService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMyAccountMockMvc;

    private MyAccount myAccount;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MyAccountResource myAccountResource = new MyAccountResource(myAccountService);
        this.restMyAccountMockMvc = MockMvcBuilders.standaloneSetup(myAccountResource)
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
    public static MyAccount createEntity(EntityManager em) {
        MyAccount myAccount = new MyAccount()
            .name(DEFAULT_NAME)
            .balance(DEFAULT_BALANCE);
        return myAccount;
    }

    @Before
    public void initTest() {
        myAccount = createEntity(em);
    }

    @Test
    @Transactional
    public void createMyAccount() throws Exception {
        int databaseSizeBeforeCreate = myAccountRepository.findAll().size();

        // Create the MyAccount
        MyAccountDTO myAccountDTO = myAccountMapper.toDto(myAccount);
        restMyAccountMockMvc.perform(post("/api/my-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(myAccountDTO)))
            .andExpect(status().isCreated());

        // Validate the MyAccount in the database
        List<MyAccount> myAccountList = myAccountRepository.findAll();
        assertThat(myAccountList).hasSize(databaseSizeBeforeCreate + 1);
        MyAccount testMyAccount = myAccountList.get(myAccountList.size() - 1);
        assertThat(testMyAccount.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMyAccount.getBalance()).isEqualTo(DEFAULT_BALANCE);
    }

    @Test
    @Transactional
    public void createMyAccountWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = myAccountRepository.findAll().size();

        // Create the MyAccount with an existing ID
        myAccount.setId(1L);
        MyAccountDTO myAccountDTO = myAccountMapper.toDto(myAccount);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMyAccountMockMvc.perform(post("/api/my-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(myAccountDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MyAccount in the database
        List<MyAccount> myAccountList = myAccountRepository.findAll();
        assertThat(myAccountList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = myAccountRepository.findAll().size();
        // set the field null
        myAccount.setName(null);

        // Create the MyAccount, which fails.
        MyAccountDTO myAccountDTO = myAccountMapper.toDto(myAccount);

        restMyAccountMockMvc.perform(post("/api/my-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(myAccountDTO)))
            .andExpect(status().isBadRequest());

        List<MyAccount> myAccountList = myAccountRepository.findAll();
        assertThat(myAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMyAccounts() throws Exception {
        // Initialize the database
        myAccountRepository.saveAndFlush(myAccount);

        // Get all the myAccountList
        restMyAccountMockMvc.perform(get("/api/my-accounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(myAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].balance").value(hasItem(DEFAULT_BALANCE.intValue())));
    }

    @Test
    @Transactional
    public void getMyAccount() throws Exception {
        // Initialize the database
        myAccountRepository.saveAndFlush(myAccount);

        // Get the myAccount
        restMyAccountMockMvc.perform(get("/api/my-accounts/{id}", myAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(myAccount.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.balance").value(DEFAULT_BALANCE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMyAccount() throws Exception {
        // Get the myAccount
        restMyAccountMockMvc.perform(get("/api/my-accounts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMyAccount() throws Exception {
        // Initialize the database
        myAccountRepository.saveAndFlush(myAccount);
        int databaseSizeBeforeUpdate = myAccountRepository.findAll().size();

        // Update the myAccount
        MyAccount updatedMyAccount = myAccountRepository.findOne(myAccount.getId());
        updatedMyAccount
            .name(UPDATED_NAME)
            .balance(UPDATED_BALANCE);
        MyAccountDTO myAccountDTO = myAccountMapper.toDto(updatedMyAccount);

        restMyAccountMockMvc.perform(put("/api/my-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(myAccountDTO)))
            .andExpect(status().isOk());

        // Validate the MyAccount in the database
        List<MyAccount> myAccountList = myAccountRepository.findAll();
        assertThat(myAccountList).hasSize(databaseSizeBeforeUpdate);
        MyAccount testMyAccount = myAccountList.get(myAccountList.size() - 1);
        assertThat(testMyAccount.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMyAccount.getBalance()).isEqualTo(UPDATED_BALANCE);
    }

    @Test
    @Transactional
    public void updateNonExistingMyAccount() throws Exception {
        int databaseSizeBeforeUpdate = myAccountRepository.findAll().size();

        // Create the MyAccount
        MyAccountDTO myAccountDTO = myAccountMapper.toDto(myAccount);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMyAccountMockMvc.perform(put("/api/my-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(myAccountDTO)))
            .andExpect(status().isCreated());

        // Validate the MyAccount in the database
        List<MyAccount> myAccountList = myAccountRepository.findAll();
        assertThat(myAccountList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMyAccount() throws Exception {
        // Initialize the database
        myAccountRepository.saveAndFlush(myAccount);
        int databaseSizeBeforeDelete = myAccountRepository.findAll().size();

        // Get the myAccount
        restMyAccountMockMvc.perform(delete("/api/my-accounts/{id}", myAccount.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MyAccount> myAccountList = myAccountRepository.findAll();
        assertThat(myAccountList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MyAccount.class);
        MyAccount myAccount1 = new MyAccount();
        myAccount1.setId(1L);
        MyAccount myAccount2 = new MyAccount();
        myAccount2.setId(myAccount1.getId());
        assertThat(myAccount1).isEqualTo(myAccount2);
        myAccount2.setId(2L);
        assertThat(myAccount1).isNotEqualTo(myAccount2);
        myAccount1.setId(null);
        assertThat(myAccount1).isNotEqualTo(myAccount2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MyAccountDTO.class);
        MyAccountDTO myAccountDTO1 = new MyAccountDTO();
        myAccountDTO1.setId(1L);
        MyAccountDTO myAccountDTO2 = new MyAccountDTO();
        assertThat(myAccountDTO1).isNotEqualTo(myAccountDTO2);
        myAccountDTO2.setId(myAccountDTO1.getId());
        assertThat(myAccountDTO1).isEqualTo(myAccountDTO2);
        myAccountDTO2.setId(2L);
        assertThat(myAccountDTO1).isNotEqualTo(myAccountDTO2);
        myAccountDTO1.setId(null);
        assertThat(myAccountDTO1).isNotEqualTo(myAccountDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(myAccountMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(myAccountMapper.fromId(null)).isNull();
    }
}
