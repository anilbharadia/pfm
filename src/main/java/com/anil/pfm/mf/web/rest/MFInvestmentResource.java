package com.anil.pfm.mf.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anil.pfm.mf.service.MFInvestmentService;
import com.anil.pfm.mf.service.dto.CreateMFInvestmentVM;
import com.anil.pfm.mf.service.dto.MFInvestmentDTO;
import com.anil.pfm.mf.service.dto.UpdateMFInvestmentVM;
import com.anil.pfm.web.rest.util.HeaderUtil;
import com.anil.pfm.web.rest.util.PaginationUtil;
import com.codahale.metrics.annotation.Timed;

import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;

/**
 * REST controller for managing MFInvestment.
 */
@RestController
@RequestMapping("/api")
public class MFInvestmentResource {

    private final Logger log = LoggerFactory.getLogger(MFInvestmentResource.class);

    private static final String ENTITY_NAME = "mFInvestment";

    private final MFInvestmentService mFInvestmentService;

    public MFInvestmentResource(MFInvestmentService mFInvestmentService) {
        this.mFInvestmentService = mFInvestmentService;
    }

    /**
     * POST  /m-f-investments : Create a new mFInvestment.
     *
     * @param vm the mFInvestmentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mFInvestmentDTO, or with status 400 (Bad Request) if the mFInvestment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/m-f-investments")
    @Timed
    public ResponseEntity<MFInvestmentDTO> createMFInvestment(@Valid @RequestBody CreateMFInvestmentVM vm) throws URISyntaxException {
        log.debug("REST request to save MFInvestment : {}", vm);
        
        MFInvestmentDTO result = mFInvestmentService.save(vm);
        return ResponseEntity.created(new URI("/api/m-f-investments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /m-f-investments : Updates an existing mFInvestment.
     *
     * @param vm the mFInvestmentDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mFInvestmentDTO,
     * or with status 400 (Bad Request) if the mFInvestmentDTO is not valid,
     * or with status 500 (Internal Server Error) if the mFInvestmentDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/m-f-investments")
    @Timed
    public ResponseEntity<MFInvestmentDTO> updateMFInvestment(@Valid @RequestBody UpdateMFInvestmentVM vm) throws URISyntaxException {
        log.debug("REST request to update MFInvestment : {}", vm);
        
        MFInvestmentDTO result = mFInvestmentService.update(vm);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, vm.getId().toString()))
            .body(result);
    }

    /**
     * GET  /m-f-investments : get all the mFInvestments.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mFInvestments in body
     */
    @GetMapping("/m-f-investments")
    @Timed
    public ResponseEntity<List<MFInvestmentDTO>> getAllMFInvestments(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of MFInvestments");
        Page<MFInvestmentDTO> page = mFInvestmentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/m-f-investments");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /m-f-investments/:id : get the "id" mFInvestment.
     *
     * @param id the id of the mFInvestmentDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mFInvestmentDTO, or with status 404 (Not Found)
     */
    @GetMapping("/m-f-investments/{id}")
    @Timed
    public ResponseEntity<MFInvestmentDTO> getMFInvestment(@PathVariable Long id) {
        log.debug("REST request to get MFInvestment : {}", id);
        MFInvestmentDTO mFInvestmentDTO = mFInvestmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mFInvestmentDTO));
    }

    /**
     * DELETE  /m-f-investments/:id : delete the "id" mFInvestment.
     *
     * @param id the id of the mFInvestmentDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/m-f-investments/{id}")
    @Timed
    public ResponseEntity<Void> deleteMFInvestment(@PathVariable Long id) {
        log.debug("REST request to delete MFInvestment : {}", id);
        mFInvestmentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
