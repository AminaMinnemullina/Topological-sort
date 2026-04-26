import java.io.*;
import java.util.Random;

public class GraphGenerator {
    public static void generate() throws IOException {
        File dir = new File("data");
        if (!dir.exists()) {
            dir.mkdir();
        }

        Random rand = new Random();

        for (int i = 1; i <= 50; i++) {
            int V = i * 200;
            int E = V * 2;

            File file = new File("data/graph_" + V + ".txt");
            try (PrintWriter pw = new PrintWriter(file)) {
                pw.println(V + " " + E);

                for (int e = 0; e < E; e++) {
                    int u = rand.nextInt(V - 1);

                    // Гарантируем отсутствие циклов (DAG): ребро всегда идет от меньшей вершины к большей
                    int v = u + 1 + rand.nextInt(V - u - 1);
                    pw.println(u + " " + v);
                }
            }
        }
        System.out.println("50 файлов с графами успешно сгенерированы!");
    }
}