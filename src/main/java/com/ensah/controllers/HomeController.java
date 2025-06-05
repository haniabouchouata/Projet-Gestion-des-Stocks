package com.ensah.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @RequestMapping(value = "/")
    public String home() {
        return "Home";
    }

    @RequestMapping(value = "/home")
    public ModelAndView home2() {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("Home");
        return mav;
    }

    @RequestMapping(value = "/blank")
    public String blank() {
        return "blank/blank";
    }


}