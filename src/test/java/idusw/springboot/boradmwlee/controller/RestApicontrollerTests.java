package idusw.springboot.boradmwlee.controller;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;

@SpringBootTest
public class RestApicontrollerTests {

    @Autowired
    RestApiController restApiController;
    @Test
    public void postTest(){
        Model model = null;
        restApiController.updateMember(123L, model);
    }
}
