/**
 * Created by fredPersonal on 2/14/14.
 */
import java.util.*;

/**
 * These objects will be held in our hash table and will have an integer key and data composed of a string
 */
public class Node
{
    private int key;
    private String data;

    public Node(int key, String data)
    {
        this.key = key;
        this.data = data;
    }

    public int getKey()
    {
        return key;
    }

    public String getData()
    {
        return data;
    }

    public void setKey(int key)
    {
        this.key = key;
    }

    public void setData(String data)
    {
        this.data = data;
    }

}