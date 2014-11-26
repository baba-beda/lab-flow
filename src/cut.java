/**
 * Created by daria on 26.11.14.
 */
import java.io.*;
import java.util.*;

public class cut {
    class FastScanner {
        StreamTokenizer st;

        FastScanner() {
            st = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
        }

        FastScanner(File f) {
            try {
                st = new StreamTokenizer(new BufferedReader(new FileReader(f)));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        int nextInt() throws IOException {
            st.nextToken();
            return (int) st.nval;
        }

        String nextString() throws IOException {
            st.nextToken();
            return st.sval;
        }
    }




    class Edge {
        int v, to, cap, fl;

        public Edge(int v, int to, int cap, int fl) {
            this.v = v;
            this.to = to;
            this.cap = cap;
            this.fl = fl;
        }
    }

    FastScanner in;
    PrintWriter out;



    int n;
    int[][] capacity, flow;
    int s;
    int t;
    int[] d;
    int[] q;
    boolean[] visited;
    int[] ptr;

    final int INF = 1000000010;

    public void solve() throws IOException {
        n = in.nextInt();
        int m = in.nextInt();
        capacity = new int[n][n];
        flow = new int[n][n];
        s = 0;
        t = n - 1;

        d = new int[n];
        q = new int[n];
        ptr = new int[n];
        visited = new boolean[n];

        for (int i = 0; i < m; i++) {
            int v = in.nextInt() - 1;
            int to = in.nextInt() - 1;
            int cap = in.nextInt();
            capacity[v][to] = cap;
            capacity[to][v] = cap;
        }

        int ans = 0;
        for(;;) {

            if (!bfs())
                break;
            Arrays.fill(ptr, 0);
            int pushed;
            while ((pushed = dfs(s, INF)) != 0) {
                ans += pushed;
            }
        }


        ArrayList<Integer>[] rGraph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            rGraph[i] = new ArrayList<Integer>();
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (capacity[i][j] - flow[i][j] > 0)
                    rGraph[i].add(j);
            }
        }

        dfs(s, rGraph);

        ArrayList<Integer> answer = new ArrayList<Integer>();
        for (int i = 0; i < n; i++) {
            if (visited[i])
                answer.add(i + 1);
        }

        out.println(answer.size());
        for (int i : answer) {
            out.print(i + " ");
        }



    }

    boolean bfs() {
        int qh = 0, qt = 0;
        q[qt++] = s;
        Arrays.fill(d, -1);
        d[s] = 0;
        while (qh < qt) {
            int v = q[qh++];
            for (int to = 0; to < n; to++) {
                if (d[to] == -1 && flow[v][to] < capacity[v][to]) {
                    q[qt++] = to;
                    d[to] = d[v] + 1;
                }
            }
        }
        return (d[t] != -1);
    }

    int dfs (int v, int f) {
        if (f == 0)
            return 0;
        if (v == t)
            return f;

        for (int to = ptr[v]; to < n; ptr[v]++, to++) {
            if (d[to] != d[v] + 1)
                continue;
            int pushed = dfs(to, Math.min(f, capacity[v][to] - flow[v][to]));
            if (pushed != 0) {
                flow[v][to] += pushed;
                flow[to][v] -= pushed;
                return pushed;
            }
        }
        return 0;
    }

    void dfs(int v, ArrayList<Integer>[] rGraph) {
        visited[v] = true;
        for (int to : rGraph[v]) {
            if (!visited[to]) {
                dfs(to, rGraph);
            }
        }
    }




    public void run() {
        try {
            File defaultInput = new File("input.txt");
            if (defaultInput.exists()) {
                in = new FastScanner(new File("input.txt"));
                out = new PrintWriter(new File("output.txt"));
            } else {
                in = new FastScanner(new File("cut" + ".in"));
                out = new PrintWriter(new File("cut" + ".out"));
            }
            solve();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] arg) {
        new cut().run();
    }
}
