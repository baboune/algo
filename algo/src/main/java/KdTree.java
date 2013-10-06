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
    private Point2D minPoint;
    private double minDist;

    private static class Node {
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
                } else if (value.x() < p.x()) {
                    xmin = this.value.x();
                }
            } else {
                // HORIZONTAL
                // Is it above or less?
                if (value.y() > p.y()) {
                    ymax = this.value.y();
                } else if (value.y() < p.y()) {
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

        draw(root);
    }

    // all points in the set that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        // Range search. To find all points contained in a given query rectangle, start at the root and
        // recursively search for points in both subtrees using the following pruning rule:
        // if the query rectangle does not intersect the rectangle corresponding to a node, there is no need to
        // explore that node (or its subtrees). A subtree is searched only if it might contain a point contained in
        // the query rectangle.
        Stack<Point2D> stack = new Stack<Point2D>();
        range(root, rect, stack);
        return stack;
    }

    // a nearest neighbor in the set to p; null if set is empty
    public Point2D nearest(Point2D p) {
        // Nearest neighbor search. To find a closest point to a given query point, start at the root and
        // recursively search in both subtrees using the following pruning rule: if the closest point discovered
        // so far is closer than the distance between the query point and the rectangle corresponding to a node,
        // there is no need to explore that node (or its subtrees). That is, a node is searched only if it might
        // contain a point that is closer than the best one found so far. The effectiveness of the pruning rule
        // depends on quickly finding a nearby point. To do this, organize your recursive method so that when
        // there are two possible subtrees to go down, you always choose the subtree that is on the same side of
        // the splitting line as the query point as the first subtree to explore—the closest point found while
        // exploring the first subtree may enable pruning of the second subtree.
        minPoint = root.value;
        minDist = minPoint.distanceSquaredTo(p);

        searchNearest(root, p);
        return minPoint;
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

    private void draw(Node node) {
        if (node == null) {
            return;
        }

        StdDraw.setPenColor(StdDraw.BLACK);
        node.value.draw();

        double x = node.value.x();
        double y = node.value.y();
        //StdDraw.point(x, y);
        if (node.type == HORIZONTAL) {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(node.rect.xmin(), y, node.rect.xmax(), y);
        } else {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(x, node.rect.ymin(), x, node.rect.ymax());
        }
        draw(node.left);
        draw(node.right);
    }

    private int opposite(int type) {
        if (type == VERTICAL) {
            return HORIZONTAL;
        }
        return VERTICAL;
    }

    private void range(Node node, RectHV rect, Stack<Point2D> stack) {
        if (node == null) {
            return;
        }

        if (node.compareTo(rect) > 0) {
            range(node.left, rect, stack);
        } else if (node.compareTo(rect) < 0) {
            range(node.right, rect, stack);
        } else {
            // both
            if (rect.contains(node.value)) {
                stack.push(node.value);
            }
            range(node.left, rect, stack);
            range(node.right, rect, stack);
        }
    }

    private void searchNearest(Node node, Point2D p) {
        double dist = node.value.distanceSquaredTo(p);

        if (minDist > dist) {
            minPoint = node.value;
            minDist = dist;
        }

        if (node.left != null && node.right != null) {
            double left, right;

            left = node.left.rect.distanceSquaredTo(p);
            right = node.right.rect.distanceSquaredTo(p);

            if (left < right) {
                searchNearest(node.left, p);

                if (right < minDist) {
                    searchNearest(node.right, p);
                }

            } else {
                searchNearest(node.right, p);

                if (left < minDist) {
                    searchNearest(node.left, p);
                }
            }

            return;
        }

        if (node.left != null) {
            if (node.left.rect.distanceSquaredTo(p) < minDist) {
                searchNearest(node.left, p);
            }
        }

        if (node.right != null) {
            if (node.right.rect.distanceSquaredTo(p) < minDist) {
                searchNearest(node.right, p);
            }
        }

        return;
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
