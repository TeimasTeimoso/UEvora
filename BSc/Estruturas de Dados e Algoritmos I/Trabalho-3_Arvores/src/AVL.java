public class AVL {
    class Node {
        int key, height;
        Node left, right;

        Node(int d) {
            key = d;
            height = 1;
        }
    }
        Node root;

        // Ve o tamanho
        int height(Node N) {
            if (N == null)
                return 0;

            return N.height;
        }

        // maximo de 2 inteiros. se true a, else b
        int max(int a, int b) {
            return (a > b) ? a : b;
        }

        //roda o y

        Node rightRotate(Node y) {
            Node x = y.left;
            Node T2 = x.right;

            // rotaçap
            x.right = y;
            y.left = T2;

            y.height = max(height(y.left), height(y.right)) + 1;
            x.height = max(height(x.left), height(x.right)) + 1;

            // nova raiz
            return x;
        }

        // roda x

        Node leftRotate(Node x) {
            Node y = x.right;
            Node T2 = y.left;

            y.left = x;
            x.right = T2;


            x.height = max(height(x.left), height(x.right)) + 1;
            y.height = max(height(y.left), height(y.right)) + 1;


            return y;
        }

        // Get Balance
        int getBalance(Node N) {
            if (N == null)
                return 0;

            return height(N.left) - height(N.right);
        }

        Node insert(int key){
            return insert(root, key);
        }

        Node insert(Node node, int key) {
            if (node == null)
                return (new Node(key));

            if (key < node.key)
                node.left = insert(node.left, key);
            else if (key > node.key)
                node.right = insert(node.right, key);
            else
                return node;

            //2. update da altura
            node.height = 1 + max(height(node.left),
                    height(node.right));

        //3. Verifica se o no anterior esta balanceado
            int balance = getBalance(node);


            if (balance > 1 && key < node.left.key)
                return rightRotate(node);

            if (balance < -1 && key > node.right.key)
                return leftRotate(node);

            if (balance > 1 && key > node.left.key) {
                node.left = leftRotate(node.left);
                return rightRotate(node);
            }

            if (balance < -1 && key < node.right.key) {
                node.right = rightRotate(node.right);
                return leftRotate(node);
            }

            return node;
        }

        void preOrder(Node node) {
            if (node != null) {
                System.out.print(node.key + " ");
                preOrder(node.left);
                preOrder(node.right);
            }
        }

    public static void main(String[] args) {
        AVL tree = new AVL();

        tree.root = tree.insert(2);
        tree.root = tree.insert(10);
        tree.root = tree.insert(20);
        tree.root = tree.insert(30);
        tree.root = tree.insert(40);
        tree.root = tree.insert(50);
        tree.root = tree.insert(25);

        System.out.println("Preorder: ");
        tree.preOrder(tree.root);
    }

}