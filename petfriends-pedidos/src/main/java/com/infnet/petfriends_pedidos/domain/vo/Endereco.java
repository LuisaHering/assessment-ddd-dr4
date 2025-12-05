package com.infnet.petfriends_pedidos.domain.vo;

import jakarta.persistence.Embeddable;
import java.util.Objects;

/**
 * QUESTÃO 2 DO AT - Value Object Endereco
 * Características:
 * - Imutável (sem setters)
 * - Sem identidade própria
 * - Validações no construtor
 * - Equals e HashCode baseados em valores
 */
@Embeddable
public class Endereco {

    private String rua;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;

    // Construtor default (obrigatório para JPA)
    protected Endereco() {}

    // Construtor com validações (garante objeto sempre válido)
    public Endereco(String rua, String numero, String bairro,
                    String cidade, String estado, String cep) {
        // Validações
        if (rua == null || rua.isBlank()) {
            throw new IllegalArgumentException("Rua não pode ser vazia");
        }
        if (cidade == null || cidade.isBlank()) {
            throw new IllegalArgumentException("Cidade não pode ser vazia");
        }
        if (estado == null || estado.isBlank()) {
            throw new IllegalArgumentException("Estado não pode ser vazio");
        }
        if (cep == null || !cep.matches("\\d{5}-?\\d{3}")) {
            throw new IllegalArgumentException("CEP inválido. Use formato: 12345-678");
        }

        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
    }

    // APENAS GETTERS (imutabilidade - não pode mudar depois de criado)
    public String getRua() { return rua; }
    public String getNumero() { return numero; }
    public String getComplemento() { return complemento; }
    public String getBairro() { return bairro; }
    public String getCidade() { return cidade; }
    public String getEstado() { return estado; }
    public String getCep() { return cep; }

    // Equals e HashCode baseados em TODOS os campos (value equality)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Endereco)) return false;
        Endereco endereco = (Endereco) o;
        return Objects.equals(rua, endereco.rua) &&
                Objects.equals(numero, endereco.numero) &&
                Objects.equals(cep, endereco.cep) &&
                Objects.equals(cidade, endereco.cidade) &&
                Objects.equals(estado, endereco.estado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rua, numero, cep, cidade, estado);
    }

    @Override
    public String toString() {
        return String.format("%s, %s - %s - %s/%s - CEP: %s",
                rua, numero, bairro, cidade, estado, cep);
    }
}