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
        int x, y;
        Pair(int y, int x) {
            this.y = y;
            this.x = x;
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

    public void solve() throws IOException {
        int n = in.nextInt(), m = in.nextInt();
        ArrayList<Pair>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<Pair>();
        }
        for (int i = 0; i < m; i++) {
            graph[in.nextInt() - 1].add(new Pair(in.nextInt() - 1, in.nextInt()));
        }

    }


    boolean dfs(int v, ArrayList<Integer>[] graph, int[] matching, boolean[] used) {
        if (used[v])
            return false;

        used[v] = true;
        for (int to : graph[v]) {
            if (matching[to] == -1 || dfs(matching[to], graph, matching, used)) {
                matching[to] = v;
                return true;
            }
        }
        return false;
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
