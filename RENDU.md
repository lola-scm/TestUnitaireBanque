Lola MOUREAU 
Campus de Toulon.

Partie A 

Pour la casse Compte :

La règle C3 ne fonctionnait pas, 
car la fonction créditer, acceptait un montant nul
(j'ai remplacé '<0' par '<= 0')
le test concerné est : shouldRejectZeroWhenCrediting

La règle C5 
la focntion refusait un découvert qui atteint exactement la limite,
j'ai rempkacé '<0' par '<= 0'.
le test concerné est : shouldAcceptDebitAtOverdraftLimit

La règle C6 
la fonction ne reconnaissait pas 0.00 comme un solde null 
le test concerné est : shouldBeEmptyWhenBalanceIsZero