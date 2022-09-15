package com.sathish.codes.leftview.service;

import com.sathish.codes.leftview.model.Node;
import com.sathish.codes.leftview.model.Tree;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public class PrintService {

    public PrintService() {
        segments = new ArrayList<>();
    }

    private List<Segment> segments;

    public void printTree(Tree tree) {
        Node startNode = tree.getStartNode();
        processLineForNode(startNode);
    }

    public void processLineForNode(Node node) {
        Node leftNode = node.getLeftNode();
        Integer spaceForRightNode = 1;
        Integer spaceForNode = leftNode != null ? leftNode.getValue().length() + 1 : 1;
        var segment1 = getSegmentForNodeId(node.getId());
        if (segment1 == null) {
            segment1 = new Segment(node.getId(), node.getValue(), spaceForNode, 1);
            segments.add(segment1);
        }
        if (leftNode != null) {
            Integer spaceForLeftNode = 0;
            spaceForRightNode = leftNode != null ? leftNode.getValue().length() + 1 + node.getValue().length() + 1 : 1;

            var segment2 = getSegmentForNodeId(leftNode.getId());
            if (segment2 == null) {
                segment2 = new Segment(leftNode.getId(), leftNode.getValue(), spaceForLeftNode, segment1.getLineNo() + 1);
                segments.add(segment2);
            }

//        segment1.setNoOfSpace(segment1.getNoOfSpace() + spaceForNode);
            segment2.setNoOfSpace(segment2.getNoOfSpace() + spaceForLeftNode);
//        segment3.setNoOfSpace(segment3.getNoOfSpace()+spaceForRightNode);
            if (segment2.getNoOfSpace() != 0) {
                updateParentSpacing(node, spaceForLeftNode);
            }

            processLineForNode(leftNode);
        }
        Node rightNode = node.getRightNode();
        if (rightNode != null) {
            var segment3 = getSegmentForNodeId(rightNode.getId());
            if (segment3 == null) {
                segment3 = new Segment(rightNode.getId(), rightNode.getValue(), spaceForRightNode, segment1.getLineNo() + 1);
                segments.add(segment3);
            }
            processLineForNode(rightNode);
        }

        printSegments();
        System.out.println("=================================================");

    }

    private void printSegments() {
        segments.sort((s1, s2) -> s1.getLineNo() - s2.getLineNo());
        for (int i = 0; i < segments.size(); i++) {
            Segment s = segments.get(i);
//            System.out.println("%1$" + s.getNoOfSpace() + "s");
            System.out.printf("%1$" + (s.getNoOfSpace()>0?s.getNoOfSpace():"") + "s", s.getValue());
            if (i != segments.size() - 1 && s.getLineNo() != segments.get(i + 1).getLineNo()) {
                System.out.println();
            }
        }
        ;
    }

    public void updateParentSpacing(Node node, Integer spaceToAdd) {
        if (node == null) return;
        Segment segment = getSegmentForNodeId(node.getId());
        if (segment != null) {
            segment.setNoOfSpace(segment.getNoOfSpace() + spaceToAdd);
            updateParentSpacing(node.getParentNode(), spaceToAdd);
        }
    }

    private Segment getSegmentForNodeId(String id) {
        return this.segments.stream().filter(segment -> segment.nodeId.equals(id)).findFirst().orElse(null);
    }

    @Data
    class Line {
        private List<Segment> segments = new ArrayList<>();
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
