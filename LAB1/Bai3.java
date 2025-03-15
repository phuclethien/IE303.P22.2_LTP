package LAB1;

import java.util.*;

class Point {
    int x, y;
    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    @Override
    public String toString() {
        return  x + " " + y;
    }
}

public class Bai3 {
    static Point p0;
    
    static Point nextToTop(Stack<Point> S) {
        Point p = S.pop();
        Point res = S.peek();
        S.push(p);
        return res;
    }
    
    static void swap(Point[] points, int i, int j) {
        Point temp = points[i];
        points[i] = points[j];
        points[j] = temp;
    }
    
    static int distSq(Point p1, Point p2) {
        return (p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y);
    }
    
    static int orientation(Point p, Point q, Point r) {
        int val = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y);
        if (val == 0)
            return 0;
        return (val > 0) ? 1 : 2;
    }
    
    static int compare(Point p1, Point p2) {
        int o = orientation(p0, p1, p2);
        if (o == 0) {
            return (distSq(p0, p2) >= distSq(p0, p1)) ? -1 : 1;
        }
        return (o == 2) ? -1 : 1;
    }
    
    static void convexHull(Point[] points, int n) {
        if (n < 3) return;
        
        int min = 0;
        for (int i = 1; i < n; i++) {
            if (points[i].y < points[min].y || 
                (points[i].y == points[min].y && points[i].x < points[min].x)) {
                min = i;
            }
        }
        swap(points, 0, min);
        p0 = points[0];
        Arrays.sort(points, 1, n, Bai3::compare);
        
        int m = 1;
        for (int i = 1; i < n; i++) {
            while (i < n - 1 && orientation(p0, points[i], points[i + 1]) == 0)
                i++;
            points[m++] = points[i];
        }
        
        Stack<Point> S = new Stack<>();
        S.push(points[0]);
        S.push(points[1]);
        S.push(points[2]);
        
        for (int i = 3; i < m; i++) {
            while (orientation(nextToTop(S), S.peek(), points[i]) != 2)
                S.pop();
            S.push(points[i]);
        }
        Collections.reverse((List<?>) S);
        for (Point p : S)
            System.out.println(p);
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        Point[] points = new Point[n];
        
        for (int i = 0; i < n; i++) {
            int x = sc.nextInt();
            int y = sc.nextInt();
            points[i] = new Point(x, y);
        }
        
        convexHull(points, n);
        sc.close();
    }
}
