package PropPack;

import org.graphstream.algorithm.generator.BarabasiAlbertGenerator;
import org.graphstream.algorithm.generator.Generator;
import org.graphstream.algorithm.generator.RandomGenerator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.file.FileSourceEdge;

import java.io.IOException;

public class Graph_Gen {

public static Graph Gen_Graph_Al(int nb, double avragedegree)
{
    Graph graph = new SingleGraph("Graph aléatoire");
    Generator gen = new RandomGenerator(avragedegree);
    gen.addSink(graph);
    gen.begin();
    for(int i=0; i<nb; i++)gen.nextEvents();
    gen.end();
    System.setProperty("org.graphstream.ui", "swing");
    graph.setAttribute("ui.stylesheet", "url('src/main/resources/TpPropagation/Style.css')");

    return graph;
}


    public static Graph Gen_Graph_fichier(String fichier)
    {
        Graph graph = new SingleGraph("Graphe à partir d'un fichier");
        FileSourceEdge fs = new FileSourceEdge();
        fs.addSink(graph);

        try {
            fs.readAll("./src/main/resources/TpPropagation/"+fichier);
        } catch (IOException e) {
            System.out.println("Fichier inexistant ou chemin incorrect");
        } finally {
            fs.removeSink(graph);
        }
        return graph;
    }

    public static Graph Gen_Graph_BarabasiAlbert(int maxlink, int nb)
    {
        Graph grapheAlbert = new SingleGraph("Graph à partir du générateur Barabàsi-Albert");
        Generator gen = new BarabasiAlbertGenerator(maxlink);
        gen.addSink(grapheAlbert);
        gen.begin();
        for(int i=0; i<nb; i++) {
            gen.nextEvents();
        }
        gen.end();
        return  grapheAlbert;
    }
}