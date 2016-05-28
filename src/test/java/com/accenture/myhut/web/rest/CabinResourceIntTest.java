package com.accenture.myhut.web.rest;

import com.accenture.myhut.MyHutApp;
import com.accenture.myhut.domain.Cabin;
import com.accenture.myhut.repository.CabinRepository;
import com.accenture.myhut.service.CabinService;

import org.boon.primitive.Int;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the CabinResource REST controller.
 *
 * @see CabinResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MyHutApp.class)
@WebAppConfiguration
@IntegrationTest
public class CabinResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";
    private static final String DEFAULT_LOCATION = "AAAAA";
    private static final String UPDATED_LOCATION = "BBBBB";
    private static final Integer DEFAULT_CAPACITY = 1;
    private static final Integer UPDATED_CAPACITY = 2;
    private static final String DEFAULT_PHOTOS = "AAAAA";
    private static final String UPDATED_PHOTOS = "BBBBB";

    @Inject
    private CabinRepository cabinRepository;

    @Inject
    private CabinService cabinService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCabinMockMvc;

    private Cabin cabin;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CabinResource cabinResource = new CabinResource();
        ReflectionTestUtils.setField(cabinResource, "cabinService", cabinService);
        this.restCabinMockMvc = MockMvcBuilders.standaloneSetup(cabinResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        cabin = new Cabin();
        cabin.setName(DEFAULT_NAME);
        cabin.setDescription(DEFAULT_DESCRIPTION);
        cabin.setLocation(DEFAULT_LOCATION);
        cabin.setCapacity(DEFAULT_CAPACITY);
        cabin.setPhotos(DEFAULT_PHOTOS);
    }

    @Test
    @Transactional
    public void createCabin() throws Exception {
        int databaseSizeBeforeCreate = cabinRepository.findAll().size();

        // Create the Cabin

        restCabinMockMvc.perform(post("/api/cabins")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cabin)))
                .andExpect(status().isCreated());

        // Validate the Cabin in the database
        List<Cabin> cabins = cabinRepository.findAll();
        assertThat(cabins).hasSize(databaseSizeBeforeCreate + 1);
        Cabin testCabin = cabins.get(cabins.size() - 1);
        assertThat(testCabin.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCabin.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCabin.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testCabin.getCapacity()).isEqualTo(DEFAULT_CAPACITY);
        assertThat(testCabin.getPhotos()).isEqualTo(DEFAULT_PHOTOS);
    }

    @Test
    @Transactional
    public void getAllCabins() throws Exception {
        // Initialize the database
        cabinRepository.saveAndFlush(cabin);

        // Get all the cabins
        restCabinMockMvc.perform(get("/api/cabins?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(cabin.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION.toString())))
                .andExpect(jsonPath("$.[*].capacity").value(hasItem(DEFAULT_CAPACITY.toString())))
                .andExpect(jsonPath("$.[*].photos").value(hasItem(DEFAULT_PHOTOS.toString())));
    }

    @Test
    @Transactional
    public void getCabin() throws Exception {
        // Initialize the database
        cabinRepository.saveAndFlush(cabin);

        // Get the cabin
        restCabinMockMvc.perform(get("/api/cabins/{id}", cabin.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(cabin.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION.toString()))
            .andExpect(jsonPath("$.capacity").value(DEFAULT_CAPACITY.toString()))
            .andExpect(jsonPath("$.photos").value(DEFAULT_PHOTOS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCabin() throws Exception {
        // Get the cabin
        restCabinMockMvc.perform(get("/api/cabins/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCabin() throws Exception {
        // Initialize the database
        cabinService.save(cabin);

        int databaseSizeBeforeUpdate = cabinRepository.findAll().size();

        // Update the cabin
        Cabin updatedCabin = new Cabin();
        updatedCabin.setId(cabin.getId());
        updatedCabin.setName(UPDATED_NAME);
        updatedCabin.setDescription(UPDATED_DESCRIPTION);
        updatedCabin.setLocation(UPDATED_LOCATION);
        updatedCabin.setCapacity(UPDATED_CAPACITY);
        updatedCabin.setPhotos(UPDATED_PHOTOS);

        restCabinMockMvc.perform(put("/api/cabins")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCabin)))
                .andExpect(status().isOk());

        // Validate the Cabin in the database
        List<Cabin> cabins = cabinRepository.findAll();
        assertThat(cabins).hasSize(databaseSizeBeforeUpdate);
        Cabin testCabin = cabins.get(cabins.size() - 1);
        assertThat(testCabin.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCabin.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCabin.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testCabin.getCapacity()).isEqualTo(UPDATED_CAPACITY);
        assertThat(testCabin.getPhotos()).isEqualTo(UPDATED_PHOTOS);
    }

    @Test
    @Transactional
    public void deleteCabin() throws Exception {
        // Initialize the database
        cabinService.save(cabin);

        int databaseSizeBeforeDelete = cabinRepository.findAll().size();

        // Get the cabin
        restCabinMockMvc.perform(delete("/api/cabins/{id}", cabin.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Cabin> cabins = cabinRepository.findAll();
        assertThat(cabins).hasSize(databaseSizeBeforeDelete - 1);
    }
}
