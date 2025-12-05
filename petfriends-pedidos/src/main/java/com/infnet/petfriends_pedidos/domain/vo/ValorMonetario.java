package com.infnet.petfriends_pedidos.domain.vo;

import jakarta.persistence.Embeddable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * QUESTÃO 2 DO AT - Value Object ValorMonetario
 * Encapsula BigDecimal com regras de negócio monetárias
 */
@Embeddable
public class ValorMonetario {

    private BigDecimal valor;

    // Construtor default (JPA)
    protected ValorMonetario() {}

    // Construtor privado (usar factory methods)
    private ValorMonetario(BigDecimal valor) {
        if (valor == null) {
            throw new IllegalArgumentException("Valor não pode ser nulo");
        }
        if (valor.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Valor não pode ser negativo");
        }
        // Sempre 2 casas decimais
        this.valor = valor.setScale(2, RoundingMode.HALF_UP);
    }

    // Factory methods
    public static ValorMonetario de(BigDecimal valor) {
        return new ValorMonetario(valor);
    }

    public static ValorMonetario de(double valor) {
        return new ValorMonetario(BigDecimal.valueOf(valor));
    }

    public static ValorMonetario zero() {
        return new ValorMonetario(BigDecimal.ZERO);
    }

    // Operações (sempre retornam novo objeto - imutabilidade)
    public ValorMonetario somar(ValorMonetario outro) {
        return new ValorMonetario(this.valor.add(outro.valor));
    }

    public ValorMonetario subtrair(ValorMonetario outro) {
        return new ValorMonetario(this.valor.subtract(outro.valor));
    }

    public boolean maiorQue(ValorMonetario outro) {
        return this.valor.compareTo(outro.valor) > 0;
    }

    public boolean menorQue(ValorMonetario outro) {
        return this.valor.compareTo(outro.valor) < 0;
    }

    // Getter
    public BigDecimal getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ValorMonetario)) return false;
        ValorMonetario that = (ValorMonetario) o;
        return Objects.equals(valor, that.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }

    @Override
    public String toString() {
        return "R$ " + valor.toString();
    }
}