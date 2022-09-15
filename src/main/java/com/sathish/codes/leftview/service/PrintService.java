package com.sathish.codes.leftview.service;

import com.sathish.codes.leftview.model.Node;
import com.sathish.codes.leftview.model.Tree;
import com.sathish.codes.leftview.view.PlotTree;
import com.sathish.codes.leftview.view.Point;

import java.util.*;

public class PrintService {

    public PrintService() {
        plotTree = new PlotTree();
    }

    private final PlotTree plotTree;

    public void printTree(Tree tree) {
        Node startNode = tree.getStartNode();
        processLinesForNode(startNode, 1);
    }

    public void processLinesForNode(Node node, int lineNo) {
        if (node != null) {
            plotTree.addNode(node, lineNo);
            System.out.println("\n\n=================================================");
            printPlotTree();
            processLinesForNode(node.getLeftNode(), lineNo + 1);
            processLinesForNode(node.getRightNode(), lineNo + 1);
        }

    }

    private void printPlotTree() {
        List<Point> points = plotTree.getPoints();
        Map<Integer, List<Point>> pointsGroups = new HashMap<>();
        points.forEach(point -> {
            pointsGroups.computeIfAbsent(point.getY(), k -> new ArrayList<>());
            pointsGroups.get(point.getY()).add(point);
        });
        pointsGroups.forEach((groupNo, gPoints) -> gPoints.sort(Comparator.comparingInt(Point::getX)));
        pointsGroups.values().stream().sorted(Comparator.comparingInt(pg -> pg.get(0).getY())).forEach(gPoints -> {
            System.out.println("");
            for (int i = 0; i < gPoints.size(); i++) {
                Point point = gPoints.get(i);
                Point prevPoint = i > 0 ? gPoints.get(i - 1) : null;
                int spaceToLeave = prevPoint != null ? point.getX() - prevPoint.getX() : point.getX();
//                System.out.println(point.node.getValue() + " :(" + point.x + ", " + point.y + ")");
                System.out.printf("%1$" + (spaceToLeave > 0 ? spaceToLeave : "") + "s", point.getNode().getValue());
            }
        });
    }


}
