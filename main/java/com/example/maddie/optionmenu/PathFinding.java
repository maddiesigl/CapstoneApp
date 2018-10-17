package com.example.maddie.optionmenu;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static com.example.maddie.optionmenu.PathActivity.getNeighbors;

/**
 * Created by Maddie on 9/4/2018.
 */

public class PathFinding {
    private PointLocation startPos;
    private PointLocation endPos;
    //DatabaseAccess databaseAccess;
    static UndirectedGraphNode [] nodeArr;


    public PathFinding(int [] arr){
        nodeArr = getNodes(arr);
    }

    class UndirectedGraphNode {
        int val;
        List<UndirectedGraphNode> neighbors;
        UndirectedGraphNode(int x) {
            val = x;
            neighbors = new ArrayList<UndirectedGraphNode>();
        }
    }

    public String shortestPath2(int a, int b) {
        System.out.println("a = " + a);
           if(a == 0 || b == 0 || a == b){
               System.out.println("not a valid parameter for search");
               return "";
           }
           else{
               return shortestPath2(nodeArr[a], nodeArr[b]);

           }
    }

    public static String shortestPath2(UndirectedGraphNode a, UndirectedGraphNode b) {
        //System.out.println("shortestPath a = " + a.val + ", b = " + b.val);
        if (a == null || b == null) {
            return "";
        }
        Queue<UndirectedGraphNode> queue = new LinkedList<>();
        Queue<String> paths = new LinkedList<>();
        HashSet<UndirectedGraphNode> set = new HashSet<>();
        queue.offer(a);
        paths.offer(a.val + "");
        set.add(a);
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                UndirectedGraphNode curr = queue.poll();
                String path = paths.poll();
                if (curr == b) {
                    return path;
                }
                for (UndirectedGraphNode neighbor : curr.neighbors) {
                    if (set.contains(neighbor)) {
                        continue;
                    }
                    queue.offer(neighbor);
                    paths.offer(path + " " + neighbor.val);//neighbor.val, not neighbor !!!
                    set.add(neighbor);
                }
            }
        }
        return "";
    }

    public UndirectedGraphNode [] getNodes (int [] arr){
        GlobalApplication globalApplication = new GlobalApplication();

        System.out.println("DB opened");

        nodeArr = new UndirectedGraphNode[arr.length +10] ;
        for(int i = 0; i< arr.length; i++){
            nodeArr[arr[i]] = new UndirectedGraphNode(arr[i]);

        }

        for(int i = 1; i < nodeArr.length; i++) {
            //System.out.println("i = " + i);
            if (nodeArr[i] != null) {
                int[] neighborNodes = PathActivity.getNeighbors(nodeArr[i].val);
                for (int j = 0; j < neighborNodes.length; j++) {
                    if (nodeArr[i] != null && nodeArr[neighborNodes[j]] != null && neighborNodes[j] != 0) {
                        //System.out.println("nodearr[i] = " + nodeArr[i].val + " nodearr[neighborNodes[j] = " + nodeArr[neighborNodes[j]].val);
                        nodeArr[i].neighbors.add(nodeArr[neighborNodes[j]]);
                        nodeArr[neighborNodes[j]].neighbors.add(nodeArr[i]);
                    }

                }
            }
        }

            //System.out.println("nodearr[2] = " + nodeArr[2].neighbors.size());

            //System.out.println("nodearr[201] = " + Arrays.toString(getNeighbors(201)));


        return nodeArr;

    }
}
