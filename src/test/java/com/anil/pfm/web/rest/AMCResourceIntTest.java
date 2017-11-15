package com.anil.pfm.web.rest;

import com.anil.pfm.PfmApp;

import com.anil.pfm.domain.AMC;
import com.anil.pfm.repository.AMCRepository;
import com.anil.pfm.service.AMCService;
import com.anil.pfm.service.dto.AMCDTO;
import com.anil.pfm.service.mapper.AMCMapper;
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
 * Test class for the AMCResource REST controller.
 *
 * @see AMCResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PfmApp.class)
public class AMCResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_WEBSITE = "AAAAAAAAAA";
    private static final String UPDATED_WEBSITE = "BBBBBBBBBB";

    private static final String DEFAULT_LOGO_URL = "AAAAAAAAAA";
    private static final String UPDATED_LOGO_URL = "BBBBBBBBBB";

    @Autowired
    private AMCRepository aMCRepository;

    @Autowired
    private AMCMapper aMCMapper;

    @Autowired
    private AMCService aMCService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAMCMockMvc;

    private AMC aMC;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AMCResource aMCResource = new AMCResource(aMCService);
        this.restAMCMockMvc = MockMvcBuilders.standaloneSetup(aMCResource)
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
    public static AMC createEntity(EntityManager em) {
        AMC aMC = new AMC()
            .name(DEFAULT_NAME)
            .website(DEFAULT_WEBSITE)
            .logoURL(DEFAULT_LOGO_URL);
        return aMC;
    }

    @Before
    public void initTest() {
        aMC = createEntity(em);
    }

    @Test
    @Transactional
    public void createAMC() throws Exception {
        int databaseSizeBeforeCreate = aMCRepository.findAll().size();

        // Create the AMC
        AMCDTO aMCDTO = aMCMapper.toDto(aMC);
        restAMCMockMvc.perform(post("/api/a-mcs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aMCDTO)))
            .andExpect(status().isCreated());

        // Validate the AMC in the database
        List<AMC> aMCList = aMCRepository.findAll();
        assertThat(aMCList).hasSize(databaseSizeBeforeCreate + 1);
        AMC testAMC = aMCList.get(aMCList.size() - 1);
        assertThat(testAMC.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAMC.getWebsite()).isEqualTo(DEFAULT_WEBSITE);
        assertThat(testAMC.getLogoURL()).isEqualTo(DEFAULT_LOGO_URL);
    }

    @Test
    @Transactional
    public void createAMCWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = aMCRepository.findAll().size();

        // Create the AMC with an existing ID
        aMC.setId(1L);
        AMCDTO aMCDTO = aMCMapper.toDto(aMC);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAMCMockMvc.perform(post("/api/a-mcs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aMCDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AMC in the database
        List<AMC> aMCList = aMCRepository.findAll();
        assertThat(aMCList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = aMCRepository.findAll().size();
        // set the field null
        aMC.setName(null);

        // Create the AMC, which fails.
        AMCDTO aMCDTO = aMCMapper.toDto(aMC);

        restAMCMockMvc.perform(post("/api/a-mcs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aMCDTO)))
            .andExpect(status().isBadRequest());

        List<AMC> aMCList = aMCRepository.findAll();
        assertThat(aMCList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAMCS() throws Exception {
        // Initialize the database
        aMCRepository.saveAndFlush(aMC);

        // Get all the aMCList
        restAMCMockMvc.perform(get("/api/a-mcs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aMC.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].website").value(hasItem(DEFAULT_WEBSITE.toString())))
            .andExpect(jsonPath("$.[*].logoURL").value(hasItem(DEFAULT_LOGO_URL.toString())));
    }

    @Test
    @Transactional
    public void getAMC() throws Exception {
        // Initialize the database
        aMCRepository.saveAndFlush(aMC);

        // Get the aMC
        restAMCMockMvc.perform(get("/api/a-mcs/{id}", aMC.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(aMC.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.website").value(DEFAULT_WEBSITE.toString()))
            .andExpect(jsonPath("$.logoURL").value(DEFAULT_LOGO_URL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAMC() throws Exception {
        // Get the aMC
        restAMCMockMvc.perform(get("/api/a-mcs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAMC() throws Exception {
        // Initialize the database
        aMCRepository.saveAndFlush(aMC);
        int databaseSizeBeforeUpdate = aMCRepository.findAll().size();

        // Update the aMC
        AMC updatedAMC = aMCRepository.findOne(aMC.getId());
        updatedAMC
            .name(UPDATED_NAME)
            .website(UPDATED_WEBSITE)
            .logoURL(UPDATED_LOGO_URL);
        AMCDTO aMCDTO = aMCMapper.toDto(updatedAMC);

        restAMCMockMvc.perform(put("/api/a-mcs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aMCDTO)))
            .andExpect(status().isOk());

        // Validate the AMC in the database
        List<AMC> aMCList = aMCRepository.findAll();
        assertThat(aMCList).hasSize(databaseSizeBeforeUpdate);
        AMC testAMC = aMCList.get(aMCList.size() - 1);
        assertThat(testAMC.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAMC.getWebsite()).isEqualTo(UPDATED_WEBSITE);
        assertThat(testAMC.getLogoURL()).isEqualTo(UPDATED_LOGO_URL);
    }

    @Test
    @Transactional
    public void updateNonExistingAMC() throws Exception {
        int databaseSizeBeforeUpdate = aMCRepository.findAll().size();

        // Create the AMC
        AMCDTO aMCDTO = aMCMapper.toDto(aMC);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAMCMockMvc.perform(put("/api/a-mcs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aMCDTO)))
            .andExpect(status().isCreated());

        // Validate the AMC in the database
        List<AMC> aMCList = aMCRepository.findAll();
        assertThat(aMCList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAMC() throws Exception {
        // Initialize the database
        aMCRepository.saveAndFlush(aMC);
        int databaseSizeBeforeDelete = aMCRepository.findAll().size();

        // Get the aMC
        restAMCMockMvc.perform(delete("/api/a-mcs/{id}", aMC.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AMC> aMCList = aMCRepository.findAll();
        assertThat(aMCList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AMC.class);
        AMC aMC1 = new AMC();
        aMC1.setId(1L);
        AMC aMC2 = new AMC();
        aMC2.setId(aMC1.getId());
        assertThat(aMC1).isEqualTo(aMC2);
        aMC2.setId(2L);
        assertThat(aMC1).isNotEqualTo(aMC2);
        aMC1.setId(null);
        assertThat(aMC1).isNotEqualTo(aMC2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AMCDTO.class);
        AMCDTO aMCDTO1 = new AMCDTO();
        aMCDTO1.setId(1L);
        AMCDTO aMCDTO2 = new AMCDTO();
        assertThat(aMCDTO1).isNotEqualTo(aMCDTO2);
        aMCDTO2.setId(aMCDTO1.getId());
        assertThat(aMCDTO1).isEqualTo(aMCDTO2);
        aMCDTO2.setId(2L);
        assertThat(aMCDTO1).isNotEqualTo(aMCDTO2);
        aMCDTO1.setId(null);
        assertThat(aMCDTO1).isNotEqualTo(aMCDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(aMCMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(aMCMapper.fromId(null)).isNull();
    }
}
