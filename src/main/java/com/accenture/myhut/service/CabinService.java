package com.accenture.myhut.service;

import com.accenture.myhut.domain.Cabin;
import com.accenture.myhut.repository.CabinRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Cabin.
 */
@Service
@Transactional
public class CabinService {

    private final Logger log = LoggerFactory.getLogger(CabinService.class);
    
    @Inject
    private CabinRepository cabinRepository;
    
    /**
     * Save a cabin.
     * 
     * @param cabin the entity to save
     * @return the persisted entity
     */
    public Cabin save(Cabin cabin) {
        log.debug("Request to save Cabin : {}", cabin);
        Cabin result = cabinRepository.save(cabin);
        return result;
    }

    /**
     *  Get all the cabins.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Cabin> findAll() {
        log.debug("Request to get all Cabins");
        List<Cabin> result = cabinRepository.findAll();
        return result;
    }

    /**
     *  Get one cabin by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Cabin findOne(Long id) {
        log.debug("Request to get Cabin : {}", id);
        Cabin cabin = cabinRepository.findOne(id);
        return cabin;
    }

    /**
     *  Delete the  cabin by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Cabin : {}", id);
        cabinRepository.delete(id);
    }
}
