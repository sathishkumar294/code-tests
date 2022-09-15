package com.sathish.codes.leftview.view;

import com.sathish.codes.leftview.model.Node;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class PlotTree {
    List<Point> points = new ArrayList<>();
    private final Integer SPACE_TO_ADD = 4;

    public void addNode(Node node, int lineNo) {
        var point = points.stream().filter(p -> p.getNode().getId().equals(node.getId())).findFirst();
        if (point.isEmpty()) {
            int maxX = points.stream().filter(p -> p.getY() == lineNo).map(Point::getX).reduce(Math::max).orElse(0);
            // If a point already exists on the line, leave a space and add this point. else add it at the start.
            int newX = (maxX > 0 ? maxX + SPACE_TO_ADD : 1);
            Point pt = new Point(newX, lineNo, node);
            points.add(pt);
            adjustParentPositions(lineNo);
        }
    }

    void adjustParentPositions(int lineNo) {
        if (lineNo <= 0) return;
        // TODO: Sort the elements by parent id
        List<Point> pointsOnLine = points.stream().filter(p -> p.getY() == lineNo).collect(Collectors.toList());
        for (int i = 0; i < pointsOnLine.size(); i++) {
            Point p1 = pointsOnLine.get(i);
            Point p2 = pointsOnLine.stream().filter(p -> p.getNode().getParentNode() != null && p.getNode().getParentNode().equals(p1.getNode().getParentNode())).findFirst().orElse(null);
            int parentX = 1;
            if (p2 != null && p2.getX() != 0) {
                parentX = (p1.getX() + p2.getX()) / 2;
            } else {
                parentX = p1.getX();
            }
            Point parentPoint = findPointForNode(p1.getNode().getParentNode());
            if (parentPoint != null) {
                parentPoint.setX(parentX);
            }
        }
        adjustParentPositions(lineNo - 1);
    }

    private Point getPointForNode(Node node) {
        if (node == null) return null;
        return this.points.stream().filter(point -> point.getNode().getId().equals(node.getId())).findFirst().orElse(new Point(0, 0, node));
    }

    private Point findPointForNode(Node node) {
        if (node == null) return null;
        return this.points.stream().filter(point -> point.getNode().getId().equals(node.getId())).findFirst().orElse(null);
    }
}
