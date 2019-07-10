#include <stdio.h>
#include <string.h>
#include <stdbool.h>

#define VICTORY_POINTS 3
#define MAXCHARPERNAME 21

struct team
{
    char team_name[MAXCHARPERNAME];
    int victory;
    int draw;
    int defeat;
    int goals_scored;
    int goals_conceded;
    int total_points;
};

/*
Funcao que cria as equipas inicializando os seus atributos
Sendo o team_name passado pelo scanf
*/
void create_teams(int n_teams, struct team v[n_teams])
{
    
    for(int i = 0; i < n_teams; i++)
    {
        scanf("%s", v[i].team_name);
        v[i].victory = 0;
        v[i].draw = 0;
        v[i].defeat = 0;
        v[i].goals_scored = 0;
        v[i].goals_conceded = 0;
        v[i].total_points = 0;
    }

}

/*
Encontra o id da equipa(posicao no array)
Procurando pelo nome e comparado-o com o 
Campo team_name da Struct
*/
int find_team(char team_name[], int n_teams, struct team v[n_teams])
{

    for(int i = 0; i < n_teams; i++)
    {
        if(strcmp(v[i].team_name,team_name) == 0)
            return i;
    }

    return 0;
}

/*
Funcao que dá update dos golos marcados e sofridos a cada jogo
*/
void update_goals(int team1_id, int team2_id, struct team v[], int result1, int result2)
{
        v[team1_id].goals_scored += result1;
        v[team1_id].goals_conceded += result2;
        v[team2_id].goals_scored += result2;
        v[team2_id].goals_conceded += result1;
}

/*
Funcao que efetivamente passa as estatisticas de cada equipa
*/
void game_results(int n_games, int n_teams, struct team v[n_teams])
{
    char team1[MAXCHARPERNAME], team2[MAXCHARPERNAME];
    int result1, result2;

    for(int i = 0; i < n_games; i++)
    {

        scanf("%s %d - %s %d", team1, &result1, team2, &result2);

        int team1_id = find_team(team1, n_teams, v);
        int team2_id = find_team(team2, n_teams, v);

        update_goals(team1_id, team2_id, v, result1, result2);


        if(result1 > result2)
        {
            v[team1_id].victory++;
            v[team2_id].defeat++;
            v[team1_id].total_points += VICTORY_POINTS;
        }
        else if(result1 < result2)
        {

            v[team2_id].victory++;
            v[team1_id].defeat++;
            v[team2_id].total_points += VICTORY_POINTS;
        }
        else
        {
            v[team1_id].draw++;
            v[team2_id].draw++; 
            v[team1_id].total_points++;
            v[team2_id].total_points++; 
        }
    }
}

/*
Funcao que calcula a pontuacao total de cada equipa
E que faz a comparacao entre a pontuacao de cada
Retornando o id(posicao do array) da equipa vencedora,
Ou -1 caso nao exista nenhum vencedor
*/
int final_score(int n_teams, struct team v[n_teams])
{
    int winner = 0;
    bool tie = false;

    for(int i = 1; i < n_teams; i++)
    {
        if(v[i].total_points > v[winner].total_points)
        {
            winner = i;
            tie = false;
        }
        else if(v[i].total_points == v[winner].total_points)
            tie = true;
    }

    if(tie)
        return -1;
    
    return winner;
} 

/*
Funcao que serve exclusivamente para fazer o
Print final após ter sido achado o vencedor(caso exista)
*/
void winners(int n_teams, struct team v[n_teams])
{
    int team_id = final_score(n_teams, v);

    if(team_id == -1)
        printf("torneio sem vencedora\n");
    else
    {
        printf("a vencedora foi %s, com %d ponto(s)\n", v[team_id].team_name, v[team_id].total_points);
        printf("ganhou %d jogo(s), empatou %d jogo(s) e perdeu %d jogo(s)\n", v[team_id].victory, v[team_id].draw, v[team_id].defeat);
        printf("marcou %d golo(s) e sofreu %d golo(s)\n",v[team_id].goals_scored, v[team_id].goals_conceded);
    }

        

}

int main(void)
{
    int nmb_of_teams, nmb_of_games;

    scanf("%d %d", &nmb_of_teams, &nmb_of_games);

    struct team stats[nmb_of_teams];

    create_teams(nmb_of_teams, stats);

    game_results(nmb_of_games, nmb_of_teams, stats);

    winners(nmb_of_teams, stats);

    return 0;
}

