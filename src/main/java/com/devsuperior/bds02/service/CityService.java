package com.devsuperior.bds02.service;

import com.devsuperior.bds02.dto.CityDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.exceptions.DataBaseException;
import com.devsuperior.bds02.exceptions.ResourceNotFoundException;
import com.devsuperior.bds02.repository.CityRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CityService {

    final CityRepository repository;

    public CityService(CityRepository repository) {
        this.repository = repository;
    }

    public List<CityDTO> findAll() {
        List<City> cities = repository.findAll(Sort.by("name"));
        return cities.stream().map(CityDTO::new).collect(Collectors.toList());
    }


    public CityDTO insert(CityDTO dto) {
        return new CityDTO(repository.save(new City(dto)));
    }

    public void delete(Long id) {
        try {
            repository.deleteById(id);
        }
        catch (EmptyResultDataAccessException | EntityNotFoundException e) {
            throw new ResourceNotFoundException("Resource id: %d not found".formatted(id));
        }
        catch (DataIntegrityViolationException e){
            throw new DataBaseException("DataBase error");
        }
    }
}
