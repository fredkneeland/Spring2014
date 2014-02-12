 
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

public boolean add(int item) {

    System.out.println("Starting to search for a a place to put " + item);
    increase = false;
    root = add(root, item);
    return addReturn;
  }

  private Node add(Node localRoot, int item) {
     
    if (localRoot == null) {
      addReturn = true;
      increase = true;
       System.out.println("Added " + item);
      return new Node(item);
    }
   System.out.println("Add called with " + localRoot.getItem() + " as the root.");
    if (item == localRoot.getItem()) {                                //item is alreday in tree
      increase = false;
      addReturn = false;
      return localRoot;
    }

    else if (item  < localRoot.getItem()) {
       System.out.println("Branching left");						                        // item < data
      localRoot.setLeft(add(localRoot.getLeft(), item));

      if (increase) {
        decrementBalance(localRoot);
        if (localRoot.balance < Node.LEFT_HEAVY) {
          increase = false;
          return rebalanceLeft(localRoot);
        }
      }
      return localRoot;                                            // Rebalance not needed.
    }
    else {
      System.out.println("Branching right");                                                                   // item > data
      localRoot.setRight(add(localRoot.getRight(), item));
      if (increase) {
        incrementBalance(localRoot);
        if (localRoot.balance > Node.RIGHT_HEAVY) {
          return rebalanceRight(localRoot);
        }
        else {
          return localRoot;
        }
      }
      else {
        return localRoot;
      }
    }

  }

    private void decrementBalance(Node node) 
    {
        node.balance--;
         if (node.balance == Node.BALANCED) {
           increase = false;
         }
     }


     
      private Node rebalanceRight(Node localRoot) {
   

   // Obtain reference to right child
       
    // See if right-left heavy
    if
    {	
      // Obtain reference to right-left child
      
      /* Adjust the balances to be their new values after
         the rotates are performed.
       */
      if 
	  {
	  
      }
      else if 
	  {
      }
      else {
        
      }
      /** After the rotates the overall height will be
          reduced thus increase is now false, but
          decrease is now true.
       */
      increase = false;
      decrease = true;
      // Perform double rotation
      
      return 
    }
    if  
	{
      /* In this case both the rightChild (the new root)
         and the root (new left child) will both be balanced
         after the rotate. Also the overall height will be
         reduced, thus increase will be fasle, but decrease
         will be true.
       */
      
    }
    else {
      /* After the rotate the rightChild (new root) will
         be left heavy, and the local root (new left child)
         will be right heavy. The overall height of the tree
         will not change, thus increase and decrease remain
         unchanged.
       */
    
    }
    // Now rotate the
    return rotateLeft(localRoot);
  }

  private Node rebalanceLeft(Node localRoot) {
    /* I took all of this one out, but it's a mirror 
	image of the one above. */
  }

    private void incrementBalance(Node node) {
      node.balance++;
      if (node.balance > Node.BALANCED) {
         /* if now right heavy, the overall height has increased, but
         it has not decreased */
         increase = true;
         decrease = false;
      }
      else {
         /* if now balanced, the overall height has not increased, but
             it has decreased. */
         increase = false;
         decrease = true;
    }
  }
  
   private Node  rotateRight(Node  root) {
    System.out.println("Rotating Right");
   //There is where you set up your references to get the proper rotation
   //see hint in rotateLeft
    return temp;
  }


   private Node rotateLeft(Node localRoot) {
     System.out.println("Rotating Left");
   //// hint this was only three lines that I took out. 
     return temp;
   }
   
}