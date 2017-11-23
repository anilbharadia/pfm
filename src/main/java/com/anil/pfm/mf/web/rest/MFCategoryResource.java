package com.anil.pfm.mf.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.anil.pfm.web.rest.errors.BadRequestAlertException;
import com.anil.pfm.web.rest.util.HeaderUtil;
import com.anil.pfm.web.rest.util.PaginationUtil;
import com.anil.pfm.mf.service.MFCategoryService;
import com.anil.pfm.service.dto.MFCategoryDTO;
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
 * REST controller for managing MFCategory.
 */
@RestController
@RequestMapping("/api")
public class MFCategoryResource {

    private final Logger log = LoggerFactory.getLogger(MFCategoryResource.class);

    private static final String ENTITY_NAME = "mFCategory";

    private final MFCategoryService mFCategoryService;

    public MFCategoryResource(MFCategoryService mFCategoryService) {
        this.mFCategoryService = mFCategoryService;
    }

    /**
     * POST  /m-f-categories : Create a new mFCategory.
     *
     * @param mFCategoryDTO the mFCategoryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mFCategoryDTO, or with status 400 (Bad Request) if the mFCategory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/m-f-categories")
    @Timed
    public ResponseEntity<MFCategoryDTO> createMFCategory(@Valid @RequestBody MFCategoryDTO mFCategoryDTO) throws URISyntaxException {
        log.debug("REST request to save MFCategory : {}", mFCategoryDTO);
        if (mFCategoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new mFCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MFCategoryDTO result = mFCategoryService.save(mFCategoryDTO);
        return ResponseEntity.created(new URI("/api/m-f-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /m-f-categories : Updates an existing mFCategory.
     *
     * @param mFCategoryDTO the mFCategoryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mFCategoryDTO,
     * or with status 400 (Bad Request) if the mFCategoryDTO is not valid,
     * or with status 500 (Internal Server Error) if the mFCategoryDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/m-f-categories")
    @Timed
    public ResponseEntity<MFCategoryDTO> updateMFCategory(@Valid @RequestBody MFCategoryDTO mFCategoryDTO) throws URISyntaxException {
        log.debug("REST request to update MFCategory : {}", mFCategoryDTO);
        if (mFCategoryDTO.getId() == null) {
            return createMFCategory(mFCategoryDTO);
        }
        MFCategoryDTO result = mFCategoryService.save(mFCategoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mFCategoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /m-f-categories : get all the mFCategories.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mFCategories in body
     */
    @GetMapping("/m-f-categories")
    @Timed
    public ResponseEntity<List<MFCategoryDTO>> getAllMFCategories(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of MFCategories");
        Page<MFCategoryDTO> page = mFCategoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/m-f-categories");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /m-f-categories/:id : get the "id" mFCategory.
     *
     * @param id the id of the mFCategoryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mFCategoryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/m-f-categories/{id}")
    @Timed
    public ResponseEntity<MFCategoryDTO> getMFCategory(@PathVariable Long id) {
        log.debug("REST request to get MFCategory : {}", id);
        MFCategoryDTO mFCategoryDTO = mFCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mFCategoryDTO));
    }

    /**
     * DELETE  /m-f-categories/:id : delete the "id" mFCategory.
     *
     * @param id the id of the mFCategoryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/m-f-categories/{id}")
    @Timed
    public ResponseEntity<Void> deleteMFCategory(@PathVariable Long id) {
        log.debug("REST request to delete MFCategory : {}", id);
        mFCategoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
