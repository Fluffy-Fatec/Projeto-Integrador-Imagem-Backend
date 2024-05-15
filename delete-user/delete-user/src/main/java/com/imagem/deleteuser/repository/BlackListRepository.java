package com.imagem.deleteuser.repository;

import com.imagem.deleteuser.collections.BlackList;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BlackListRepository extends MongoRepository<BlackList, String> {
}
