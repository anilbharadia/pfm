package com.anil.pfm.web.rest;

import com.anil.pfm.PfmApp;

import com.anil.pfm.domain.MFPortfolio;
import com.anil.pfm.repository.MFPortfolioRepository;
import com.anil.pfm.service.MFPortfolioService;
import com.anil.pfm.service.dto.MFPortfolioDTO;
import com.anil.pfm.service.mapper.MFPortfolioMapper;
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
 * Test class for the MFPortfolioResource REST controller.
 *
 * @see MFPortfolioResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PfmApp.class)
public class MFPortfolioResourceIntTest {

    private static final String DEFAULT_FOLIO_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_FOLIO_NUMBER = "BBBBBBBBBB";

    @Autowired
    private MFPortfolioRepository mFPortfolioRepository;

    @Autowired
    private MFPortfolioMapper mFPortfolioMapper;

    @Autowired
    private MFPortfolioService mFPortfolioService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMFPortfolioMockMvc;

    private MFPortfolio mFPortfolio;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MFPortfolioResource mFPortfolioResource = new MFPortfolioResource(mFPortfolioService);
        this.restMFPortfolioMockMvc = MockMvcBuilders.standaloneSetup(mFPortfolioResource)
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
    public static MFPortfolio createEntity(EntityManager em) {
        MFPortfolio mFPortfolio = new MFPortfolio()
            .folioNumber(DEFAULT_FOLIO_NUMBER);
        return mFPortfolio;
    }

    @Before
    public void initTest() {
        mFPortfolio = createEntity(em);
    }

