package com.anil.pfm.web.rest;

import com.anil.pfm.PfmApp;
import com.anil.pfm.mf.domain.MutualFund;
import com.anil.pfm.mf.repository.MutualFundRepository;
import com.anil.pfm.mf.service.MutualFundService;
import com.anil.pfm.mf.service.mapper.MutualFundMapper;
import com.anil.pfm.mf.web.rest.MutualFundResource;
import com.anil.pfm.service.dto.MutualFundDTO;
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
 * Test class for the MutualFundResource REST controller.
 *
 * @see MutualFundResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PfmApp.class)
public class MutualFundResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MANAGER = "AAAAAAAAAA";
    private static final String UPDATED_MANAGER = "BBBBBBBBBB";

    @Autowired
    private MutualFundRepository mutualFundRepository;

    @Autowired
    private MutualFundMapper mutualFundMapper;

    @Autowired
    private MutualFundService mutualFundService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMutualFundMockMvc;

    private MutualFund mutualFund;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MutualFundResource mutualFundResource = new MutualFundResource(mutualFundService);
        this.restMutualFundMockMvc = MockMvcBuilders.standaloneSetup(mutualFundResource)
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
    public static MutualFund createEntity(EntityManager em) {
        MutualFund mutualFund = new MutualFund()
            .name(DEFAULT_NAME)
            .manager(DEFAULT_MANAGER);
        return mutualFund;
    }

    @Before
    public void initTest() {
        mutualFund = createEntity(em);
    }

    @Test
    @Transactional
    public void createMutualFund() throws Exception {
        int databaseSizeBeforeCreate = mutualFundRepository.findAll().size();

        // Create the MutualFund
        MutualFundDTO mutualFundDTO = mutualFundMapper.toDto(mutualFund);
        restMutualFundMockMvc.perform(post("/api/mutual-funds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mutualFundDTO)))
            .andExpect(status().isCreated());

        // Validate the MutualFund in the database
        List<MutualFund> mutualFundList = mutualFundRepository.findAll();
        assertThat(mutualFundList).hasSize(databaseSizeBeforeCreate + 1);
        MutualFund testMutualFund = mutualFundList.get(mutualFundList.size() - 1);
        assertThat(testMutualFund.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMutualFund.getManager()).isEqualTo(DEFAULT_MANAGER);
    }

    @Test
    @Transactional
    public void createMutualFundWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mutualFundRepository.findAll().size();

        // Create the MutualFund with an existing ID
        mutualFund.setId(1L);
        MutualFundDTO mutualFundDTO = mutualFundMapper.toDto(mutualFund);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMutualFundMockMvc.perform(post("/api/mutual-funds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mutualFundDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MutualFund in the database
        List<MutualFund> mutualFundList = mutualFundRepository.findAll();
        assertThat(mutualFundList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = mutualFundRepository.findAll().size();
        // set the field null
        mutualFund.setName(null);

        // Create the MutualFund, which fails.
        MutualFundDTO mutualFundDTO = mutualFundMapper.toDto(mutualFund);

        restMutualFundMockMvc.perform(post("/api/mutual-funds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mutualFundDTO)))
            .andExpect(status().isBadRequest());

        List<MutualFund> mutualFundList = mutualFundRepository.findAll();
        assertThat(mutualFundList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMutualFunds() throws Exception {
        // Initialize the database
        mutualFundRepository.saveAndFlush(mutualFund);

        // Get all the mutualFundList
        restMutualFundMockMvc.perform(get("/api/mutual-funds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mutualFund.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].manager").value(hasItem(DEFAULT_MANAGER.toString())));
    }

    @Test
    @Transactional
    public void getMutualFund() throws Exception {
        // Initialize the database
        mutualFundRepository.saveAndFlush(mutualFund);

        // Get the mutualFund
        restMutualFundMockMvc.perform(get("/api/mutual-funds/{id}", mutualFund.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mutualFund.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.manager").value(DEFAULT_MANAGER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMutualFund() throws Exception {
        // Get the mutualFund
        restMutualFundMockMvc.perform(get("/api/mutual-funds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMutualFund() throws Exception {
        // Initialize the database
        mutualFundRepository.saveAndFlush(mutualFund);
        int databaseSizeBeforeUpdate = mutualFundRepository.findAll().size();

        // Update the mutualFund
        MutualFund updatedMutualFund = mutualFundRepository.findOne(mutualFund.getId());
        updatedMutualFund
            .name(UPDATED_NAME)
            .manager(UPDATED_MANAGER);
        MutualFundDTO mutualFundDTO = mutualFundMapper.toDto(updatedMutualFund);

        restMutualFundMockMvc.perform(put("/api/mutual-funds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mutualFundDTO)))
            .andExpect(status().isOk());

        // Validate the MutualFund in the database
        List<MutualFund> mutualFundList = mutualFundRepository.findAll();
        assertThat(mutualFundList).hasSize(databaseSizeBeforeUpdate);
        MutualFund testMutualFund = mutualFundList.get(mutualFundList.size() - 1);
        assertThat(testMutualFund.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMutualFund.getManager()).isEqualTo(UPDATED_MANAGER);
    }

    @Test
    @Transactional
    public void updateNonExistingMutualFund() throws Exception {
        int databaseSizeBeforeUpdate = mutualFundRepository.findAll().size();

        // Create the MutualFund
        MutualFundDTO mutualFundDTO = mutualFundMapper.toDto(mutualFund);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMutualFundMockMvc.perform(put("/api/mutual-funds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mutualFundDTO)))
            .andExpect(status().isCreated());

        // Validate the MutualFund in the database
        List<MutualFund> mutualFundList = mutualFundRepository.findAll();
        assertThat(mutualFundList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMutualFund() throws Exception {
        // Initialize the database
        mutualFundRepository.saveAndFlush(mutualFund);
        int databaseSizeBeforeDelete = mutualFundRepository.findAll().size();

        // Get the mutualFund
        restMutualFundMockMvc.perform(delete("/api/mutual-funds/{id}", mutualFund.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MutualFund> mutualFundList = mutualFundRepository.findAll();
        assertThat(mutualFundList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MutualFund.class);
        MutualFund mutualFund1 = new MutualFund();
        mutualFund1.setId(1L);
        MutualFund mutualFund2 = new MutualFund();
        mutualFund2.setId(mutualFund1.getId());
        assertThat(mutualFund1).isEqualTo(mutualFund2);
        mutualFund2.setId(2L);
        assertThat(mutualFund1).isNotEqualTo(mutualFund2);
        mutualFund1.setId(null);
        assertThat(mutualFund1).isNotEqualTo(mutualFund2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MutualFundDTO.class);
        MutualFundDTO mutualFundDTO1 = new MutualFundDTO();
        mutualFundDTO1.setId(1L);
        MutualFundDTO mutualFundDTO2 = new MutualFundDTO();
        assertThat(mutualFundDTO1).isNotEqualTo(mutualFundDTO2);
        mutualFundDTO2.setId(mutualFundDTO1.getId());
        assertThat(mutualFundDTO1).isEqualTo(mutualFundDTO2);
        mutualFundDTO2.setId(2L);
        assertThat(mutualFundDTO1).isNotEqualTo(mutualFundDTO2);
        mutualFundDTO1.setId(null);
        assertThat(mutualFundDTO1).isNotEqualTo(mutualFundDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(mutualFundMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(mutualFundMapper.fromId(null)).isNull();
    }
}
