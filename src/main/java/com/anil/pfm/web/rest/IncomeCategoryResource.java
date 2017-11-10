package com.anil.pfm.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.anil.pfm.service.IncomeCategoryService;
import com.anil.pfm.web.rest.errors.BadRequestAlertException;
import com.anil.pfm.web.rest.util.HeaderUtil;
import com.anil.pfm.web.rest.util.PaginationUtil;
import com.anil.pfm.service.dto.IncomeCategoryDTO;
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
 * REST controller for managing IncomeCategory.
 */
@RestController
@RequestMapping("/api")
public class IncomeCategoryResource {

    private final Logger log = LoggerFactory.getLogger(IncomeCategoryResource.class);

    private static final String ENTITY_NAME = "incomeCategory";

    private final IncomeCategoryService incomeCategoryService;

    public IncomeCategoryResource(IncomeCategoryService incomeCategoryService) {
        this.incomeCategoryService = incomeCategoryService;
    }

    /**
     * POST  /income-categories : Create a new incomeCategory.
     *
     * @param incomeCategoryDTO the incomeCategoryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new incomeCategoryDTO, or with status 400 (Bad Request) if the incomeCategory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/income-categories")
    @Timed
    public ResponseEntity<IncomeCategoryDTO> createIncomeCategory(@Valid @RequestBody IncomeCategoryDTO incomeCategoryDTO) throws URISyntaxException {
        log.debug("REST request to save IncomeCategory : {}", incomeCategoryDTO);
        if (incomeCategoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new incomeCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IncomeCategoryDTO result = incomeCategoryService.save(incomeCategoryDTO);
        return ResponseEntity.created(new URI("/api/income-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /income-categories : Updates an existing incomeCategory.
     *
     * @param incomeCategoryDTO the incomeCategoryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated incomeCategoryDTO,
     * or with status 400 (Bad Request) if the incomeCategoryDTO is not valid,
     * or with status 500 (Internal Server Error) if the incomeCategoryDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/income-categories")
    @Timed
    public ResponseEntity<IncomeCategoryDTO> updateIncomeCategory(@Valid @RequestBody IncomeCategoryDTO incomeCategoryDTO) throws URISyntaxException {
        log.debug("REST request to update IncomeCategory : {}", incomeCategoryDTO);
        if (incomeCategoryDTO.getId() == null) {
            return createIncomeCategory(incomeCategoryDTO);
        }
        IncomeCategoryDTO result = incomeCategoryService.save(incomeCategoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, incomeCategoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /income-categories : get all the incomeCategories.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of incomeCategories in body
     */
    @GetMapping("/income-categories")
    @Timed
    public ResponseEntity<List<IncomeCategoryDTO>> getAllIncomeCategories(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of IncomeCategories");
        Page<IncomeCategoryDTO> page = incomeCategoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/income-categories");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /income-categories/:id : get the "id" incomeCategory.
     *
     * @param id the id of the incomeCategoryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the incomeCategoryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/income-categories/{id}")
    @Timed
    public ResponseEntity<IncomeCategoryDTO> getIncomeCategory(@PathVariable Long id) {
        log.debug("REST request to get IncomeCategory : {}", id);
        IncomeCategoryDTO incomeCategoryDTO = incomeCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(incomeCategoryDTO));
    }

    /**
     * DELETE  /income-categories/:id : delete the "id" incomeCategory.
     *
     * @param id the id of the incomeCategoryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/income-categories/{id}")
    @Timed
    public ResponseEntity<Void> deleteIncomeCategory(@PathVariable Long id) {
        log.debug("REST request to delete IncomeCategory : {}", id);
        incomeCategoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
