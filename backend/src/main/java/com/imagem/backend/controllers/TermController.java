package com.imagem.backend.controllers;

import com.imagem.backend.domain.StatusTerm;
import com.imagem.backend.domain.Term;
import com.imagem.backend.services.StatusTermService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/term")
public class TermController {

    private final StatusTermService statusTerm;

    public TermController(StatusTermService statusTerm) {
        this.statusTerm = statusTerm;
    }

    @GetMapping
    public ResponseEntity<Term> termVersion(){
        Term term = this.statusTerm.termActual();

        return ResponseEntity.ok(term);
    }
}
