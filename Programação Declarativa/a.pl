%:- use_module(library(clpfd)).

% Packg: [(W1,S1), (W2, S2), ... ,(Wn, Sn)].
% Index:    0          1            (n-1)

% Verifica se o peso anterior é menor ou igual ao que aguenta.
not_squashed((W1,_),(_,S2)):- W1 =< S2.

% Se a tail for vazia (chegou ao elemento de baixo)
% Ent é sempre true.
safe_packing([_|[]]).
safe_packing([I1, I2|T]):-  not_squashed(I1, I2),
                            safe_packing([I2|T]).