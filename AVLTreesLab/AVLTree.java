
import java.util.*;

public class AVLTree
{
    private Node root;
    private boolean increase;
    private boolean decrease;
    private boolean addReturn;
    private Scanner stop;

    public AVLTree()
    {
        root = null;
        addReturn = false;
        increase = false;
        stop = new Scanner(System.in);
    }

    public boolean add(int item)
    {
        // we will make sure that the tree has the proper balances
        balanceTree(root);
        System.out.println("Starting to search for a a place to put " + item);
        increase = false;
        root = add(root, item);
        return addReturn;
    }

    private Node add(Node localRoot, int item)
    {
        if (localRoot == null)
        {
            addReturn = true;
            increase = true;
            System.out.println("Added " + item);
            return new Node(item);
        }
        System.out.println("Add called with " + localRoot.getItem() + " as the root.");
        if (item == localRoot.getItem())
        {                                //item is alreday in tree
            increase = false;
            addReturn = false;
            return localRoot;
        }

        else if (item  < localRoot.getItem())
        {
            System.out.println("Branching left");						                        // item < data
            localRoot.setLeft(add(localRoot.getLeft(), item));

            if (increase)
            {
                decrementBalance(localRoot);
                if (localRoot.balance < Node.LEFT_HEAVY)
                {
                    increase = false;
                    return rebalanceLeft(localRoot);
                }
            }
            return localRoot;                                            // Rebalance not needed.
        }
        else
        {
            System.out.println("Branching right");
            // item > data
            localRoot.setRight(add(localRoot.getRight(), item));
            if (increase)
            {
                incrementBalance(localRoot);
                if (localRoot.balance > Node.RIGHT_HEAVY)
                {
                    return rebalanceRight(localRoot);
                }
                else
                {
                    return localRoot;
                }
            }
            else
            {
                return localRoot;
            }
        }
    }

    private void decrementBalance(Node node)
    {
        node.balance--;
        if (node.balance == Node.BALANCED)
        {
            increase = false;
        }
    }

    private Node rebalanceRight(Node localRoot)
    {
        // Obtain reference to right child
        Node rightChild = localRoot.getRight();

        // See if right-left heavy
        if (rightChild.balance <= Node.LEFT_HEAVY)
        {
            System.out.println("Double rotation");
            // Obtain reference to right-left child
            Node temp = rightChild.getLeft();

            /* Adjust the balances to be their new values after
            the rotates are performed.
            */

            // we will recursively rebalance the tree after we have done a rotation

            /** After the rotates the overall height will be
             reduced thus increase is now false, but
             decrease is now true.
             */
            increase = false;
            decrease = true;
            // Perform double rotation
            localRoot.setRight(temp);
            rightChild.setLeft(temp.getRight());
            temp.setRight(rightChild);

            // here we recursively rebalance the tree
            balanceTree(root);

            return rotateLeft(localRoot);
        }
        // added in else
        else if (rightChild.balance > 0)
        {
            /* In this case both the rightChild (the new root)
            and the root (new left child) will both be balanced
            after the rotate. Also the overall height will be
            reduced, thus increase will be fasle, but decrease
            will be true.
            */
            increase = false;
            decrease = true;
        }
        else
        {
            /* After the rotate the rightChild (new root) will
            be left heavy, and the local root (new left child)
            will be right heavy. The overall height of the tree
            will not change, thus increase and decrease remain
            unchanged.
            */
        }

        // after we rotate we will recursively redo the balances so we don't need
        // to worry about that

        // Now rotate the tree
        return rotateLeft(localRoot);
    }

    private Node rebalanceLeft(Node localRoot)
    {
        System.out.println("Rotating left");
        // Obtain reference to right child
        Node rightChild = localRoot.getRight();
        Node leftChild = localRoot.getLeft();

        // See if right-left heavy
        if (leftChild.balance >= Node.RIGHT_HEAVY)
        {
            System.out.println("Double rotation");
            // Obtain reference to right-left child
            Node temp = leftChild.getRight();

            /* Adjust the balances to be their new values after
            the rotates are performed.
            */
            // I will use a recursive method to fix the balances after I have preformed the first rotation

            /** After the rotates the overall height will be
             reduced thus increase is now false, but
             decrease is now true.
             */
            increase = false;
            decrease = true;
            // Perform double rotation
            localRoot.setLeft(temp);
            leftChild.setRight(temp.getLeft());
            temp.setLeft(leftChild);

            // this method will recursively calculate the balances for the tree
            balanceTree(root);

            return rotateRight(localRoot);
        }
        // added in else
        else if (leftChild.balance > 0)
        {
            /* In this case both the rightChild (the new root)
            and the root (new left child) will both be balanced
            after the rotate. Also the overall height will be
            reduced, thus increase will be fasle, but decrease
            will be true.
            */
            increase = false;
            decrease = true;
        }
        else
        {
            /* After the rotate the rightChild (new root) will
            be left heavy, and the local root (new left child)
            will be right heavy. The overall height of the tree
            will not change, thus increase and decrease remain
            unchanged.
            */
        }

        // a recursive method will fix the balances after the rotation

        // Now rotate the tree
        return rotateRight(localRoot);
    }

