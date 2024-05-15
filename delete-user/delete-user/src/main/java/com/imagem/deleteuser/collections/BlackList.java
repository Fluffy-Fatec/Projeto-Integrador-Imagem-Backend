package com.imagem.deleteuser.collections;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collation = "blacklist")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlackList {

    @Id
    private ObjectId _id;

    private Integer idBlacklist;
}
