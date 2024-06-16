package com.imagem.deleteuser.service;

import com.imagem.deleteuser.collections.Log;
import com.imagem.deleteuser.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LogService {

    private final LogRepository logRepository;

    public ArrayList<Log> listAllLogin(){
        return (ArrayList<Log>) logRepository.findByRegistroRegex("logged");
    }
}
