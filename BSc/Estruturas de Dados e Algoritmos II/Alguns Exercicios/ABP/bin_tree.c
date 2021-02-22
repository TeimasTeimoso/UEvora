#include <stdlib.h>
#include <stdio.h>
#include <stdbool.h>
#include "bintree.h"


//No com elemento e referencia para no direito e esquero
typedef struct node
{
    int element;

    struct node *rightNode, *leftNode;
} Node;


typedef struct tree
{
    Node *root;
} Tree;


Tree *new_tree()
{
    Tree *tree = malloc(sizeof(Tree));

    tree->root = NULL;
    
    return tree;
}


Node *new_node(int data)
{
    Node *node = malloc(sizeof(Node));

    //Inicializa o novo no com os atributos base
    node->element = data;
    node->leftNode = NULL;
    node->rightNode = NULL;

    return node;
}

Node *insert_node(Node *node, int data)
{
    //Se nao existir root, cria uma

    if(node == NULL)
    {
        node = new_node(data);
        return node;
    }

    //caso o valor a introduzir seja maior que o elemento do current vai pela subtree esquerda
    if(data < node->element)
        node->leftNode = insert_node(node->leftNode, data);
    //Se for maior vai pela direita
    else if(data > node->element)
        node->rightNode = insert_node(node->rightNode, data);
    
    return node;
}

//Procurar o menor valor na tree
Node *findMin(Node *node)
{
    Node *current = node;

    while(current->leftNode != NULL)
        current = current->leftNode;

    return current;
}

void tree_on_array(int size, Tree *array[size])
{
    for(int i = 0; i<size; i++)
    {
        Tree *tree = new_tree();
        array[i] = tree;
    }
}

void insert_on_tree(Tree *tree, int data)
{
    Node *current = tree->root;

    if(current == NULL)
    {
        current = new_node(data);
        tree->root = current;
    }
    else
    {
        insert_node(tree->root, data);
    }
}

Node *remove_node(Node *node, int data)
{
    //Se a tree ta vazia nao faz nada
    if(node == NULL)
        return node;

    //Se o elemento e menor que o elemnto da root vai para a subtree esquerda
    if(data < node->element)
        node->leftNode = remove_node(node->leftNode, data);
    //Se for maior vai para a subtree direita
    else if(data > node->element)
        node->rightNode = remove_node(node->rightNode, data);
    //Ao achar o elemento
    else
    {       
        //So tem filho direito
        if(node->leftNode == NULL)
        {
            Node *temp = node->rightNode;
            free(node);
            return temp;
        }
        //So tem filho esquerdo
        else if(node->rightNode == NULL)
        {
            Node *temp = node->leftNode;
            free(node);
            return temp;
        }
        
        Node *temp = findMin(node->rightNode);
        
        //Modifica o valor do no existente para o menor da direita
        node->element = temp->element;
        //Elimina o nó que tinha o valor atualmente no node
        node->rightNode = remove_node(node->rightNode, temp->element);

    }

    return node;
}

void remove_from_tree(Tree *tree, int data)
{
    remove_node(tree->root, data);
}


bool find_node(Node *node, int data)
{
    if(node == NULL)
        return false;

    if(data == node->element)
        return true;
    if(data < node->element)
        return find_node(node->leftNode, data);
    else if(data > node->element)
        return find_node(node->rightNode, data);

    return false;
}

bool find_on_tree(Tree *tree, int data)
{
    return find_node(tree->root, data);
}

void destroy_nodes(Node *node)
{
    if(node != NULL)
    {
        destroy_nodes(node->leftNode);
        destroy_nodes(node->rightNode);
        free(node);
    }
}

void destroy_tree(Tree *tree)
{
    destroy_nodes(tree->root);
}

void print_treeAux(Node *node)
{
    if(node != NULL)
    {
        print_treeAux(node->leftNode);
        printf("%d \n", node->element);
        print_treeAux(node->rightNode);
    }
}

void print_tree(Tree *tree)
{
    Node *root = tree->root;
    print_treeAux(root);
}