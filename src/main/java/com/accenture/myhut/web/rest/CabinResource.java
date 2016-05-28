package com.accenture.myhut.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.accenture.myhut.domain.Cabin;
import com.accenture.myhut.service.CabinService;
import com.accenture.myhut.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Cabin.
 */
@RestController
@RequestMapping("/api")
public class CabinResource {

    private final Logger log = LoggerFactory.getLogger(CabinResource.class);
        
    @Inject
    private CabinService cabinService;
    
    /**
     * POST  /cabins : Create a new cabin.
     *
     * @param cabin the cabin to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cabin, or with status 400 (Bad Request) if the cabin has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/cabins",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cabin> createCabin(@RequestBody Cabin cabin) throws URISyntaxException {
        log.debug("REST request to save Cabin : {}", cabin);
        if (cabin.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cabin", "idexists", "A new cabin cannot already have an ID")).body(null);
        }
        Cabin result = cabinService.save(cabin);
        return ResponseEntity.created(new URI("/api/cabins/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("cabin", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cabins : Updates an existing cabin.
     *
     * @param cabin the cabin to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cabin,
     * or with status 400 (Bad Request) if the cabin is not valid,
     * or with status 500 (Internal Server Error) if the cabin couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/cabins",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cabin> updateCabin(@RequestBody Cabin cabin) throws URISyntaxException {
        log.debug("REST request to update Cabin : {}", cabin);
        if (cabin.getId() == null) {
            return createCabin(cabin);
        }
        Cabin result = cabinService.save(cabin);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cabin", cabin.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cabins : get all the cabins.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of cabins in body
     */
    @RequestMapping(value = "/cabins",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Cabin> getAllCabins() {
        log.debug("REST request to get all Cabins");
        return cabinService.findAll();
    }

    /**
     * GET  /cabins/:id : get the "id" cabin.
     *
     * @param id the id of the cabin to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cabin, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/cabins/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cabin> getCabin(@PathVariable Long id) {
        log.debug("REST request to get Cabin : {}", id);
        Cabin cabin = cabinService.findOne(id);
        return Optional.ofNullable(cabin)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /cabins/:id : delete the "id" cabin.
     *
     * @param id the id of the cabin to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/cabins/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCabin(@PathVariable Long id) {
        log.debug("REST request to delete Cabin : {}", id);
        cabinService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cabin", id.toString())).build();
    }

}
