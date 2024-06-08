package com.imagem.deleteuser.repository;

import com.imagem.deleteuser.collections.BlackList;
import com.imagem.deleteuser.collections.Log;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface LogRepository extends MongoRepository<Log, String> {
    List<Log> findByRegistroRegexAndCreationDateRegex(String registro, String creationDate);

    List<Log> findByRegistroRegex(String registro);

    List<Log> findByCreationDateRegex(String creationDate);

}