    private void incrementBalance(Node node)
    {
        node.balance++;
        if (node.balance > Node.BALANCED)
        {
            // if now right heavy, the overall height has increased, but
            //it has not decreased
            increase = true;
            decrease = false;
        }
        else {
            // if now balanced, the overall height has not increased, but
            //    it has decreased.
            increase = false;
            decrease = true;
        }
    }

    private Node  rotateRight(Node  root)
    {
        System.out.println("Rotating Right");
        //There is where you set up your references to get the proper rotation
        //see hint in rotateLeft
        Node temp = root.getLeft();
        root.setLeft(temp.getRight());
        temp.setRight(root);
        return temp;
    }


    private Node rotateLeft(Node localRoot)
    {
        System.out.println("Rotating Left");
        //// hint this was only three lines that I took out.
        Node temp = localRoot.getRight();
        localRoot.setRight(temp.getLeft());
        temp.setLeft(localRoot);
        return temp;
    }

    public void printLeft(Node localRoot)
    {
        if (localRoot == null)
        {
        }
        else if (localRoot.equals(root))
        {
            printLeft(localRoot.getLeft());
            System.out.print(" "+localRoot.getItem() + ",");
        }
        else
        {
            printLeft(localRoot.getLeft());
            System.out.print(" "+localRoot.getItem() + ",");
            printLeft(localRoot.getRight());
        }
    }

    public void printRight(Node localRoot)
    {
        if (localRoot == null) {}
        else if (localRoot.equals(root))
        {
            System.out.print(" "+localRoot.getItem() + ",");
            printRight(localRoot.getRight());
        }
        else
        {
            printRight(localRoot.getLeft());
            System.out.print(" "+localRoot.getItem() + ",");
            printRight(localRoot.getRight());
        }
    }

    public void printBalances(Node localRoot)
    {
        if (localRoot == null)
        {
        }
        else
        {
            printBalances(localRoot.getLeft());
            System.out.print(" " + localRoot.balance + ", ");
            printBalances(localRoot.getRight());
        }
    }

    public void preOrderPrint(Node localRoot)
    {
        if (localRoot == null)
        {
        }
        else
        {
            System.out.print(" " + localRoot.getItem() + ", ");
            if (localRoot.getLeft() != null)
            {
                System.out.print(" Left: (");
            }
            preOrderPrint(localRoot.getLeft());

            if (localRoot.getRight() != null)
            {
                System.out.print(") Right: (");
            }
            preOrderPrint(localRoot.getRight());
            System.out.print(" )");
        }
    }

    public void preOrderBalancePrint(Node localRoot)
    {
        if (localRoot == null)
        {
        }
        else
        {
            System.out.print(" " + localRoot.balance + ", ");
            preOrderBalancePrint(localRoot.getLeft());
            preOrderBalancePrint(localRoot.getRight());
        }
    }

    public void inOrderPrint(Node localRoot)
    {
        if (localRoot == null)
        {
        }
        else
        {
            inOrderPrint(localRoot.getLeft());
            System.out.print(" " + localRoot.getItem() + ",");
            inOrderPrint(localRoot.getRight());
        }
    }

    public int balanceTree(Node localRoot)
    {
        if (localRoot == null)
        {
            return 0;
        }
        else
        {
            int left = balanceTree(localRoot.getLeft());
            int right = balanceTree(localRoot.getRight());

            if (localRoot.getLeft() != null)
            {
                left++;
            }

            if (localRoot.getRight() != null)
            {
                right++;
            }

            localRoot.balance = right - left;

            if (left > right)
            {
                return left;
            }
            else
            {
                return right;
            }
        }
    }

    public Node getRoot()
    {
        return root;
    }

}