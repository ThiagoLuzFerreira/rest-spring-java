package com.thiago.restspringjava.service;

import com.thiago.restspringjava.controller.PersonController;
import com.thiago.restspringjava.data.vo.v1.PersonVO;
import com.thiago.restspringjava.exception.RequiredObjectIsNullException;
import com.thiago.restspringjava.exception.ResourceNotFoundException;
import com.thiago.restspringjava.mapper.DozerMapper;
import com.thiago.restspringjava.mapper.custom.PersonMapper;
import com.thiago.restspringjava.model.Person;
import com.thiago.restspringjava.repository.PersonRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class PersonService {

    @Autowired
    private PersonRepository repository;

    @Autowired
    private PersonMapper mapper;

    @Autowired
    private PagedResourcesAssembler<PersonVO> assembler;

    private Logger logger = Logger.getLogger(PersonService.class.getName());

    public PagedModel<EntityModel<PersonVO>> findAll(Pageable pageable){
        logger.info("Finding all people");
        var personPage = repository.findAll(pageable);
        var personVosPage = personPage.map(p -> DozerMapper.parseObject(p, PersonVO.class));
        personVosPage.map(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));

        return assembler.toModel(personVosPage, linkTo(methodOn(PersonController.class).findAll(pageable.getPageNumber(), pageable.getPageSize(), "asc")).withSelfRel());
    }
    public PagedModel<EntityModel<PersonVO>> findPeopleByFirstName(String firstName, Pageable pageable){
        logger.info("Finding all people");
        var personPage = repository.findPeopleByFirstName(firstName, pageable);
        var personVosPage = personPage.map(p -> DozerMapper.parseObject(p, PersonVO.class));
        personVosPage.map(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));

        return assembler.toModel(personVosPage, linkTo(methodOn(PersonController.class).findAll(pageable.getPageNumber(), pageable.getPageSize(), "asc")).withSelfRel());
    }

    public PersonVO findById(Long id){
        logger.info("Finding one person");
        var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Person not found"));
        PersonVO vo = DozerMapper.parseObject(entity, PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
        return vo;
    }

    public PersonVO create(PersonVO person){

        if(person == null) throw new RequiredObjectIsNullException();

        logger.info("Creating one person");
        Person savedPerson = repository.save(DozerMapper.parseObject(person, Person.class));

        var vo = DozerMapper.parseObject(savedPerson, PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());

        return vo;
    }

    /*public PersonVOV2 createV2(PersonVOV2 person) {
        logger.info("Creating one person with V2");
        Person savedPerson = repository.save(mapper.convertVoToEntity(person));
        return mapper.convertEntityToVo(savedPerson);
    }*/

    public PersonVO update(PersonVO person){

        if(person == null) throw new RequiredObjectIsNullException();

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

    @Transactional
    public PersonVO disablePerson(Long id){
        logger.info("Disabling one person");
        repository.disabledPerson(id);

        var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Person not found"));
        PersonVO vo = DozerMapper.parseObject(entity, PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
        return vo;
    }

    public void delete(Long id){
        logger.info("Deleting one person");
        repository.deleteById(id);
    }
}
