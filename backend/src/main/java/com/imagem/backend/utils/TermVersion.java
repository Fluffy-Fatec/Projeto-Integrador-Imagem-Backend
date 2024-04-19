package com.imagem.backend.utils;

import com.imagem.backend.repositories.StatusTermoRepository;

public class TermVersion {

    private static TermVersion instance;

    private final StatusTermoRepository statusTermoRepository;

    // Construtor privado para evitar instanciação fora da classe
    private TermVersion(StatusTermoRepository statusTermoRepository) {
        this.statusTermoRepository = statusTermoRepository;
        // Aqui você pode inicializar recursos necessários
    }

    // Método estático para obter a instância única da classe
    public static TermVersion getInstance() {
        // Verifica se a instância ainda não foi criada
        if (instance == null) {
            // Se não foi criada, cria uma nova instância
            instance = new TermVersion();
        }
        // Retorna a instância única da classe
        return instance;
    }

}
