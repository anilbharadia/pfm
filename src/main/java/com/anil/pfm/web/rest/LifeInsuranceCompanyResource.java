package com.anil.pfm.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.anil.pfm.service.LifeInsuranceCompanyService;
import com.anil.pfm.web.rest.errors.BadRequestAlertException;
import com.anil.pfm.web.rest.util.HeaderUtil;
import com.anil.pfm.web.rest.util.PaginationUtil;
import com.anil.pfm.service.dto.LifeInsuranceCompanyDTO;
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
 * REST controller for managing LifeInsuranceCompany.
 */
@RestController
@RequestMapping("/api")
public class LifeInsuranceCompanyResource {

    private final Logger log = LoggerFactory.getLogger(LifeInsuranceCompanyResource.class);

    private static final String ENTITY_NAME = "lifeInsuranceCompany";

    private final LifeInsuranceCompanyService lifeInsuranceCompanyService;

    public LifeInsuranceCompanyResource(LifeInsuranceCompanyService lifeInsuranceCompanyService) {
        this.lifeInsuranceCompanyService = lifeInsuranceCompanyService;
    }

    /**
     * POST  /life-insurance-companies : Create a new lifeInsuranceCompany.
     *
     * @param lifeInsuranceCompanyDTO the lifeInsuranceCompanyDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new lifeInsuranceCompanyDTO, or with status 400 (Bad Request) if the lifeInsuranceCompany has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/life-insurance-companies")
    @Timed
    public ResponseEntity<LifeInsuranceCompanyDTO> createLifeInsuranceCompany(@Valid @RequestBody LifeInsuranceCompanyDTO lifeInsuranceCompanyDTO) throws URISyntaxException {
        log.debug("REST request to save LifeInsuranceCompany : {}", lifeInsuranceCompanyDTO);
        if (lifeInsuranceCompanyDTO.getId() != null) {
            throw new BadRequestAlertException("A new lifeInsuranceCompany cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LifeInsuranceCompanyDTO result = lifeInsuranceCompanyService.save(lifeInsuranceCompanyDTO);
        return ResponseEntity.created(new URI("/api/life-insurance-companies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /life-insurance-companies : Updates an existing lifeInsuranceCompany.
     *
     * @param lifeInsuranceCompanyDTO the lifeInsuranceCompanyDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated lifeInsuranceCompanyDTO,
     * or with status 400 (Bad Request) if the lifeInsuranceCompanyDTO is not valid,
     * or with status 500 (Internal Server Error) if the lifeInsuranceCompanyDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/life-insurance-companies")
    @Timed
    public ResponseEntity<LifeInsuranceCompanyDTO> updateLifeInsuranceCompany(@Valid @RequestBody LifeInsuranceCompanyDTO lifeInsuranceCompanyDTO) throws URISyntaxException {
        log.debug("REST request to update LifeInsuranceCompany : {}", lifeInsuranceCompanyDTO);
        if (lifeInsuranceCompanyDTO.getId() == null) {
            return createLifeInsuranceCompany(lifeInsuranceCompanyDTO);
        }
        LifeInsuranceCompanyDTO result = lifeInsuranceCompanyService.save(lifeInsuranceCompanyDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, lifeInsuranceCompanyDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /life-insurance-companies : get all the lifeInsuranceCompanies.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of lifeInsuranceCompanies in body
     */
    @GetMapping("/life-insurance-companies")
    @Timed
    public ResponseEntity<List<LifeInsuranceCompanyDTO>> getAllLifeInsuranceCompanies(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of LifeInsuranceCompanies");
        Page<LifeInsuranceCompanyDTO> page = lifeInsuranceCompanyService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/life-insurance-companies");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /life-insurance-companies/:id : get the "id" lifeInsuranceCompany.
     *
     * @param id the id of the lifeInsuranceCompanyDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the lifeInsuranceCompanyDTO, or with status 404 (Not Found)
     */
    @GetMapping("/life-insurance-companies/{id}")
    @Timed
    public ResponseEntity<LifeInsuranceCompanyDTO> getLifeInsuranceCompany(@PathVariable Long id) {
        log.debug("REST request to get LifeInsuranceCompany : {}", id);
        LifeInsuranceCompanyDTO lifeInsuranceCompanyDTO = lifeInsuranceCompanyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(lifeInsuranceCompanyDTO));
    }

    /**
     * DELETE  /life-insurance-companies/:id : delete the "id" lifeInsuranceCompany.
     *
     * @param id the id of the lifeInsuranceCompanyDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/life-insurance-companies/{id}")
    @Timed
    public ResponseEntity<Void> deleteLifeInsuranceCompany(@PathVariable Long id) {
        log.debug("REST request to delete LifeInsuranceCompany : {}", id);
        lifeInsuranceCompanyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
