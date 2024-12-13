package br.com.louzeiroheitor.phone_book.controller;

import br.com.louzeiroheitor.phone_book.models.Phones;
import jakarta.annotation.PostConstruct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/phoneManagement")
public class PhoneController {

    private List<Phones> phones = new ArrayList<>();
    private final int PAGE_SIZE = 5;

    @PostConstruct
    public void init() {
        phones.add(new Phones(UUID.randomUUID(), "John Doe", "123-456-7890"));
        phones.add(new Phones(UUID.randomUUID(), "Jane Smith", "987-654-3210"));
        phones.add(new Phones(UUID.randomUUID(), "Peter Jones", "111-222-3333"));
        phones.add(new Phones(UUID.randomUUID(), "Maria Oliveira", "444-555-6666"));
        phones.add(new Phones(UUID.randomUUID(), "Ana Souza", "777-888-9999"));
        phones.add(new Phones(UUID.randomUUID(), "Bob Silva", "222-333-4444"));
        phones.add(new Phones(UUID.randomUUID(), "Charlie Brown", "555-666-7777"));
        phones.add(new Phones(UUID.randomUUID(), "Diana Green", "888-999-0000"));
        phones.add(new Phones(UUID.randomUUID(), "Ethan White", "333-444-5555"));
        phones.add(new Phones(UUID.randomUUID(), "Fiona Black", "666-777-8888"));
        phones.add(new Phones(UUID.randomUUID(), "George Grey", "999-000-1111"));
        phones.add(new Phones(UUID.randomUUID(), "Hannah Blue", "444-333-2222"));

    }


    @GetMapping("/novo-telefone")
    public String showAddPhoneForm(Model model) {
        model.addAttribute("phone", new Phones()); // Create an empty Phone to bind to the form
        return "phoneManagement/cadastrar-telefone";
    }
    
    @PostMapping("/cadastrar-telefone")
    public String postCadastrarPhone(@ModelAttribute Phones phone, RedirectAttributes redirectAttributes) {
        phone.setId(UUID.randomUUID());
        phones.add(phone);
        redirectAttributes.addFlashAttribute("message", "Phone added successfully.");
        return "redirect:/phoneManagement/listar-telefones";
    }

    @GetMapping("/listar-telefones")
    public String getListarTelefones(Model model, @RequestParam(defaultValue = "0") int page,
                                     @ModelAttribute("message") String message,@ModelAttribute("error") String error) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        Page<Phones> phonePage = getPaginatedPhones(pageable);


        model.addAttribute("phonePage", phonePage);
        if(message != null && !message.isEmpty()){
            model.addAttribute("message", message);
        }
        if(error != null && !error.isEmpty()){
            model.addAttribute("error", error);
        }
        return "phoneManagement/listar-telefones";
    }

     @PostMapping("/{id}/deletar-telefone")
    public String deletePhone(@PathVariable UUID id, RedirectAttributes redirectAttributes) {
        try {
            phones.removeIf(phone -> phone.getId().equals(id));
            redirectAttributes.addFlashAttribute("message", "Phone deleted successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error deleting phone: " + e.getMessage());
        }
        return "redirect:/phoneManagement/listar-telefones";
    }


     @GetMapping("/editar-telefone")
     public String showEditForm(@RequestParam UUID id, Model model){
        Phones phone = phones.stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);
        if (phone == null) {
            return "redirect:/phoneManagement/listar-telefones";
        }
        model.addAttribute("phone", phone);
        return "phoneManagement/editar-telefone";
     }

    @PostMapping("/editar-telefone")
    public String atualizar(@ModelAttribute Phones phone, RedirectAttributes redirectAttributes) {
           boolean updated = false;
        for (int i = 0; i < phones.size(); i++) {
            if(phones.get(i).getId().equals(phone.getId())){
                 phones.set(i, phone);
                 updated = true;
                 break;
             }
        }
        if (updated) {
            redirectAttributes.addFlashAttribute("message", "Phone updated successfully.");
        } else {
              redirectAttributes.addFlashAttribute("error", "Error updating phone.");
        }
           
        return "redirect:/phoneManagement/listar-telefones";
    }

    private Page<Phones> getPaginatedPhones(Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), phones.size());
        List<Phones> sublist = phones.subList(start, end);
        return new PageImpl<>(sublist, pageable, phones.size());
    }
}