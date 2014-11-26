import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by daria on 21.11.14.
 */
public class path {
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
        int ans = 0;
        ArrayList<Integer>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<Integer>();
        }
        for (int i = 0; i < m; i++) {
            int a = in.nextInt() - 1, b = in.nextInt() - 1;
            if (a != b) {
                graph[a].add(b);
            }
        }
        int[] matching = new int[n * n];
        Arrays.fill(matching, -1);
        boolean[] used = new boolean[n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(used, false);
            if (dfs(i, graph, matching, used)) {
                ans++;
            }
        }
        out.println(n - ans);

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
                in = new FastScanner(new File("paths" + ".in"));
                out = new PrintWriter(new File("paths" + ".out"));
            }
            solve();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] arg) {
        new path().run();
    }
}
