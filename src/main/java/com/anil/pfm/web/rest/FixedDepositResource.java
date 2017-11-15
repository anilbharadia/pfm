package com.anil.pfm.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.anil.pfm.service.FixedDepositService;
import com.anil.pfm.web.rest.errors.BadRequestAlertException;
import com.anil.pfm.web.rest.util.HeaderUtil;
import com.anil.pfm.web.rest.util.PaginationUtil;
import com.anil.pfm.service.dto.FixedDepositDTO;
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
 * REST controller for managing FixedDeposit.
 */
@RestController
@RequestMapping("/api")
public class FixedDepositResource {

    private final Logger log = LoggerFactory.getLogger(FixedDepositResource.class);

    private static final String ENTITY_NAME = "fixedDeposit";

    private final FixedDepositService fixedDepositService;

    public FixedDepositResource(FixedDepositService fixedDepositService) {
        this.fixedDepositService = fixedDepositService;
    }

    /**
     * POST  /fixed-deposits : Create a new fixedDeposit.
     *
     * @param fixedDepositDTO the fixedDepositDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new fixedDepositDTO, or with status 400 (Bad Request) if the fixedDeposit has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/fixed-deposits")
    @Timed
    public ResponseEntity<FixedDepositDTO> createFixedDeposit(@Valid @RequestBody FixedDepositDTO fixedDepositDTO) throws URISyntaxException {
        log.debug("REST request to save FixedDeposit : {}", fixedDepositDTO);
        if (fixedDepositDTO.getId() != null) {
            throw new BadRequestAlertException("A new fixedDeposit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FixedDepositDTO result = fixedDepositService.save(fixedDepositDTO);
        return ResponseEntity.created(new URI("/api/fixed-deposits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /fixed-deposits : Updates an existing fixedDeposit.
     *
     * @param fixedDepositDTO the fixedDepositDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated fixedDepositDTO,
     * or with status 400 (Bad Request) if the fixedDepositDTO is not valid,
     * or with status 500 (Internal Server Error) if the fixedDepositDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/fixed-deposits")
    @Timed
    public ResponseEntity<FixedDepositDTO> updateFixedDeposit(@Valid @RequestBody FixedDepositDTO fixedDepositDTO) throws URISyntaxException {
        log.debug("REST request to update FixedDeposit : {}", fixedDepositDTO);
        if (fixedDepositDTO.getId() == null) {
            return createFixedDeposit(fixedDepositDTO);
        }
        FixedDepositDTO result = fixedDepositService.save(fixedDepositDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, fixedDepositDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /fixed-deposits : get all the fixedDeposits.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of fixedDeposits in body
     */
    @GetMapping("/fixed-deposits")
    @Timed
    public ResponseEntity<List<FixedDepositDTO>> getAllFixedDeposits(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of FixedDeposits");
        Page<FixedDepositDTO> page = fixedDepositService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/fixed-deposits");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /fixed-deposits/:id : get the "id" fixedDeposit.
     *
     * @param id the id of the fixedDepositDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the fixedDepositDTO, or with status 404 (Not Found)
     */
    @GetMapping("/fixed-deposits/{id}")
    @Timed
    public ResponseEntity<FixedDepositDTO> getFixedDeposit(@PathVariable Long id) {
        log.debug("REST request to get FixedDeposit : {}", id);
        FixedDepositDTO fixedDepositDTO = fixedDepositService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(fixedDepositDTO));
    }

    /**
     * DELETE  /fixed-deposits/:id : delete the "id" fixedDeposit.
     *
     * @param id the id of the fixedDepositDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/fixed-deposits/{id}")
    @Timed
    public ResponseEntity<Void> deleteFixedDeposit(@PathVariable Long id) {
        log.debug("REST request to delete FixedDeposit : {}", id);
        fixedDepositService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
