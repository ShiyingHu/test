import java.util.Random;

public class test {
    public static void main(String[] args) {
        int m = 25;
        int n = 25;
        System.out.println(m+" "+n);
        Random x = new Random();
        for(int t=0;t<m;t++){
            int y = x.nextInt(1,n);
            System.out.print(x.nextInt(1,25)
                    +" "+y+" ");
            if(t%2==0){
                for(int a=1;a<=y;a++){
                    System.out.print(a+" ");
                }
            }else {
                for(int a=n-y+1;a<=n;a++){
                    System.out.print(a+" ");
                }
            }
            System.out.println();
        }
        for(int t=0;t<n;t++){
            System.out.print(x.nextInt(1,25)+" ");
        }
    }
}
