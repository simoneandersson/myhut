package com.accenture.myhut.service;

import com.accenture.myhut.domain.Person;
import com.accenture.myhut.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Person.
 */
@Service
@Transactional
public class PersonService {

    private final Logger log = LoggerFactory.getLogger(PersonService.class);
    
    @Inject
    private PersonRepository personRepository;
    
    /**
     * Save a person.
     * 
     * @param person the entity to save
     * @return the persisted entity
     */
    public Person save(Person person) {
        log.debug("Request to save Person : {}", person);
        Person result = personRepository.save(person);
        return result;
    }

    /**
     *  Get all the people.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Person> findAll() {
        log.debug("Request to get all People");
        List<Person> result = personRepository.findAll();
        return result;
    }

    /**
     *  Get one person by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Person findOne(Long id) {
        log.debug("Request to get Person : {}", id);
        Person person = personRepository.findOne(id);
        return person;
    }

    /**
     *  Delete the  person by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Person : {}", id);
        personRepository.delete(id);
    }
}
