package fr.esgi.banque.business;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CalculFraisTest {

    // test pour la règle F1 : titulaire mineur refusé et solde moyen obligatoire
    @Test
    void shouldRejectMinorHolder() {
        CalculFrais calcul = new CalculFrais();
        assertThatThrownBy(()-> calcul.fraisMensuels(17, new BigDecimal(500), false))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldRejectNullAverageBalance() {
        CalculFrais calcul = new CalculFrais();
        assertThatThrownBy(()-> calcul.fraisMensuels(30, null, false))
                .isInstanceOf(IllegalArgumentException.class);
    }

    // test pour la règle F2 : il faut retirer les 2,00 € de frais
    @Test
    void shouldChargeTwoEuros() {
        CalculFrais calcul = new CalculFrais();
        assertThat(calcul.fraisMensuels(30, new BigDecimal(500), false)).isEqualByComparingTo("2.00");
    }

    // test pour la règle F3 : frais offerts pour les moins de 26ans
    @Test
    void shouldBeFreeWhenTwentyFive() {
        CalculFrais calcul = new CalculFrais();
        assertThat(calcul.fraisMensuels(25, new BigDecimal(500), false)).isEqualByComparingTo("0");
    }

    // Test pour la règle F4 : frais payants si le solde moyen est sous 1000e
    @Test
    void shouldChargeWhenBalanceUnderThousand() {
        CalculFrais calcul = new CalculFrais();
        assertThat(calcul.fraisMensuels(30, new BigDecimal("999.99"), false)).isEqualByComparingTo("2.00");
    }


    // Test pour la règle F5 : les clients premium payent leur carte meme si la tenue est offerte

    @Test
    void shouldChargePremiumWhenTaxIsFree() {
        CalculFrais calcul = new CalculFrais();
        assertThat(calcul.fraisMensuels(20, new BigDecimal(500), true)).isEqualByComparingTo("5.00");
    }
}
