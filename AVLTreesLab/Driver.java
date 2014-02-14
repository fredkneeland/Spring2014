import java.util.*;

public class Driver
{
	public static void main(String [] args)
	{
	   AVLTree avl = new AVLTree();
	   Scanner scan = new Scanner(System.in);
	   int start = 0;
	   do{
           System.out.println("Add what? (-1 to quit)");
           start = scan.nextInt();
           scan.nextLine();
           boolean ans = avl.add(start);
           if(!ans)
           {
               System.out.println(start + " not added");
           }

           // here we will print some things out to test the algorithm

           // first we will do inorder print of the left side of the tree
           System.out.println();
           System.out.println("Left Tree: ");
           avl.printLeft(avl.getRoot());
           System.out.println();
           // now we will do inorder print of right side of tree
           System.out.println("Right Tree: ");
           avl.printRight(avl.getRoot());
           System.out.println();
           // now we will preorder print the nodes
           System.out.println("Pre-order print");
           avl.preOrderPrint(avl.getRoot());
           System.out.println();
           // now we will print out the balances
           System.out.println("Pre-order balances: ");
           avl.balanceTree(avl.getRoot());
           avl.preOrderBalancePrint(avl.getRoot());
           System.out.println();
           System.out.println("In order print");
           avl.inOrderPrint(avl.getRoot());
           System.out.println();

       } while(start != -1);
	    
	    System.out.println("Goodbye");
	}
}
