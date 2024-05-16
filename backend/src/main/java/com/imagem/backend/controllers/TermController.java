package com.imagem.backend.controllers;

import com.imagem.backend.domain.NotificationTerm;
import com.imagem.backend.domain.Term;
import com.imagem.backend.domain.TermFunction;
import com.imagem.backend.dtos.AcceptFucntionsRequest;
import com.imagem.backend.dtos.GlobalResponseDTO;
import com.imagem.backend.services.StatusTermService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/notification")
    public ResponseEntity<List<NotificationTerm>> notificationTerm(){
        List<NotificationTerm> notification = this.statusTerm.notificationTerm();
        return ResponseEntity.ok().body(notification);
    }

    @PutMapping("/notification/update/{id}")
    public ResponseEntity updateNotification(@PathVariable("id") Integer id){
        this.statusTerm.updateNotificationTerm(id);
        return ResponseEntity.ok().body(new GlobalResponseDTO("Atualizacao de acesso realizada"));
    }

    @GetMapping("/function/list")
    public ResponseEntity<List<TermFunction>> listTermFunction(){
        List<TermFunction> termFunctions = this.statusTerm.listAllTermFunction();

        return ResponseEntity.ok().body(termFunctions);
    }

    @PostMapping("/function/accept")
    public ResponseEntity accepptFunctions(@RequestBody AcceptFucntionsRequest acceptFucntionsRequest){

        this.statusTerm.updateTermFunction(acceptFucntionsRequest.functionId(), acceptFucntionsRequest.username(),acceptFucntionsRequest.statusTerm());

        return ResponseEntity.ok().body(new GlobalResponseDTO("Atualizacao realizada"));
    }

}