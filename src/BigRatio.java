import java.math.BigInteger;

public class BigRatio {
    private BigInteger q, p;

    public BigRatio (int n){
        q = BigInteger.valueOf(n);
        p = BigInteger.ONE;
    }
    public BigRatio (BigInteger q, BigInteger p){
        this.q = q;
        this.p = p;
        reduce();
    }

    public BigRatio negate (){
        return new BigRatio(q.negate(),p);
    }

    public BigRatio add (BigRatio b){
        return new BigRatio(q.multiply(b.p).add(b.q.multiply(p)),p.multiply(b.p));
    }

    public BigRatio subtract (BigRatio b){
        return new BigRatio(q.multiply(b.p).subtract(b.q.multiply(p)),p.multiply(b.p));
    }

    public BigRatio multiply (BigRatio b){
        return new BigRatio(q.multiply(b.q),p.multiply(b.p));
    }

    public BigRatio invert (){
        if (p.equals(BigInteger.ZERO)){
            System.out.println("Division by zero!!!");
            return new BigRatio(0);
        }
        return new BigRatio(p,q);
    }

    public BigRatio divide (BigRatio b){
        return this.multiply(b.invert());
    }

    public BigRatio pow (int k){
        if (k==0) return new BigRatio(1);
        if (k<0) return pow(-k).invert();
        return new BigRatio(q.pow(k),p.pow(k));
    }

    public boolean equals (BigRatio b){
        return q.multiply(b.p).equals(b.q.multiply(p));
    }

    public String toString (){
        return q+"/"+p;
    }

    public double doubleValue (){
        return q.doubleValue()/ p.doubleValue();
    }

    public int signum (){
        return q.signum();
    }

    private void reduce (){
        if (p.signum()<0){
            q = q.negate();
            p = p.negate();
        }
        BigInteger div = q.gcd(p);
        if (div.compareTo(BigInteger.ONE)>0){
            q = q.divide(div);
            p = p.divide(div);
        }
    }

    public BigInteger getNumerator (){
        return q;
    }
    public BigInteger getDenominator (){
        return p;
    }
}
