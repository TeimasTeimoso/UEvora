
%
%
%:- use_module(library(clpfd)).
%
%% Wt ? weight on top
%not_squashed(Wt,(_,S2)):- Wt =< S2.
%
%safe_packing(_,[_|[]]).
%safe_packing(PW, [(W1,_), I2|T]):-  Wt #= PW+W1,
%                                    not_squashed(Wt, I2),
%                                    safe_packing(Wt, [I2|T]).
%safe_packing([(W1,_), I2|T]):- safe_packing(0, [(W1,_), I2|T]).
%                            
%    
%safe(L, R):-
%    safe_packing(L),
%    label(R).
%    
%permut()

% CSP P = (V,D,C).
%
% V = conjunto de variáveis {V1, V2, ...}.
% D = dominio das variáveis, i.e, Di representa os valores possiveis de Vi.
% C = conjunto de restrições sobre V*, constraints
%--------------------------------------------------

%
%
% IDEIA
%   Variaveis = 0....(N-1)
%   Dominio = Items.
%   Restrições = Força do item tem de ser superior ou igual as anteriores, items têm de ser diferentes nas poscoes.
%

:- use_module(library(clpfd)).

%test(X,Y):- X in 0..10,
%            Y in 4..8,
%            X #> Y.
%
%t(N, List, R):-  Vars = 0..(N-1),
%                    member(X, List),
%                    Vars ins X,
%                    all_different(Vars),
%                    labeling(Vars, R).
%            
%t(List, R):-
%                    member(R, [List]).

%a(Items, R):- X = List, X ins 

%a(N, Items, Pack):- length(Pack, N),
%                    length(Items, Indexes),
%                    Pack ins 0..Indexes,
%                    all_different(Pack),
%                    label(Pack).
%

%a(N, Items, Pack):- length(Pack, N),
%                    I ins 1..N,
%                    Pack in I,
%                    all_different(Pack),
%                    %element(I, Pack, (W1,_)),
%                    %element(I+1, Pack, (_,S2)),
%                    %W1 #=<S2,
%                    label(Pack).

%a(N, Items, Pack):- length(Pack, N),
%                    length(Items, Indexes),
%                    I ins 1..Indexes,
%                    
%                    all_different(Pack),
%                    label(Pack).

b(N, Items, Pack):- length(Pack, N),
                    %length(Items, Indexes),
                    %I ins 1..N,
                    Pack in(Items),
                    label(Pack).
                    