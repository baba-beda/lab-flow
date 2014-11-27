import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by daria on 12.11.14.
 */
public class maxflow {
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


    class Pair {
        int to, cap;

        public Pair(int to, int cap) {
            this.to = to;
            this.cap = cap;
        }
    }

    class Triplet {
        int to, cap, flow;

        public Triplet(int to, int cap, int flow) {
            this.to = to;
            this.cap = cap;
            this.flow = flow;
        }
    }

    class Edge implements Comparable<Edge> {
        int u, v;
        int weight;

        Edge(int u, int v, int weight) {
            this.u = u;
            this.v = v;
            this.weight = weight;
        }

        public int compareTo(Edge a) {
            return Integer.compare(weight, a.weight);
        }
    }

    FastScanner in;
    PrintWriter out;


    int n;
    int[][] capacity, flow;
    int s;
    int t;
    int[] d;
    int[] ptr;
    int[] q;
    boolean[] visited;


    final int INF = 1000000010;

    public void solve() throws IOException {
        n = in.nextInt();
        int m = in.nextInt();
        capacity = new int[n][n];
        flow = new int[n][n];
        s = 0;
        t = n - 1;

        d = new int[n];
        ptr = new int[n];
        q = new int[n];
        visited = new boolean[n];

        for (int i = 0; i < m; i++) {
            int v = in.nextInt() - 1;
            int to = in.nextInt() - 1;
            int cap = in.nextInt();
            capacity[v][to] = cap;
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
        out.println(ans);

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

        visited[v] = true;
        for (int to = ptr[v]; to < n; to++, ptr[v]++) {
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





    public void run() {
        try {
            File defaultInput = new File("input.txt");
            if (defaultInput.exists()) {
                in = new FastScanner(new File("input.txt"));
                out = new PrintWriter(new File("output.txt"));
            } else {
                in = new FastScanner(new File("maxflow" + ".in"));
                out = new PrintWriter(new File("maxflow" + ".out"));
            }
            solve();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] arg) {
        new maxflow().run();
    }
}
