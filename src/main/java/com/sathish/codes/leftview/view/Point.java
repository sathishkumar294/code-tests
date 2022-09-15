package com.sathish.codes.leftview.view;

import com.sathish.codes.leftview.model.Node;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Point {
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