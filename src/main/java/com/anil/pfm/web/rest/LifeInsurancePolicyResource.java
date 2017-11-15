package com.anil.pfm.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.anil.pfm.service.LifeInsurancePolicyService;
import com.anil.pfm.web.rest.errors.BadRequestAlertException;
import com.anil.pfm.web.rest.util.HeaderUtil;
import com.anil.pfm.web.rest.util.PaginationUtil;
import com.anil.pfm.service.dto.LifeInsurancePolicyDTO;
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
 * REST controller for managing LifeInsurancePolicy.
 */
@RestController
@RequestMapping("/api")
public class LifeInsurancePolicyResource {

    private final Logger log = LoggerFactory.getLogger(LifeInsurancePolicyResource.class);

    private static final String ENTITY_NAME = "lifeInsurancePolicy";

    private final LifeInsurancePolicyService lifeInsurancePolicyService;

    public LifeInsurancePolicyResource(LifeInsurancePolicyService lifeInsurancePolicyService) {
        this.lifeInsurancePolicyService = lifeInsurancePolicyService;
    }

    /**
     * POST  /life-insurance-policies : Create a new lifeInsurancePolicy.
     *
     * @param lifeInsurancePolicyDTO the lifeInsurancePolicyDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new lifeInsurancePolicyDTO, or with status 400 (Bad Request) if the lifeInsurancePolicy has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/life-insurance-policies")
    @Timed
    public ResponseEntity<LifeInsurancePolicyDTO> createLifeInsurancePolicy(@Valid @RequestBody LifeInsurancePolicyDTO lifeInsurancePolicyDTO) throws URISyntaxException {
        log.debug("REST request to save LifeInsurancePolicy : {}", lifeInsurancePolicyDTO);
        if (lifeInsurancePolicyDTO.getId() != null) {
            throw new BadRequestAlertException("A new lifeInsurancePolicy cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LifeInsurancePolicyDTO result = lifeInsurancePolicyService.save(lifeInsurancePolicyDTO);
        return ResponseEntity.created(new URI("/api/life-insurance-policies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /life-insurance-policies : Updates an existing lifeInsurancePolicy.
     *
     * @param lifeInsurancePolicyDTO the lifeInsurancePolicyDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated lifeInsurancePolicyDTO,
     * or with status 400 (Bad Request) if the lifeInsurancePolicyDTO is not valid,
     * or with status 500 (Internal Server Error) if the lifeInsurancePolicyDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/life-insurance-policies")
    @Timed
    public ResponseEntity<LifeInsurancePolicyDTO> updateLifeInsurancePolicy(@Valid @RequestBody LifeInsurancePolicyDTO lifeInsurancePolicyDTO) throws URISyntaxException {
        log.debug("REST request to update LifeInsurancePolicy : {}", lifeInsurancePolicyDTO);
        if (lifeInsurancePolicyDTO.getId() == null) {
            return createLifeInsurancePolicy(lifeInsurancePolicyDTO);
        }
        LifeInsurancePolicyDTO result = lifeInsurancePolicyService.save(lifeInsurancePolicyDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, lifeInsurancePolicyDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /life-insurance-policies : get all the lifeInsurancePolicies.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of lifeInsurancePolicies in body
     */
    @GetMapping("/life-insurance-policies")
    @Timed
    public ResponseEntity<List<LifeInsurancePolicyDTO>> getAllLifeInsurancePolicies(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of LifeInsurancePolicies");
        Page<LifeInsurancePolicyDTO> page = lifeInsurancePolicyService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/life-insurance-policies");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /life-insurance-policies/:id : get the "id" lifeInsurancePolicy.
     *
     * @param id the id of the lifeInsurancePolicyDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the lifeInsurancePolicyDTO, or with status 404 (Not Found)
     */
    @GetMapping("/life-insurance-policies/{id}")
    @Timed
    public ResponseEntity<LifeInsurancePolicyDTO> getLifeInsurancePolicy(@PathVariable Long id) {
        log.debug("REST request to get LifeInsurancePolicy : {}", id);
        LifeInsurancePolicyDTO lifeInsurancePolicyDTO = lifeInsurancePolicyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(lifeInsurancePolicyDTO));
    }

    /**
     * DELETE  /life-insurance-policies/:id : delete the "id" lifeInsurancePolicy.
     *
     * @param id the id of the lifeInsurancePolicyDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/life-insurance-policies/{id}")
    @Timed
    public ResponseEntity<Void> deleteLifeInsurancePolicy(@PathVariable Long id) {
        log.debug("REST request to delete LifeInsurancePolicy : {}", id);
        lifeInsurancePolicyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
