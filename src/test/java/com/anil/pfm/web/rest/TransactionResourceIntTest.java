package com.anil.pfm.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.persistence.EntityManager;

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

import com.anil.pfm.PfmApp;
import com.anil.pfm.domain.PPFAccount;
import com.anil.pfm.domain.PPFTransaction;
import com.anil.pfm.domain.TransactionType;
import com.anil.pfm.repository.MyAccountRepository;
import com.anil.pfm.repository.PPFAccountRepository;
import com.anil.pfm.repository.PPFTransactionRepository;
import com.anil.pfm.repository.TransactionRepository;
import com.anil.pfm.repository.TransactionTypeRepository;
import com.anil.pfm.service.mapper.TransactionMapper;
import com.anil.pfm.tx.domain.MyAccount;
import com.anil.pfm.tx.domain.Transaction;
import com.anil.pfm.tx.service.TransactionService;
import com.anil.pfm.tx.service.dto.CreateTransactionVM;
import com.anil.pfm.tx.service.dto.TransactionDTO;
import com.anil.pfm.tx.web.rest.TransactionResource;
import com.anil.pfm.web.rest.errors.ExceptionTranslator;

/**
 * Test class for the TransactionResource REST controller.
 *
 * @see TransactionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PfmApp.class)
public class TransactionResourceIntTest {

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    private static final String DEFAULT_DESC = "AAAAAAAAAA";
    private static final String UPDATED_DESC = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private TransactionTypeRepository txTypeRepository;

    @Autowired
    private TransactionRepository transactionRepository;
    
    @Autowired
    private PPFAccountRepository ppfAccRepository;
    
    @Autowired
    private PPFTransactionRepository ppfTxRepository;
    
    @Autowired
    private MyAccountRepository accountRepository;

    @Autowired
    private TransactionMapper transactionMapper;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTransactionMockMvc;

    private Transaction transaction;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TransactionResource transactionResource = new TransactionResource(transactionService);
        this.restTransactionMockMvc = MockMvcBuilders.standaloneSetup(transactionResource)
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
    public static Transaction createEntity(EntityManager em) {
        Transaction transaction = new Transaction()
            .amount(DEFAULT_AMOUNT)
            .desc(DEFAULT_DESC)
            .date(DEFAULT_DATE);
        
        transaction.setOpeningBalance(new BigDecimal(0));
    	transaction.setClosingBalance(transaction.getAmount());
        
        return transaction;
    }

    @Before
    public void initTest() {
        transaction = createEntity(em);
    }

    @Test
    @Transactional
    public void createExpenseTransaction() throws Exception {
        int databaseSizeBeforeCreate = transactionRepository.findAll().size();

        MyAccount account = MyAccountResourceIntTest.createEntity(em);
    	BigDecimal accountBalanceBefore = BigDecimal.valueOf(5555.55);
		account.setBalance(accountBalanceBefore);
    	account = accountRepository.saveAndFlush(account);
        
        // Create the Transaction
        CreateTransactionVM vm = new CreateTransactionVM();
        vm.setAccountId(account.getId());
        BigDecimal txAmount = BigDecimal.valueOf(54.76);
		vm.setAmount(txAmount);
		vm.setDate(DEFAULT_DATE);
		vm.setDesc(DEFAULT_DESC);
		vm.setTxTypeId(TransactionType.EXPENSE);
        
        restTransactionMockMvc.perform(post("/api/transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vm)))
            .andExpect(status().isCreated());

        // Validate the Transaction in the database
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeCreate + 1);
        Transaction testTransaction = transactionList.get(transactionList.size() - 1);
        assertThat(testTransaction.getAmount()).isEqualTo(txAmount);
        assertThat(testTransaction.getDesc()).isEqualTo(DEFAULT_DESC);
        assertThat(testTransaction.getDate()).isEqualTo(DEFAULT_DATE);
        
        // assert account is deducted
        account = accountRepository.findOne(account.getId());
        BigDecimal expectedBalance = accountBalanceBefore.subtract(txAmount);
        assertEquals(expectedBalance, account.getBalance());
    }
    
    @Test
    @Transactional
    public void createTransferTransaction() throws Exception {
        int databaseSizeBeforeCreate = transactionRepository.findAll().size();

        MyAccount toAccount = MyAccountResourceIntTest.createEntity(em);
    	BigDecimal toAccountBalanceBefore = BigDecimal.valueOf(8465.74);
		toAccount.setBalance(toAccountBalanceBefore);
    	toAccount = accountRepository.saveAndFlush(toAccount);
        
        MyAccount account = MyAccountResourceIntTest.createEntity(em);
    	BigDecimal accountBalanceBefore = BigDecimal.valueOf(5555.55);
		account.setBalance(accountBalanceBefore);
    	account = accountRepository.saveAndFlush(account);
        
        // Create the Transaction
        CreateTransactionVM vm = new CreateTransactionVM();
        vm.setAccountId(account.getId());
        BigDecimal txAmount = BigDecimal.valueOf(54.76);
		vm.setAmount(txAmount);
		vm.setDate(DEFAULT_DATE);
		vm.setDesc(DEFAULT_DESC);
		vm.setTxTypeId(TransactionType.TRANSFER);
		vm.setTransferAccountId(toAccount.getId());
        
        restTransactionMockMvc.perform(post("/api/transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vm)))
            .andExpect(status().isCreated());

        // Validate the Transaction in the database
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeCreate + 1);
        Transaction testTransaction = transactionList.get(transactionList.size() - 1);
        assertThat(testTransaction.getAmount()).isEqualTo(txAmount);
        assertThat(testTransaction.getDesc()).isEqualTo(DEFAULT_DESC);
        assertThat(testTransaction.getDate()).isEqualTo(DEFAULT_DATE);
        
        // assert account is deducted
        account = accountRepository.findOne(account.getId());
        BigDecimal expectedBalance = accountBalanceBefore.subtract(txAmount);
        assertEquals(expectedBalance, account.getBalance());
        
        // assert to account is credited
        toAccount = accountRepository.findOne(toAccount.getId());
        expectedBalance = toAccountBalanceBefore.add(txAmount);
        assertEquals(expectedBalance, toAccount.getBalance());
    }
    
    // TODO createInvestmentTransaction()
    
    @Test
    @Transactional
    public void createIncomeTransaction() throws Exception {
        int databaseSizeBeforeCreate = transactionRepository.findAll().size();

        MyAccount account = MyAccountResourceIntTest.createEntity(em);
    	BigDecimal accountBalanceBefore = BigDecimal.valueOf(5555.55);
		account.setBalance(accountBalanceBefore);
    	account = accountRepository.saveAndFlush(account);
        
        // Create the Transaction
        CreateTransactionVM vm = new CreateTransactionVM();
        vm.setAccountId(account.getId());
        BigDecimal txAmount = BigDecimal.valueOf(54.76);
		vm.setAmount(txAmount);
		vm.setDate(DEFAULT_DATE);
		vm.setDesc(DEFAULT_DESC);
		vm.setTxTypeId(TransactionType.INCOME);
        
        restTransactionMockMvc.perform(post("/api/transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vm)))
            .andExpect(status().isCreated());

        // Validate the Transaction in the database
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeCreate + 1);
        Transaction testTransaction = transactionList.get(transactionList.size() - 1);
        assertThat(testTransaction.getAmount()).isEqualTo(txAmount);
        assertThat(testTransaction.getDesc()).isEqualTo(DEFAULT_DESC);
        assertThat(testTransaction.getDate()).isEqualTo(DEFAULT_DATE);
        
        // assert account is credited
        account = accountRepository.findOne(account.getId());
        BigDecimal expectedBalance = accountBalanceBefore.add(txAmount);
        assertEquals(expectedBalance, account.getBalance());
    }

    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionRepository.findAll().size();
        // set the field null
        transaction.setAmount(null);

        // Create the Transaction, which fails.
        TransactionDTO transactionDTO = transactionMapper.toDto(transaction);

        restTransactionMockMvc.perform(post("/api/transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionDTO)))
            .andExpect(status().isBadRequest());

        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionRepository.findAll().size();
        // set the field null
        transaction.setDesc(null);

        // Create the Transaction, which fails.
        TransactionDTO transactionDTO = transactionMapper.toDto(transaction);

        restTransactionMockMvc.perform(post("/api/transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionDTO)))
            .andExpect(status().isBadRequest());

        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionRepository.findAll().size();
        // set the field null
        transaction.setDate(null);

        // Create the Transaction, which fails.
        TransactionDTO transactionDTO = transactionMapper.toDto(transaction);

        restTransactionMockMvc.perform(post("/api/transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionDTO)))
            .andExpect(status().isBadRequest());

        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeTest);
    }


    @Test
    @Transactional
    public void getAllTransactions() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList
        restTransactionMockMvc.perform(get("/api/transactions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transaction.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].desc").value(hasItem(DEFAULT_DESC.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }

    @Test
    @Transactional
    public void getTransaction() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get the transaction
        restTransactionMockMvc.perform(get("/api/transactions/{id}", transaction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(transaction.getId().intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.desc").value(DEFAULT_DESC.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTransaction() throws Exception {
        // Get the transaction
        restTransactionMockMvc.perform(get("/api/transactions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTransaction() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);
        int databaseSizeBeforeUpdate = transactionRepository.findAll().size();

        // Update the transaction
        Transaction updatedTransaction = transactionRepository.findOne(transaction.getId());
        updatedTransaction
            .amount(UPDATED_AMOUNT)
            .desc(UPDATED_DESC)
            .date(UPDATED_DATE);
        TransactionDTO transactionDTO = transactionMapper.toDto(updatedTransaction);

        restTransactionMockMvc.perform(put("/api/transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionDTO)))
            .andExpect(status().isOk());

        // Validate the Transaction in the database
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeUpdate);
        Transaction testTransaction = transactionList.get(transactionList.size() - 1);
        assertThat(testTransaction.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testTransaction.getDesc()).isEqualTo(UPDATED_DESC);
        assertThat(testTransaction.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingTransaction() throws Exception {
        int databaseSizeBeforeUpdate = transactionRepository.findAll().size();

        // Create the Transaction
        TransactionDTO transactionDTO = transactionMapper.toDto(transaction);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTransactionMockMvc.perform(put("/api/transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Transaction in the database
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeUpdate);
    }

    
    @Test
    @Transactional
    public void deleteIncomeTransaction() throws Exception {
        // Initialize the database
    	
    	MyAccount account = MyAccountResourceIntTest.createEntity(em);
    	BigDecimal accountBalanceBeforeDelete = BigDecimal.valueOf(5555.55);
		account.setBalance(accountBalanceBeforeDelete);
    	account = accountRepository.saveAndFlush(account);
    	
    	transaction.setAccount(account);
    	BigDecimal txAmount = BigDecimal.valueOf(44.44);
		transaction.setAmount(txAmount);
		transaction.setTxType(txTypeRepository.findOne(TransactionType.INCOME));
    	
        transactionRepository.saveAndFlush(transaction);
        int databaseSizeBeforeDelete = transactionRepository.findAll().size();

        // Get the transaction
        restTransactionMockMvc.perform(delete("/api/transactions/{id}", transaction.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeDelete - 1);
        
        // validate account is deducted
        account = accountRepository.findOne(account.getId());
        BigDecimal expectedBalance = accountBalanceBeforeDelete.subtract(txAmount);
        assertEquals(expectedBalance, account.getBalance());
    }
    
    @Test
    @Transactional
    public void deleteTransferTransaction() throws Exception {
        // Initialize the database
    	
    	MyAccount account = MyAccountResourceIntTest.createEntity(em);
    	BigDecimal accountBalanceBeforeDelete = BigDecimal.valueOf(5555.55);
		account.setBalance(accountBalanceBeforeDelete);
    	account = accountRepository.saveAndFlush(account);
    	
    	MyAccount toAccount = MyAccountResourceIntTest.createEntity(em);
    	BigDecimal toAccountBalanceBeforeDelete = BigDecimal.valueOf(3333.33);
		toAccount.setBalance(toAccountBalanceBeforeDelete);
    	toAccount = accountRepository.saveAndFlush(toAccount);
    	
    	transaction.setAccount(account);
    	transaction.setTransferAccount(toAccount);
    	
    	BigDecimal txAmount = BigDecimal.valueOf(44.44);
		transaction.setAmount(txAmount);
		transaction.setTxType(txTypeRepository.findOne(TransactionType.TRANSFER));
    	
        transactionRepository.saveAndFlush(transaction);
        int databaseSizeBeforeDelete = transactionRepository.findAll().size();

        // Get the transaction
        restTransactionMockMvc.perform(delete("/api/transactions/{id}", transaction.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeDelete - 1);
        
        // validate account is refunded
        account = accountRepository.findOne(account.getId());
        BigDecimal expectedBalance = accountBalanceBeforeDelete.add(txAmount);
        assertEquals(expectedBalance, account.getBalance());
        
        // validate toAccount is deducted
        toAccount = accountRepository.findOne(toAccount.getId());
        expectedBalance = toAccountBalanceBeforeDelete.subtract(txAmount);
        assertEquals(expectedBalance, toAccount.getBalance());
    }
    
    // TODO deleteInvestmentTransaction
    @Test
    @Transactional
    public void deleteInvestmentTransaction() throws Exception {
        // Initialize the database
    	
    	// create  my account
    	MyAccount account = MyAccountResourceIntTest.createEntity(em);
    	BigDecimal accountBalanceBeforeDelete = BigDecimal.valueOf(5555.55);
		account.setBalance(accountBalanceBeforeDelete);
    	account = accountRepository.saveAndFlush(account);
    	
    	// create tx
    	transaction.setAccount(account);
    	BigDecimal txAmount = BigDecimal.valueOf(44.44);
		transaction.setAmount(txAmount);
		transaction.setTxType(txTypeRepository.findOne(TransactionType.INVESTMENT));
		transactionRepository.saveAndFlush(transaction);
		
		// create  ppf account
		PPFAccount ppfAcc = PPFAccountResourceIntTest.createEntity(em);
        BigDecimal ppfBalanceBefore = BigDecimal.valueOf(36436.54);
		ppfAcc.setBalance(ppfBalanceBefore);
		ppfAcc = ppfAccRepository.saveAndFlush(ppfAcc);
		
		// create ppf tx
		PPFTransaction ppfTx = PPFTransactionResourceIntTest.createEntity(em);
		ppfTx.setAccount(ppfAcc);
		ppfTx.setAmount(txAmount);
		ppfTx.setTransaction(transaction);
		ppfTx = ppfTxRepository.saveAndFlush(ppfTx);
		
        
        int databaseSizeBeforeDelete = transactionRepository.findAll().size();

        // Get the transaction
        restTransactionMockMvc.perform(delete("/api/transactions/{id}", transaction.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isInternalServerError());

        // Validate tx is not deleted
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeDelete);
        
        // validate account balance is not changed
        account = accountRepository.findOne(account.getId());
        assertEquals(accountBalanceBeforeDelete, account.getBalance());
    }
    
    @Test
    @Transactional
    public void deleteExpenseTransaction() throws Exception {
        // Initialize the database
    	
    	MyAccount account = MyAccountResourceIntTest.createEntity(em);
    	BigDecimal accountBalanceBeforeDelete = BigDecimal.valueOf(5555.55);
		account.setBalance(accountBalanceBeforeDelete);
    	account = accountRepository.saveAndFlush(account);
    	
    	transaction.setAccount(account);
    	BigDecimal txAmount = BigDecimal.valueOf(44.44);
		transaction.setAmount(txAmount);
		transaction.setTxType(txTypeRepository.findOne(TransactionType.EXPENSE));
    	
        transactionRepository.saveAndFlush(transaction);
        int databaseSizeBeforeDelete = transactionRepository.findAll().size();

        // Get the transaction
        restTransactionMockMvc.perform(delete("/api/transactions/{id}", transaction.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeDelete - 1);
        
        // validate account is refunded
        account = accountRepository.findOne(account.getId());
        BigDecimal expectedBalance = accountBalanceBeforeDelete.add(txAmount);
        assertEquals(expectedBalance, account.getBalance());
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Transaction.class);
        Transaction transaction1 = new Transaction();
        transaction1.setId(1L);
        Transaction transaction2 = new Transaction();
        transaction2.setId(transaction1.getId());
        assertThat(transaction1).isEqualTo(transaction2);
        transaction2.setId(2L);
        assertThat(transaction1).isNotEqualTo(transaction2);
        transaction1.setId(null);
        assertThat(transaction1).isNotEqualTo(transaction2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransactionDTO.class);
        TransactionDTO transactionDTO1 = new TransactionDTO();
        transactionDTO1.setId(1L);
        TransactionDTO transactionDTO2 = new TransactionDTO();
        assertThat(transactionDTO1).isNotEqualTo(transactionDTO2);
        transactionDTO2.setId(transactionDTO1.getId());
        assertThat(transactionDTO1).isEqualTo(transactionDTO2);
        transactionDTO2.setId(2L);
        assertThat(transactionDTO1).isNotEqualTo(transactionDTO2);
        transactionDTO1.setId(null);
        assertThat(transactionDTO1).isNotEqualTo(transactionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        // assertThat(transactionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(transactionMapper.fromId(null)).isNull();
    }
}
