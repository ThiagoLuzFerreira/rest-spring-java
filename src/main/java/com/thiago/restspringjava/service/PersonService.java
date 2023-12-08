package com.thiago.restspringjava.service;

import com.thiago.restspringjava.controller.PersonController;
import com.thiago.restspringjava.data.vo.v1.PersonVO;
import com.thiago.restspringjava.data.vo.v2.PersonVOV2;
import com.thiago.restspringjava.exception.ResourceNotFoundException;
import com.thiago.restspringjava.mapper.DozerMapper;
import com.thiago.restspringjava.mapper.custom.PersonMapper;
import com.thiago.restspringjava.model.Person;
import com.thiago.restspringjava.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class PersonService {

    @Autowired
    private PersonRepository repository;

    @Autowired
    private PersonMapper mapper;

    private Logger logger = Logger.getLogger(PersonService.class.getName());

    public List<PersonVO> findAll(){
        logger.info("Finding all people");
        var persons = DozerMapper.parseListObjects(repository.findAll(), PersonVO.class);
        persons.forEach(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));
        return persons;
    }

    public PersonVO findById(Long id){
        logger.info("Finding one person");
        var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Person not found"));
        PersonVO vo = DozerMapper.parseObject(entity, PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
        return vo;
    }

    public PersonVO create(PersonVO person){
        logger.info("Creating one person");
        Person savedPerson = repository.save(DozerMapper.parseObject(person, Person.class));

        var vo = DozerMapper.parseObject(savedPerson, PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());

        return vo;
    }

    public PersonVOV2 createV2(PersonVOV2 person) {
        logger.info("Creating one person with V2");
        Person savedPerson = repository.save(mapper.convertVoToEntity(person));
        return mapper.convertEntityToVo(savedPerson);
    }

    public PersonVO update(PersonVO person){
        logger.info("Updating one person");
        var entity = repository.findById(person.getKey()).orElseThrow(() -> new ResourceNotFoundException("Person not found"));
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        PersonVO vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());

        return vo;
    }

    public void delete(Long id){
        logger.info("Deleting one person");
        repository.deleteById(id);
    }
}
