package com.anil.pfm.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.anil.pfm.service.TransactionTypeService;
import com.anil.pfm.web.rest.errors.BadRequestAlertException;
import com.anil.pfm.web.rest.util.HeaderUtil;
import com.anil.pfm.web.rest.util.PaginationUtil;
import com.anil.pfm.service.dto.TransactionTypeDTO;
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
 * REST controller for managing TransactionType.
 */
@RestController
@RequestMapping("/api")
public class TransactionTypeResource {

    private final Logger log = LoggerFactory.getLogger(TransactionTypeResource.class);

    private static final String ENTITY_NAME = "transactionType";

    private final TransactionTypeService transactionTypeService;

    public TransactionTypeResource(TransactionTypeService transactionTypeService) {
        this.transactionTypeService = transactionTypeService;
    }

    /**
     * POST  /transaction-types : Create a new transactionType.
     *
     * @param transactionTypeDTO the transactionTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new transactionTypeDTO, or with status 400 (Bad Request) if the transactionType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/transaction-types")
    @Timed
    public ResponseEntity<TransactionTypeDTO> createTransactionType(@Valid @RequestBody TransactionTypeDTO transactionTypeDTO) throws URISyntaxException {
        log.debug("REST request to save TransactionType : {}", transactionTypeDTO);
        if (transactionTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new transactionType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TransactionTypeDTO result = transactionTypeService.save(transactionTypeDTO);
        return ResponseEntity.created(new URI("/api/transaction-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /transaction-types : Updates an existing transactionType.
     *
     * @param transactionTypeDTO the transactionTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated transactionTypeDTO,
     * or with status 400 (Bad Request) if the transactionTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the transactionTypeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/transaction-types")
    @Timed
    public ResponseEntity<TransactionTypeDTO> updateTransactionType(@Valid @RequestBody TransactionTypeDTO transactionTypeDTO) throws URISyntaxException {
        log.debug("REST request to update TransactionType : {}", transactionTypeDTO);
        if (transactionTypeDTO.getId() == null) {
            return createTransactionType(transactionTypeDTO);
        }
        TransactionTypeDTO result = transactionTypeService.save(transactionTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, transactionTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /transaction-types : get all the transactionTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of transactionTypes in body
     */
    @GetMapping("/transaction-types")
    @Timed
    public ResponseEntity<List<TransactionTypeDTO>> getAllTransactionTypes(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of TransactionTypes");
        Page<TransactionTypeDTO> page = transactionTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/transaction-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /transaction-types/:id : get the "id" transactionType.
     *
     * @param id the id of the transactionTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the transactionTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/transaction-types/{id}")
    @Timed
    public ResponseEntity<TransactionTypeDTO> getTransactionType(@PathVariable Long id) {
        log.debug("REST request to get TransactionType : {}", id);
        TransactionTypeDTO transactionTypeDTO = transactionTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(transactionTypeDTO));
    }

    /**
     * DELETE  /transaction-types/:id : delete the "id" transactionType.
     *
     * @param id the id of the transactionTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/transaction-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteTransactionType(@PathVariable Long id) {
        log.debug("REST request to delete TransactionType : {}", id);
        transactionTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
