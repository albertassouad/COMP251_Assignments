import java.util.*;
import java.io.*;

public class Multiply{

    private static int randomInt(int size) {
        Random rand = new Random();
        int maxval = (1 << size) - 1;
        return rand.nextInt(maxval + 1);
    }
    
    public static int[] naive(int size, int x, int y) {

        // YOUR CODE GOES HERE  (Note: Change return statement)
    	int[] result = new int[2];
    	int product;
    	int cost = 0;
    	if ( size == 1 ) {
    		product = x*y;
    		cost += 1;
    	}
    	else {
    		int m,a,b,c,d,e,f,g,h ;
    		m = (int) Math.ceil((double)size / 2 ) ;
    		int pow2m = (int) Math.pow(2, m);
    		a =   x / pow2m;
    		b = x % pow2m;
    		c = y / pow2m;
    		d = y % pow2m;
    		
    		int[] storeE = naive(m,a,c);
    		e = storeE[0];
    		cost+= storeE[1];
    		
    		int[] storeF = naive(m,b,d);
    		f = storeF[0];
    		cost+= storeF[1];
    		
    		
    		int[] storeG = naive(m,b,c);
    		g = storeG[0];
    		cost += storeG[1];
    		
    		int[] storeH = naive(m,a,d);
    		h = storeH[0];
    		cost += storeG[1];
    		
    		product = (int) Math.pow(2, 2*m) * e + pow2m * (g+h) + f;
    		cost += 3*m;
    	}
        result[0] = product;
        result[1] = cost;
        return result;
        
    }

    public static int[] karatsuba(int size, int x, int y) {
        
        // YOUR CODE GOES HERE  (Note: Change return statement)
    	int[] result = new int[2];
    	int product;
    	int cost = 0;
    	if ( size == 1 ) {
    		product = x*y;
    		cost += 1;
    	}
    	else {
    		int m,a,b,c,d,e,f,g ;
    		m = (int) Math.ceil((double)size / 2 );
    		
    		int pow2m = (int) Math.pow(2, m);
    		
    		a =   x / pow2m;
    		b = x % pow2m;
    		c = y / pow2m;
    		d = y % pow2m;
    		
    		int[] storeE = karatsuba(m,a,c);
    		e = storeE[0];
    		cost += storeE[1];
    		
    		int[] storeF = karatsuba(m,b,d);
    		f = storeF[0];
    		cost+= storeF[1];
    		
    		int[] storeG = karatsuba(m,a-b,c-d);
    		g = storeG[0];
    		cost += storeG[1];
    		
    		product = (int) Math.pow(2, 2*m) * e + pow2m * (e+f-g) + f;
    		
    		cost += 6*m;
    	}
        result[0] = product;
        result[1] = cost;
        return result;
        
    }
    
    public static void main(String[] args){

        try {
            
            int maxRound = 20;
            int maxIntBitSize = 16;
            for (int size=1; size<=maxIntBitSize; size++) {
                int sumOpNaive = 0;
                int sumOpKaratsuba = 0;
                for (int round=0; round<maxRound; round++) {
                    int x = randomInt(size);
                    int y = randomInt(size);
                    int[] resNaive = naive(size,x,y);
                    int[] resKaratsuba = karatsuba(size,x,y);
            
                    if (resNaive[0] != resKaratsuba[0]) {
                        throw new Exception("Return values do not match! (x=" + x + "; y=" + y + "; Naive=" + resNaive[0] + "; Karatsuba=" + resKaratsuba[0] + ")");
                    }
                    
                    if (resNaive[0] != (x*y)) {
                        int myproduct = x*y;
                        throw new Exception("Evaluation is wrong! (x=" + x + "; y=" + y + "; Your result=" + resNaive[0] + "; True value=" + myproduct + ")");
                    }
                    
                    sumOpNaive += resNaive[1];
                    sumOpKaratsuba += resKaratsuba[1];
                }
                int avgOpNaive = sumOpNaive / maxRound;
                int avgOpKaratsuba = sumOpKaratsuba / maxRound;
                System.out.println(size + "," + avgOpNaive + "," + avgOpKaratsuba);
            
            }
        }
        catch (Exception e){
            System.out.println(e);
        }

   } 
}
