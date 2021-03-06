package com.anil.pfm.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.anil.pfm.service.MyAccountService;
import com.anil.pfm.web.rest.errors.BadRequestAlertException;
import com.anil.pfm.web.rest.util.HeaderUtil;
import com.anil.pfm.web.rest.util.PaginationUtil;
import com.anil.pfm.service.dto.MyAccountDTO;
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
 * REST controller for managing MyAccount.
 */
@RestController
@RequestMapping("/api")
public class MyAccountResource {

    private final Logger log = LoggerFactory.getLogger(MyAccountResource.class);

    private static final String ENTITY_NAME = "myAccount";

    private final MyAccountService myAccountService;

    public MyAccountResource(MyAccountService myAccountService) {
        this.myAccountService = myAccountService;
    }

    /**
     * POST  /my-accounts : Create a new myAccount.
     *
     * @param myAccountDTO the myAccountDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new myAccountDTO, or with status 400 (Bad Request) if the myAccount has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/my-accounts")
    @Timed
    public ResponseEntity<MyAccountDTO> createMyAccount(@Valid @RequestBody MyAccountDTO myAccountDTO) throws URISyntaxException {
        log.debug("REST request to save MyAccount : {}", myAccountDTO);
        if (myAccountDTO.getId() != null) {
            throw new BadRequestAlertException("A new myAccount cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MyAccountDTO result = myAccountService.save(myAccountDTO);
        return ResponseEntity.created(new URI("/api/my-accounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /my-accounts : Updates an existing myAccount.
     *
     * @param myAccountDTO the myAccountDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated myAccountDTO,
     * or with status 400 (Bad Request) if the myAccountDTO is not valid,
     * or with status 500 (Internal Server Error) if the myAccountDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/my-accounts")
    @Timed
    public ResponseEntity<MyAccountDTO> updateMyAccount(@Valid @RequestBody MyAccountDTO myAccountDTO) throws URISyntaxException {
        log.debug("REST request to update MyAccount : {}", myAccountDTO);
        if (myAccountDTO.getId() == null) {
            return createMyAccount(myAccountDTO);
        }
        MyAccountDTO result = myAccountService.save(myAccountDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, myAccountDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /my-accounts : get all the myAccounts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of myAccounts in body
     */
    @GetMapping("/my-accounts")
    @Timed
    public ResponseEntity<List<MyAccountDTO>> getAllMyAccounts(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of MyAccounts");
        Page<MyAccountDTO> page = myAccountService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/my-accounts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /my-accounts/:id : get the "id" myAccount.
     *
     * @param id the id of the myAccountDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the myAccountDTO, or with status 404 (Not Found)
     */
    @GetMapping("/my-accounts/{id}")
    @Timed
    public ResponseEntity<MyAccountDTO> getMyAccount(@PathVariable Long id) {
        log.debug("REST request to get MyAccount : {}", id);
        MyAccountDTO myAccountDTO = myAccountService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(myAccountDTO));
    }

    /**
     * DELETE  /my-accounts/:id : delete the "id" myAccount.
     *
     * @param id the id of the myAccountDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/my-accounts/{id}")
    @Timed
    public ResponseEntity<Void> deleteMyAccount(@PathVariable Long id) {
        log.debug("REST request to delete MyAccount : {}", id);
        myAccountService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
