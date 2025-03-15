/*Chỉ sử dụng bán kính r, không được sử dụng bất kỳ hằng số nào khác, 
hãy xấp xỉ diện tính hình tròn tâm O(0,0) bán kính r*/
package LAB1;

import java.util.Scanner;
import java.util.Random;

public class Bai1 
{
    public static void main(String[] args) 
    {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhap ban kinh r: ");
        double r = scanner.nextDouble();
        
        int Total = 1000000; 
        int InsideCircle = 0;
        Random random = new Random();

        for (int i = 0; i < Total; i++) 
        {
            double x = (random.nextDouble() * 2 * r) - r;
            double y = (random.nextDouble() * 2 * r) - r;
            if (x * x + y * y <= r * r) 
            {
                InsideCircle++;
            }
        }

        double Area = 4.0 * r * r * InsideCircle / Total;
        System.out.println("Dien tich hinh tron: " + Area);
        scanner.close();
    }
}


