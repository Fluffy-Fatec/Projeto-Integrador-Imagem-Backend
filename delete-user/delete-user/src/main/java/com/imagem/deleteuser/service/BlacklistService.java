package com.imagem.deleteuser.service;


import com.imagem.deleteuser.collections.BlackList;
import com.imagem.deleteuser.repository.BlackListRepository;
import org.springframework.stereotype.Service;

@Service
public class BlacklistService {

    private final BlackListRepository blackListRepository;

    public BlacklistService(BlackListRepository blackListRepository) {
        this.blackListRepository = blackListRepository;
    }

    public void save(Integer id) {

        BlackList blackList = new BlackList();
        blackList.setIdBlacklist(id);

        blackListRepository.save(blackList);
    }
}
