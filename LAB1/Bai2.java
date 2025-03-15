/*Xấp xỉ giá trị của pi thông qua đường tròn đơn vị
tâm O(0,0), bán kính r=1.0*/
package LAB1;

import java.util.Random;
public class Bai2 {
    public static void main(String[] args) 
    {
        int Total = 10000000; 
        int InsideCircle = 0;
        Random random = new Random();

        for (int i = 0; i < Total; i++) 
        {
            double x = (random.nextDouble() * 2) - 1;
            double y = (random.nextDouble() * 2) - 1;
            if (x * x + y * y <= 1) {
                InsideCircle++;
            }
        }

        double pi = 4.0 * InsideCircle / Total;
        System.out.println("Gia tri xap xi cua pi: " + pi);
    }
}