package chapterone;

public class ChapterOne {
    /**
     * 欧几里得算法，求最大公约数
     * gcd(greatest common divisor)
     * 递归实现方式
     * @param p
     * @param q
     * @return
     */
    public static int gcd(int p, int q){
        if( q == 0)   return p;
        return gcd(q, p % q);
    }

    public static int rank(int key, int[] a){
        return rank(key, a, 0, a.length - 1);
    }
    public static int rank(int key, int[] a, int lo, int hi){
        if(lo > hi) return -1;

        int mid = (lo + hi) / 2;
        if(key > a[mid]) return rank(key, a, mid + 1, hi);
        if(key < a[mid]) return rank(key, a, lo, hi -1);
        return mid;
    }

    public void oneOneOne(){
        System.out.println("( 0 + 15) / 2 = " + ((0 + 15) / 2) );
        System.out.println((0 + 15) / 2);
    }

    public static  void main(String[] args){
        new ChapterOne().oneOneOne();
    }
}