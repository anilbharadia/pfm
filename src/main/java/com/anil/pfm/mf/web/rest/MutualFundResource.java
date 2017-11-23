package com.anil.pfm.mf.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.anil.pfm.web.rest.errors.BadRequestAlertException;
import com.anil.pfm.web.rest.util.HeaderUtil;
import com.anil.pfm.web.rest.util.PaginationUtil;
import com.anil.pfm.mf.service.MutualFundService;
import com.anil.pfm.service.dto.MutualFundDTO;
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
 * REST controller for managing MutualFund.
 */
@RestController
@RequestMapping("/api")
public class MutualFundResource {

    private final Logger log = LoggerFactory.getLogger(MutualFundResource.class);

    private static final String ENTITY_NAME = "mutualFund";

    private final MutualFundService mutualFundService;

    public MutualFundResource(MutualFundService mutualFundService) {
        this.mutualFundService = mutualFundService;
    }

    /**
     * POST  /mutual-funds : Create a new mutualFund.
     *
     * @param mutualFundDTO the mutualFundDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mutualFundDTO, or with status 400 (Bad Request) if the mutualFund has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mutual-funds")
    @Timed
    public ResponseEntity<MutualFundDTO> createMutualFund(@Valid @RequestBody MutualFundDTO mutualFundDTO) throws URISyntaxException {
        log.debug("REST request to save MutualFund : {}", mutualFundDTO);
        if (mutualFundDTO.getId() != null) {
            throw new BadRequestAlertException("A new mutualFund cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MutualFundDTO result = mutualFundService.save(mutualFundDTO);
        return ResponseEntity.created(new URI("/api/mutual-funds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /mutual-funds : Updates an existing mutualFund.
     *
     * @param mutualFundDTO the mutualFundDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mutualFundDTO,
     * or with status 400 (Bad Request) if the mutualFundDTO is not valid,
     * or with status 500 (Internal Server Error) if the mutualFundDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mutual-funds")
    @Timed
    public ResponseEntity<MutualFundDTO> updateMutualFund(@Valid @RequestBody MutualFundDTO mutualFundDTO) throws URISyntaxException {
        log.debug("REST request to update MutualFund : {}", mutualFundDTO);
        if (mutualFundDTO.getId() == null) {
            return createMutualFund(mutualFundDTO);
        }
        MutualFundDTO result = mutualFundService.save(mutualFundDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mutualFundDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /mutual-funds : get all the mutualFunds.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mutualFunds in body
     */
    @GetMapping("/mutual-funds")
    @Timed
    public ResponseEntity<List<MutualFundDTO>> getAllMutualFunds(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of MutualFunds");
        Page<MutualFundDTO> page = mutualFundService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mutual-funds");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /mutual-funds/:id : get the "id" mutualFund.
     *
     * @param id the id of the mutualFundDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mutualFundDTO, or with status 404 (Not Found)
     */
    @GetMapping("/mutual-funds/{id}")
    @Timed
    public ResponseEntity<MutualFundDTO> getMutualFund(@PathVariable Long id) {
        log.debug("REST request to get MutualFund : {}", id);
        MutualFundDTO mutualFundDTO = mutualFundService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mutualFundDTO));
    }

    /**
     * DELETE  /mutual-funds/:id : delete the "id" mutualFund.
     *
     * @param id the id of the mutualFundDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mutual-funds/{id}")
    @Timed
    public ResponseEntity<Void> deleteMutualFund(@PathVariable Long id) {
        log.debug("REST request to delete MutualFund : {}", id);
        mutualFundService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
