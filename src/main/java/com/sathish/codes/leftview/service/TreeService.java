package com.sathish.codes.leftview.service;

import com.sathish.codes.leftview.model.Node;
import com.sathish.codes.leftview.model.Tree;

public class TreeService {

    private static TreeService instance;
    private TreeService(){}

    public static TreeService getInstance() {
        if (instance == null) instance = new TreeService();
        return instance;
    }

    public Tree getSampleTree() {
        Node n1 = new Node("1");
        Node n2 = new Node("2");
        Node n3 = new Node("3");
        Node n4 = new Node("4");
        Node n5 = new Node("5");
        Node n6 = new Node("6");
        Node n7 = new Node("7");
        Node n8 = new Node("8");
        Node n9 = new Node("9");
        Node n10 = new Node("0");
        Node n11 = new Node("1");
        Node n12 = new Node("2");
        Node n13 = new Node("3");
        Node n14 = new Node("4");

        n1.setNodes(n2, n3);
        n2.setNodes(n4, n5);
        n3.setNodes(n6, n7);
        n4.setNodes(n8, n9);
        n5.setNodes(n10, null);
        n6.setNodes(n11, n12);
        n7.setNodes(n13, n14);

        return new Tree(n1);
    }

}
