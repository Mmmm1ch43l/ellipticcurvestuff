import java.math.BigInteger;

public class Main {
    public static void main(String[] args) {
        point a = new point(-4,28);
        int k = 9;
        a = a.multiply(k);
        System.out.println(k+"*(-4,28) is at "+ a);
        if (a.admissible()) System.out.println("Found a point at "+a+"!!!!!");
        BigInteger[] solution = a.correspondingSolution();
        System.out.println("The corresponding solution is at a="+solution[0]+", b="+solution[1]+" and c="+solution[2]);
    }
}

