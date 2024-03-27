package com.imagem.backend.validators;

import com.imagem.backend.dtos.RegisterDTO;
import com.imagem.backend.exceptions.InvalidCelphone;
import com.imagem.backend.exceptions.InvalidCpf;
import com.imagem.backend.exceptions.InvalidPassword;
import com.imagem.backend.exceptions.UserCreateRequestNotValidException;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
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

        if(!matcher.matches()) throw new InvalidCelphone();
    }

    public void validCpf(String cpf){
        // Expressão regular para validar Cpf
        String regex = "^(?!000\\.000\\.000-00|111\\.111\\.111-11|222\\.222\\.222-22|333\\.333\\.333-33|" +
                "444\\.444\\.444-44|555\\.555\\.555-55|666\\.666\\.666-66|777\\.777\\.777-77|888\\.888\\.888-88|" +
                "999\\.999\\.999-99)(\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2})$\n";

        // Compila a expressão regular em um padrão
        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(cpf);

        if(matcher.matches()) throw new InvalidCpf();
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

        if(!matcher.matches()) throw new InvalidPassword();

    }

}