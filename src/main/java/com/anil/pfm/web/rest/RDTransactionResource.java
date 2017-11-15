package com.anil.pfm.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.anil.pfm.service.RDTransactionService;
import com.anil.pfm.web.rest.errors.BadRequestAlertException;
import com.anil.pfm.web.rest.util.HeaderUtil;
import com.anil.pfm.web.rest.util.PaginationUtil;
import com.anil.pfm.service.dto.RDTransactionDTO;
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
 * REST controller for managing RDTransaction.
 */
@RestController
@RequestMapping("/api")
public class RDTransactionResource {

    private final Logger log = LoggerFactory.getLogger(RDTransactionResource.class);

    private static final String ENTITY_NAME = "rDTransaction";

    private final RDTransactionService rDTransactionService;

    public RDTransactionResource(RDTransactionService rDTransactionService) {
        this.rDTransactionService = rDTransactionService;
    }

    /**
     * POST  /r-d-transactions : Create a new rDTransaction.
     *
     * @param rDTransactionDTO the rDTransactionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rDTransactionDTO, or with status 400 (Bad Request) if the rDTransaction has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/r-d-transactions")
    @Timed
    public ResponseEntity<RDTransactionDTO> createRDTransaction(@Valid @RequestBody RDTransactionDTO rDTransactionDTO) throws URISyntaxException {
        log.debug("REST request to save RDTransaction : {}", rDTransactionDTO);
        if (rDTransactionDTO.getId() != null) {
            throw new BadRequestAlertException("A new rDTransaction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RDTransactionDTO result = rDTransactionService.save(rDTransactionDTO);
        return ResponseEntity.created(new URI("/api/r-d-transactions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /r-d-transactions : Updates an existing rDTransaction.
     *
     * @param rDTransactionDTO the rDTransactionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rDTransactionDTO,
     * or with status 400 (Bad Request) if the rDTransactionDTO is not valid,
     * or with status 500 (Internal Server Error) if the rDTransactionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/r-d-transactions")
    @Timed
    public ResponseEntity<RDTransactionDTO> updateRDTransaction(@Valid @RequestBody RDTransactionDTO rDTransactionDTO) throws URISyntaxException {
        log.debug("REST request to update RDTransaction : {}", rDTransactionDTO);
        if (rDTransactionDTO.getId() == null) {
            return createRDTransaction(rDTransactionDTO);
        }
        RDTransactionDTO result = rDTransactionService.save(rDTransactionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rDTransactionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /r-d-transactions : get all the rDTransactions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of rDTransactions in body
     */
    @GetMapping("/r-d-transactions")
    @Timed
    public ResponseEntity<List<RDTransactionDTO>> getAllRDTransactions(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of RDTransactions");
        Page<RDTransactionDTO> page = rDTransactionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/r-d-transactions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /r-d-transactions/:id : get the "id" rDTransaction.
     *
     * @param id the id of the rDTransactionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rDTransactionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/r-d-transactions/{id}")
    @Timed
    public ResponseEntity<RDTransactionDTO> getRDTransaction(@PathVariable Long id) {
        log.debug("REST request to get RDTransaction : {}", id);
        RDTransactionDTO rDTransactionDTO = rDTransactionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(rDTransactionDTO));
    }

    /**
     * DELETE  /r-d-transactions/:id : delete the "id" rDTransaction.
     *
     * @param id the id of the rDTransactionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/r-d-transactions/{id}")
    @Timed
    public ResponseEntity<Void> deleteRDTransaction(@PathVariable Long id) {
        log.debug("REST request to delete RDTransaction : {}", id);
        rDTransactionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