    @Test
    @Transactional
    public void createMFPortfolio() throws Exception {
        int databaseSizeBeforeCreate = mFPortfolioRepository.findAll().size();

        // Create the MFPortfolio
        MFPortfolioDTO mFPortfolioDTO = mFPortfolioMapper.toDto(mFPortfolio);
        restMFPortfolioMockMvc.perform(post("/api/m-f-portfolios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mFPortfolioDTO)))
            .andExpect(status().isCreated());

        // Validate the MFPortfolio in the database
        List<MFPortfolio> mFPortfolioList = mFPortfolioRepository.findAll();
        assertThat(mFPortfolioList).hasSize(databaseSizeBeforeCreate + 1);
        MFPortfolio testMFPortfolio = mFPortfolioList.get(mFPortfolioList.size() - 1);
        assertThat(testMFPortfolio.getFolioNumber()).isEqualTo(DEFAULT_FOLIO_NUMBER);
    }

    @Test
    @Transactional
    public void createMFPortfolioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mFPortfolioRepository.findAll().size();

        // Create the MFPortfolio with an existing ID
        mFPortfolio.setId(1L);
        MFPortfolioDTO mFPortfolioDTO = mFPortfolioMapper.toDto(mFPortfolio);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMFPortfolioMockMvc.perform(post("/api/m-f-portfolios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mFPortfolioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MFPortfolio in the database
        List<MFPortfolio> mFPortfolioList = mFPortfolioRepository.findAll();
        assertThat(mFPortfolioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkFolioNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = mFPortfolioRepository.findAll().size();
        // set the field null
        mFPortfolio.setFolioNumber(null);

        // Create the MFPortfolio, which fails.
        MFPortfolioDTO mFPortfolioDTO = mFPortfolioMapper.toDto(mFPortfolio);

        restMFPortfolioMockMvc.perform(post("/api/m-f-portfolios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mFPortfolioDTO)))
            .andExpect(status().isBadRequest());

        List<MFPortfolio> mFPortfolioList = mFPortfolioRepository.findAll();
        assertThat(mFPortfolioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMFPortfolios() throws Exception {
        // Initialize the database
        mFPortfolioRepository.saveAndFlush(mFPortfolio);

        // Get all the mFPortfolioList
        restMFPortfolioMockMvc.perform(get("/api/m-f-portfolios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mFPortfolio.getId().intValue())))
            .andExpect(jsonPath("$.[*].folioNumber").value(hasItem(DEFAULT_FOLIO_NUMBER.toString())));
    }

    @Test
    @Transactional
    public void getMFPortfolio() throws Exception {
        // Initialize the database
        mFPortfolioRepository.saveAndFlush(mFPortfolio);

        // Get the mFPortfolio
        restMFPortfolioMockMvc.perform(get("/api/m-f-portfolios/{id}", mFPortfolio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mFPortfolio.getId().intValue()))
            .andExpect(jsonPath("$.folioNumber").value(DEFAULT_FOLIO_NUMBER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMFPortfolio() throws Exception {
        // Get the mFPortfolio
        restMFPortfolioMockMvc.perform(get("/api/m-f-portfolios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMFPortfolio() throws Exception {
        // Initialize the database
        mFPortfolioRepository.saveAndFlush(mFPortfolio);
        int databaseSizeBeforeUpdate = mFPortfolioRepository.findAll().size();

        // Update the mFPortfolio
        MFPortfolio updatedMFPortfolio = mFPortfolioRepository.findOne(mFPortfolio.getId());
        updatedMFPortfolio
            .folioNumber(UPDATED_FOLIO_NUMBER);
        MFPortfolioDTO mFPortfolioDTO = mFPortfolioMapper.toDto(updatedMFPortfolio);

        restMFPortfolioMockMvc.perform(put("/api/m-f-portfolios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mFPortfolioDTO)))
            .andExpect(status().isOk());

        // Validate the MFPortfolio in the database
        List<MFPortfolio> mFPortfolioList = mFPortfolioRepository.findAll();
        assertThat(mFPortfolioList).hasSize(databaseSizeBeforeUpdate);
        MFPortfolio testMFPortfolio = mFPortfolioList.get(mFPortfolioList.size() - 1);
        assertThat(testMFPortfolio.getFolioNumber()).isEqualTo(UPDATED_FOLIO_NUMBER);
    }

    @Test
    @Transactional
    public void updateNonExistingMFPortfolio() throws Exception {
        int databaseSizeBeforeUpdate = mFPortfolioRepository.findAll().size();

        // Create the MFPortfolio
        MFPortfolioDTO mFPortfolioDTO = mFPortfolioMapper.toDto(mFPortfolio);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMFPortfolioMockMvc.perform(put("/api/m-f-portfolios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mFPortfolioDTO)))
            .andExpect(status().isCreated());

        // Validate the MFPortfolio in the database
        List<MFPortfolio> mFPortfolioList = mFPortfolioRepository.findAll();
        assertThat(mFPortfolioList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMFPortfolio() throws Exception {
        // Initialize the database
        mFPortfolioRepository.saveAndFlush(mFPortfolio);
        int databaseSizeBeforeDelete = mFPortfolioRepository.findAll().size();

        // Get the mFPortfolio
        restMFPortfolioMockMvc.perform(delete("/api/m-f-portfolios/{id}", mFPortfolio.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MFPortfolio> mFPortfolioList = mFPortfolioRepository.findAll();
        assertThat(mFPortfolioList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MFPortfolio.class);
        MFPortfolio mFPortfolio1 = new MFPortfolio();
        mFPortfolio1.setId(1L);
        MFPortfolio mFPortfolio2 = new MFPortfolio();
        mFPortfolio2.setId(mFPortfolio1.getId());
        assertThat(mFPortfolio1).isEqualTo(mFPortfolio2);
        mFPortfolio2.setId(2L);
        assertThat(mFPortfolio1).isNotEqualTo(mFPortfolio2);
        mFPortfolio1.setId(null);
        assertThat(mFPortfolio1).isNotEqualTo(mFPortfolio2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MFPortfolioDTO.class);
        MFPortfolioDTO mFPortfolioDTO1 = new MFPortfolioDTO();
        mFPortfolioDTO1.setId(1L);
        MFPortfolioDTO mFPortfolioDTO2 = new MFPortfolioDTO();
        assertThat(mFPortfolioDTO1).isNotEqualTo(mFPortfolioDTO2);
        mFPortfolioDTO2.setId(mFPortfolioDTO1.getId());
        assertThat(mFPortfolioDTO1).isEqualTo(mFPortfolioDTO2);
        mFPortfolioDTO2.setId(2L);
        assertThat(mFPortfolioDTO1).isNotEqualTo(mFPortfolioDTO2);
        mFPortfolioDTO1.setId(null);
        assertThat(mFPortfolioDTO1).isNotEqualTo(mFPortfolioDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(mFPortfolioMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(mFPortfolioMapper.fromId(null)).isNull();
    }
}
