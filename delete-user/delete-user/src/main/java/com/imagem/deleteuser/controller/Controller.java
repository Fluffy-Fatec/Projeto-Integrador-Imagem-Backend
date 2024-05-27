package com.imagem.deleteuser.controller;

import com.imagem.deleteuser.collections.Log;
import com.imagem.deleteuser.dto.BlackListDTO;
import com.imagem.deleteuser.dto.LogSender;
import com.imagem.deleteuser.repository.LogRepository;
import com.imagem.deleteuser.service.BlacklistService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping
@RestController
public class Controller {

    private final BlacklistService blacklistService;

    private final LogRepository logRepository;

    public Controller(BlacklistService blacklistService, LogRepository logRepository) {
        this.blacklistService = blacklistService;
        this.logRepository = logRepository;
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
}
