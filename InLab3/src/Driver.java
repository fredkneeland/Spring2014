/**
 * Created by fredPersonal on 2/14/14.
 */
import java.util.*;
import java.io.*;

public class Driver
{
    public static void main(String [] args)
    {
        // first we import the file
        String filename = "hashTable.txt";
        File file = new File(filename);
        String content = null;

        try
        {
            FileReader reader = new FileReader(file);
            char[] chars = new char[(int) file.length()];
            reader.read(chars);
            content = new String(chars);
            reader.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        int[] numbs = new int[20];
        int b = 0;
        HashTable hashTable = new HashTable();

        // we will attempt to read in the file
        try
        {
            BufferedReader in = new BufferedReader(new FileReader(filename));

            String line = in.readLine();
            while (line != null)
            {
                StringTokenizer a = new StringTokenizer(line);

                int key = Integer.parseInt(a.nextToken());

                if (b < 20)
                {
                    numbs[b] = key;
                }
                b++;

                String data = a.nextToken();


                String data2;// = a.nextToken();
                try
                {
                    data2 = a.nextToken();
                    if (data2 != null)
                    {
                        data = data + " " + data2;
                    }
                } catch (Exception e) {}


                Node newNode = new Node(key, data);
                hashTable.insertObject(newNode);

                line = in.readLine();
            }

        } catch (IOException e) {}

        // here we will print out the table after creating it
        hashTable.printTable();

        // here we will test searching
        System.out.println();
        System.out.println("Search:");
        System.out.println(hashTable.search(1234));

        // here we will test deleting
        System.out.println();
        System.out.println("Deleted then searching");
        hashTable.delete(1234);
        System.out.println(hashTable.search(1234));

    }
}
