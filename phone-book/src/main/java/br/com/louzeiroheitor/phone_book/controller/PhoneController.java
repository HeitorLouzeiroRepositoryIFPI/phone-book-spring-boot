package br.com.louzeiroheitor.phone_book.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import br.com.louzeiroheitor.phone_book.models.Phones;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
public class PhoneController {

    private List<Phones> phones = new ArrayList<>();

    @GetMapping("/")
    public String phone(Model model) {
        model.addAttribute("phone", new Phones());
        return "phoneManagement/cadastrar-telefone";
    }

    @GetMapping("/listar-telefones")
    public String getListarTelefones(Model model) {
        model.addAttribute("phones", phones);
        return "phoneManagement/listar-telefones";
    }
    

    @PostMapping("/cadastrar-telefone")
    public String postCadastrarPhone(@ModelAttribute Phones phone) {
        phone.setId(UUID.randomUUID());
        phones.add(phone);
        return "phoneManagement/listar-telefones";
    }
    
}
