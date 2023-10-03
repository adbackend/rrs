package com.ms.review.service;

import com.ms.review.model.TestEntity;
import com.ms.review.repository.TestRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class TestService {

    private final TestRepository testRepository;

    public void create(String name, Integer age){

        TestEntity testEntity = new TestEntity(name, age);
        testRepository.save(testEntity);
    }

    public List<TestEntity> findAllByNameByJPA(String name){
        return testRepository.findAllByName(name);
    }

    public List<TestEntity> findAllByNameByQuerydsl(String name){
        return testRepository.findAllByNameByQuerydsl(name);
    }

}
