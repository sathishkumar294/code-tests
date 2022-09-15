package com.sathish.codes.leftview.view;

import com.sathish.codes.leftview.model.Node;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Point {

    private int x;
    private int y;
    private Node node;
}