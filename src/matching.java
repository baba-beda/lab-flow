import java.io.*;
import java.util.*;

/**
 * Created by daria on 12.11.14.
 */

public class matching {
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
        int n = in.nextInt(), m = in.nextInt(), e = in.nextInt();
        ArrayList<Integer>[] v1 = new ArrayList[n];
        ArrayList<Integer>[] v2 = new ArrayList[m];
        for (int i = 0; i < n; i++) {
            v1[i] = new ArrayList<Integer>();
        }
        for (int i = 0; i < m; i++) {
            v2[i] = new ArrayList<Integer>();
        }

        for (int i = 0; i < e; i++) {
            int a = in.nextInt() - 1, b = in.nextInt() - 1;
            v1[a].add(b);
            v2[b].add(a);
        }

        int[] matching = new int[e];
        Arrays.fill(matching, -1);
        boolean[] used = new boolean[n];
        boolean aux;
        for (int i = 0; i < n; i++) {
            Arrays.fill(used, false);
            aux = dfs(i, v1, matching, used);
        }

        int ans = 0;
        for (int i = 0; i < e; i++) {
            if (matching[i] != -1) {
                ans++;
            }
        }

        out.println(ans);
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
                in = new FastScanner(new File("matching" + ".in"));
                out = new PrintWriter(new File("matching" + ".out"));
            }
            solve();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] arg) {
        new matching().run();
    }
}