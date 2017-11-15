package com.anil.pfm.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.anil.pfm.service.PPFTransactionService;
import com.anil.pfm.web.rest.errors.BadRequestAlertException;
import com.anil.pfm.web.rest.util.HeaderUtil;
import com.anil.pfm.web.rest.util.PaginationUtil;
import com.anil.pfm.service.dto.PPFTransactionDTO;
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
 * REST controller for managing PPFTransaction.
 */
@RestController
@RequestMapping("/api")
public class PPFTransactionResource {

    private final Logger log = LoggerFactory.getLogger(PPFTransactionResource.class);

    private static final String ENTITY_NAME = "pPFTransaction";

    private final PPFTransactionService pPFTransactionService;

    public PPFTransactionResource(PPFTransactionService pPFTransactionService) {
        this.pPFTransactionService = pPFTransactionService;
    }

    /**
     * POST  /p-pf-transactions : Create a new pPFTransaction.
     *
     * @param pPFTransactionDTO the pPFTransactionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pPFTransactionDTO, or with status 400 (Bad Request) if the pPFTransaction has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/p-pf-transactions")
    @Timed
    public ResponseEntity<PPFTransactionDTO> createPPFTransaction(@Valid @RequestBody PPFTransactionDTO pPFTransactionDTO) throws URISyntaxException {
        log.debug("REST request to save PPFTransaction : {}", pPFTransactionDTO);
        if (pPFTransactionDTO.getId() != null) {
            throw new BadRequestAlertException("A new pPFTransaction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PPFTransactionDTO result = pPFTransactionService.save(pPFTransactionDTO);
        return ResponseEntity.created(new URI("/api/p-pf-transactions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /p-pf-transactions : Updates an existing pPFTransaction.
     *
     * @param pPFTransactionDTO the pPFTransactionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pPFTransactionDTO,
     * or with status 400 (Bad Request) if the pPFTransactionDTO is not valid,
     * or with status 500 (Internal Server Error) if the pPFTransactionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/p-pf-transactions")
    @Timed
    public ResponseEntity<PPFTransactionDTO> updatePPFTransaction(@Valid @RequestBody PPFTransactionDTO pPFTransactionDTO) throws URISyntaxException {
        log.debug("REST request to update PPFTransaction : {}", pPFTransactionDTO);
        if (pPFTransactionDTO.getId() == null) {
            return createPPFTransaction(pPFTransactionDTO);
        }
        PPFTransactionDTO result = pPFTransactionService.save(pPFTransactionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pPFTransactionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /p-pf-transactions : get all the pPFTransactions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pPFTransactions in body
     */
    @GetMapping("/p-pf-transactions")
    @Timed
    public ResponseEntity<List<PPFTransactionDTO>> getAllPPFTransactions(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of PPFTransactions");
        Page<PPFTransactionDTO> page = pPFTransactionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/p-pf-transactions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /p-pf-transactions/:id : get the "id" pPFTransaction.
     *
     * @param id the id of the pPFTransactionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pPFTransactionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/p-pf-transactions/{id}")
    @Timed
    public ResponseEntity<PPFTransactionDTO> getPPFTransaction(@PathVariable Long id) {
        log.debug("REST request to get PPFTransaction : {}", id);
        PPFTransactionDTO pPFTransactionDTO = pPFTransactionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(pPFTransactionDTO));
    }

    /**
     * DELETE  /p-pf-transactions/:id : delete the "id" pPFTransaction.
     *
     * @param id the id of the pPFTransactionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/p-pf-transactions/{id}")
    @Timed
    public ResponseEntity<Void> deletePPFTransaction(@PathVariable Long id) {
        log.debug("REST request to delete PPFTransaction : {}", id);
        pPFTransactionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
