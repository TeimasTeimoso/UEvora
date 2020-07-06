:- use_module(library(clpfd)).

printBoard:-
    estado_inicial(E),
    transpose(E, B),
    printMatriz(B).

printBoard(E):-
    transpose(E, B),
    printMatriz(B).

%Imprime uma matriz
printMatriz([]).
printMatriz([H|T]) :- printLinha(H),
                      nl,
                      printMatriz(T).

% Imprime uma linha da matriz
printLinha([]).
printLinha([H|T]):- write(H),
                    write(" "),
                    printLinha(T).

