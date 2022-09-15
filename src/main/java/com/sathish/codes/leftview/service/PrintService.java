package com.sathish.codes.leftview.service;

import com.sathish.codes.leftview.model.Node;
import com.sathish.codes.leftview.model.Tree;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

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


    @Data
    static
    class PlotTree {
        List<Point> points = new ArrayList<>();

        public void addNode(Node node, int lineNo) {
            var point = points.stream().filter(p -> p.getNode().getId().equals(node.getId())).findFirst();
            if (point.isEmpty()) {
                int maxX = points.stream().filter(p -> p.getY() == lineNo).map(Point::getX).reduce(Math::max).orElse(0);
                Point parentPoint = getPointForNode(node.getParentNode());
                Point leftPoint = getPointForNode(node.getLeftNode());
                Point rightPoint = getPointForNode(node.getRightNode());
                Point pt = new Point((maxX > 0 ? maxX + 2 : 1), lineNo, node, parentPoint, leftPoint, rightPoint);
                points.add(pt);
                if (parentPoint != null) {
                    adjustParentPosition(parentPoint);
                }
            }
        }

        void adjustParentPosition(Point parentPoint) {
            if (parentPoint != null) {
                Point leftPoint = getPointForNode(parentPoint.getNode().getLeftNode());
                Point rightPoint = getPointForNode(parentPoint.getNode().getRightNode());
                int newX = 0;
                if (leftPoint.getX() != 0 && rightPoint.getX() == 0) {
                    newX = rightPoint.getX();
                } else if (leftPoint.getX() == 0 && rightPoint.getX() != 0) {
                    newX = rightPoint.getX();
                } else {
                    newX = (leftPoint.getX() + rightPoint.getX()) / 2;
                }
                parentPoint.setX(newX);
                adjustParentPosition(parentPoint.getParentPoint());
            }
        }

        private Point getPointForNode(Node node) {
            if (node == null) return null;
            return this.points.stream().filter(point -> point.getNode().getId().equals(node.getId())).findFirst().orElse(new Point(0, 0, node));
        }
    }

    @Data
    @AllArgsConstructor
    @RequiredArgsConstructor
    static
    class Point {
        @NonNull
        private int x;
        @NonNull
        private int y;
        @NonNull
        private Node node;

        private Point parentPoint;

        private Point leftChildPoint;
        private Point rightChildPoint;
    }

}
