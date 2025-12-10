import java.util.*;

enum Color { RED, BLACK }

class RBNode {
    int key;
    Color color;
    RBNode left, right, parent;

    RBNode(int key) {
        this.key = key;
        this.color = Color.RED;
        this.left = this.right = this.parent = null;
    }
}

public class RedBlackTree {
    private RBNode root;
    private static final RBNode NIL = new RBNode(0);
    static { NIL.color = Color.BLACK; }

    public RedBlackTree() {
        root = NIL;
    }

  
    private void leftRotate(RBNode x) {
        RBNode y = x.right;
        x.right = y.left;
        if (y.left != NIL) y.left.parent = x;
        y.parent = x.parent;
        if (x.parent == NIL) root = y;
        else if (x == x.parent.left) x.parent.left = y;
        else x.parent.right = y;
        y.left = x;
        x.parent = y;
    }

    
    private void rightRotate(RBNode y) {
        RBNode x = y.left;
        y.left = x.right;
        if (x.right != NIL) x.right.parent = y;
        x.parent = y.parent;
        if (y.parent == NIL) root = x;
        else if (y == y.parent.right) y.parent.right = x;
        else y.parent.left = x;
        x.right = y;
        y.parent = x;
    }

    
    private void insertFixup(RBNode z) {
        while (z.parent.color == Color.RED) {
            if (z.parent == z.parent.parent.left) {
                RBNode y = z.parent.parent.right;
                if (y.color == Color.RED) {
                    z.parent.color = Color.BLACK;
                    y.color = Color.BLACK;
                    z.parent.parent.color = Color.RED;
                    z = z.parent.parent;
                } else {
                    if (z == z.parent.right) {
                        z = z.parent;
                        leftRotate(z);
                    }
                    z.parent.color = Color.BLACK;
                    z.parent.parent.color = Color.RED;
                    rightRotate(z.parent.parent);
                }
            } else {
                RBNode y = z.parent.parent.left;
                if (y.color == Color.RED) {
                    z.parent.color = Color.BLACK;
                    y.color = Color.BLACK;
                    z.parent.parent.color = Color.RED;
                    z = z.parent.parent;
                } else {
                    if (z == z.parent.left) {
                        z = z.parent;
                        rightRotate(z);
                    }
                    z.parent.color = Color.BLACK;
                    z.parent.parent.color = Color.RED;
                    leftRotate(z.parent.parent);
                }
            }
        }
        root.color = Color.BLACK;
    }

    
    public void insert(int key) {
        RBNode z = new RBNode(key);
        RBNode y = NIL;
        RBNode x = root;

        while (x != NIL) {
            y = x;
            if (z.key < x.key) x = x.left;
            else x = x.right;
        }

        z.parent = y;
        if (y == NIL) root = z;
        else if (z.key < y.key) y.left = z;
        else y.right = z;

        z.left = NIL;
        z.right = NIL;
        z.color = Color.RED;
        insertFixup(z);
    }

 
    private void inOrder(RBNode node, List<Integer> result) {
        if (node != NIL) {
            inOrder(node.left, result);
            result.add(node.key);
            inOrder(node.right, result);
        }
    }

    public List<Integer> inOrderTraversal() {
        List<Integer> result = new ArrayList<>();
        inOrder(root, result);
        return result;
    }

    
    private void printTree(RBNode node, String indent, boolean isLeft) {
        if (node == NIL) return;

        String colorStr = node.color == Color.RED ? "R" : "B";
        String marker = isLeft ? "├── " : "└── ";
        System.out.println(indent + marker + node.key + "(" + colorStr + ")");

        indent += isLeft ? "│   " : "    ";
        printTree(node.left, indent, true);
        printTree(node.right, indent, false);
    }

    public void print() {
        if (root == NIL) {
            System.out.println("Дерево порожнє.");
            return;
        }
        printTree(root, "", false);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        RedBlackTree tree = new RedBlackTree();
        Random rand = new Random();

        System.out.println("=== Червонo-чорне дерево ===");
        System.out.println("1. Додати випадкові числа");
        System.out.println("2. Додати впорядковані числа");
        System.out.println("3. Додати вручну");
        System.out.print("Виберіть опцію (1-3): ");
        int option = scanner.nextInt();

        List<Integer> addedNumbers = new ArrayList<>();

        if (option == 1) {
            
            int[] randomArray = new int[10];
            for (int i = 0; i < 10; i++) {
                randomArray[i] = rand.nextInt(100);
            }
            System.out.print("Додаємо у довільному порядку: ");
            for (int num : randomArray) {
                System.out.print(num + " ");
                tree.insert(num);
                addedNumbers.add(num);
            }
            System.out.println();

        } else if (option == 2) {
           
            int[] sortedArray = new int[10];
            for (int i = 0; i < 10; i++) {
                sortedArray[i] = (i + 1) * 10;
            }
            System.out.print("Додаємо по зростанню: ");
            for (int num : sortedArray) {
                System.out.print(num + " ");
                tree.insert(num);
                addedNumbers.add(num);
            }
            System.out.println();

        } else if (option == 3) {
          
            System.out.println("Вводьте числа (по одному). Введіть -1 для завершення:");
            while (true) {
                int num = scanner.nextInt();
                if (num == -1) break;
                tree.insert(num);
                addedNumbers.add(num);
                System.out.println("Додано: " + num);
            }
        }

       
        System.out.println("\nСтруктура червонo-чорного дерева:");
        tree.print();

      
        System.out.println("\nIn-order обхід (відсортовані значення): " + tree.inOrderTraversal());

        scanner.close();
    }
}