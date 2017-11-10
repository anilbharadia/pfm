package com.anil.pfm.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.anil.pfm.service.ExpenseCategoryService;
import com.anil.pfm.web.rest.errors.BadRequestAlertException;
import com.anil.pfm.web.rest.util.HeaderUtil;
import com.anil.pfm.web.rest.util.PaginationUtil;
import com.anil.pfm.service.dto.ExpenseCategoryDTO;
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
 * REST controller for managing ExpenseCategory.
 */
@RestController
@RequestMapping("/api")
public class ExpenseCategoryResource {

    private final Logger log = LoggerFactory.getLogger(ExpenseCategoryResource.class);

    private static final String ENTITY_NAME = "expenseCategory";

    private final ExpenseCategoryService expenseCategoryService;

    public ExpenseCategoryResource(ExpenseCategoryService expenseCategoryService) {
        this.expenseCategoryService = expenseCategoryService;
    }

    /**
     * POST  /expense-categories : Create a new expenseCategory.
     *
     * @param expenseCategoryDTO the expenseCategoryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new expenseCategoryDTO, or with status 400 (Bad Request) if the expenseCategory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/expense-categories")
    @Timed
    public ResponseEntity<ExpenseCategoryDTO> createExpenseCategory(@Valid @RequestBody ExpenseCategoryDTO expenseCategoryDTO) throws URISyntaxException {
        log.debug("REST request to save ExpenseCategory : {}", expenseCategoryDTO);
        if (expenseCategoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new expenseCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExpenseCategoryDTO result = expenseCategoryService.save(expenseCategoryDTO);
        return ResponseEntity.created(new URI("/api/expense-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /expense-categories : Updates an existing expenseCategory.
     *
     * @param expenseCategoryDTO the expenseCategoryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated expenseCategoryDTO,
     * or with status 400 (Bad Request) if the expenseCategoryDTO is not valid,
     * or with status 500 (Internal Server Error) if the expenseCategoryDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/expense-categories")
    @Timed
    public ResponseEntity<ExpenseCategoryDTO> updateExpenseCategory(@Valid @RequestBody ExpenseCategoryDTO expenseCategoryDTO) throws URISyntaxException {
        log.debug("REST request to update ExpenseCategory : {}", expenseCategoryDTO);
        if (expenseCategoryDTO.getId() == null) {
            return createExpenseCategory(expenseCategoryDTO);
        }
        ExpenseCategoryDTO result = expenseCategoryService.save(expenseCategoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, expenseCategoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /expense-categories : get all the expenseCategories.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of expenseCategories in body
     */
    @GetMapping("/expense-categories")
    @Timed
    public ResponseEntity<List<ExpenseCategoryDTO>> getAllExpenseCategories(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of ExpenseCategories");
        Page<ExpenseCategoryDTO> page = expenseCategoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/expense-categories");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /expense-categories/:id : get the "id" expenseCategory.
     *
     * @param id the id of the expenseCategoryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the expenseCategoryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/expense-categories/{id}")
    @Timed
    public ResponseEntity<ExpenseCategoryDTO> getExpenseCategory(@PathVariable Long id) {
        log.debug("REST request to get ExpenseCategory : {}", id);
        ExpenseCategoryDTO expenseCategoryDTO = expenseCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(expenseCategoryDTO));
    }

    /**
     * DELETE  /expense-categories/:id : delete the "id" expenseCategory.
     *
     * @param id the id of the expenseCategoryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/expense-categories/{id}")
    @Timed
    public ResponseEntity<Void> deleteExpenseCategory(@PathVariable Long id) {
        log.debug("REST request to delete ExpenseCategory : {}", id);
        expenseCategoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
