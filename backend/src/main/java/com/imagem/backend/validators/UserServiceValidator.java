package com.imagem.backend.validators;

import com.imagem.backend.dtos.RegisterDTO;
import com.imagem.backend.exceptions.InvalidCelphone;
import com.imagem.backend.exceptions.InvalidCpf;
import com.imagem.backend.exceptions.InvalidPassword;
import com.imagem.backend.exceptions.UserCreateRequestNotValidException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class UserServiceValidator {




    public void apply(RegisterDTO dto){
        this.validCelphone(dto.celular());
        this.validCpf(dto.cpf());
        this.validPassword(dto.password());
    }

    public void validCelphone(String numero) {
        // Expressão regular para validar um número de celular brasileiro
        String regex = "^^(\\(?11\\)?|\\(?12\\)?|\\(?13\\)?|\\(?14\\)?|\\(?15\\)?" +
                "|\\(?16\\)?|\\(?17\\)?|\\(?18\\)?|\\(?19\\)?|\\(?21\\)?|\\(?22\\)?" +
                "|\\(?24\\)?|\\(?27\\)?|\\(?28\\)?|\\(?31\\)?|\\(?32\\)?|\\(?33\\)?" +
                "|\\(?34\\)?|\\(?35\\)?|\\(?37\\)?|\\(?38\\)?|\\(?41\\)?|\\(?42\\)?" +
                "|\\(?43\\)?|\\(?44\\)?|\\(?45\\)?|\\(?46\\)?|\\(?47\\)?|\\(?48\\)?" +
                "|\\(?49\\)?|\\(?51\\)?|\\(?53\\)?|\\(?54\\)?|\\(?55\\)?|\\(?61\\)?" +
                "|\\(?62\\)?|\\(?63\\)?|\\(?64\\)?|\\(?65\\)?|\\(?66\\)?|\\(?67\\)?" +
                "|\\(?68\\)?|\\(?69\\)?|\\(?71\\)?|\\(?73\\)?|\\(?74\\)?|\\(?75\\)?" +
                "|\\(?77\\)?|\\(?79\\)?|\\(?81\\)?|\\(?82\\)?|\\(?83\\)?|\\(?84\\)?" +
                "|\\(?85\\)?|\\(?86\\)?|\\(?87\\)?|\\(?88\\)?|\\(?89\\)?|\\(?91\\)?" +
                "|\\(?92\\)?|\\(?93\\)?|\\(?94\\)?|\\(?95\\)?|\\(?96\\)?|\\(?97\\)?" +
                "|\\(?98\\)?|\\(?99\\)?) ?9[0-9]{4}-?[0-9]{4}$";

        // Compila a expressão regular em um padrão
        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(numero);

        log.info("Validando celular...");
        if(!matcher.matches()) throw new InvalidCelphone();
    }

    public void validCpf(String cpf){
        // Expressão regular para validar Cpf
        String regex = "^(?!([0-9])\\1{2}\\.\\1{3}\\.\\1{3}-\\1{2}$)(\\d{3}\\.){2}\\d{3}-\\d{2}$";

        // Compila a expressão regular em um padrão
        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(cpf);

        log.info("Validando cpf...");
        if(!matcher.matches() || cpf.length() != 14) throw new InvalidCpf();
    }

    public void validPassword(String password){

//        Expressão regular para validar senha
//        A senha deve conter pelo menos um dígito
//        A senha deve conter pelo menos um caractere minúsculo
//        A senha deve conter pelo menos um caracter maiúsculo
//        A senha deve conter pelo menos um caractere especial
//        A senha deve conter no mínimo 8 caracteres e no máximo 20 caracteres.
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";


        // Compila a expressão regular em um padrão
        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(password);

        log.info("Validando senha...");
        if(!matcher.matches()) throw new InvalidPassword();

    }

}