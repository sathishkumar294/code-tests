package com.sathish.codes.leftview.model;

import lombok.*;

import java.util.Objects;
import java.util.UUID;

/**
 * Node in a binary tree
 */
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class Node {

    @NonNull
    private String value;
    private Node leftNode;
    private Node rightNode;

    private Node parentNode;

    // Unique ID
    private String id = UUID.randomUUID().toString();

    public void setNodes(Node leftNode, Node rightNode) {
        this.leftNode = leftNode;
        this.rightNode = rightNode;
        if (leftNode != null) leftNode.setParentNode(this);
        if (rightNode != null) rightNode.setParentNode(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return id.equals(node.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
