package com.exemplo.facade;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FreteTest {

    private Frete frete;

    @BeforeEach
    void setUp() {
        frete = new Frete();
    }

    @Test
    void calcularDeveLancarExcecaoQuandoCepForNulo() {
        assertThrows(IllegalArgumentException.class, () -> frete.calcular(null, 100.0));
    }

    @Test
    void calcularDeveLancarExcecaoQuandoCepForInvalido() {
        assertThrows(IllegalArgumentException.class, () -> frete.calcular("1234-567", 100.0));
    }

    @Test
    void calcularDeveRetornarFreteGratisQuandoValorAtingirMinimo() {
        assertEquals(0.0, frete.calcular("12345678", Frete.VALOR_MINIMO_FRETE_GRATIS));
    }

    @Test
    void calcularDeveRetornarFreteRegionalQuandoCepComecarComZero() {
        assertEquals(Frete.FRETE_REGIONAL, frete.calcular("01234567", 100.0));
    }

    @Test
    void calcularDeveRetornarFreteNacionalQuandoCepNaoComecarComZero() {
        assertEquals(Frete.FRETE_NACIONAL, frete.calcular("12345678", 100.0));
    }
}
