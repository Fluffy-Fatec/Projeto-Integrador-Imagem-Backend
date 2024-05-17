package com.imagem.deleteuser.controller;

import com.imagem.deleteuser.dto.BlackListDTO;
import com.imagem.deleteuser.service.BlacklistService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping
@RestController
public class BlacklistController {

    private final BlacklistService blacklistService;

    public BlacklistController(BlacklistService blacklistService) {
        this.blacklistService = blacklistService;
    }

    @PostMapping(value = "/blacklist")
    public void save(@RequestBody BlackListDTO dto) {
        System.out.println("oooioioioi");
        blacklistService.save(dto.getId());
    }
}
