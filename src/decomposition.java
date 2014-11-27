import java.io.*;
import java.util.*;

/**
 * Created by daria on 27.11.14.
 */
public class decomposition {
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

        dinic();

        fullDecomposition();
    }

    ArrayList<Integer> simpleDecomposition(int s) {
        ArrayList<Integer> ed = new ArrayList<Integer>();
        ArrayList<Integer> vertices = new ArrayList<Integer>();
        int[] ind = new int[n];
        int count = 0;
        int v = s;
        int e = -1;
        while (!vertices.contains(v)) {

            for (int id : graph[v]) {
                if (id % 2 == 0) {
                    if (edges.get(id).flow > 0) {
                        e = id;
                    }
                }
            }

            if (e == -1) {
                if (v == t) {
                    break;
                }
                else {
                    return null;
                }
            }
            ed.add(e);
            vertices.add(v);
            ind[v] = count++;
            v = edges.get(e).to;
        }

        if (vertices.contains(v)) {
            for (int i = ed.size() - 1; i >= 0; i--)  {
                if (i >= ind[v]) {
                    ed.remove(i);
                }
                else {
                    break;
                }
            }
        }
        int min = Integer.MAX_VALUE;

        for (int i = 0; i < ed.size(); i++) {
            min = Math.min(min, edges.get(ed.get(i)).flow);
        }

        for (int i = 0; i < ed.size(); i++) {
            edges.get(ed.get(i)).flow -= min;
        }

        Collections.reverse(ed);
        ed.add(min);
        return ed;
    }

    void fullDecomposition() {
        ArrayList<ArrayList<Integer>> d = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> p;
        while ((p = simpleDecomposition(s)) != null) {
            d.add(p);
        }
        for (int i = 0; i < n - 1; i++) {
            while ((p = simpleDecomposition(i)) != null) {
                d.add(p);
            }
        }

        out.println(d.size());
        for (ArrayList<Integer> el : d) {
            out.print(el.get(el.size() - 1) + " ");
            out.print(el.size() - 1 + " ");
            for (int i = el.size() - 2; i >= 0; i--) {
                out.print((el.get(i) / 2 + 1) + " ");
            }
            out.println();
        }
    }


    void dinic() {
        long flow = 0;
        while (bfs()) {
            Arrays.fill(ptr, 0);
            long pushed;
            while ((pushed = dfs(s, INF)) != 0) {
                flow += pushed;
            }
        }
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
                in = new FastScanner(new File("decomposition" + ".in"));
                out = new PrintWriter(new File("decomposition" + ".out"));
            }
            solve();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] arg) {
        new decomposition().run();
    }

}
