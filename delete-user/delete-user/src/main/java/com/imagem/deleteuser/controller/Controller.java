package com.imagem.deleteuser.controller;

import com.imagem.deleteuser.collections.Log;
import com.imagem.deleteuser.dto.BlackListDTO;
import com.imagem.deleteuser.dto.LogsGroupByDay;
import com.imagem.deleteuser.repository.LogRepository;
import com.imagem.deleteuser.service.BlacklistService;
import com.imagem.deleteuser.service.LogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public ResponseEntity<List<Log>> listAllLogin(){

        List<Log> allLogins = logService.listAllLogin();
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

    @GetMapping("/log/group")
    public ResponseEntity<List<LogsGroupByDay>> groupLogsByDay() {
        List<Log> logs = logRepository.findByRegistroRegex("user has been created");

        // Agrupando os logs por data de criação (apenas a parte da data, sem a hora) e contando os registros por dia
        Map<String, Long> groupedLogs = logs.stream()
                .collect(Collectors.groupingBy(log -> {
                    // Extrair a data no formato YYYY-MM-DD
                    String datePart = extractDate(log.getCreationDate());
                    return datePart;
                }, Collectors.counting()));

        // Convertendo o Map em uma lista de LogsGroupByDay
        List<LogsGroupByDay> logsGroupByDayList = groupedLogs.entrySet().stream()
                .map(entry -> new LogsGroupByDay(entry.getKey(), entry.getValue().intValue()))
                .collect(Collectors.toList());


        return ResponseEntity.ok().body(logsGroupByDayList);
    }

    @GetMapping("/log/login")
    public ResponseEntity<List<LogsGroupByDay>> groupLoginByDay() {
        List<Log> logs = logRepository.findByRegistroRegex("logged");

        // Agrupando os logs por data de criação (apenas a parte da data, sem a hora) e contando os registros por dia
        Map<String, Long> groupedLogs = logs.stream()
                .collect(Collectors.groupingBy(log -> {
                    // Extrair a data no formato YYYY-MM-DD
                    String datePart = extractDate(log.getCreationDate());
                    return datePart;
                }, Collectors.counting()));

        // Convertendo o Map em uma lista de LogsGroupByDay
        List<LogsGroupByDay> logsGroupByDayList = groupedLogs.entrySet().stream()
                .map(entry -> new LogsGroupByDay(entry.getKey(), entry.getValue().intValue()))
                .collect(Collectors.toList());


        return ResponseEntity.ok().body(logsGroupByDayList);
    }

    public static String extractDate(String dateTime) {
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, DateTimeFormatter.ISO_DATE_TIME);
        return localDateTime.toLocalDate().toString();  // Extrai apenas a parte da data
    }
}
