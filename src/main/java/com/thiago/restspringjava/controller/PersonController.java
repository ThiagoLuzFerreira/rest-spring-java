package com.thiago.restspringjava.controller;

import com.thiago.restspringjava.data.vo.v1.PersonVO;
import com.thiago.restspringjava.data.vo.v2.PersonVOV2;
import com.thiago.restspringjava.service.PersonService;
import com.thiago.restspringjava.util.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/person/v1")
public class PersonController {

    @Autowired
    private PersonService service;

    @GetMapping(produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
    public ResponseEntity<List<PersonVO>> findAll(){
        return ResponseEntity.ok().body(service.findAll());
    }

    @GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
    public ResponseEntity<PersonVO> findById(@PathVariable(value = "id") Long id) {

       return ResponseEntity.ok().body(service.findById(id));
    }

    @PostMapping(produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML }, consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
    public ResponseEntity<PersonVO> create(@RequestBody PersonVO person){
        return ResponseEntity.ok().body(service.create(person));
    }

    @PostMapping(value = "/v2",produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML }, consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
    public ResponseEntity<PersonVOV2> createV2(@RequestBody PersonVOV2 person) {
        return ResponseEntity.ok().body(service.createV2(person));
    }

    @PutMapping(produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML }, consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
    public ResponseEntity<PersonVO> update(@RequestBody PersonVO person) {
        return ResponseEntity.ok().body(service.update(person));
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(value = "id") Long id) {
         service.delete(id);
    }

}
