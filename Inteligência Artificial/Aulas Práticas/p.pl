homem('Afonso Henriques','rei de Portugal',1109).
homem('Henrique de Borgonha','conde de Portugal',1069).

homem('Sancho I','rei de Portugal',1154).
homem('Fernando II','rei de Leão',1137).
homem('Afonso IX', 'rei de Leão e Castela', 1171).
homem('Afonso II', 'rei de Portugal',1185).

homem('Sancho II', 'rei de Portugal',1207).
homem('Afonso III', 'rei de Portugal',1210).


mulher('Teresa de Castela', 'condessa de Portugal', 1080).
mulher('Mafalda', 'condessa de Saboia', 1125).
mulher('Urraca', 'infanta de Portugal',1151).
mulher('Dulce de Barcelona','infanta de Aragão',1160).
mulher('Berengária', 'infanta de Portugal',1194).
mulher('Urraca C','infanta de Castela',1186).

filho('Afonso Henriques','Henrique de Borgonha').
filho('Afonso Henriques','Teresa de Castela').
filho('Urraca','Afonso Henriques').
filho('Sancho I','Afonso Henriques').
filho('Urraca','Mafalda').
filho('Sancho I','Mafalda').
filho('Afonso IX','Urraca').
filho('Afonso IX','Fernando II').
filho('Afonso II','Sancho I').
filho('Afonso II','Dulce de Barcelona').
filho('Berengária','Sancho I').
filho('Berengária','Dulce de Barcelona').
filho('Sancho II','Afonso II').
filho('Afonso III','Afonso II').
filho('Sancho II','Urraca C').
filho('Afonso III','Urraca C').

irmao(Nome1, Nome2):- filho(Nome1, X),
                      filho(Nome2, X),
                      Nome1 \= Nome2.

primoDireito(Nome1, Nome2):- filho(Nome1, X),
                             filho(Nome2, Y),
                             irmao(X, Y).

% Z é o que deve ser exstraido para a lista
% setof elimina repeticoes do findall
irmaos(X, L):- setof(Z, irmao(X, Z), L).

primo(X, Y):- primoDireito(X, Y).
primo(X, Y):- filho(Y, A),
              primoDireito(X, A).
primo(X, Y):-filho(X, A),
             primoDireito(Y, A).
              
primos(X, L):- setof(Y, primo(X, Y), L).

esposa(X, Y):- mulher(X,_,_), homem(Y,_,_), filho(Z, X), filho(Z, Y).

% Y é ascendente de X
ascendente(X, Y):- filho(X, Y).
ascendente(X, Y):- filho(X, Z), ascendente(Z, Y).
descende(X, L):- setof(Y, ascendente(X, Y), L).

pai(X, Y):- filho(X, Y), homem(Y,_,_).
mae(X, Y):- filho(X, Y), mulher(Y,_,_).

ascendentes(X, c(X, AP, AM)):- pai(X, P),
                               ascendentes(P, AP),
                               mae(X, M),
                               ascendentes(M, AM).
ascendentes(X, c(X,0,0)).
