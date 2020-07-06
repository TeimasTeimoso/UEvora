% Adiciona elementos na lista pela cabeça
append(X,[],[X]).
append(X,[H|T], [X|W]):- append(H,T, W).


% Representacao do problema 
% O tabuleiro é identificado por uma lista de tuplos (p(Coluna,Posicao), Heuristica).

geraTabuleiro([],_,_).
geraTabuleiro([H|T], N, P):-random(1, N, R),
                            H = (p(P, R), 0),
                            P1 is P+1,
                            geraTabuleiro(T, N, P1).

resolve(N):-length(L, N),
            geraTabuleiro(L, N, 1),
            heuristicaTabuleiro(L, L, NB),
            pesquisa_local(NB).


% Se R for zero, ent esta numa diagonal
diagonal(p(X, Y), p(W, Z), R):- abs(X-W, A),
                              abs(Y-Z, B),
                              R is A - B.

linha(p(_,Y), p(_,Y)).

calculaHeuristica(_,[],-1).
calculaHeuristica(p(X, Y), [H|T], Heur):- (A,_) = H,
                                        diagonal(p(X,Y), A, 0), !,
                                        calculaHeuristica(p(X, Y), T, Z),
                                        Heur is Z+1.
calculaHeuristica(p(X, Y), [H|T], Heur):- (A,_) = H,
                                        linha(p(X, Y), A), !,
                                        calculaHeuristica(p(X, Y), T, Z),
                                        Heur is Z+1.
calculaHeuristica(p(X, Y), [_|T], Heur):- calculaHeuristica(p(X, Y), T, Heur).


heuristicaTabuleiro([],_,_).
heuristicaTabuleiro([H|T], Tabuleiro, L):- (Pos,_) = H,
                            calculaHeuristica(Pos, Tabuleiro, Heur),
                            append((Pos, Heur), L1, L),
                            heuristicaTabuleiro(T, Tabuleiro, L1).

% Algoritmo acaba quando todos os estados tiverme 0 ataques
terminal([]).
terminal([H|T]):- (_,0) = H,
                  terminal(T).

pesquisa_local(E):-
    terminal(E),
    sort(1,@=<, E, X),
    length(X, L),
    printTabuleiro(X, L),
    write("ESTADO FINAL: "),
    write(X).

pesquisa_local(E):-
    random_permutation(E, X), %Escolhe a rainha random
    expande(X, EstadosCol),
    random_permutation(EstadosCol, PermutEstados),
    melhorVizinho(PermutEstados, NovaPos),
    %write("Tablueiro com melhor jogada "),
    %write(NovaPos), nl,
    pesquisa_local(NovaPos).

% expande o tabuleiro para todas as pos da coluna
expande([(p(X,_),_)|T], Vizinhos):- length([(p(X,_),_)|T], Len),
                                    findall([(p(X,Y),_)|T], between(1, Len, Y), TabList),
                                    heuristicaExp(TabList, Vizinhos).

heuristicaExp([],_).
heuristicaExp([PrimCol|R], E):- heuristicaExp(R, X),
                        heuristicaTabuleiro(PrimCol, PrimCol, TabHeur),
                        append(TabHeur, X, E).


melhorVizinho([[(p(X1,Y1),H)|T]], [(p(X1,Y1),H)|T]).
melhorVizinho([[(p(X1,Y1),H)|T]| TabR], Tabuleiro):-
    melhorVizinho(TabR, [(p(X2,Y2),HeurT)|Tt]),
    (H < HeurT, Tabuleiro = [(p(X1,Y1),H)|T] ; Tabuleiro = [(p(X2,Y2),HeurT)|Tt]).                


% Condicao de final de linha
printLinha(Tamanho,_,PosAtual):- PosAtual > Tamanho,!,
                                            nl.
printLinha(Tamanho, PosRainha, PosAtual):-PosAtual = PosRainha,!,
                                          write("- "),
                                          PosProx is PosAtual+1,
                                          printLinha(Tamanho, PosRainha, PosProx).
printLinha(Tamanho, PosRainha, PosAtual):-write("X "),
                                          PosProx is PosAtual+1,
                                          printLinha(Tamanho, PosRainha, PosProx).

printTabuleiro([],_).
printTabuleiro([(p(_,X),_)|T], Tamanho):- printLinha(Tamanho, X, 1),
                                          printTabuleiro(T, Tamanho).
      