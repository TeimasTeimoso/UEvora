#include <stdlib.h>
#include <stdbool.h>

typedef struct t_decls_ *t_decls;
typedef struct t_decl_ *t_decl;
typedef struct t_argdefs_ *t_argdefs;
typedef struct t_argdef_ *t_argdef;
typedef struct t_args_ *t_args;
typedef struct t_ids_ *t_ids;
typedef struct t_stms_ *t_stms;
typedef struct t_stm_ *t_stm;
typedef struct t_exp_ *t_exp;
typedef struct t_lit_ *t_lit;
typedef struct t_type_ *t_type;

t_decls t_decls_new(t_decl decl, t_decls decls);
t_decl t_decl_new_var(t_ids id, t_type type, t_exp exp);
t_decl t_decl_new_type(char *id, t_type type);
t_decl t_decl_new_funct(char *id, t_argdefs args, t_type returnType, t_stms stms);
t_argdefs t_argdefs_new_arg(t_argdef argdef, t_argdefs arglist);
t_argdef t_argdef_new_arg(char *id, t_type argtype);
t_args t_args_new_args(t_exp exp, t_args arglist);
t_ids t_ids_new_ids(char *id, t_ids idlist);
t_stms t_stms_new_stm(t_stm stm, t_stms stmlist);
t_stm t_stm_new_decl(t_decl decl);
t_stm t_stm_new_exp(t_exp exp);
t_stm t_stm_new_return(t_exp exp);
t_stm t_stm_new_if_else(t_exp condition, t_stms ifstm, t_stms elsestm);
t_stm t_stm_new_while(t_exp condition, t_stms ifstm);
t_stm t_stm_new_next();
t_type t_type_new(char* type);
t_type t_type_new_array(t_type type, int size);
t_lit t_lit_new_intlit(int lit);
t_lit t_lit_new_floatlit(float lit);
t_lit t_lit_new_strlit(char* lit);
t_lit t_lit_new_boollit(bool lit);
t_exp t_exp_new_lit(t_lit lit);
t_exp t_exp_new_id(char* id);
t_exp t_exp_new_array_access(t_exp exp, int lit);
t_exp t_exp_new_op(char* op, t_exp arg1, t_exp arg2);
t_exp t_exp_new_assign(t_exp id, t_exp exp);
t_exp t_exp_new_fcall(char *id, t_args args);

void t_decls_ant(t_decls ds);
void t_decl_ant(t_decl d);
void t_stms_ant(t_stms stms);
void t_stm_ant(t_stm stm);
t_type t_lit_ant(t_lit l);
t_type t_exp_ant(t_exp e);
bool arithmeticOperator(char* operator);