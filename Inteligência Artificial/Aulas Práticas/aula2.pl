%inicialmente estao ambos vazios
estado_inicial((0,0)).

%Ter 1L no segundo jarro
estado_final((_,1)).

capacidade(c1, 3).
capacidade(c2, 2).

%op(estado_atual, operacao, estado_seguinte, custo)
%A \= A1 confirma que o jarro nao ta ja cheio ao encher
op((A,B), encher(c1), (A1, B), 1):- capacidade(c1, A1), A \= A1.
op((A,B), encher(c2), (A, B2), 1):- capacidade(c2, B2), B \= B2.
op((A,B), despejar(c1), (0, B), 1):- A \= 0.
op((A,B), despejar(c2), (A,0), 1):- B \= 0.
%Quando é possivel passar tudo o que ta no jarro 1 par ao 2
op((A, B), despejar(c1, c2), (0, B2), 1):- capacidade(c2, X),
                                            X >= A+B,
                                            B2 is A+B.
%Quando nao é possivel passar tudo do c1 para c2
op((A,B), despejar(c1, c2), (A1, B2), 1):- capacidade(c2, B2),
                                          B2 < A+B,
                                          A1 is A-(B2-B).
%As mesmas operacoes que as 2 acima mas para o jarro 2 no 1
op((A,B), despejar(c2, c1), (A1, 0), 1):- capacidade(c1, X),
                                          X >= A+B,
                                          A1 is A+B.
op((A,B), despejar(c2, c1), (A1, B2), 1):- capacidade(c1, A1),
                                           A1 < A+B,
                                           B2 is B-(A1-A).
                            
%Dado pelo prof
%representacao dos nos
%no(Estado,no_pai,Operador,Custo,Profundidade)
pesquisa_largura([no(E,Pai,Op,C,P)|_],no(E,Pai,Op,C,P)) :- 
	estado_final(E).
pesquisa_largura([E|R],Sol):- 
	expande(E,Lseg),
        insere_fim(Lseg,R,LFinal),
        pesquisa_largura(LFinal,Sol).

expande(no(E,Pai,Op,C,P),L):- 
	findall(no(En,no(E,Pai,Op,C,P), Opn, Cnn, P1),
                (op(E,Opn,En,Cn), P1 is P+1, Cnn is Cn+C),
                L).

pesquisa :-
	estado_inicial(S0),
	pesquisa_largura([no(S0,[],[],0,0)], S),
	write(S), nl.


insere_fim([],L,L).
insere_fim(L,[],L).
insere_fim(R,[A|S],[A|L]):- insere_fim(R,S,L).


