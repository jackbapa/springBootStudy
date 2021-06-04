package com.demo.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class testMyTemplateRest{
    @Autowired
    RestTemplate mytemplatetest;

    public testMyTemplateRest() {
    }

    @Test
    public void testfunc(){
        ResponseEntity<String> res = this.mytemplatetest.getForEntity("https://www.baidu.com",String.class);
        var ress = res.getBody();
        System.out.println(ress);
    }

}