package fr.esgi.banque.business;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Test d'exemple fourni pour vérifier que le projet est bien configuré.
 * À compléter (partie A).
 */
class CompteTest {

	@Test
	void shouldIncreaseBalanceWhenCrediting() {
		// Arrange
		Compte compte = new Compte("FR001", "Alice", new BigDecimal("100.00"), new BigDecimal("200"));

		// Act
		compte.crediter(new BigDecimal("50.00"));

		// Assert
		assertThat(compte.getSolde()).isEqualByComparingTo("150.00");
	}

	// Test pour la règle C1, numéro et titulaire obligatoire
	@Test
	void shouldRejectNullNumber() {
		assertThatThrownBy(()-> new Compte(null, "Alice", new BigDecimal(100), new BigDecimal(200)))
				.isInstanceOf(IllegalArgumentException.class);
	}
    @Test
	void shouldRejectNullTitulaire() {
		assertThatThrownBy(()-> new Compte("FR001", null, new BigDecimal(100), new BigDecimal(200)))
				.isInstanceOf(IllegalArgumentException.class);
	}

	// Test pour la règle C2, le découvert autorisé ne peut pas être négatif
	@Test
	void shouldRejectNegativeOverdraft() {
		assertThatThrownBy(()-> new Compte("FR001", "Alice", new BigDecimal(100), new BigDecimal(-1)))
				.isInstanceOf(IllegalArgumentException.class);
	}

	// Test pour la règle C3, on ne crédite que des montants positifs
	@Test
	void shouldRejectZeroWhenCrediting() {
		Compte compte = new Compte("FR001", "Alice", new BigDecimal(100), new BigDecimal(200));
		assertThatThrownBy(()-> compte.crediter(new BigDecimal(0)))
				.isInstanceOf(IllegalArgumentException.class);
	}

	// Test pour la règle C4, on ne débite que des montants positifs
	@Test
	void shouldRejectZeroWhenDebiting() {
		Compte compte = new Compte("FR001", "Alice", new BigDecimal(100), new BigDecimal(200));
		assertThatThrownBy(()-> compte.debiter(new BigDecimal(0)))
				.isInstanceOf(IllegalArgumentException.class);
	}

	// test pour la règle C5, on ne peut pas dépasser le découvert autorisé
	@Test
	void shouldAcceptDebitAtOverdraftLimit() {
		Compte compte = new Compte("FR001", "Alice", new BigDecimal(100), new BigDecimal(200));
		compte.debiter(new BigDecimal(300));
		assertThat(compte.getSolde()).isEqualByComparingTo("-200");
	}

	@Test
	void shouldRejectDebitBeyondOverdraftLimit() {
		Compte compte = new Compte("FR001", "Alice", new BigDecimal(100), new BigDecimal(200));
		assertThatThrownBy(()-> compte.debiter(new BigDecimal("300.01")))
				.isInstanceOf(SoldeInsuffisantException.class);
	}

	// test pour la règle C6, le compte est vide quand il atteint 0/0.00 ...
	@Test
	void shouldBeEmptyWhenBalanceIsZero() {
		Compte compte = new Compte("FR001", "Alice", new BigDecimal("0.00"), new BigDecimal(200));
		assertThat(compte.estVide()).isTrue();
	}

	// test pour la regle C7, le compte est a découvert quand le solde est négatif
	@Test
	void shouldBeOverdrawnWhenBalanceIsNegative() {
		Compte compte = new Compte("FR001", "Alice", new BigDecimal(-10), new BigDecimal(200));
		assertThat(compte.estADecouvert()).isTrue();
	}

}
