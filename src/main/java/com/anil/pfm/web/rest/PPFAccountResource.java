package com.anil.pfm.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.anil.pfm.service.PPFAccountService;
import com.anil.pfm.web.rest.errors.BadRequestAlertException;
import com.anil.pfm.web.rest.util.HeaderUtil;
import com.anil.pfm.web.rest.util.PaginationUtil;
import com.anil.pfm.service.dto.PPFAccountDTO;
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
 * REST controller for managing PPFAccount.
 */
@RestController
@RequestMapping("/api")
public class PPFAccountResource {

    private final Logger log = LoggerFactory.getLogger(PPFAccountResource.class);

    private static final String ENTITY_NAME = "pPFAccount";

    private final PPFAccountService pPFAccountService;

    public PPFAccountResource(PPFAccountService pPFAccountService) {
        this.pPFAccountService = pPFAccountService;
    }

    /**
     * POST  /p-pf-accounts : Create a new pPFAccount.
     *
     * @param pPFAccountDTO the pPFAccountDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pPFAccountDTO, or with status 400 (Bad Request) if the pPFAccount has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/p-pf-accounts")
    @Timed
    public ResponseEntity<PPFAccountDTO> createPPFAccount(@Valid @RequestBody PPFAccountDTO pPFAccountDTO) throws URISyntaxException {
        log.debug("REST request to save PPFAccount : {}", pPFAccountDTO);
        if (pPFAccountDTO.getId() != null) {
            throw new BadRequestAlertException("A new pPFAccount cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PPFAccountDTO result = pPFAccountService.save(pPFAccountDTO);
        return ResponseEntity.created(new URI("/api/p-pf-accounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /p-pf-accounts : Updates an existing pPFAccount.
     *
     * @param pPFAccountDTO the pPFAccountDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pPFAccountDTO,
     * or with status 400 (Bad Request) if the pPFAccountDTO is not valid,
     * or with status 500 (Internal Server Error) if the pPFAccountDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/p-pf-accounts")
    @Timed
    public ResponseEntity<PPFAccountDTO> updatePPFAccount(@Valid @RequestBody PPFAccountDTO pPFAccountDTO) throws URISyntaxException {
        log.debug("REST request to update PPFAccount : {}", pPFAccountDTO);
        if (pPFAccountDTO.getId() == null) {
            return createPPFAccount(pPFAccountDTO);
        }
        PPFAccountDTO result = pPFAccountService.save(pPFAccountDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pPFAccountDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /p-pf-accounts : get all the pPFAccounts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pPFAccounts in body
     */
    @GetMapping("/p-pf-accounts")
    @Timed
    public ResponseEntity<List<PPFAccountDTO>> getAllPPFAccounts(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of PPFAccounts");
        Page<PPFAccountDTO> page = pPFAccountService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/p-pf-accounts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /p-pf-accounts/:id : get the "id" pPFAccount.
     *
     * @param id the id of the pPFAccountDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pPFAccountDTO, or with status 404 (Not Found)
     */
    @GetMapping("/p-pf-accounts/{id}")
    @Timed
    public ResponseEntity<PPFAccountDTO> getPPFAccount(@PathVariable Long id) {
        log.debug("REST request to get PPFAccount : {}", id);
        PPFAccountDTO pPFAccountDTO = pPFAccountService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(pPFAccountDTO));
    }

    /**
     * DELETE  /p-pf-accounts/:id : delete the "id" pPFAccount.
     *
     * @param id the id of the pPFAccountDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/p-pf-accounts/{id}")
    @Timed
    public ResponseEntity<Void> deletePPFAccount(@PathVariable Long id) {
        log.debug("REST request to delete PPFAccount : {}", id);
        pPFAccountService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
