#include <stdbool.h>

typedef struct node Node;
typedef struct tree Tree;

Node *new_node(int data);
Tree *new_tree();
void insert_on_tree(Tree *tree,int data);
Node *findMin(Node *node);
void remove_from_tree(Tree *tree, int data);
void print_tree(Tree *tree);
bool find_on_tree(Tree *tree, int data);
void destroy_tree(Tree *tree);