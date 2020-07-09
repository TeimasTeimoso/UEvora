#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include "apt.h"
#include "symbolTable.h"

struct t_decls_ {
    enum {DECLS_SINGLE, DECLS_LIST} kind;
    
    struct {
        t_decl decl;
        t_decls decls;
    } u;
};

struct t_decl_ {
    enum {VAR_DECL, VAR_DECL_W_VALUE, FUNC_DECL, FUNC_DECL_W_ARGS, TYPE_DECL} kind;

    union {
        struct {
            t_ids id; 
            t_type type;
            t_exp exp;
        } var;

        struct {
            char *id;
            t_type type;
        } newtype;

        struct {
            char *id;
            t_argdefs args;
            t_type returnType;
            t_stms stms;
        } funct;
    } u;
};

struct t_argdefs_ {
    enum {ARGS_SINGLE, ARGS_LIST} kind;

    struct {
        t_argdef argdef;
        t_argdefs argdefs;
    } u;
};

struct t_argdef_ {
    enum {ARGDEF} kind;

    struct {
        char *id;
        t_type type;
    } u;
};

struct t_args_ {
    enum {ARG_CALL_SINGLE, ARG_CALL_LIST} kind;

    struct {
        t_exp exp;
        t_args args;
    } u;
};

struct t_ids_ {
    enum {ID_SINGLE, ID_LIST} kind;

    struct {
        char *id;
        t_ids ids;
    } u;
};

struct t_stms_ {
    enum {STMS_SINLGE, STMS_LIST} kind;

    struct {
        t_stm stm;
        t_stms stms;
    } u;
};

struct t_stm_ {
    enum {DECL_STM, EXP_STM, RETURN_STM, IF_STM, IF_ELSE_STM, WHILE_STM, NEXT_STM} kind;

    union {
        t_decl decl;
        t_exp exp;
        struct {
            t_exp exp;
            t_stms stms;
            t_stms elseStms;
        } conditional;
    } u;
};

/* Este tipo tá-me a deixar confuso */
struct t_type_ {
    enum {T_INT, T_FLOAT, T_STRING, T_BOOL, T_VOID, T_NEW} kind;

    union {
        char *type;
        struct{
            int size;
            char *type;   
        } array;
    } u;
};

struct t_lit_ {
    enum {INTLIT, FLOATLIt, STRLIT, BOOLIT} kind;

    union {
        int intlit;
        float floatlit;
        char *strlit;
        bool boolit;
    } u;
};


struct t_exp_ {
    enum {LIT_EXP, ARRAY_EXP, ID_EXP, BINOP_EXP, UNOP_EXP, ASSIGN_EXP, FUNCT_CALL_EXP, FUNCT_CALL_W_ARG_EXP} kind;

    union {
        t_lit litexp;
        char* id;
        struct {
            t_exp exp;
            int lit;
        } array_access;
        struct {
            char* op;
            t_exp arg1;
            t_exp arg2;
        } binop;
        struct {
            char* op;
            t_exp arg;
        } unop;
        struct {
            t_exp id;
            t_exp exp;
        } assign;
        struct {
            char *id;
            t_args args;
        } function_call;
    } u;
};

t_decls t_decls_new(t_decl decl, t_decls decls)
{
    t_decls ret = (t_decls)malloc(sizeof(*ret));

    if(decls)
        ret->kind = DECLS_LIST;
    else
        ret->kind = DECLS_SINGLE;

    ret->u.decl = decl;
    ret->u.decls = decls;

    return ret;
} 

t_decl t_decl_new_var(t_ids id, t_type type, t_exp exp)
{
    t_decl ret = (t_decl)malloc(sizeof(*ret));

    if(exp)
    {
        ret->kind = VAR_DECL_W_VALUE;
        ret->u.var.exp = exp;
    }
    else
        ret->kind = VAR_DECL;
    
    ret->u.var.id = id;
    ret->u.var.type = type;

    return ret;
}

t_decl t_decl_new_type(char *id, t_type type)
{
    t_decl ret = (t_decl)malloc(sizeof(*ret));

    ret->kind = TYPE_DECL;
    ret->u.newtype.id = id;
    ret->u.newtype.type = type;

    return ret;
}

