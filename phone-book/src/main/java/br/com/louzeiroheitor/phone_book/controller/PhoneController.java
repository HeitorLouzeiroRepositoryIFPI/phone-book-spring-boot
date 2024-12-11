package br.com.louzeiroheitor.phone_book.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


import br.com.louzeiroheitor.phone_book.models.Phone;
import org.springframework.ui.Model;

@Controller
public class PhoneController {

    @GetMapping("/")
    public String phone(Model model) {
        model.addAttribute("phone", new Phone());
        return "phoneManagement/cadastrar-telefone";
    } 
}
