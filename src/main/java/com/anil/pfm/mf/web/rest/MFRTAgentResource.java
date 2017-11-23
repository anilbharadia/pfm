package com.anil.pfm.mf.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.anil.pfm.web.rest.errors.BadRequestAlertException;
import com.anil.pfm.web.rest.util.HeaderUtil;
import com.anil.pfm.web.rest.util.PaginationUtil;
import com.anil.pfm.mf.service.MFRTAgentService;
import com.anil.pfm.service.dto.MFRTAgentDTO;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing MFRTAgent.
 */
@RestController
@RequestMapping("/api")
public class MFRTAgentResource {

    private final Logger log = LoggerFactory.getLogger(MFRTAgentResource.class);

    private static final String ENTITY_NAME = "mFRTAgent";

    private final MFRTAgentService mFRTAgentService;

    public MFRTAgentResource(MFRTAgentService mFRTAgentService) {
        this.mFRTAgentService = mFRTAgentService;
    }

    /**
     * POST  /m-frt-agents : Create a new mFRTAgent.
     *
     * @param mFRTAgentDTO the mFRTAgentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mFRTAgentDTO, or with status 400 (Bad Request) if the mFRTAgent has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/m-frt-agents")
    @Timed
    public ResponseEntity<MFRTAgentDTO> createMFRTAgent(@Valid @RequestBody MFRTAgentDTO mFRTAgentDTO) throws URISyntaxException {
        log.debug("REST request to save MFRTAgent : {}", mFRTAgentDTO);
        if (mFRTAgentDTO.getId() != null) {
            throw new BadRequestAlertException("A new mFRTAgent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MFRTAgentDTO result = mFRTAgentService.save(mFRTAgentDTO);
        return ResponseEntity.created(new URI("/api/m-frt-agents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /m-frt-agents : Updates an existing mFRTAgent.
     *
     * @param mFRTAgentDTO the mFRTAgentDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mFRTAgentDTO,
     * or with status 400 (Bad Request) if the mFRTAgentDTO is not valid,
     * or with status 500 (Internal Server Error) if the mFRTAgentDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/m-frt-agents")
    @Timed
    public ResponseEntity<MFRTAgentDTO> updateMFRTAgent(@Valid @RequestBody MFRTAgentDTO mFRTAgentDTO) throws URISyntaxException {
        log.debug("REST request to update MFRTAgent : {}", mFRTAgentDTO);
        if (mFRTAgentDTO.getId() == null) {
            return createMFRTAgent(mFRTAgentDTO);
        }
        MFRTAgentDTO result = mFRTAgentService.save(mFRTAgentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mFRTAgentDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /m-frt-agents : get all the mFRTAgents.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mFRTAgents in body
     */
    @GetMapping("/m-frt-agents")
    @Timed
    public ResponseEntity<List<MFRTAgentDTO>> getAllMFRTAgents(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of MFRTAgents");
        Page<MFRTAgentDTO> page = mFRTAgentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/m-frt-agents");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /m-frt-agents/:id : get the "id" mFRTAgent.
     *
     * @param id the id of the mFRTAgentDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mFRTAgentDTO, or with status 404 (Not Found)
     */
    @GetMapping("/m-frt-agents/{id}")
    @Timed
    public ResponseEntity<MFRTAgentDTO> getMFRTAgent(@PathVariable Long id) {
        log.debug("REST request to get MFRTAgent : {}", id);
        MFRTAgentDTO mFRTAgentDTO = mFRTAgentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mFRTAgentDTO));
    }

    /**
     * DELETE  /m-frt-agents/:id : delete the "id" mFRTAgent.
     *
     * @param id the id of the mFRTAgentDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/m-frt-agents/{id}")
    @Timed
    public ResponseEntity<Void> deleteMFRTAgent(@PathVariable Long id) {
        log.debug("REST request to delete MFRTAgent : {}", id);
        mFRTAgentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
