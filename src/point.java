import java.math.BigInteger;

public class point {
    public static final point INFINITY = new point();
    public BigRatio x,y;

    public point (int x, int y) {
        this.x = new BigRatio(x);
        this.y = new BigRatio(y);
        if(!onCurve()) System.out.println(this +" doesn't lie on the curve!!!");
    }

    public point (BigRatio x, BigRatio y) {
        this.x = x;
        this.y = y;
        if(!onCurve()) System.out.println(this+" doesn't lie on the curve!!!");
    }

    public point () {
        x = new BigRatio(0);
        y = new BigRatio(0);
    }

    public boolean onCurve (){
        if (this==INFINITY) return true;
        return x.pow(2).add(x.multiply(new BigRatio(109))).add(new BigRatio(224)).multiply(x).equals(y.pow(2));
    }

    public String toString (){
        if(this==INFINITY){
            return "infinity";
        } else {
            return "approximately x="+x.doubleValue()+", y="+y.doubleValue()+" and exactly x="+x+", y="+y;
        }
    }

    public point negate (){
        if (this==INFINITY){
            return INFINITY;
        } else {
            return new point(x,y.negate());
        }
    }

    public point multiply (int k){
        if (this==INFINITY||k==0) return point.INFINITY;
        if (k<0) return this.multiply(-k).negate();
        if (k%2==0) return this.add(this).multiply(k/2);
        return this.multiply(k-1).add(this);
    }

    public point add (point b){
        if (this==INFINITY){
            if (b==INFINITY){
                return INFINITY;
            }
            return new point(b.x,b.y);
        }
        if (b==INFINITY){
            return new point(x,y);
        }
        BigRatio l,m,x3,y3;
        if (x.equals(b.x)){
            if (y.add(b.y).equals(new BigRatio(0))){
                return point.INFINITY;
            } else {
                l = x.pow(2).multiply(new BigRatio(3)).add(x.multiply(new BigRatio(218))).add(new BigRatio(224)).divide(y.multiply(new BigRatio(2)));
                m = x.multiply(new BigRatio(224)).subtract(x.pow(3)).divide(y.multiply(new BigRatio(2)));
            }
        } else {
            l = b.y.subtract(y).divide(b.x.subtract(x));
            m = y.multiply(b.x).subtract(b.y.multiply(x)).divide(b.x.subtract(x));
        }
        x3 = l.pow(2).subtract(x).subtract(b.x).subtract(new BigRatio(109));
        y3 = x3.multiply(l).add(m).negate();
        return new point(x3,y3);
    }

    public boolean admissible (){
        BigRatio X = x.subtract(y).subtract(new BigRatio(56));
        BigRatio Y = x.add(y).subtract(new BigRatio(56));
        BigRatio Z = x.multiply(new BigRatio(12)).add(new BigRatio(56));
        return X.multiply(Y).signum()>0&&X.multiply(Z).signum()>0;
    }

    public BigInteger[] correspondingSolution (){
        BigInteger[] output = new BigInteger[3];
        if(this==INFINITY) {
            output[0] = BigInteger.ZERO;
            output[1] = BigInteger.ONE;
            output[2] = BigInteger.ONE.negate();
            return output;
        }
        BigInteger xq = x.getNumerator();
        BigInteger xp = x.getDenominator();
        BigInteger yq = y.getNumerator();
        BigInteger yp = y.getDenominator();
        BigInteger temp = xp.multiply(yp).multiply(BigInteger.valueOf(56));
        output[0] = xq.multiply(yp).subtract(yq.multiply(xp)).subtract(temp);
        output[1] = xq.multiply(yp).add(yq.multiply(xp)).subtract(temp);
        output[2] = xq.multiply(yp).multiply(BigInteger.valueOf(12)).add(temp);
        BigInteger div = output[0].gcd(output[1]).gcd(output[2]);
        if (div.compareTo(BigInteger.ONE)>0){
            output[0] = output[0].divide(div);
            output[1] = output[1].divide(div);
            output[2] = output[2].divide(div);
        }
        if(output[0].signum()+output[1].signum()+output[2].signum()<0){
            output[0] = output[0].negate();
            output[1] = output[1].negate();
            output[2] = output[2].negate();
        }
        if(output[0].abs().compareTo(output[1].abs())>0){
            temp = output[0];
            output[0] = output[1];
            output[1] = temp;
        }
        if(output[1].abs().compareTo(output[2].abs())>0){
            temp = output[1];
            output[1] = output[2];
            output[2] = temp;
        }
        if(output[0].abs().compareTo(output[1].abs())>0){
            temp = output[0];
            output[0] = output[1];
            output[1] = temp;
        }
        return output;
    }
}