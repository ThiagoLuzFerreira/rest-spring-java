package com.thiago.restspringjava.mapper.custom;

import com.thiago.restspringjava.data.vo.v2.PersonVOV2;
import com.thiago.restspringjava.model.Person;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PersonMapper {

    public PersonVOV2 convertEntityToVo(Person person){
        PersonVOV2 vo = new PersonVOV2();
        vo.setId(person.getId());
        vo.setFirstName(person.getFirstName());
        vo.setLastName(person.getLastName());
        vo.setBirthDay(new Date());
        vo.setGender(person.getGender());
        vo.setAddress(person.getAddress());
        return vo;
    }

    public Person convertVoToEntity(PersonVOV2 person){
        Person entity = new Person();
        entity.setId(person.getId());
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        //entity.setBirthDay(new Date());
        entity.setGender(person.getGender());
        entity.setAddress(person.getAddress());
        return entity;
    }
}
