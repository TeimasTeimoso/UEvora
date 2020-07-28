:-use_module(library(clpfd)).

not_squashed(Wt,(_,S2)):- Wt #=< S2.


check_constraint(_,[_|[]], _).
check_constraint(ItemSet, [P1, P2|T], W):- nth0(P1, ItemSet, (W1,_)),
                                        nth0(P2, ItemSet, I2),
                                        Wt #= W+W1,
                                        not_squashed(Wt, I2),
                                        check_constraint(ItemSet, [P2|T], Wt).        

safe_pack(N, ItemSet, P):-  Nf is N-1,
                            length(P, N),
                            P ins 0..Nf,
                            all_different(P),
                            check_constraint(ItemSet, P, 0),
                            label(P).
