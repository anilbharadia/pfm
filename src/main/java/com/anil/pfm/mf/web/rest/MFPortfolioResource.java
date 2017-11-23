package com.anil.pfm.mf.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.anil.pfm.web.rest.errors.BadRequestAlertException;
import com.anil.pfm.web.rest.util.HeaderUtil;
import com.anil.pfm.web.rest.util.PaginationUtil;
import com.anil.pfm.mf.service.MFPortfolioService;
import com.anil.pfm.service.dto.MFPortfolioDTO;
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
 * REST controller for managing MFPortfolio.
 */
@RestController
@RequestMapping("/api")
public class MFPortfolioResource {

    private final Logger log = LoggerFactory.getLogger(MFPortfolioResource.class);

    private static final String ENTITY_NAME = "mFPortfolio";

    private final MFPortfolioService mFPortfolioService;

    public MFPortfolioResource(MFPortfolioService mFPortfolioService) {
        this.mFPortfolioService = mFPortfolioService;
    }

    /**
     * POST  /m-f-portfolios : Create a new mFPortfolio.
     *
     * @param mFPortfolioDTO the mFPortfolioDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mFPortfolioDTO, or with status 400 (Bad Request) if the mFPortfolio has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/m-f-portfolios")
    @Timed
    public ResponseEntity<MFPortfolioDTO> createMFPortfolio(@Valid @RequestBody MFPortfolioDTO mFPortfolioDTO) throws URISyntaxException {
        log.debug("REST request to save MFPortfolio : {}", mFPortfolioDTO);
        if (mFPortfolioDTO.getId() != null) {
            throw new BadRequestAlertException("A new mFPortfolio cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MFPortfolioDTO result = mFPortfolioService.save(mFPortfolioDTO);
        return ResponseEntity.created(new URI("/api/m-f-portfolios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /m-f-portfolios : Updates an existing mFPortfolio.
     *
     * @param mFPortfolioDTO the mFPortfolioDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mFPortfolioDTO,
     * or with status 400 (Bad Request) if the mFPortfolioDTO is not valid,
     * or with status 500 (Internal Server Error) if the mFPortfolioDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/m-f-portfolios")
    @Timed
    public ResponseEntity<MFPortfolioDTO> updateMFPortfolio(@Valid @RequestBody MFPortfolioDTO mFPortfolioDTO) throws URISyntaxException {
        log.debug("REST request to update MFPortfolio : {}", mFPortfolioDTO);
        if (mFPortfolioDTO.getId() == null) {
            return createMFPortfolio(mFPortfolioDTO);
        }
        MFPortfolioDTO result = mFPortfolioService.save(mFPortfolioDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mFPortfolioDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /m-f-portfolios : get all the mFPortfolios.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mFPortfolios in body
     */
    @GetMapping("/m-f-portfolios")
    @Timed
    public ResponseEntity<List<MFPortfolioDTO>> getAllMFPortfolios(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of MFPortfolios");
        Page<MFPortfolioDTO> page = mFPortfolioService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/m-f-portfolios");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /m-f-portfolios/:id : get the "id" mFPortfolio.
     *
     * @param id the id of the mFPortfolioDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mFPortfolioDTO, or with status 404 (Not Found)
     */
    @GetMapping("/m-f-portfolios/{id}")
    @Timed
    public ResponseEntity<MFPortfolioDTO> getMFPortfolio(@PathVariable Long id) {
        log.debug("REST request to get MFPortfolio : {}", id);
        MFPortfolioDTO mFPortfolioDTO = mFPortfolioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mFPortfolioDTO));
    }

    /**
     * DELETE  /m-f-portfolios/:id : delete the "id" mFPortfolio.
     *
     * @param id the id of the mFPortfolioDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/m-f-portfolios/{id}")
    @Timed
    public ResponseEntity<Void> deleteMFPortfolio(@PathVariable Long id) {
        log.debug("REST request to delete MFPortfolio : {}", id);
        mFPortfolioService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
