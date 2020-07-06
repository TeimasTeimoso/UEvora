% Estados dados por um tuplo com as coordenadas da sala
estado_inicial((1, 1)).
estado_final((1, 4)).

% bloqueadas(sala1, sal2).
bloqueada((1,1), (1,2)).
bloqueada((1,2), (1,1)).
bloqueada((2,1), (2,2)).
bloqueada((2,2), (2,1)).
bloqueada((3,1), (4,1)).
bloqueada((4,1), (3,1)).
bloqueada((3,2), (3,3)).
bloqueada((3,3), (3,2)).
bloqueada((4,2), (4,3)).
bloqueada((4,3), (4,2)).

% op(sala_atual, direcao, sala_seguinte, custo).
op((X, Y), esquerda, (W, Y), 1):-
    X > 1,
    W is X-1,
    \+ bloqueada((X, Y), (W, Y)).

op((X, Y), baixo, (X, Z), 1):-
    Y < 4,
    Z is Y+1,
    \+ bloqueada((X, Y), (X,Z)).

op((X, Y), direita, (W, Y), 1):-
    X < 4,
    W is X+1,
    \+ bloqueada((X, Y), (W, Y)).

op((X, Y), cima, (X, Z), 1):- 
    Y > 1,
    Z is Y-1,
    \+ bloqueada((X, Y), (X,Z)).

% Predicado que servirá para contar os estados visitados e em memória
:- dynamic(visitados/1).

pesquisa_local_hill_climbingSemCiclos(E, _) :- 
    retract(visitados(X)),
    X1 is X+1,
    asserta(visitados(X1)),
	estado_final(E),
	write(E), write(' ').

pesquisa_local_hill_climbingSemCiclos(E, L) :- 
	write(E), write(' '),
	expande(E,LSeg),
	sort(3, @=<, LSeg, LOrd),
	obtem_no(LOrd, no(ES, Op, _)),
	\+ member(ES, L),
	write(Op), nl,
	(pesquisa_local_hill_climbingSemCiclos(ES,[E|L]) ; write(undo(Op)), write(' '), fail).

expande(E, L):- 
	findall(no(En,Opn, Heur),
                (op(E,Opn,En,_), heur(En, Heur)),
                L).

obtem_no([H|_], H).
obtem_no([_|T], H1) :-
	obtem_no(T, H1).

pesquisa :-
    asserta(visitados(0)),
	estado_inicial(S0),
    pesquisa_local_hill_climbingSemCiclos(S0, []),
    nl,
    write("Visitados: " ),
    retract(visitados(Y)),
    write(Y), nl.

% Heuristica admissivel
heur(_, 1). 
