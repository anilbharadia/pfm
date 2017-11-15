package com.anil.pfm.web.rest;

import com.anil.pfm.PfmApp;

import com.anil.pfm.domain.LifeInsuranceCompany;
import com.anil.pfm.repository.LifeInsuranceCompanyRepository;
import com.anil.pfm.service.LifeInsuranceCompanyService;
import com.anil.pfm.service.dto.LifeInsuranceCompanyDTO;
import com.anil.pfm.service.mapper.LifeInsuranceCompanyMapper;
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
 * Test class for the LifeInsuranceCompanyResource REST controller.
 *
 * @see LifeInsuranceCompanyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PfmApp.class)
public class LifeInsuranceCompanyResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private LifeInsuranceCompanyRepository lifeInsuranceCompanyRepository;

    @Autowired
    private LifeInsuranceCompanyMapper lifeInsuranceCompanyMapper;

    @Autowired
    private LifeInsuranceCompanyService lifeInsuranceCompanyService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLifeInsuranceCompanyMockMvc;

    private LifeInsuranceCompany lifeInsuranceCompany;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LifeInsuranceCompanyResource lifeInsuranceCompanyResource = new LifeInsuranceCompanyResource(lifeInsuranceCompanyService);
        this.restLifeInsuranceCompanyMockMvc = MockMvcBuilders.standaloneSetup(lifeInsuranceCompanyResource)
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
    public static LifeInsuranceCompany createEntity(EntityManager em) {
        LifeInsuranceCompany lifeInsuranceCompany = new LifeInsuranceCompany()
            .name(DEFAULT_NAME);
        return lifeInsuranceCompany;
    }

    @Before
    public void initTest() {
        lifeInsuranceCompany = createEntity(em);
    }

    @Test
    @Transactional
    public void createLifeInsuranceCompany() throws Exception {
        int databaseSizeBeforeCreate = lifeInsuranceCompanyRepository.findAll().size();

        // Create the LifeInsuranceCompany
        LifeInsuranceCompanyDTO lifeInsuranceCompanyDTO = lifeInsuranceCompanyMapper.toDto(lifeInsuranceCompany);
        restLifeInsuranceCompanyMockMvc.perform(post("/api/life-insurance-companies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lifeInsuranceCompanyDTO)))
            .andExpect(status().isCreated());

        // Validate the LifeInsuranceCompany in the database
        List<LifeInsuranceCompany> lifeInsuranceCompanyList = lifeInsuranceCompanyRepository.findAll();
        assertThat(lifeInsuranceCompanyList).hasSize(databaseSizeBeforeCreate + 1);
        LifeInsuranceCompany testLifeInsuranceCompany = lifeInsuranceCompanyList.get(lifeInsuranceCompanyList.size() - 1);
        assertThat(testLifeInsuranceCompany.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createLifeInsuranceCompanyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lifeInsuranceCompanyRepository.findAll().size();

        // Create the LifeInsuranceCompany with an existing ID
        lifeInsuranceCompany.setId(1L);
        LifeInsuranceCompanyDTO lifeInsuranceCompanyDTO = lifeInsuranceCompanyMapper.toDto(lifeInsuranceCompany);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLifeInsuranceCompanyMockMvc.perform(post("/api/life-insurance-companies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lifeInsuranceCompanyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LifeInsuranceCompany in the database
        List<LifeInsuranceCompany> lifeInsuranceCompanyList = lifeInsuranceCompanyRepository.findAll();
        assertThat(lifeInsuranceCompanyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = lifeInsuranceCompanyRepository.findAll().size();
        // set the field null
        lifeInsuranceCompany.setName(null);

        // Create the LifeInsuranceCompany, which fails.
        LifeInsuranceCompanyDTO lifeInsuranceCompanyDTO = lifeInsuranceCompanyMapper.toDto(lifeInsuranceCompany);

        restLifeInsuranceCompanyMockMvc.perform(post("/api/life-insurance-companies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lifeInsuranceCompanyDTO)))
            .andExpect(status().isBadRequest());

        List<LifeInsuranceCompany> lifeInsuranceCompanyList = lifeInsuranceCompanyRepository.findAll();
        assertThat(lifeInsuranceCompanyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLifeInsuranceCompanies() throws Exception {
        // Initialize the database
        lifeInsuranceCompanyRepository.saveAndFlush(lifeInsuranceCompany);

        // Get all the lifeInsuranceCompanyList
        restLifeInsuranceCompanyMockMvc.perform(get("/api/life-insurance-companies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lifeInsuranceCompany.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getLifeInsuranceCompany() throws Exception {
        // Initialize the database
        lifeInsuranceCompanyRepository.saveAndFlush(lifeInsuranceCompany);

        // Get the lifeInsuranceCompany
        restLifeInsuranceCompanyMockMvc.perform(get("/api/life-insurance-companies/{id}", lifeInsuranceCompany.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(lifeInsuranceCompany.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLifeInsuranceCompany() throws Exception {
        // Get the lifeInsuranceCompany
        restLifeInsuranceCompanyMockMvc.perform(get("/api/life-insurance-companies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLifeInsuranceCompany() throws Exception {
        // Initialize the database
        lifeInsuranceCompanyRepository.saveAndFlush(lifeInsuranceCompany);
        int databaseSizeBeforeUpdate = lifeInsuranceCompanyRepository.findAll().size();

        // Update the lifeInsuranceCompany
        LifeInsuranceCompany updatedLifeInsuranceCompany = lifeInsuranceCompanyRepository.findOne(lifeInsuranceCompany.getId());
        updatedLifeInsuranceCompany
            .name(UPDATED_NAME);
        LifeInsuranceCompanyDTO lifeInsuranceCompanyDTO = lifeInsuranceCompanyMapper.toDto(updatedLifeInsuranceCompany);

        restLifeInsuranceCompanyMockMvc.perform(put("/api/life-insurance-companies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lifeInsuranceCompanyDTO)))
            .andExpect(status().isOk());

        // Validate the LifeInsuranceCompany in the database
        List<LifeInsuranceCompany> lifeInsuranceCompanyList = lifeInsuranceCompanyRepository.findAll();
        assertThat(lifeInsuranceCompanyList).hasSize(databaseSizeBeforeUpdate);
        LifeInsuranceCompany testLifeInsuranceCompany = lifeInsuranceCompanyList.get(lifeInsuranceCompanyList.size() - 1);
        assertThat(testLifeInsuranceCompany.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingLifeInsuranceCompany() throws Exception {
        int databaseSizeBeforeUpdate = lifeInsuranceCompanyRepository.findAll().size();

        // Create the LifeInsuranceCompany
        LifeInsuranceCompanyDTO lifeInsuranceCompanyDTO = lifeInsuranceCompanyMapper.toDto(lifeInsuranceCompany);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLifeInsuranceCompanyMockMvc.perform(put("/api/life-insurance-companies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lifeInsuranceCompanyDTO)))
            .andExpect(status().isCreated());

        // Validate the LifeInsuranceCompany in the database
        List<LifeInsuranceCompany> lifeInsuranceCompanyList = lifeInsuranceCompanyRepository.findAll();
        assertThat(lifeInsuranceCompanyList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLifeInsuranceCompany() throws Exception {
        // Initialize the database
        lifeInsuranceCompanyRepository.saveAndFlush(lifeInsuranceCompany);
        int databaseSizeBeforeDelete = lifeInsuranceCompanyRepository.findAll().size();

        // Get the lifeInsuranceCompany
        restLifeInsuranceCompanyMockMvc.perform(delete("/api/life-insurance-companies/{id}", lifeInsuranceCompany.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LifeInsuranceCompany> lifeInsuranceCompanyList = lifeInsuranceCompanyRepository.findAll();
        assertThat(lifeInsuranceCompanyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LifeInsuranceCompany.class);
        LifeInsuranceCompany lifeInsuranceCompany1 = new LifeInsuranceCompany();
        lifeInsuranceCompany1.setId(1L);
        LifeInsuranceCompany lifeInsuranceCompany2 = new LifeInsuranceCompany();
        lifeInsuranceCompany2.setId(lifeInsuranceCompany1.getId());
        assertThat(lifeInsuranceCompany1).isEqualTo(lifeInsuranceCompany2);
        lifeInsuranceCompany2.setId(2L);
        assertThat(lifeInsuranceCompany1).isNotEqualTo(lifeInsuranceCompany2);
        lifeInsuranceCompany1.setId(null);
        assertThat(lifeInsuranceCompany1).isNotEqualTo(lifeInsuranceCompany2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LifeInsuranceCompanyDTO.class);
        LifeInsuranceCompanyDTO lifeInsuranceCompanyDTO1 = new LifeInsuranceCompanyDTO();
        lifeInsuranceCompanyDTO1.setId(1L);
        LifeInsuranceCompanyDTO lifeInsuranceCompanyDTO2 = new LifeInsuranceCompanyDTO();
        assertThat(lifeInsuranceCompanyDTO1).isNotEqualTo(lifeInsuranceCompanyDTO2);
        lifeInsuranceCompanyDTO2.setId(lifeInsuranceCompanyDTO1.getId());
        assertThat(lifeInsuranceCompanyDTO1).isEqualTo(lifeInsuranceCompanyDTO2);
        lifeInsuranceCompanyDTO2.setId(2L);
        assertThat(lifeInsuranceCompanyDTO1).isNotEqualTo(lifeInsuranceCompanyDTO2);
        lifeInsuranceCompanyDTO1.setId(null);
        assertThat(lifeInsuranceCompanyDTO1).isNotEqualTo(lifeInsuranceCompanyDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(lifeInsuranceCompanyMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(lifeInsuranceCompanyMapper.fromId(null)).isNull();
    }
}
