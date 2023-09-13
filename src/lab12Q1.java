import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class lab12Q1 {

    public static final int big = 2004121712;

    public static int[] pre;
    public static node[] nodes;

    public static void main(String[] args) {
        QReader in = new QReader();
        QWriter out = new QWriter();
        int m = in.nextInt();
        int n = in.nextInt();
        int sum = (m+n+2);
        int C = 0;
        pre = new int[sum];
        nodes = new node[sum];//原图
        for(int x=0;x<sum;x++){
            nodes[x] = new node(x);
        }
        for(int x=1;x<=m;x++){
            int b = in.nextInt();
            C += b;
            nodes[0].children.add(nodes[x]);
            nodes[0].dC.add(b);
            nodes[x].fathers.add(nodes[0]);
            nodes[x].dF.add(0);
            int s = in.nextInt();
            for(int i=0;i<s;i++){
                int S = in.nextInt();
                nodes[x].children.add(nodes[m+S]);
                nodes[x].dC.add(big);
                nodes[m+S].fathers.add(nodes[x]);
                nodes[m+S].dF.add(0);
            }
        }
        for(int x=m+1;x<=sum-2;x++){
            int a = in.nextInt();
            nodes[x].children.add(nodes[sum-1]);
            nodes[x].dC.add(a);
            nodes[sum-1].fathers.add(nodes[x]);
            nodes[sum-1].dF.add(0);
        }
        int c = find(nodes[0],nodes[sum-1]);
        nodes[0].dFS = true;
        dfs(nodes[0]);
        if(c!=C){
            String string = "";
            for(int x=1;x<=m;x++){
                if(nodes[x].dFS){
                    string = string.concat(x+" ");
                }
            }
            out.println(string.substring(0,string.length()-1));
            string = "";
            for(int x=m+1;x<=sum-2;x++){
                if(nodes[x].dFS){
                    string = string.concat(x-m+" ");
                }
            }
            out.println(string.substring(0,string.length()-1));
        }
        out.println(C-c);
        out.close();
    }

    public static void dfs(node current){
        for(int x=0;x<current.children.size();x++){
            node son = current.children.get(x);
            if(current.dC.get(x)>0&&!son.dFS){
                son.dFS = true;
                dfs(son);
            }
        }
        for(int x=0;x<current.fathers.size();x++){
            node father = current.fathers.get(x);
            if(current.dF.get(x)>0&&!father.dFS){
                father.dFS = true;
                dfs(father);
            }
        }
    }
    public static int find(node sN,node tN){
        int maxFlow = 0;
        while (bfs(sN,tN)){
            int position = tN.index;
            node current = tN;
            node before = tN;
            int flow = big;
            while (before!=sN){
                before = nodes[pre[position]];
                if(before.children.contains(current)){
                    int x = before.children.indexOf(current);
                    flow = Math.min(flow,before.dC.get(x));
                }else {
                    int x = before.fathers.indexOf(current);
                    flow = Math.min(flow,before.dF.get(x));
                }
                current = before;
                position = current.index;
            }
            position = tN.index;
            current = tN;
            before = tN;
            while (before!=sN){
                before = nodes[pre[position]];
                if(before.children.contains(current)){
                    int x = before.children.indexOf(current);
                    before.dC.set(x,before.dC.get(x)-flow);
                    int y = current.fathers.indexOf(before);
                    current.dF.set(y,current.dF.get(y)+flow);
                }else {
                    int x = before.fathers.indexOf(current);
                    before.dF.set(x,before.dF.get(x)-flow);
                    int y = current.children.indexOf(before);
                    current.dC.set(y,current.dC.get(y)+flow);
                }
                current = before;
                position = current.index;
            }
            maxFlow += flow;

            for(int x=0;x<nodes.length;x++){
                nodes[x].visit = false;
                pre[x] = 0;
            }
        }
        return maxFlow;
    }

    public static boolean bfs(node sN,node tN){
        Queue<node> queue = new LinkedList<>();
        queue.offer(sN);
        sN.visit = true;
        while (queue.size()>0){
            node father = queue.poll();
            for(int x=0;x<father.children.size();x++){
                node son = father.children.get(x);
                if(!son.visit&&father.dC.get(x)>0){
                    son.visit = true;
                    queue.offer(son);
                    pre[son.index] = father.index;
                    if(son==tN){
                        return true;
                    }
                }
            }
            for(int x=0;x<father.fathers.size();x++){
                node son = father.fathers.get(x);
                if(!son.visit&&father.dF.get(x)>0){
                    son.visit = true;
                    queue.offer(son);
                    pre[son.index] = father.index;
                    if(son==tN){
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
class node{
    int index;
    boolean visit;
    boolean dFS;
    ArrayList<node> children = new ArrayList<>();
    ArrayList<Integer> dC = new ArrayList<>();
    ArrayList<node> fathers = new ArrayList<>();
    ArrayList<Integer> dF = new ArrayList<>();

    public node(int index){
        this.index = index;
    }
}
class QReader {
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private StringTokenizer tokenizer = new StringTokenizer("");

    private String innerNextLine() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            return null;
        }
    }

    public boolean hasNext() {
        while (!tokenizer.hasMoreTokens()) {
            String nextLine = innerNextLine();
            if (nextLine == null) {
                return false;
            }
            tokenizer = new StringTokenizer(nextLine);
        }
        return true;
    }

    public String nextLine() {
        tokenizer = new StringTokenizer("");
        return innerNextLine();
    }

    public String next() {
        hasNext();
        return tokenizer.nextToken();
    }

    public int nextInt() {
        return Integer.parseInt(next());
    }

    public long nextLong() {
        return Long.parseLong(next());
    }
}

class QWriter implements Closeable {
    private BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

    public void print(Object object) {
        try {
            writer.write(object.toString());
        } catch (IOException e) {
            return;
        }
    }

    public void println(Object object) {
        try {
            writer.write(object.toString());
            writer.write("\n");
        } catch (IOException e) {
            return;
        }
    }

    @Override
    public void close() {
        try {
            writer.close();
        } catch (IOException e) {
            return;
        }
    }
}