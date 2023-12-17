package com.thiago.restspringjava.controller;

import com.thiago.restspringjava.data.vo.v1.PersonVO;
import com.thiago.restspringjava.data.vo.v2.PersonVOV2;
import com.thiago.restspringjava.service.PersonService;
import com.thiago.restspringjava.util.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin
@RestController
@RequestMapping("/api/person/v1")
@Tag(name = "People", description = "Endpoints to manage people")
public class PersonController {

    @Autowired
    private PersonService service;

    @GetMapping(produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
    @Operation(summary = "Finds All People", description = "Finds All People",
        tags = {"People"},
            responses = {
            @ApiResponse(description = "Success", responseCode = "200",
                content = {
                    @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = PersonVO.class))
                    )
                }),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content),
        }
    )
    public ResponseEntity<List<PersonVO>> findAll(){
        return ResponseEntity.ok().body(service.findAll());
    }


    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
    @Operation(summary = "Finds person by id", description = "Finds person by id",
            tags = {"People"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                        content = @Content(schema = @Schema(implementation = PersonVO.class))
                    ),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<PersonVO> findById(@PathVariable(value = "id") Long id) {

       return ResponseEntity.ok().body(service.findById(id));
    }

    @CrossOrigin(origins = {"http://localhost:8080", "https://thiago.com.br"})
    @PostMapping(produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML }, consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
    @Operation(summary = "Adds a new person", description = "Adds a new person",
            tags = {"People"},
            responses = {
                    @ApiResponse(description = "Created", responseCode = "201",
                            content = @Content(schema = @Schema(implementation = PersonVO.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<PersonVO> create(@RequestBody PersonVO person){
        return ResponseEntity.ok().body(service.create(person));
    }

    /*@PostMapping(value = "/v2",produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML }, consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
    public ResponseEntity<PersonVOV2> createV2(@RequestBody PersonVOV2 person) {
        return ResponseEntity.ok().body(service.createV2(person));
    }*/

    @PutMapping(produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML }, consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
    @Operation(summary = "Deletes a person", description = "Deletes a person",
            tags = {"People"},
            responses = {
                    @ApiResponse(description = "Updated", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<PersonVO> update(@RequestBody PersonVO person) {
        return ResponseEntity.ok().body(service.update(person));
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Finds person by id", description = "Finds person by id",
            tags = {"People"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = PersonVO.class))
                    ),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content),
            }
    )
    public void delete(@PathVariable(value = "id") Long id) {
         service.delete(id);
    }

}
