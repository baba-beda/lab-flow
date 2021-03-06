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

    class Edge {
        int from, to, cap, flow;

        public Edge(int from, int to, int cap) {
            this.from = from;
            this.to = to;
            this.cap = cap;
        }
    }

    FastScanner in;
    PrintWriter out;

    final long INF = 10000000000010L;
    int n, s, t;
    int[] d, ptr, q;
    ArrayList<Edge> edges;
    ArrayList<Integer>[] graph;

    public void solve() throws IOException {
        n = in.nextInt();
        int m = in.nextInt();
        s = 0; t = n - 1;
        d = new int[n];
        ptr = new int[n];
        q = new int[n];
        edges = new ArrayList<Edge>();
        graph = new ArrayList[n];
        for (int i = 0; i<n;i++) {
            graph[i] = new ArrayList<Integer>();
        }
        for (int i = 0; i < m; i++) {
            int from = in.nextInt() - 1, to = in.nextInt() - 1, cap = in.nextInt();
            graph[from].add(edges.size());
            edges.add(new Edge(from, to, cap));
            graph[to].add(edges.size());
            edges.add(new Edge(to, from, 0));
        }

        out.println(dinic());

    }

    long dinic() {
        long flow = 0;
        while (bfs()) {
            Arrays.fill(ptr, 0);
            long pushed;
            while ((pushed = dfs(s, INF)) != 0) {
                flow += pushed;
            }
        }
        return flow;
    }

    boolean bfs() {
        int qh = 0, qt = 0;
        q[qt++] = s;
        Arrays.fill(d, -1);
        d[s] = 0;
        while (qh < qt && d[t] == -1) {
            int v = q[qh++];
            for (int i = 0; i < graph[v].size(); i++) {
                int id = graph[v].get(i), to = edges.get(id).to;
                if (d[to] == -1 && edges.get(id).flow < edges.get(id).cap) {
                    q[qt++] = to;
                    d[to] = d[v] + 1;
                }
            }
        }
        return (d[t] != -1);
    }

    long dfs(int v, long flow) {
        if (flow == 0)
            return 0;
        if (v == t)
            return flow;

        for (; ptr[v] < graph[v].size(); ptr[v]++) {
            int id = graph[v].get(ptr[v]), to = edges.get(id).to;
            if (d[to] != d[v] + 1)
                continue;
            long pushed = dfs(to, Math.min(flow, edges.get(id).cap - edges.get(id).flow));
            if (pushed != 0) {
                edges.get(id).flow += pushed;
                edges.get(id ^ 1).flow -= pushed;
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
