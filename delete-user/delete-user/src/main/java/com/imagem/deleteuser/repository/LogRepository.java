package com.imagem.deleteuser.repository;

import com.imagem.deleteuser.collections.BlackList;
import com.imagem.deleteuser.collections.Log;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LogRepository extends MongoRepository<Log, String> {
}
