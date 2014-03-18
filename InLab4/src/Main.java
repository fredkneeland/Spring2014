/**
 * Created by fredPersonal on 2/28/14.
 */

import java.util.*;
import java.io.*;


public class Main {

    public static void main(String[] args)
    {
        int size = 0;
        File file = new File("graph.txt");
        Reader reader = null;

        try
        {
            BufferedReader in = new BufferedReader(new FileReader("graph.txt"));

            String line = in.readLine();

            while (line != null)
            {
                size++;
                line = in.readLine();
            }

            reader = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(file)));

        } catch (IOException e) {}

        int array[][] = new int[size][size];

        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                try
                {
                    array[i][j] = reader.read() - '0';
                    System.out.print(array[i][j]);
                } catch (IOException e) {}
            }
            try
            {
                reader.read();
            } catch (IOException e) {}
            System.out.println();
        }

        // breadth-first-search
        breadthFirstSearch(array, 0);

        //depth-first-search
        int[] accessed = new int[size];

        for (int k = 0; k < size; k++)
        {
            accessed[k] = 0;
        }

        System.out.println();
        System.out.println("Depth-first-search");
        depthFirstSearch(accessed, array, 0);
    }


    public static void breadthFirstSearch(int[][] matrix, int startingPoint)
    {
        System.out.println("Breadth First Search");
        int[] accessiblePoints = new int[matrix.length];

        int[] pointsToSearch = new int[matrix.length];

        for (int i = 0; i < matrix.length; i++)
        {
            pointsToSearch[i] = -1;
            accessiblePoints[i] = 0;
        }

        pointsToSearch[0] = startingPoint;
        accessiblePoints[startingPoint] = 1;
        System.out.print(startingPoint);

        while (pointsToSearch[0] != -1)
        {
            int currentPoint = pointsToSearch[0];

            for (int j = 0; j < matrix.length; j++)
            {
                int pointToAdd = matrix[currentPoint][j];

                // if we can traverse this point we might do something with it
                if (pointToAdd != 0)
                {
                    // if it is not already in our traversed list Then lets add it to nodes to traverse
                    if (accessiblePoints[j] == 0)
                    {
                        int k = 0;
                        while (k < pointsToSearch.length && pointsToSearch[k] != -1)
                        {
                            k++;
                        }
                        System.out.print(j);
                        pointsToSearch[k] = pointToAdd;
                        accessiblePoints[j] = 1;
                    }
                }
            }
            for (int l = 0; l < pointsToSearch.length-1; l++)
            {
                pointsToSearch[l] = pointsToSearch[l+1];
            }
            pointsToSearch[pointsToSearch.length-1] = -1;
        }
    }

    public static void depthFirstSearch(int[] accessed, int[][] matrix, int startingPoint)
    {
        System.out.print(startingPoint);
        accessed[startingPoint] = 1;

        for (int i = 0; i < matrix.length; i++)
        {
            if (matrix[startingPoint][i] == 1)
            {
                if (accessed[i] == 0)
                {
                    depthFirstSearch(accessed, matrix, i);
                }
            }
        }
    }
}