t_decl t_decl_new_funct(char *id, t_argdefs args, t_type returnType, t_stms stms)
{
    t_decl ret = (t_decl)malloc(sizeof(*ret));

    if(args)
    {
        ret->kind = FUNC_DECL_W_ARGS;
        ret->u.funct.args = args;
    }
    else
        ret->kind = FUNC_DECL;

    ret->u.funct.id = id;
    ret->u.funct.returnType = returnType;
    ret->u.funct.stms = stms;

    return ret;
}

t_argdefs t_argdefs_new_arg(t_argdef argdef, t_argdefs arglist)
{
    t_argdefs ret = (t_argdefs)malloc(sizeof(*ret));

    if(arglist)
    {
        ret->kind = ARGS_LIST;
        ret->u.argdefs = arglist;
    }
    else
        ret->kind = ARGS_SINGLE;
    
    return ret;
}

t_argdef t_argdef_new_arg(char *id, t_type argtype)
{
    t_argdef ret = (t_argdef)malloc(sizeof(*ret));

    ret->kind = ARGDEF;
    ret->u.id = id;
    ret->u.type = argtype;

    return ret;
}

t_args t_args_new_args(t_exp exp, t_args arglist)
{
    t_args ret = (t_args)malloc(sizeof(*ret));

    if(arglist)
    {
        ret->kind = ARG_CALL_LIST;
        ret->u.args = arglist;
    }
    else
        ret->kind = ARG_CALL_SINGLE;

    ret->u.exp = exp;

    return ret;
}

t_ids t_ids_new_ids(char *id, t_ids idlist)
{
    t_ids ret = (t_ids)malloc(sizeof(*ret));

    if(idlist)
    {
        ret->kind = ID_LIST;
        ret->u.ids = idlist;
    }
    else
        ret->kind = ID_SINGLE;

    ret->u.id = id;

    return ret;
}

t_stms t_stms_new_stm(t_stm stm, t_stms stmlist)
{
    t_stms ret = (t_stms)malloc(sizeof(*ret));

    if(stmlist)
    {
        ret->kind = STMS_LIST;
        ret->u.stms = stmlist;
    }
    else
        ret->kind = STMS_SINLGE;
    
    ret->u.stm = stm;

    return ret;
}

t_stm t_stm_new_decl(t_decl decl)
{
    t_stm ret = (t_stm)malloc(sizeof(*ret));

    ret->kind = DECL_STM;
    ret->u.decl = decl;

    return ret;
}

t_stm t_stm_new_exp(t_exp exp)
{
    t_stm ret = (t_stm)malloc(sizeof(*ret));

    ret->kind = EXP_STM;
    ret->u.exp = exp;

    return ret;
}


t_stm t_stm_new_return(t_exp exp)
{
    t_stm ret = (t_stm)malloc(sizeof(*ret));

    ret->kind = RETURN_STM;
    ret->u.exp = exp;

    return ret;
}


t_stm t_stm_new_if_else(t_exp condition, t_stms ifstm, t_stms elsestm)
{
    t_stm ret = (t_stm)malloc(sizeof(*ret));

    if(elsestm)
    {
        ret->kind = IF_ELSE_STM;
        ret->u.conditional.elseStms = elsestm;
    }
    else
        ret->kind = IF_STM;

    ret->u.conditional.exp = condition;
    ret->u.conditional.stms = ifstm;

    return ret;
}

t_stm t_stm_new_while(t_exp condition, t_stms ifstm)
{
    t_stm ret = (t_stm)malloc(sizeof(*ret));


    ret->kind = WHILE_STM;

    ret->u.conditional.exp = condition;
    ret->u.conditional.stms = ifstm;

    return ret;
}

t_stm t_stm_new_next()
{
    t_stm ret = (t_stm)malloc(sizeof(*ret));

    ret->kind = NEXT_STM;

    return ret;
}

t_type t_type_new(char* type)
{
    t_type ret = (t_type)malloc(sizeof(*ret));

    if(strcmp(type, "int") == 0)
        ret->kind = T_INT;
    else if(strcmp(type, "string") == 0)
        ret->kind = T_STRING;
    else if(strcmp(type, "float") ==0)
        ret->kind = T_FLOAT;
    else if(strcmp(type, "bool") == 0)
        ret->kind = T_BOOL;
    else if(strcmp(type, "void") == 0)
        ret->kind = T_VOID;

    ret->u.type = type;

    return ret;
}

t_type t_type_new_array(t_type type, int size)
{
    t_type ret = (t_type)malloc(sizeof(*ret));

    ret->kind = T_NEW;
    ret->u.array.size = size;
    ret->u.array.type = type->u.type;

    return ret;
}

