package com.sathish.codes.leftview.service;

import com.sathish.codes.leftview.model.Node;
import com.sathish.codes.leftview.model.Tree;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.*;

public class PrintService {

    public PrintService() {
        points = new ArrayList<>();
    }

    private List<Point> points;

    public void printTree(Tree tree) {
        Node startNode = tree.getStartNode();
        processPointsForNode(startNode, false);
    }

    public void processPointsForNode(Node node, boolean isRightNode) {
        if (node != null) {
            Point nodePoint = getPointForNode(node);
            Point parentPoint = getPointForNode(node.getParentNode() != null ? node.getParentNode() : null);
            if (nodePoint.getX() == 0 && nodePoint.getY() == 0) {
                int x = parentPoint != null ? parentPoint.getX() : 1;
                int y = parentPoint != null ? parentPoint.getY() : 0;
                nodePoint = new Point(x + (parentPoint != null ? 1 : 0) + (isRightNode ? 1 : 0), y + 1, node);
                // Move the parent if exists to right
                if (parentPoint != null && !isRightNode) {
                    updateParentSpacing(node, 1 + (parentPoint != null ? 1 : 0));
                }
                points.add(nodePoint);
            }
            processPointsForNode(node.getLeftNode(), false);
            processPointsForNode(node.getRightNode(), true);


            printPoints();
        }
        System.out.println("\n\n=================================================");

    }

    private void printPoints() {
        Map<Integer, List<Point>> pointsGroups = new HashMap<>();
        points.forEach(point -> {
            if (pointsGroups.get(point.getY()) == null) {
                pointsGroups.put(point.getY(), new ArrayList<>());
            }
            pointsGroups.get(point.getY()).add(point);
        });
        pointsGroups.forEach((groupNo, points) -> {
            points.sort(Comparator.comparingInt(Point::getX));
        });
//        pointsGroups.forEach((groupNo, points) -> {
//            for (int i = 1; i < points.size(); i++) {
//                Point thisPoint = points.get(i);
//                Point prevPoint = points.get(i - 1);
//                thisPoint.setX(thisPoint.getX() - prevPoint.getX());
//            }
//        });
        pointsGroups.values().stream().sorted(Comparator.comparingInt(pg -> pg.get(0).getY())).forEach(points -> {
            System.out.println("\n");
            for (int i = 0; i < points.size(); i++) {
                Point point = points.get(i);
                Point prevPoint = i > 1 ? points.get(i - 1) : null;
                int spaceToLeave = prevPoint != null ? point.getX() - prevPoint.getX() : point.getX();
                System.out.println(point.node.getValue() + " :(" + point.x + ", " +point.y+")");
//                System.out.printf("%1$" + (spaceToLeave > 0 ? spaceToLeave : "") + "s", point.getNode().getValue());
            };
        });
    }

    public void updateParentSpacing(Node node, Integer spaceToAdd) {
        if (node == null) return;
        Point point = getPointForNode(node);
        if (point != null) {
            point.setX(point.getX() + spaceToAdd);
            updateParentSpacing(node.getParentNode(), spaceToAdd);
            // Move only the right node, as this method is triggered by left node movement
            updateChildSpacing(node.getRightNode(), spaceToAdd);
        }
    }

    public void updateChildSpacing(Node node, Integer spaceToMove) {
        if (node == null) return;
        Point point = getPointForNode(node);
        if (point != null) {
            point.setX(point.getX() + spaceToMove);
            updateChildSpacing(node.getLeftNode(), spaceToMove);
            updateChildSpacing(node.getRightNode(), spaceToMove);
        }

    }

    private Point getPointForNode(Node node) {
        if (node == null) return null;
        return this.points.stream().filter(point -> point.getNode().getId().equals(node.getId())).findFirst().orElse(new Point(0, 0, node));
    }

    @Data
    class TreeDiagram {
        List<Point> points = new ArrayList<>();
    }

    @Data
    @AllArgsConstructor
    class Point {
        private int x;
        private int y;
        private Node node;
    }

    @Data
    @AllArgsConstructor
    class Segment {
        private String nodeId;
        private String value;
        private Integer noOfSpace;
        private Integer lineNo;
    }


}
