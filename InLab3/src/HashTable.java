/**
 * Created by fredPersonal on 2/14/14.
 */
import java.util.*;

public class HashTable
{
    Node[] array;

    public HashTable()
    {
        array = new Node[5];
    }

    /**
     * Here we will insert a node into the hash table
     * @param newNode
     */
    public void insertObject(Node newNode)
    {
        int filledNodes = 0;

        for (int i = 0; i < array.length; i++)
        {
            if (array[i] != null)
            {
                filledNodes++;
            }
        }

        if ((double)filledNodes / (double) array.length >= .8)
        {
            doubleArraySize();
        }

        int key = newNode.getKey();

        int index = key % array.length;

        if (array[index] == null)
        {
            array[index] = newNode;
        }
        else
        {
            while (array[index] != null)
            {
                index = (index + 1) % array.length;
            }
            array[index] = newNode;
        }
    }

    /**
     * Now we will delete a node in the hash table or replace it with a dummy value
     * @param key
     */
    public void delete(int key)
    {
        int key2 = key;
        key %= array.length;

        if (array[key] == null)
        {
        }
        else if (array[key].getKey() == key2)
        {
            array[key] = new Node(-999, "");
        }
        else
        {
            while (array[key] != null && array[key].getKey() != key2)
            {
                key = (key + 1) % array.length;
            }
            if (array[key] == null)
            {
            }
            else
            {
                array[key] = new Node(-999, "");
            }
        }
    }

    /**
     * Here we will search for a node in the hash table given a key
     * @param key
     * @return
     */
    public String search(int key)
    {
        int key2 = key;
        key %= array.length;

        if (array[key] == null)
        {
            return "";
        }
        else if (array[key].getKey() == key2)
        {
            return array[key].getData();
        }
        else
        {
            while (array[key] != null && array[key].getKey() != key2)
            {
                key = (key + 1) % array.length;
            }

            if (array[key] == null)
            {
                return "";
            }
            else
            {
                return array[key].getData();
            }
        }
    }

    /**
     * Now we will double the size of the hash table to accommodate more entries
     */
    public void doubleArraySize()
    {
        System.out.println("Double array size");
        Node[] newArray = new Node[array.length * 2];
        int key;

        for (int i = 0; i < array.length; i++)
        {
            if (array[i] != null)
            {
                if (array[i].getKey() !=- - 999)
                {
                    key = array[i].getKey() % newArray.length;


                    if (newArray[key] == null)
                    {
                        newArray[key] = array[i];
                    }
                    else
                    {
                        while (newArray[key] != null)
                        {
                            key =( key +1 )% newArray.length;
                        }
                        newArray[key] = array[i];
                    }
                }
            }
        }
        array = newArray;
    }

    /**
     * We will loop through the table and print out all non null entries data
     */
    public void printTable()
    {
        for (int i = 0; i < array.length; i++)
        {
            if (array[i] != null)
            {
                //System.out.println(""+array[i].getKey());
                System.out.println(" " + array[i].getData());
            }
        }
    }
}
