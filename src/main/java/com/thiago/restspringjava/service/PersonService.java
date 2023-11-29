package com.thiago.restspringjava.service;

import com.thiago.restspringjava.data.vo.v1.PersonVO;
import com.thiago.restspringjava.exception.ResourceNotFoundException;
import com.thiago.restspringjava.mapper.DozerMapper;
import com.thiago.restspringjava.model.Person;
import com.thiago.restspringjava.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class PersonService {

    @Autowired
    private PersonRepository repository;

    private Logger logger = Logger.getLogger(PersonService.class.getName());

    public List<PersonVO> findAll(){
        logger.info("Finding all people");
        return DozerMapper.parseListObjects(repository.findAll(), PersonVO.class);
    }

    public PersonVO findById(Long id){
        logger.info("Finding one person");

        return DozerMapper.parseObject(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Person not found")), PersonVO.class);
    }

    public PersonVO create(PersonVO person){
        logger.info("Creating one person");
        Person savedPerson = repository.save(DozerMapper.parseObject(person, Person.class));
        return DozerMapper.parseObject(savedPerson, PersonVO.class);
    }

    public PersonVO update(PersonVO person){
        logger.info("Updating one person");
        var entity = repository.findById(person.getId()).orElseThrow(() -> new ResourceNotFoundException("Person not found"));
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        return DozerMapper.parseObject(repository.save(entity), PersonVO.class);
    }

    public void delete(Long id){
        logger.info("Deleting one person");
        repository.deleteById(id);
    }
}
