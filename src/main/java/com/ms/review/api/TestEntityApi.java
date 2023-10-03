package com.ms.review.api;

import com.ms.review.service.TestService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class TestEntityApi {

    private final TestService testService;

    @PostMapping("/test/entity/create")
    public void createTestEntity(CreateTestEntityRequest re){
        testService.create(re.getName(), re.getAge());
    }

    @AllArgsConstructor
    @Getter
    public static class CreateTestEntityRequest{
        private final String name;
        private final Integer age;
    }
}
