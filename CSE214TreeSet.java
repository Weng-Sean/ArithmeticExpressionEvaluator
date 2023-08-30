import java.util.Arrays;

public class CSE214TreeSet<E extends Comparable<E>> extends BinaryTree<E> implements CSE214Set<E> {


    private enum Color {
        RED("red"),
        BLACK("black");

        String value;

        Color(String s) {
            this.value = s;
        }
    }

    Color color;
    CSE214TreeSet<E> root;

    public CSE214TreeSet() {
        this(null);
    }

    public CSE214TreeSet(E element) {
        super(element);
        color = Color.BLACK;
        this.root = this;
    }

    public CSE214TreeSet<E> getLeft_() {
        return (CSE214TreeSet<E>) this.getLeft();
    }

    public CSE214TreeSet<E> getRight_() {
        return (CSE214TreeSet<E>) this.getRight();
    }


    public int size() {
        return size(root);
    }

    public int size(BinaryTree<E> node) {
        if (node == null) {
            return 0;
        }
        return 1 + size(node.getLeft()) + size(node.getRight());
    }


    public boolean contains(Object o) {
        CSE214TreeSet<E> that = (CSE214TreeSet<E>) o;
        CSE214TreeSet<E> iterator = root;
        boolean isFound = false;
        while (iterator != null && !isFound) {
            if (iterator.getValue().compareTo(that.getValue()) == 0) {
                isFound = true;
                break;
            }

            if (iterator.getValue().compareTo(that.getValue()) > 0)
                iterator = iterator.getLeft_();
            else
                iterator = iterator.getRight_();

        }
        return isFound;
    }


    public boolean add(E e) {
        if (this.getValue() == null) {
            setValue(e);
            color = Color.BLACK;
            return true;
        }

        CSE214TreeSet<E> node = new CSE214TreeSet<>(e);
        node.color = Color.RED;

        if (contains(node))
            return false;
        add(root, node);
        maintainBalance(node);

        return true;
    }

    private void add(CSE214TreeSet<E> root, CSE214TreeSet<E> node) {
        CSE214TreeSet<E> iterator = root;
        CSE214TreeSet<E> iterator_parent = null;
        while (iterator != null) {
            iterator_parent = iterator;
            if (node.getValue().compareTo(iterator.getValue()) > 0)
                iterator = iterator.getRight_();
            else
                iterator = iterator.getLeft_();
        }
        if (node.getValue().compareTo(iterator_parent.getValue()) > 0)
            iterator_parent.setRight(node);
        else
            iterator_parent.setLeft(node);


    }

    private void maintainBalance(CSE214TreeSet<E> node) {
        // rule 1 A red node cannot have a red child
        // rule 2 Every path from a given node to one of its non-branching descendants
        //          contains the same number of black nodes.
        // rule 3 The color of the root node must be black.


        CSE214TreeSet<E> iterator = node;
        CSE214TreeSet<E> parent = iterator.getParent(root, 1);
        CSE214TreeSet<E> grandparent = iterator.getParent(root, 2);
        CSE214TreeSet<E> uncle = iterator.getUncle(root);


        while (parent != null && parent.color.equals(Color.RED)) {

            // case 1: parent is red and uncle is also red:
            if (uncle != null && uncle.color.equals(Color.RED)) {
                iterator = case1(iterator);
                parent = iterator.getParent(root, 1);
                uncle = iterator.getUncle(root);
                grandparent = iterator.getParent(root, 2);
            }

            // case 2 & 3: parent is red but uncle is not red:
            // grandparent cannot be null for case 2 and case 3
            else if ((uncle == null || uncle.color.equals(Color.BLACK)) && grandparent != null) {
                // case 2 it forms a triangle

                if ((parent.equals(grandparent.getRight_()) != iterator.equals(parent.getRight())) || (parent.equals(grandparent.getLeft_()) != iterator.equals(parent.getLeft()))) {
                    iterator = case2(iterator);
                    parent = iterator.getParent(root, 1);
                    uncle = iterator.getUncle(root);
                    grandparent = iterator.getParent(root, 2);
                    case3(iterator);
                }
                // case 3 it forms a line
                else {
                    case3(iterator);


                }
            } else
                break;
        }
        // maintain root to be black
        if (root.color.equals(Color.RED))
            root.color = Color.BLACK;
    }

    public CSE214TreeSet<E> getParent(CSE214TreeSet<E> root, int generation) {
        Object[] ancestors = new Object[root.getLongestPath()];
        int count = 0;
        CSE214TreeSet<E> iterator = root;
        boolean isFound = false;
        while (iterator != null && !isFound) {
            ancestors[count++] = iterator;

            if (iterator.getValue().equals(this.getValue())) {
                isFound = true;
                break;
            }
            if (iterator.getValue().compareTo(this.getValue()) > 0)
                iterator = iterator.getLeft_();
            else
                iterator = iterator.getRight_();
        }
//        for (Object node: ancestors)
//            if (node != null)
//                System.out.println(((CSE214TreeSet<E>)node).getValue());

        int index = 0;
        while (!ancestors[index].equals(this))
            index++;

        if (index - generation >= 0)
            return (CSE214TreeSet<E>) ancestors[index - generation];
        else
            return null;

    }

