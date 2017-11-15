package com.anil.pfm.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.anil.pfm.service.RecurringDepositService;
import com.anil.pfm.web.rest.errors.BadRequestAlertException;
import com.anil.pfm.web.rest.util.HeaderUtil;
import com.anil.pfm.web.rest.util.PaginationUtil;
import com.anil.pfm.service.dto.RecurringDepositDTO;
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
 * REST controller for managing RecurringDeposit.
 */
@RestController
@RequestMapping("/api")
public class RecurringDepositResource {

    private final Logger log = LoggerFactory.getLogger(RecurringDepositResource.class);

    private static final String ENTITY_NAME = "recurringDeposit";

    private final RecurringDepositService recurringDepositService;

    public RecurringDepositResource(RecurringDepositService recurringDepositService) {
        this.recurringDepositService = recurringDepositService;
    }

    /**
     * POST  /recurring-deposits : Create a new recurringDeposit.
     *
     * @param recurringDepositDTO the recurringDepositDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new recurringDepositDTO, or with status 400 (Bad Request) if the recurringDeposit has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/recurring-deposits")
    @Timed
    public ResponseEntity<RecurringDepositDTO> createRecurringDeposit(@Valid @RequestBody RecurringDepositDTO recurringDepositDTO) throws URISyntaxException {
        log.debug("REST request to save RecurringDeposit : {}", recurringDepositDTO);
        if (recurringDepositDTO.getId() != null) {
            throw new BadRequestAlertException("A new recurringDeposit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RecurringDepositDTO result = recurringDepositService.save(recurringDepositDTO);
        return ResponseEntity.created(new URI("/api/recurring-deposits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /recurring-deposits : Updates an existing recurringDeposit.
     *
     * @param recurringDepositDTO the recurringDepositDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated recurringDepositDTO,
     * or with status 400 (Bad Request) if the recurringDepositDTO is not valid,
     * or with status 500 (Internal Server Error) if the recurringDepositDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/recurring-deposits")
    @Timed
    public ResponseEntity<RecurringDepositDTO> updateRecurringDeposit(@Valid @RequestBody RecurringDepositDTO recurringDepositDTO) throws URISyntaxException {
        log.debug("REST request to update RecurringDeposit : {}", recurringDepositDTO);
        if (recurringDepositDTO.getId() == null) {
            return createRecurringDeposit(recurringDepositDTO);
        }
        RecurringDepositDTO result = recurringDepositService.save(recurringDepositDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, recurringDepositDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /recurring-deposits : get all the recurringDeposits.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of recurringDeposits in body
     */
    @GetMapping("/recurring-deposits")
    @Timed
    public ResponseEntity<List<RecurringDepositDTO>> getAllRecurringDeposits(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of RecurringDeposits");
        Page<RecurringDepositDTO> page = recurringDepositService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/recurring-deposits");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /recurring-deposits/:id : get the "id" recurringDeposit.
     *
     * @param id the id of the recurringDepositDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the recurringDepositDTO, or with status 404 (Not Found)
     */
    @GetMapping("/recurring-deposits/{id}")
    @Timed
    public ResponseEntity<RecurringDepositDTO> getRecurringDeposit(@PathVariable Long id) {
        log.debug("REST request to get RecurringDeposit : {}", id);
        RecurringDepositDTO recurringDepositDTO = recurringDepositService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(recurringDepositDTO));
    }

    /**
     * DELETE  /recurring-deposits/:id : delete the "id" recurringDeposit.
     *
     * @param id the id of the recurringDepositDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/recurring-deposits/{id}")
    @Timed
    public ResponseEntity<Void> deleteRecurringDeposit(@PathVariable Long id) {
        log.debug("REST request to delete RecurringDeposit : {}", id);
        recurringDepositService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