t_lit t_lit_new_intlit(int lit)
{
    t_lit ret = (t_lit)malloc(sizeof(*ret));

    ret->kind = INTLIT;
    ret->u.intlit = lit;

    return ret;
}

t_lit t_lit_new_floatlit(float lit)
{
    t_lit ret = (t_lit)malloc(sizeof(*ret));

    ret->kind = FLOATLIt;
    ret->u.floatlit = lit;

    return ret;
}

t_lit t_lit_new_strlit(char* lit)
{
    t_lit ret = (t_lit)malloc(sizeof(*ret));

    ret->kind = STRLIT;
    ret->u.strlit = lit;

    return ret;
}

t_lit t_lit_new_boollit(bool lit)
{
    t_lit ret = (t_lit)malloc(sizeof(*ret));

    ret->kind = BOOLIT;
    ret->u.boolit = lit;

    return ret;
}

t_exp t_exp_new_lit(t_lit lit)
{
    t_exp ret = (t_exp)malloc(sizeof(*ret));

    ret->kind = LIT_EXP;
    ret->u.litexp = lit;

    return ret;
}

t_exp t_exp_new_id(char* id)
{
    t_exp ret = (t_exp)malloc(sizeof(*ret));

    ret->kind = ID_EXP;
    ret->u.id = id;

    return ret;
}

t_exp t_exp_new_array_access(t_exp exp, int lit)
{
    t_exp ret = (t_exp)malloc(sizeof(*ret));

    ret->kind = ARRAY_EXP;
    ret->u.array_access.exp = exp;
    ret->u.array_access.lit = lit;

    return ret;
}

t_exp t_exp_new_op(char* op, t_exp arg1, t_exp arg2)
{
    t_exp ret = (t_exp)malloc(sizeof(*ret));

    if(arg2)
    {
        ret->kind = BINOP_EXP;
        ret->u.binop.op = op;
        ret->u.binop.arg2 = arg2;
        ret->u.binop.arg1 = arg1;
    }
    else
    {
        ret->kind = UNOP_EXP;
        ret->u.unop.op = op;
        ret->u.unop.arg = arg1;
    }

    return ret;
}

t_exp t_exp_new_assign(t_exp id, t_exp exp)
{
    t_exp ret = (t_exp)malloc(sizeof(*ret));

    ret->kind = ASSIGN_EXP;
    ret->u.assign.id = id;
    ret->u.assign.exp = exp;

    return ret;
}

t_exp t_exp_new_fcall(char *id, t_args args)
{
    t_exp ret = (t_exp)malloc(sizeof(*ret));

    if(args)
    {
        ret->kind = FUNCT_CALL_W_ARG_EXP;
        ret->u.function_call.args = args;
    }
    else
        ret->kind = FUNCT_CALL_EXP;

    ret->u.function_call.id = id;
   
    return ret;
}

/* ---------------------------------------------------------------------------------------------------------------------------- */
/*                                                      ANALISE SEMANTICA                                                       */

char* currentFunct;
bool insideWhile; 


void t_decls_ant(t_decls ds)
{
    if(!STinitialized)
    {
        initSymbolTable();
        STinitialized = true;
    }

    switch (ds->kind)
    {
    case DECLS_SINGLE:
        t_decl_ant(ds->u.decl);
        break;

    case DECLS_LIST:
        t_decl_ant(ds->u.decl);
        t_decls_ant(ds->u.decls);
        break;

    default:
        //ERROR();
        break;
    }

    //free(SymbolTable);
}

