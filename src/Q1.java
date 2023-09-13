//import java.io.*;
//import java.lang.reflect.Array;
//import java.util.ArrayList;
//import java.util.StringTokenizer;
//
//public class Q1 {
//    static final long m = 1000000007L;
//    static int n;
//    public static void main(String[] args) {
//        QReader in = new QReader();
//        QWriter out = new QWriter();
//        n = in.nextInt();
//        int k = in.nextInt();
//        node[] nodes = new node[n+1];
//        long[] result = new long[n+1];
//        for(int x=0;x<=n;x++){
//            nodes[x] = new node(x);
//        }
//        for(int x=1;x<n;x++){
//            int u = in.nextInt();
//            int v = in.nextInt();
//            nodes[u].children.add(nodes[v]);
//            nodes[v].children.add(nodes[u]);
//        }
//        long sum = 0;
//        for(int x=0;x<n;x++){
//            int w = in.nextInt();
//            nodes[x+1].set(w,in.nextInt(),in.nextInt());
//            sum = add(sum,w);
//        }
//        long nSum = get(sum);
//        for(int x=1;x<=n;x++){
//            long[][] pro = new long[n+1][n+1];
//            node root = nodes[x];
//            pro[x][1] = multi(root.w,nSum);
//
//
//
//            pro = find(pro,root,1);
//
//
//
//
//            for(int y=1;y<=n;y++){
//                result[y] = add(result[y],pro[x][y]);
//                nodes[y].number = 1;
//                nodes[y].rank = 0;
//            }
//            //result[k] = add(result[k],pro[x][k]);
//        }
//        out.println(result[k]);
//        out.close();
//    }
//    public static long[][] find(long[][] pro,node current,int rank){
//        current.rank = rank;
//        int idCurrent = current.index;
//        if(rank!=1){
//            pro[idCurrent][0] = current.np;
//            pro[idCurrent][1] = current.p;
//        }
//        for(int x=0;x<current.children.size();x++){
//            node son = current.children.get(x);
//            if(son.rank!=0&&son.rank<current.rank){
//                continue;//保证不会遍历到父节点
//            }
//            if(son.rank==0){//若是子节点没被访问过，深度优先遍历
//                pro = find(pro,son,rank+1);
//            }
//
//            //此时current节点一定不是叶子节点，son节点一定是子节点且已被访问过
//            int idSon = son.index;
//            int numCurrent = current.number;
//            int numSon = son.number;
//            int a = numCurrent;
//            int b = numSon;
//            pro[idCurrent][a+b] = multi(pro[idCurrent][a],pro[idSon][b]);
//            if(a+b>1){
//                int sum = a+b-1;
//                while(sum>0){
//                    a = Math.min(sum,numCurrent);
//                    b = sum - a;
//                    while(a>0&&b<=numSon){
//                        if(b!=0){
//                            pro[idCurrent][sum] = add(pro[idCurrent][sum],multi(pro[idCurrent][a],pro[idSon][b]));
//                        }else {
//                            pro[idCurrent][sum] = multi(pro[idCurrent][a],pro[idSon][b]);
//                        }
//                        a--;
//                        b++;
//                    }
//                    sum--;
//                }
//            }
//            current.number += son.number;
//        }
//        return pro;
//    }
//    public static long add(long a,long b){
//        return (a%m+b%m)%m;
//    }
//    public static long multi(long a,long b){
//        return ((a%m)*(b%m))%m;
//    }
//    public static long get1(long a, long p, long mod) {
//        long t = 1, tt = a % mod;
//        while (p != 0) {
//            if ((p & 1) != 0) t = t * tt % mod;
//            tt = tt * tt % mod;
//            p >>= 1;
//        }
//        return t;
//    }
//
//    public static long get(long a) {
//        return get1(a, m - 2, m);
//    }
//}
//class node{
//    int number = 1;//包括自己在内，目前所有子节点个数
//    int rank;
//    int index;
//    int w;
//    long p;
//    long np;
//    ArrayList<node> children = new ArrayList<>();
//    public void set(int w,int a,int b){
//        this.w = w;
//        p = Q1.multi(a,Q1.get(b));
//        np = Q1.multi(b-a,Q1.get(b));
//    }
//    public node(int index){
//        this.index = index;
//    }
//}
//class QReader {
//    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//    private StringTokenizer tokenizer = new StringTokenizer("");
//
//    private String innerNextLine() {
//        try {
//            return reader.readLine();
//        } catch (IOException e) {
//            return null;
//        }
//    }
//
//    public boolean hasNext() {
//        while (!tokenizer.hasMoreTokens()) {
//            String nextLine = innerNextLine();
//            if (nextLine == null) {
//                return false;
//            }
//            tokenizer = new StringTokenizer(nextLine);
//        }
//        return true;
//    }
//
//    public String nextLine() {
//        tokenizer = new StringTokenizer("");
//        return innerNextLine();
//    }
//
//    public String next() {
//        hasNext();
//        return tokenizer.nextToken();
//    }
//
//    public int nextInt() {
//        return Integer.parseInt(next());
//    }
//
//    public long nextLong() {
//        return Long.parseLong(next());
//    }
//}
//
//class QWriter implements Closeable {
//    private BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
//
//    public void print(Object object) {
//        try {
//            writer.write(object.toString());
//        } catch (IOException e) {
//            return;
//        }
//    }
//
//    public void println(Object object) {
//        try {
//            writer.write(object.toString());
//            writer.write("\n");
//        } catch (IOException e) {
//            return;
//        }
//    }
//
//    @Override
//    public void close() {
//        try {
//            writer.close();
//        } catch (IOException e) {
//            return;
//        }
//    }
//}
