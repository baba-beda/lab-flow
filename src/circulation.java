import java.io.*;
import java.util.Arrays;

/**
 * Created by daria on 27.11.14.
 */
public class circulation {
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
        int v, to;

        public Pair(int v, int to) {
            this.v = v;
            this.to = to;
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
    int[] lowCapacity;
    int s;
    int t;
    int[] d;
    int[] ptr;
    int[] q;


    final int INF = 1000000010;

    public void solve() throws IOException {
        n = in.nextInt();
        int m = in.nextInt();
        capacity = new int[n += 2][n];
        flow = new int[n][n];
        lowCapacity = new int[m];

        Pair[] edges = new Pair[m];

        s = 0;
        t = n - 1;

        d = new int[n];
        ptr = new int[n];
        q = new int[n];

        for (int i = 0; i < m; i++) {
            int v = in.nextInt();
            int to = in.nextInt();
            int lCap = in.nextInt();
            int hCap = in.nextInt();
            capacity[v][to] = hCap - lCap;
            capacity[s][to] += lCap;
            capacity[v][t] += lCap;
            lowCapacity[i] = lCap;
            edges[i] = new Pair(v, to);
        }

        int ans = 0;
        while(bfs()) {
            Arrays.fill(ptr, 0);
            int pushed;
            while ((pushed = dfs(s, INF)) != 0) {
                ans += pushed;

            }
        }

        for (Pair edge : edges) {
            if (flow[0][edge.to] - capacity[0][edge.to] != 0) {
                out.println("NO");
                return;
            }
        }

        out.println("YES");
        for (int i = 0; i < m; i++) {
            out.println(flow[edges[i].v][edges[i].to] + lowCapacity[i]);
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
                in = new FastScanner(new File("circulation" + ".in"));
                out = new PrintWriter(new File("circulation" + ".out"));
            }
            solve();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] arg) {
        new circulation().run();
    }
}
