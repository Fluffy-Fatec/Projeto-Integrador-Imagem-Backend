package com.imagem.deleteuser.controller;

import com.imagem.deleteuser.collections.Log;
import com.imagem.deleteuser.dto.BlackListDTO;
import com.imagem.deleteuser.repository.LogRepository;
import com.imagem.deleteuser.service.BlacklistService;
import com.imagem.deleteuser.service.LogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequestMapping
@RestController
public class Controller {

    private final BlacklistService blacklistService;

    private final LogRepository logRepository;

    private final LogService logService;

    public Controller(BlacklistService blacklistService, LogRepository logRepository, LogService logService) {
        this.blacklistService = blacklistService;
        this.logRepository = logRepository;
        this.logService = logService;
    }

    @PostMapping(value = "/blacklist")
    public void save(@RequestBody BlackListDTO dto) {
        System.out.println("oooioioioi");
        blacklistService.save(dto.getId());
    }

    @PostMapping("/log")
    public void save(@RequestBody Log log){
        logRepository.save(log);
    }

    @GetMapping("/logged/all")
    public ResponseEntity<Integer> listAllLogin(){

        Integer allLogins = logService.listAllLogin();
        return ResponseEntity.ok().body(allLogins);
    }

    @GetMapping("/logged/day")
    public ResponseEntity<Integer> listLoginByDay(){

        LocalDate dataAtual = LocalDate.now();

        // Define o formato desejado
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Formata a data
        String dataFormatada = dataAtual.format(formatter);

        Integer allLogins = logRepository.findByRegistroRegexAndCreationDateRegex("logged",dataFormatada).size();
        return ResponseEntity.ok().body(allLogins);
    }

    @GetMapping("/log/list")
    public ResponseEntity<List<Log>> listAllLogs(){

        List<Log> listAllLogs = logRepository.findAll();

        return ResponseEntity.ok().body(listAllLogs);
    }

    @GetMapping("/log/count")
    public ResponseEntity<Integer> countLogsByDay(){


        LocalDate dataAtual = LocalDate.now();

        // Define o formato desejado
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Formata a data
        String dataFormatada = dataAtual.format(formatter);

        System.out.println(dataFormatada);

        Integer listAllLogs = logRepository.findByCreationDateRegex(dataFormatada).size();

        return ResponseEntity.ok().body(listAllLogs);
    }
}
