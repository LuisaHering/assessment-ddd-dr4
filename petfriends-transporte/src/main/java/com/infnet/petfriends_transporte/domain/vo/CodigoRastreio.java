package com.infnet.petfriends_transporte.domain.vo;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.Random;

/**
 * QUESTAO 4 DO AT - Value Object CodigoRastreio
 * Imutavel, com validacao e geracao automatica
 */
@Embeddable
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class CodigoRastreio {

    private String codigo;

    private CodigoRastreio(String codigo) {
        if (codigo == null || !codigo.matches("[A-Z]{2}\\d{9}BR")) {
            throw new IllegalArgumentException("Codigo de rastreio invalido");
        }
        this.codigo = codigo;
    }

    /**
     * Gera codigo unico de rastreio
     * Formato: PF123456789BR
     */
    public static CodigoRastreio gerar() {
        String prefixo = "PF"; // PetFriends
        String numero = String.format("%09d", new Random().nextInt(999999999));
        String sufixo = "BR";
        return new CodigoRastreio(prefixo + numero + sufixo);
    }

    /**
     * Cria de string existente
     */
    public static CodigoRastreio de(String codigo) {
        return new CodigoRastreio(codigo);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CodigoRastreio that)) return false;
        return Objects.equals(codigo, that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return codigo;
    }
}
