package com.sathish.codes.leftview.service;

import com.sathish.codes.leftview.model.Node;
import com.sathish.codes.leftview.model.Tree;
import com.sathish.codes.leftview.view.PlotTree;
import com.sathish.codes.leftview.view.Point;

import java.util.*;

public class PrintService {

    public PrintService(boolean printCoordinatesOnly) {
        plotTree = new PlotTree();
        this.printCoordinatesOnly = printCoordinatesOnly;
    }

    private final PlotTree plotTree;
    private boolean printCoordinatesOnly = false;

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
                if (printCoordinatesOnly) {
                    System.out.println(point.getNode().getValue() + " :(" + point.getX() + ", " + point.getY() + ")");
                } else {
                    int spaceToLeave = prevPoint != null ? point.getX() - prevPoint.getX() : point.getX();
                    System.out.printf("%1$" + (spaceToLeave > 0 ? spaceToLeave : "") + "s", point.getNode().getValue());
                }
            }
        });
    }


}
