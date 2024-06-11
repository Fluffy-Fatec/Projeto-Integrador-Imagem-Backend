package com.imagem.deleteuser.collections;

import com.imagem.deleteuser.dto.UserLog;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "log")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Log {

    private String creationDate;

    private String registro;

    private UserLog usuario;

}