    public CSE214TreeSet<E> getUncle(CSE214TreeSet<E> root) {
        CSE214TreeSet<E> parent = this.getParent(root, 1);
        if (parent == null)
            return null;
        CSE214TreeSet<E> grandparent = this.getParent(root, 2);
        if (grandparent == null)
            return null;
        if (parent.equals(grandparent.getRight_()))
            return grandparent.getLeft_();
        else
            return grandparent.getRight_();
    }

    public CSE214TreeSet<E> getSibling(CSE214TreeSet<E> root) {
        CSE214TreeSet<E> parent = this.getParent(root, 1);
        if (parent == null)
            return null;
        if (this.equals(parent.getRight_()))
            return parent.getLeft_();
        else
            return parent.getRight_();
    }

    private CSE214TreeSet<E> case1(CSE214TreeSet<E> node) {
        // case 1: parent is red and uncle is also red:
        CSE214TreeSet<E> iterator = node;
        CSE214TreeSet<E> parent = iterator.getParent(root, 1);
        CSE214TreeSet<E> uncle = iterator.getUncle(root);
        CSE214TreeSet<E> grandparent = iterator.getParent(root, 2);
        parent.color = Color.BLACK;
        uncle.color = Color.BLACK;
        grandparent.color = Color.RED;

        return grandparent;

    }

    private CSE214TreeSet<E> case2(CSE214TreeSet<E> node) {
        return rotate(node, 2);
    }

    private void case3(CSE214TreeSet<E> node) {
        node.getParent(root, 1).color = Color.BLACK;
        node.getParent(root, 2).color = Color.RED;
        rotate(node, 3);
    }

    private CSE214TreeSet<E> rotate(CSE214TreeSet<E> node, int case_) {
        CSE214TreeSet<E> parent = node.getParent(root, 1);
        CSE214TreeSet<E> grandparent = node.getParent(root, 2);
        CSE214TreeSet<E> leftSubtree = node.getLeft_();
        CSE214TreeSet<E> rightSubtree = node.getRight_();
        CSE214TreeSet<E> sibling = node.getSibling(root);


        if (case_ == 2) {
            // if this node is a right child
            if (parent.getRight_().equals(node)) {
                grandparent.setLeft(node);
                node.setLeft(parent);
                parent.setRight(leftSubtree);
                return parent;
            }
            // if this node is a left child
            else {
                grandparent.setRight(node);
                node.setRight(parent);
                parent.setLeft(rightSubtree);
                return parent;
            }
        } else if (case_ == 3) {
            CSE214TreeSet<E> ancestor = node.getParent(root, 3);
            // grandparent is not root
            if (ancestor != null) {
                // grandparent is a right child of ancestor
                if (ancestor.getRight_().equals(grandparent))
                    ancestor.setRight(parent);
                else
                    // grandparent is a left child of ancestor
                    ancestor.setLeft(parent);
            }
            // grandparent is indeed root
            else {
                root = parent;
            }


            // if this parent is a right child
            if (parent.equals(grandparent.getRight_())) {
                parent.setLeft(grandparent);
                grandparent.setRight(sibling);
            }
            // if this parent is a left child
            else {
                parent.setRight(grandparent);
                grandparent.setLeft(sibling);
            }
        }
        return null;
    }


    public int getLongestPath() {
        if (this.getLeft() != null && this.getRight() != null)
            return 1 + Math.max(this.getLeft_().getLongestPath(), this.getRight_().getLongestPath());
        if (this.getLeft() != null)
            return 1 + this.getLeft_().getLongestPath();
        if (this.getRight() != null)
            return 1 + this.getRight_().getLongestPath();
        return 1;


    }

    public String getColor() {
        return this.color.value;
    }


    private static <E extends Comparable<E>> String traversePreOrder(CSE214TreeSet<E> root) {

        if (root == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        sb.append(root.getValue());
        sb.append(" [").append(root.getColor()).append("]");

        String pointerRight = "└──";
        String pointerLeft = (root.getRight() != null) ? "├──" : "└──";

        traverseNodes(sb, "", pointerLeft, root.getLeft_(), root.getRight() != null);
        traverseNodes(sb, "", pointerRight, root.getRight_(), false);

        return sb.toString();
    }

    private static <E extends Comparable<E>> void traverseNodes(StringBuilder sb, String padding, String pointer, CSE214TreeSet<E> node, boolean hasRightSibling) {
        if (node != null) {
            sb.append("\n");
            sb.append(padding);
            sb.append(pointer);
            sb.append(node.getValue());
            sb.append(" [").append(node.getColor()).append("]");

            StringBuilder paddingBuilder = new StringBuilder(padding);
            if (hasRightSibling) {
                paddingBuilder.append("│  ");
            } else {
                paddingBuilder.append("   ");
            }

            String paddingForBoth = paddingBuilder.toString();
            String pointerRight = "└──";
            String pointerLeft = (node.getRight() != null) ? "├──" : "└──";

            traverseNodes(sb, paddingForBoth, pointerLeft, node.getLeft_(), node.getRight() != null);
            traverseNodes(sb, paddingForBoth, pointerRight, node.getRight_(), false);
        }
    }

    @Override
    public String toString() {
        return traversePreOrder(root);
    }


    public static void main(String... args) {


        CSE214TreeSet<Integer> root = new CSE214TreeSet<>();
        int node = 8;
        for (int i = 0; i < node; i++) {
            root.add(i);
        }
        System.out.println(root);

        System.out.println(root.contains(new CSE214TreeSet<Integer>(2)));



    }

}