void t_decl_ant(t_decl d)
{
    t_ids ids;
    ST_Data stvar = malloc(sizeof(ST_Data));

    switch(d->kind) 
    {
        case VAR_DECL:
            ids = d->u.var.id;
             
            while(ids)
            {
                if(ST_lookup_local(ids->u.id) != NULL)
                    printf("WARNING: Variable \"%s\" is already declared.\n", ids->u.id);
                else
                {            
                    //printf("%s\n", d->u.var.type->u.type);
                    ST_insert(ids->u.id, newVar(d->u.var.type));
                    //printf("INSERIU: %s\n", ids->u.id);
                }
                ids = ids->u.ids;
            }
            break;

        case VAR_DECL_W_VALUE:
            ids = d->u.var.id;

            while(ids)
            {
                //if(true)//Ver mais tarde! Se o valor pode ser dado à variavel
                //    printf("Por ver!");
                if(ST_lookup_local(ids->u.id) != NULL)
                    printf("WARNING: Variable \"%s\" is already declared.\n", ids->u.id);
                else
                {            
                    ST_insert(ids->u.id, newVar(d->u.var.type));

                //printf("INSERIU: %s of type %s\n", ids->u.id, ST_lookup(ids->u.id)->u.var.type->u.type);
                }
                ids = ids->u.ids;
            }
            break;
        
        case FUNC_DECL:

            if(ST_lookup(d->u.funct.id) != NULL)
                printf("ERROR:Function with name \"%s\" is already declared\n", d->u.funct.id);
            else
            {
                currentFunct = d->u.funct.id;
                ST_insert(d->u.funct.id, newFunction(NULL, d->u.funct.returnType));
                ST_new_scope();
                t_stms_ant(d->u.funct.stms);
                ST_discard();
                currentFunct = NULL;
            }
            break;

        case FUNC_DECL_W_ARGS:

            if(ST_lookup(d->u.funct.id) != NULL)
                printf("ERROR:Function with name \"%s\" is already declared\n", d->u.funct.id);
            else
            {
                currentFunct = d->u.funct.id;
                ST_insert(d->u.funct.id, newFunction(d->u.funct.args, d->u.funct.returnType));
                ST_new_scope();
                t_stms_ant(d->u.funct.stms);
                ST_discard();
                currentFunct = NULL;
            }
            break;

        case TYPE_DECL:

            if(ST_lookup(d->u.newtype.id) != NULL)
                printf("ERROR:Type \"%s\" is already defined!\n", d->u.newtype.id);
            else
            {
                stvar->kind = ST_TYPE;
                stvar->u.newType = d->u.newtype.type;
                ST_insert(d->u.newtype.id, stvar);
            }
            

            //VER TIPOS
            break;

        free(stvar);            
    }
}

void t_stms_ant(t_stms stms)
{
    switch (stms->kind)
    {
    case STMS_SINLGE:
        t_stm_ant(stms->u.stm);
        break;
    
    case STMS_LIST:
        t_stm_ant(stms->u.stm);
        t_stms_ant(stms->u.stms);
        break;

    default:
        break;
    }
}

void t_stm_ant(t_stm stm)
{
    switch (stm->kind)
    {
    case DECL_STM:
        t_decl_ant(stm->u.decl);
        break;
    
    case EXP_STM:
        t_exp_ant(stm->u.exp);
        break;
    
    case RETURN_STM:
        //t_type returnType1;
        //returnType1 = t_exp_ant(stm->u.exp);
        if(currentFunct)
        {
            //t_type returnType2 = ST_lookup(currentFunct)->u.func.returnType;
            if(t_exp_ant(stm->u.exp) && ST_lookup(currentFunct) != NULL)
                if(t_exp_ant(stm->u.exp)->kind != ST_lookup(currentFunct)->u.func.returnType->kind)
                    printf("ERROR: Return type from function \"%s\" does not match the type of returning expression \"%s\"\n", ST_lookup(currentFunct)->u.func.returnType->u.type, t_exp_ant(stm->u.exp)->u.type);
        }
        else
            printf("ERROR: \"return\" statement cannot be used outside a function.\n");

        break;

    case IF_STM:
        //t_type conditionalType = t_exp_ant(stm->u.conditional.exp);
        if(t_exp_ant(stm->u.conditional.exp)->kind != T_BOOL)
            printf("ERROR:\"if\" condition cannot be evaluated as bool type.\n");
        else
        {
            t_stms_ant(stm->u.conditional.stms);
        }
        
        break;
    
    case IF_ELSE_STM:
        //t_type conditionalType = t_exp_ant(stm->u.conditional.exp);
        if(t_exp_ant(stm->u.conditional.exp)->kind != T_BOOL)
            printf("ERROR:\"if\" condition cannot be evaluated as bool type.\n");
        else
        {
            t_stms_ant(stm->u.conditional.stms);
            t_stms_ant(stm->u.conditional.elseStms);
        }
        break;

    case WHILE_STM:
        //t_type conditionalType = t_exp_ant(stm->u.conditional.exp);
        if(t_exp_ant(stm->u.conditional.exp)->kind != T_BOOL)
            printf("ERROR:\"while\" condition cannot be evaluated as bool type.\n");
        else
        {
            insideWhile = true;
            t_stms_ant(stm->u.conditional.stms);
            insideWhile = false;
        }
        break;

    case NEXT_STM:
        if(!insideWhile)
            printf("ERROR:\"next\" instruction cannot be used outside a \"while\" loop.\n");
        break;

    default:
        break;
    }
}


