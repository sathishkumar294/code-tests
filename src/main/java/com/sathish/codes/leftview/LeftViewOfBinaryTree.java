package com.sathish.codes.leftview;

import com.sathish.codes.leftview.model.Tree;
import com.sathish.codes.leftview.service.PrintService;
import com.sathish.codes.leftview.service.TreeService;

public class LeftViewOfBinaryTree {

    public static void main(String[] args) {

        TreeService treeService = TreeService.getInstance();
        PrintService printService = new PrintService();
        Tree sampleTree = treeService.getSampleTree();
        printService.printTree(sampleTree);

    }
}
