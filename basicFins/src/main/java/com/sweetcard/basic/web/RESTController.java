package com.sweetcard.basic.web;

import com.sweetcard.basic.dao.jdbc.CustomerJdbc;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

//@RestController
//@RequestMapping("/")
public class RESTController {

    //@Autowired
    //CustomerJdbc customerJdbc;

    /*
    @Transactional
    @GetMapping("/update/{id}")
    public String makeLoh(@PathVariable Integer id)
    {
        customerJdbc.updateCustomer(id);
        return "loh";
    }
    */

    /*
    @RequestMapping(value = "/genpass/{password}")
    public String generatePassword(@PathVariable String password) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(8);
        return passwordEncoder.encode(password);
    }*/

    //@RequestMapping(value = "/Method_2",method = RequestMethod.POST)
    public String Method_2() {
        System.out.println("RESTController.Method_1 -> ");
        return "FinsPage_1";
    }
}