t_type t_lit_ant(t_lit l)
{
    switch(l->kind)
    {
    case INTLIT:
        return t_type_new("int");
        break;
    
    case FLOATLIt:
        return t_type_new("float");
        break;

    case STRLIT:
        return t_type_new("string");
        break;

    case BOOLIT:
        return t_type_new("bool");
        break;

    default:
        break;
    }

}

// Acabar
t_type t_exp_ant(t_exp e)
{
    switch (e->kind)
    {
    case LIT_EXP:
        return t_lit_ant(e->u.litexp);

    case ARRAY_EXP:
        if(e->u.array_access.lit<0)
        {
            printf("ERROR: \"%d\" is not a valid number!\n", e->u.array_access.lit);
        }
        else
        {
            return t_exp_ant(e->u.array_access.exp);
        }
        
        break;

    case ID_EXP:
        if(ST_lookup(e->u.id) != NULL)
            return ST_lookup(e->u.id)->u.var.type;
        else
            printf("ERROR: Variable \"%s\" is not defined!\n", e->u.id);
            return NULL;
        break;
    
    case BINOP_EXP:
        //t_type type1 = t_exp_ant(e->u.binop.arg1);
        //t_type type2 = t_exp_ant(e->u.binop.arg2);

        if(t_exp_ant(e->u.binop.arg1)->kind != t_exp_ant(e->u.binop.arg2)->kind)
            printf("ERROR: Operation \"%s\" does not support different types!\n", e->u.binop.op);
        else if(t_exp_ant(e->u.binop.arg1)->kind == T_STRING || t_exp_ant(e->u.binop.arg1)->kind == T_NEW || t_exp_ant(e->u.binop.arg2)->kind == T_STRING || t_exp_ant(e->u.binop.arg2)->kind == T_NEW)
            printf("ERROR:String and NewTypes operations are not supported!\n");
        else if(arithmeticOperator(e->u.binop.op))
        {
            if(t_exp_ant(e->u.binop.arg1)->kind == T_INT && t_exp_ant(e->u.binop.arg2)->kind == T_INT)
                return t_type_new("int");
            else if(t_exp_ant(e->u.binop.arg1)->kind == T_FLOAT || t_exp_ant(e->u.binop.arg2)->kind == T_FLOAT)
                return t_type_new("int");
            else
                printf("ERROR:Used types can't be used in this expression.\n");
                exit(1);
        }
        else
        {
            return t_type_new("bool");
        }
        
        break;

    case UNOP_EXP:
        if(t_exp_ant(e->u.unop.arg)->kind == T_STRING || t_exp_ant(e->u.unop.arg)->kind == T_NEW)
        {
            printf("ERROR:String and NewTypes operations are not supported!\n");
        }
        else if(arithmeticOperator(e->u.unop.op))
        {
            if(t_exp_ant(e->u.unop.arg)->kind == T_INT)
                return t_type_new("int");
            else if(t_exp_ant(e->u.unop.arg)->kind == T_FLOAT)
                return t_type_new("int");
            else
            {
                printf("ERROR:Used types can't be used in this expression.\n");
                exit(1);
            }
        }
        else
        {
            return t_type_new("bool");
        }
        break;

    case ASSIGN_EXP:
        if(e->u.assign.id->kind != ID_EXP)
            printf("ERROR: Value cannot be assigned to a literal!\n");
        else if(t_exp_ant(e->u.assign.id)->kind != t_exp_ant(e->u.assign.exp)->kind)
            printf("ERROR: Can't assign different types!\n");
        break;
        
    
    case FUNCT_CALL_EXP:
        if(ST_lookup(e->u.function_call.id) == NULL)
            printf("Function \"%s\" is not defined!\n", e->u.function_call.id);
        break;
    
    case FUNCT_CALL_W_ARG_EXP:
        /*
        if(ST_lookup(e->u.function_call.id) == NULL)
            printf("Function \"%s\" is not defined!\n", e->u.function_call.id);
        else
        {
            
        }
        */
        break;

    default:
        break;
    }
}

bool arithmeticOperator(char* operator)
{
    return (operator == "+" || operator == "-" || operator == "*" ||
            operator == "/" || operator == "^" );
}
