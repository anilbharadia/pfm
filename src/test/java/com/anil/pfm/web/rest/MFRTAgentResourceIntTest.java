package com.anil.pfm.web.rest;

import com.anil.pfm.PfmApp;
import com.anil.pfm.mf.domain.MFRTAgent;
import com.anil.pfm.mf.repository.MFRTAgentRepository;
import com.anil.pfm.mf.service.MFRTAgentService;
import com.anil.pfm.mf.service.mapper.MFRTAgentMapper;
import com.anil.pfm.mf.web.rest.MFRTAgentResource;
import com.anil.pfm.service.dto.MFRTAgentDTO;
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
 * Test class for the MFRTAgentResource REST controller.
 *
 * @see MFRTAgentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PfmApp.class)
public class MFRTAgentResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private MFRTAgentRepository mFRTAgentRepository;

    @Autowired
    private MFRTAgentMapper mFRTAgentMapper;

    @Autowired
    private MFRTAgentService mFRTAgentService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMFRTAgentMockMvc;

    private MFRTAgent mFRTAgent;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MFRTAgentResource mFRTAgentResource = new MFRTAgentResource(mFRTAgentService);
        this.restMFRTAgentMockMvc = MockMvcBuilders.standaloneSetup(mFRTAgentResource)
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
    public static MFRTAgent createEntity(EntityManager em) {
        MFRTAgent mFRTAgent = new MFRTAgent()
            .name(DEFAULT_NAME);
        return mFRTAgent;
    }

    @Before
    public void initTest() {
        mFRTAgent = createEntity(em);
    }

    @Test
    @Transactional
    public void createMFRTAgent() throws Exception {
        int databaseSizeBeforeCreate = mFRTAgentRepository.findAll().size();

        // Create the MFRTAgent
        MFRTAgentDTO mFRTAgentDTO = mFRTAgentMapper.toDto(mFRTAgent);
        restMFRTAgentMockMvc.perform(post("/api/m-frt-agents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mFRTAgentDTO)))
            .andExpect(status().isCreated());

        // Validate the MFRTAgent in the database
        List<MFRTAgent> mFRTAgentList = mFRTAgentRepository.findAll();
        assertThat(mFRTAgentList).hasSize(databaseSizeBeforeCreate + 1);
        MFRTAgent testMFRTAgent = mFRTAgentList.get(mFRTAgentList.size() - 1);
        assertThat(testMFRTAgent.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createMFRTAgentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mFRTAgentRepository.findAll().size();

        // Create the MFRTAgent with an existing ID
        mFRTAgent.setId(1L);
        MFRTAgentDTO mFRTAgentDTO = mFRTAgentMapper.toDto(mFRTAgent);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMFRTAgentMockMvc.perform(post("/api/m-frt-agents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mFRTAgentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MFRTAgent in the database
        List<MFRTAgent> mFRTAgentList = mFRTAgentRepository.findAll();
        assertThat(mFRTAgentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = mFRTAgentRepository.findAll().size();
        // set the field null
        mFRTAgent.setName(null);

        // Create the MFRTAgent, which fails.
        MFRTAgentDTO mFRTAgentDTO = mFRTAgentMapper.toDto(mFRTAgent);

        restMFRTAgentMockMvc.perform(post("/api/m-frt-agents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mFRTAgentDTO)))
            .andExpect(status().isBadRequest());

        List<MFRTAgent> mFRTAgentList = mFRTAgentRepository.findAll();
        assertThat(mFRTAgentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMFRTAgents() throws Exception {
        // Initialize the database
        mFRTAgentRepository.saveAndFlush(mFRTAgent);

        // Get all the mFRTAgentList
        restMFRTAgentMockMvc.perform(get("/api/m-frt-agents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mFRTAgent.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getMFRTAgent() throws Exception {
        // Initialize the database
        mFRTAgentRepository.saveAndFlush(mFRTAgent);

        // Get the mFRTAgent
        restMFRTAgentMockMvc.perform(get("/api/m-frt-agents/{id}", mFRTAgent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mFRTAgent.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMFRTAgent() throws Exception {
        // Get the mFRTAgent
        restMFRTAgentMockMvc.perform(get("/api/m-frt-agents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMFRTAgent() throws Exception {
        // Initialize the database
        mFRTAgentRepository.saveAndFlush(mFRTAgent);
        int databaseSizeBeforeUpdate = mFRTAgentRepository.findAll().size();

        // Update the mFRTAgent
        MFRTAgent updatedMFRTAgent = mFRTAgentRepository.findOne(mFRTAgent.getId());
        updatedMFRTAgent
            .name(UPDATED_NAME);
        MFRTAgentDTO mFRTAgentDTO = mFRTAgentMapper.toDto(updatedMFRTAgent);

        restMFRTAgentMockMvc.perform(put("/api/m-frt-agents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mFRTAgentDTO)))
            .andExpect(status().isOk());

        // Validate the MFRTAgent in the database
        List<MFRTAgent> mFRTAgentList = mFRTAgentRepository.findAll();
        assertThat(mFRTAgentList).hasSize(databaseSizeBeforeUpdate);
        MFRTAgent testMFRTAgent = mFRTAgentList.get(mFRTAgentList.size() - 1);
        assertThat(testMFRTAgent.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingMFRTAgent() throws Exception {
        int databaseSizeBeforeUpdate = mFRTAgentRepository.findAll().size();

        // Create the MFRTAgent
        MFRTAgentDTO mFRTAgentDTO = mFRTAgentMapper.toDto(mFRTAgent);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMFRTAgentMockMvc.perform(put("/api/m-frt-agents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mFRTAgentDTO)))
            .andExpect(status().isCreated());

        // Validate the MFRTAgent in the database
        List<MFRTAgent> mFRTAgentList = mFRTAgentRepository.findAll();
        assertThat(mFRTAgentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMFRTAgent() throws Exception {
        // Initialize the database
        mFRTAgentRepository.saveAndFlush(mFRTAgent);
        int databaseSizeBeforeDelete = mFRTAgentRepository.findAll().size();

        // Get the mFRTAgent
        restMFRTAgentMockMvc.perform(delete("/api/m-frt-agents/{id}", mFRTAgent.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MFRTAgent> mFRTAgentList = mFRTAgentRepository.findAll();
        assertThat(mFRTAgentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MFRTAgent.class);
        MFRTAgent mFRTAgent1 = new MFRTAgent();
        mFRTAgent1.setId(1L);
        MFRTAgent mFRTAgent2 = new MFRTAgent();
        mFRTAgent2.setId(mFRTAgent1.getId());
        assertThat(mFRTAgent1).isEqualTo(mFRTAgent2);
        mFRTAgent2.setId(2L);
        assertThat(mFRTAgent1).isNotEqualTo(mFRTAgent2);
        mFRTAgent1.setId(null);
        assertThat(mFRTAgent1).isNotEqualTo(mFRTAgent2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MFRTAgentDTO.class);
        MFRTAgentDTO mFRTAgentDTO1 = new MFRTAgentDTO();
        mFRTAgentDTO1.setId(1L);
        MFRTAgentDTO mFRTAgentDTO2 = new MFRTAgentDTO();
        assertThat(mFRTAgentDTO1).isNotEqualTo(mFRTAgentDTO2);
        mFRTAgentDTO2.setId(mFRTAgentDTO1.getId());
        assertThat(mFRTAgentDTO1).isEqualTo(mFRTAgentDTO2);
        mFRTAgentDTO2.setId(2L);
        assertThat(mFRTAgentDTO1).isNotEqualTo(mFRTAgentDTO2);
        mFRTAgentDTO1.setId(null);
        assertThat(mFRTAgentDTO1).isNotEqualTo(mFRTAgentDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(mFRTAgentMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(mFRTAgentMapper.fromId(null)).isNull();
    }
}
