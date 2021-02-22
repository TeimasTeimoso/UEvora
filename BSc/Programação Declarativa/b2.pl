:-use_module(library(clpfd)).
:-use_module(library(aggregate)).

not_squashed(Wt,(_,S2)):- Wt =< S2.

% Formula as hipoteses possiveis e junta à lista o peso
check_constraint(ItemSet,OriginalPack,[PL|[]], W, ListWithWeight):- nth0(PL, ItemSet, (WL,_)),
                                                        FW #= WL+W,
                                                        append(OriginalPack, [FW], ListWithWeight).
                                                        
check_constraint(ItemSet, OriginalPack, [P1, P2|T], W, ListWithWeight):- nth0(P1, ItemSet, (W1,_)),
                                        nth0(P2, ItemSet, I2),
                                        Wt #= W+W1,
                                        not_squashed(Wt, I2),
                                        check_constraint(ItemSet, OriginalPack, [P2|T], Wt, ListWithWeight).      


safe_pack(N, ItemSet, ListWithWeight):-  Nf is N-1,
                                        POccup in 0..N,
                                        length(P, POccup),
                                        P ins 0..Nf,
                                        all_different(P),
                                        check_constraint(ItemSet,P, P, 0, ListWithWeight),
                                        label(ListWithWeight),
                                        aggregate_all(max(Weight), last(ListWithWeight, Weight), MaxWeight),
                                        write(MaxWeight).