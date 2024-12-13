package br.com.louzeiroheitor.phone_book.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.com.louzeiroheitor.phone_book.models.Phones;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/phoneManagement")
public class PhoneController {

    private List<Phones> phones = new ArrayList<>();

    @GetMapping("/")
    public String phone(Model model) {
        model.addAttribute("phone", new Phones());
        return "phoneManagement/cadastrar-telefone";
    }
    
    @PostMapping("/cadastrar-telefone")
    public String postCadastrarPhone(@ModelAttribute Phones phone) {
        phone.setId(UUID.randomUUID());
        phones.add(phone);
        return "redirect:/phoneManagement/listar-telefones";
    }

    @GetMapping("/listar-telefones")
    public String getListarTelefones(Model model) {
        model.addAttribute("phones", phones);
        return "phoneManagement/listar-telefones";
    }

    @PostMapping("/{id}/deletar-telefone")
    public String deletar(@PathVariable UUID id) {
        phones.removeIf(phone -> phone.getId().equals(id));
        return "redirect:/phoneManagement/listar-telefones";
    }

    
    //Handle showing the edit form
     @GetMapping("/editar-telefone")
     public String showEditForm(@RequestParam UUID id, Model model){
        Phones phone = phones.stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);
        if (phone == null) {
            return "redirect:/phoneManagement/listar-telefones";
        }
        model.addAttribute("phone", phone);
        return "phoneManagement/editar-telefone";
     }
     //Handle editing the phone
    @PostMapping("/editar-telefone")
    public String atualizar(@ModelAttribute Phones phone, RedirectAttributes redirectAttributes) {
        for (int i = 0; i < phones.size(); i++) {
            if(phones.get(i).getId().equals(phone.getId())){
                 phones.set(i, phone);
                 break;
             }
        }  
        return "redirect:/phoneManagement/listar-telefones";
    }


}
