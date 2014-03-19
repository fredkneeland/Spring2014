/**
 * Created by Fred Kneeland on 2/28/14.
 * CSCI 232 Inlab 04
 */

import java.io.*;
import java.util.*;


public class Main
{
    public static void main(String[] args)
    {
        int size = 0;
        File file = new File("graph.txt");
        File file2 = new File("graph2.txt");
        Reader reader = null;
        Reader reader2 = null;
        Scanner graph2 = null;

        try
        {
            BufferedReader in = new BufferedReader(new FileReader("graph.txt"));
            graph2 = new Scanner(file2);

            String line = in.readLine();

            while (line != null)
            {
                size++;
                line = in.readLine();
            }

            reader = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(file)));
            reader2 = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(file2)));

        } catch (IOException e) {}

        int array[][] = new int[size][size];
        int matrix[][] = new int[size][size];
        int next = 0;

        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                try
                {
                    int read = reader.read();
                    array[i][j] = read - '0';
                    System.out.print(array[i][j]);
                    int current = reader2.read();
                    next = 0;
                    // we go till we hit a , or the end of the file or a non number
                    while(current != 44 && (current != -1) && current >= '0')
                    {
                        next *= 10;
                        next += current - '0';
                        current = reader2.read();
                    }
                    matrix[i][j] = next;
                } catch (IOException e) {}
            }
            try
            {
                reader.read();
            } catch (IOException e) {}
            System.out.println();
        }

        for (int a = 0; a < size; a++)
        {
            for (int b = 0; b < size; b++)
            {
                System.out.print(matrix[a][b] + ", ");
            }
            System.out.println();
        }

        // breadth-first-search
        breadthFirstSearch(array, 0);

        //depth-first-search
        int[] accessed = new int[size];

        // set up array for depth-first-search
        for (int k = 0; k < size; k++)
        {
            accessed[k] = 0;
        }

        System.out.println();
        System.out.println("Depth-first-search");
        depthFirstSearch(accessed, array, 0);

        System.out.println();
        System.out.println("Dijkstra's Algorithm: ");
        dijkstras(matrix);

        System.out.println();
        System.out.println("Prims: ");
        prims(matrix);

        System.out.println();
        System.out.println("Floyd's: ");
        floyds(array);

        System.out.println();
        System.out.println("Floyd Warsall's:");
        floydWarsalls(matrix);
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

    // finds shortest path from start to end of matrix
    public static void dijkstras(int[][] matrix)
    {
        PriorityQueue<Integer> queue = new PriorityQueue<Integer>();

        int[] accessed = new int[matrix.length];
        int[] lastNode = new int[matrix.length];
        int[] totalDist = new int[matrix.length];


        // we loop through first set adding them to queue
        for (int i = 0; i < matrix[0].length; i++)
        {
            if (matrix[0][i] > 0)
            {
                totalDist[i] = matrix[0][i];
                queue.add(i);
                lastNode[i] = 0;
            }
        }

        // as long as their are elements in the array we keep adding
        while (queue.size() > 0)
        {
            int current = queue.remove();

            for (int j = 1; j < matrix.length; j++)
            {
                if (accessed[j] != 1)
                {
                    if (matrix[current][j] > 0)
                    {
                        // if it is faster to go through us to next node then we change it
                        if (totalDist[current] + matrix[current][j] < totalDist[j] || totalDist[j] < 1)
                        {
                            totalDist[j] = totalDist[current] + matrix[current][j];
                            lastNode[j] = current;
                            queue.add(j);
                        }
                    }
                }
            }
        }

        int traversedNode = matrix.length-1;

        while(traversedNode != 0)
        {
            System.out.print(""+traversedNode+", ");
            traversedNode = lastNode[traversedNode];
        }
        System.out.println();
        System.out.println("Distance: "+totalDist[matrix.length-1]);
    }

    // here we create a spanning tree to calc shortest dist from top to bottom
    public static void prims(int[][] matrix)
    {
        int[] pointsToSearch = new int[matrix.length];
        int[] distances = new int[matrix.length];
        // first row is dist
        // second row is parent node
        int[][] spanningTree = new int[matrix.length][2];


        // first add all elements adjacent to root
        for (int i = 0; i < matrix.length; i++)
        {
            pointsToSearch[i] = -1;
        }

        pointsToSearch[0] = 0;

        while(pointsToSearch[0] != -1)
        {
            int current = pointsToSearch[0];

            for (int k = 0; k < matrix.length; k++)
            {
                if (matrix[current][k] > 0)
                {
                    // if it hasn't been included yet then we add it in
                    if (spanningTree[k][0] < 1)
                    {
                        spanningTree[k][0] = matrix[current][k];
                        spanningTree[k][1] = current;
                        int l = 0;
                        while (l < pointsToSearch.length && pointsToSearch[l] != -1)
                        {
                            l++;
                        }
                        if (l < pointsToSearch.length)
                            pointsToSearch[l] = k;
                    }
                    // if it is faster to go through us then do so
                    else if ((primsDist(spanningTree, current, 0) + matrix[current][k]) < primsDist(spanningTree, k, 0))
                    {
                        spanningTree[k][0] = matrix[current][k];
                        spanningTree[k][1] = current;
                        int l = 0;
                        while (l < pointsToSearch.length && pointsToSearch[l] != -1)
                        {
                            l++;
                        }
                        if (l < pointsToSearch.length)
                            pointsToSearch[l] = k;
                    }
                }
            }

            int j;
            for (j = 0; j < matrix.length-1; j++)
            {
                pointsToSearch[j] = pointsToSearch[j+1];
            }
            pointsToSearch[j] = -1;
        }

        // print dist to parent node for each node
        for (int m = 0; m < matrix.length; m++)
        {
            System.out.print(spanningTree[m][0]+", ");
        }
        System.out.println();
        // print out parent node fore each node
        for (int n = 0; n < matrix.length; n++)
        {
            System.out.print(spanningTree[n][1]+", ");
        }
        System.out.println();
        // gives dist from a to e or 0 to 4
        System.out.println("Shortest Dist: " + primsDist(spanningTree, matrix.length-1, 0));
    }

    // this func recursively calcs the total prims dist using its spanning tree stored in the array distances
    public static int primsDist(int distances[][], int current, int dist)
    {
        if (current == 0)
        {
            return dist;
        }
        else
        {
            if (distances[current][0] < 1)
            {
                return -1;
            }
            else
            {
                return primsDist(distances, distances[current][1], (dist + distances[current][0]));
            }
        }
    }

    // this func calculates floyds adjacency matrix
    public static void floyds(int[][] matrix)
    {
        for (int i = 0; i < matrix.length; i++)
        {
            for (int j = 0; j < matrix.length; j++)
            {
                for (int k = 0; k < matrix.length; k++)
                {
                    if (matrix[i][j] == 1 && matrix[k][i] == 1)
                    {
                        matrix[k][j] = 1;
                    }
                }
            }
        }

        // here we print out adjacency matrix
        for (int l = 0; l < matrix.length; l++)
        {
            for (int m = 0; m < matrix.length; m++)
            {
                System.out.print(matrix[l][m]+", ");
            }
            System.out.println();
        }
    }

    // this func calcs shortest dist from any node to any other node
    public static void floydWarsalls(int[][] matrix)
    {
        for (int i = 0; i < matrix.length; i++)
        {
            for (int j = 0; j < matrix.length; j++)
            {
                for (int k = 0; k < matrix.length; k++)
                {
                    if (matrix[i][j] > 0 && matrix[k][i] > 0)
                    {
                        if ((matrix[k][j] > (matrix[i][j] + matrix[k][i]) || matrix[k][j] == 0) && j != k)
                        {
                            matrix[k][j] = (matrix[i][j] + matrix[k][i]);
                        }
                    }
                }
            }
        }
        // here we print out matrix of distances from all nodes to all other nodes
        for (int l = 0; l < matrix.length; l++)
        {
            for (int m = 0; m < matrix.length; m++)
            {
                System.out.print(matrix[l][m]+", ");
            }
            System.out.println();
        }
    }
}