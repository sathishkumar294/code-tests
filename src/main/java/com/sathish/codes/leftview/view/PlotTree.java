package com.sathish.codes.leftview.view;

import com.sathish.codes.leftview.model.Node;
import com.sathish.codes.leftview.service.PrintService;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PlotTree {
    List<Point> points = new ArrayList<>();
    private final Integer SPACE_TO_ADD = 4;

    public void addNode(Node node, int lineNo) {
        var point = points.stream().filter(p -> p.getNode().getId().equals(node.getId())).findFirst();
        if (point.isEmpty()) {
            int maxX = points.stream().filter(p -> p.getY() == lineNo).map(Point::getX).reduce(Math::max).orElse(0);
            Point parentPoint = getPointForNode(node.getParentNode());
            Point leftPoint = getPointForNode(node.getLeftNode());
            Point rightPoint = getPointForNode(node.getRightNode());
            // If a point already exists on the line, leave a space and add this point. else add it at the start.
            int newX = (maxX > 0 ? maxX + SPACE_TO_ADD : 1);
            Point pt = new Point(newX, lineNo, node, parentPoint, leftPoint, rightPoint);
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
