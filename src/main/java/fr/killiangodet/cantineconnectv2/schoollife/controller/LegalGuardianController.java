package fr.killiangodet.cantineconnectv2.schoollife.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/legal-guardians")
public class LegalGuardianController {

    @GetMapping
    public List<String> getStudentList() {
        return List.of("Legal Guardian 1", "Legal Guardian 2", "Legal Guardian 3");
    }

}
