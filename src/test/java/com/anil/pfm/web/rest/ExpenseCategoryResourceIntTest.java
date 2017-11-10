package com.anil.pfm.web.rest;

import com.anil.pfm.PfmApp;

import com.anil.pfm.domain.ExpenseCategory;
import com.anil.pfm.repository.ExpenseCategoryRepository;
import com.anil.pfm.service.ExpenseCategoryService;
import com.anil.pfm.service.dto.ExpenseCategoryDTO;
import com.anil.pfm.service.mapper.ExpenseCategoryMapper;
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
 * Test class for the ExpenseCategoryResource REST controller.
 *
 * @see ExpenseCategoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PfmApp.class)
public class ExpenseCategoryResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private ExpenseCategoryRepository expenseCategoryRepository;

    @Autowired
    private ExpenseCategoryMapper expenseCategoryMapper;

    @Autowired
    private ExpenseCategoryService expenseCategoryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restExpenseCategoryMockMvc;

    private ExpenseCategory expenseCategory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ExpenseCategoryResource expenseCategoryResource = new ExpenseCategoryResource(expenseCategoryService);
        this.restExpenseCategoryMockMvc = MockMvcBuilders.standaloneSetup(expenseCategoryResource)
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
    public static ExpenseCategory createEntity(EntityManager em) {
        ExpenseCategory expenseCategory = new ExpenseCategory()
            .name(DEFAULT_NAME);
        return expenseCategory;
    }

    @Before
    public void initTest() {
        expenseCategory = createEntity(em);
    }

    @Test
    @Transactional
    public void createExpenseCategory() throws Exception {
        int databaseSizeBeforeCreate = expenseCategoryRepository.findAll().size();

        // Create the ExpenseCategory
        ExpenseCategoryDTO expenseCategoryDTO = expenseCategoryMapper.toDto(expenseCategory);
        restExpenseCategoryMockMvc.perform(post("/api/expense-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(expenseCategoryDTO)))
            .andExpect(status().isCreated());

        // Validate the ExpenseCategory in the database
        List<ExpenseCategory> expenseCategoryList = expenseCategoryRepository.findAll();
        assertThat(expenseCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        ExpenseCategory testExpenseCategory = expenseCategoryList.get(expenseCategoryList.size() - 1);
        assertThat(testExpenseCategory.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createExpenseCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = expenseCategoryRepository.findAll().size();

        // Create the ExpenseCategory with an existing ID
        expenseCategory.setId(1L);
        ExpenseCategoryDTO expenseCategoryDTO = expenseCategoryMapper.toDto(expenseCategory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExpenseCategoryMockMvc.perform(post("/api/expense-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(expenseCategoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ExpenseCategory in the database
        List<ExpenseCategory> expenseCategoryList = expenseCategoryRepository.findAll();
        assertThat(expenseCategoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = expenseCategoryRepository.findAll().size();
        // set the field null
        expenseCategory.setName(null);

        // Create the ExpenseCategory, which fails.
        ExpenseCategoryDTO expenseCategoryDTO = expenseCategoryMapper.toDto(expenseCategory);

        restExpenseCategoryMockMvc.perform(post("/api/expense-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(expenseCategoryDTO)))
            .andExpect(status().isBadRequest());

        List<ExpenseCategory> expenseCategoryList = expenseCategoryRepository.findAll();
        assertThat(expenseCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllExpenseCategories() throws Exception {
        // Initialize the database
        expenseCategoryRepository.saveAndFlush(expenseCategory);

        // Get all the expenseCategoryList
        restExpenseCategoryMockMvc.perform(get("/api/expense-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(expenseCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getExpenseCategory() throws Exception {
        // Initialize the database
        expenseCategoryRepository.saveAndFlush(expenseCategory);

        // Get the expenseCategory
        restExpenseCategoryMockMvc.perform(get("/api/expense-categories/{id}", expenseCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(expenseCategory.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingExpenseCategory() throws Exception {
        // Get the expenseCategory
        restExpenseCategoryMockMvc.perform(get("/api/expense-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExpenseCategory() throws Exception {
        // Initialize the database
        expenseCategoryRepository.saveAndFlush(expenseCategory);
        int databaseSizeBeforeUpdate = expenseCategoryRepository.findAll().size();

        // Update the expenseCategory
        ExpenseCategory updatedExpenseCategory = expenseCategoryRepository.findOne(expenseCategory.getId());
        updatedExpenseCategory
            .name(UPDATED_NAME);
        ExpenseCategoryDTO expenseCategoryDTO = expenseCategoryMapper.toDto(updatedExpenseCategory);

        restExpenseCategoryMockMvc.perform(put("/api/expense-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(expenseCategoryDTO)))
            .andExpect(status().isOk());

        // Validate the ExpenseCategory in the database
        List<ExpenseCategory> expenseCategoryList = expenseCategoryRepository.findAll();
        assertThat(expenseCategoryList).hasSize(databaseSizeBeforeUpdate);
        ExpenseCategory testExpenseCategory = expenseCategoryList.get(expenseCategoryList.size() - 1);
        assertThat(testExpenseCategory.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingExpenseCategory() throws Exception {
        int databaseSizeBeforeUpdate = expenseCategoryRepository.findAll().size();

        // Create the ExpenseCategory
        ExpenseCategoryDTO expenseCategoryDTO = expenseCategoryMapper.toDto(expenseCategory);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restExpenseCategoryMockMvc.perform(put("/api/expense-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(expenseCategoryDTO)))
            .andExpect(status().isCreated());

        // Validate the ExpenseCategory in the database
        List<ExpenseCategory> expenseCategoryList = expenseCategoryRepository.findAll();
        assertThat(expenseCategoryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteExpenseCategory() throws Exception {
        // Initialize the database
        expenseCategoryRepository.saveAndFlush(expenseCategory);
        int databaseSizeBeforeDelete = expenseCategoryRepository.findAll().size();

        // Get the expenseCategory
        restExpenseCategoryMockMvc.perform(delete("/api/expense-categories/{id}", expenseCategory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ExpenseCategory> expenseCategoryList = expenseCategoryRepository.findAll();
        assertThat(expenseCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExpenseCategory.class);
        ExpenseCategory expenseCategory1 = new ExpenseCategory();
        expenseCategory1.setId(1L);
        ExpenseCategory expenseCategory2 = new ExpenseCategory();
        expenseCategory2.setId(expenseCategory1.getId());
        assertThat(expenseCategory1).isEqualTo(expenseCategory2);
        expenseCategory2.setId(2L);
        assertThat(expenseCategory1).isNotEqualTo(expenseCategory2);
        expenseCategory1.setId(null);
        assertThat(expenseCategory1).isNotEqualTo(expenseCategory2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExpenseCategoryDTO.class);
        ExpenseCategoryDTO expenseCategoryDTO1 = new ExpenseCategoryDTO();
        expenseCategoryDTO1.setId(1L);
        ExpenseCategoryDTO expenseCategoryDTO2 = new ExpenseCategoryDTO();
        assertThat(expenseCategoryDTO1).isNotEqualTo(expenseCategoryDTO2);
        expenseCategoryDTO2.setId(expenseCategoryDTO1.getId());
        assertThat(expenseCategoryDTO1).isEqualTo(expenseCategoryDTO2);
        expenseCategoryDTO2.setId(2L);
        assertThat(expenseCategoryDTO1).isNotEqualTo(expenseCategoryDTO2);
        expenseCategoryDTO1.setId(null);
        assertThat(expenseCategoryDTO1).isNotEqualTo(expenseCategoryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(expenseCategoryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(expenseCategoryMapper.fromId(null)).isNull();
    }
}
