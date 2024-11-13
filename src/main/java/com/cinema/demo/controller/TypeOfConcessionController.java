package com.cinema.demo.controller;

import com.cinema.demo.entity.TypeOfConcessionEntity;
import com.cinema.demo.service.TypeOfConcessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/typeofconcession")
public class TypeOfConcessionController {

    @Autowired
    private TypeOfConcessionService typeOfConcessionService;

    @GetMapping("/types")
    public String listConcessionTypes(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "2") int size,
            @RequestParam(value = "search", defaultValue = "") String search,
            Model model
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<TypeOfConcessionEntity> concessionPage = typeOfConcessionService.getAllConcessionTypes(pageable, search);

        model.addAttribute("types", concessionPage.getContent());
        model.addAttribute("totalPages", concessionPage.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("search", search);
        return "admin/typeofconcession";
    }

    @PostMapping("/type/save")
    public String saveConcessionType(@ModelAttribute TypeOfConcessionEntity type) {
        if (type.getConcessionTypeId() != null) {
            // Call update method if id is present
            typeOfConcessionService.updateConcessionType(type);
        } else {
            // Call add method if no id is present
            typeOfConcessionService.saveConcessionType(type);
        }
        return "redirect:/admin/typeofconcession/types";
    }


    @PostMapping("/type/delete/{id}")
    public String deleteConcessionType(@PathVariable int id) {
        typeOfConcessionService.deleteConcessionType(id);
        return "redirect:/admin/typeofconcession/types";
    }
}
