/**
 *
 * Copyright (c) Baboune, 2013.
 *
 * All Rights Reserved. Reproduction in whole or in part is prohibited
 * without the written consent of the copyright owner.
 *
 * ERICSSON MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF
 * THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE, OR NON-INFRINGEMENT. ERICSSON SHALL NOT BE LIABLE FOR
 * ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 *
 * User: Baboune
 * Date: 10/4/13
 */

/**
 * comments.
 */
public class KdTree {
    static final int VERTICAL = 1;
    static final int HORIZONTAL = 2;
    private Node root;

    static class Node {


        private Point2D value;
        private Node left, right;
        private int n;
        private int type = HORIZONTAL;
        private RectHV rect;

        public Node(Point2D value, int n, int type, RectHV rectHV) {
            this.value = value;
            this.n = n;
            this.type = type;
            this.rect = rectHV;
        }

        public int compareTo(Point2D point) {
            double thisVal = value.y();
            double thatVal = point.y();
            if (this.type == VERTICAL) {
                thisVal = value.x();
                thatVal = point.x();
            }

            if (thisVal < thatVal) {
                return -1;
            } else if (thisVal > thatVal) {
                return 1;
            }
            return 0;
        }

        public int compareTo(RectHV r) {
            double x = value.x();
            double y = value.y();

            if (this.type == HORIZONTAL) {
                if (x > r.xmax()) {
                    // left
                    return 1;
                }
                if (x < r.xmin()) {
                    // right
                    return -1;
                }
            } else {
                if (y > r.ymax()) {
                    // bottom
                    return 1;
                }
                if (y < r.ymin()) {
                    // top
                    return -1;
                }
            }

            // intersect
            return 0;
        }

        public RectHV getChildRect(Point2D p) {
            double xmin = rect.xmin();
            double xmax = rect.xmax();
            double ymin = rect.ymin();
            double ymax = rect.ymax();

            if (type == VERTICAL) {
                //Is it left or right?
                if (value.x() > p.x()) {
                    xmax = this.value.x();
                } else {
                    xmin = this.value.x();
                }
            } else {
                // HORIZONTAL
                // Is it above or less?
                if (value.y() > p.y()) {
                    ymax = this.value.y();
                } else {
                    ymin = this.value.y();
                }
            }
            return new RectHV(xmin, ymin, xmax, ymax);
        }
    }

    // construct an empty set of points
    public KdTree() {
        root = null;
    }

    // is the set empty?
    public boolean isEmpty() {
        return (root == null);
    }

    // number of points in the set
    public int size() {
        return size(root);
    }


    // add the point p to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) {
            throw new NullPointerException("Expected a point.");
        }
        root = put(root, p, null);
    }

    // does the set contain the point p?
    public boolean contains(Point2D p) {
        if (root == null) {
            return false;
        }
        return get(root, p) != null;
    }

    // draw all of the points to standard draw
    public void draw() {
        // draw canvas border
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.line(0, 0, 1, 0);
        StdDraw.line(1, 0, 1, 1);
        StdDraw.line(1, 1, 0, 1);
        StdDraw.line(0, 1, 0, 0);

        draw(root, null);

    }

    // all points in the set that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        return null;
    }

    // a nearest neighbor in the set to p; null if set is empty
    public Point2D nearest(Point2D p) {
        return null;
    }

    private int size(Node node) {
        if (node != null) {
            return node.n;
        }
        return 0;
    }

    private Node put(Node node, Point2D point, Node parent) {
        if (node == null) {
            if (parent == null) {
                return new Node(point, 1, VERTICAL, new RectHV(0d, 0d, 1d, 1d));
            } else {
                RectHV r = parent.getChildRect(point);
                System.out.println("Rect: " + r);
                return new Node(point, 1, opposite(parent.type), r);
            }

        }
        double nodeValue = node.value.y();
        double pointValue = point.y();
        if (node.type == VERTICAL) {
            nodeValue = node.value.x();
            pointValue = point.x();
        }
        if (pointValue < nodeValue) {
            node.left = put(node.left, point, node);
        } else if (pointValue > nodeValue) {
            node.right = put(node.right, point, node);
        } else {
            node.value = point;
        }
        node.n = size(node.left) + size(node.right) + 1;
        return node;
    }

    private Point2D get(Node node, Point2D point) {
        if (node == null) {
            return null;
        }
        double nodeValue = node.value.y();
        double pointValue = point.y();
        if (node.type == VERTICAL) {
            nodeValue = node.value.x();
            pointValue = point.x();
        }
        if (pointValue < nodeValue) {
            return get(node.left, point);
        } else if (pointValue > nodeValue) {
            return get(node.right, point);
        }
        return node.value;
    }

    private void draw(Node node, Node parent) {
        if (node == null) {
            return;
        }

        StdDraw.setPenColor(StdDraw.BLACK);
        node.value.draw();

        double x = node.value.x();
        double y = node.value.y();

        if (node.type == HORIZONTAL) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(x, node.rect.ymin(), x, node.rect.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(node.rect.xmin(), y, node.rect.xmax(), y);
        }
        draw(node.left, node);
        draw(node.right, node);
    }

    private int opposite(int type) {
        if (type == VERTICAL) {
            return HORIZONTAL;
        }
        return VERTICAL;
    }

    public static void main(String[] args) {

        KdTree tree = new KdTree();

        Point2D p1 = new Point2D(0.7, 0.1);
        Point2D p2 = new Point2D(0.5, 0.4);
        Point2D p3 = new Point2D(0.9, 0.6);
        Point2D p4 = new Point2D(0.2, 0.3);
        Point2D p5 = new Point2D(0.4, 0.7);

        tree.insert(p1);
        tree.insert(p2);
        tree.insert(p3);
        tree.insert(p4);
        tree.insert(p5);

        tree.draw();
    }

}
