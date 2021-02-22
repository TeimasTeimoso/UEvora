#define PAGE_SIZE 10
#define MEMSIZE 300


/*Lista completa de paginas existentes e ocupação */
typedef struct pagination
{
    bool listOfPages[MEMSIZE/PAGE_SIZE];
    int free;
} pageSet;

typedef struct pageNode
{
    int element;
    int nOfPage;
    struct pageNode *next;
    struct pageNode *previous;
} pageNode;

//Lista de páginas de cada processo
typedef struct pageList
{
    struct pageNode *head;
    struct pageNode *tail;
} pageList;

struct pageNode *new_page(int page, int index);
struct pageList *new_pageList();
pageSet *new_pageSet(void);
void pageList_insert(pageList *list, int value,  int index);
int pageList_remove(pageList *list);
bool pageList_Next(pageList *list);
bool pageList_Previous(pageList *list);