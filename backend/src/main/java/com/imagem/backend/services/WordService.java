package com.imagem.backend.services;

import com.imagem.backend.domain.Word;
import com.imagem.backend.repositories.WordRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class WordService {

    private final WordRepository wordRepository;


    public WordService(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }


    public List<Word> listAllWords(){
        return this.wordRepository.findAll();
    }
}
