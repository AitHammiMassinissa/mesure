package tpnotemesure;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.graphstream.graph.BreadthFirstIterator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.file.FileSource;
import org.graphstream.stream.file.FileSourceEdge;
import org.graphstream.graph.Node;
import org.graphstream.algorithm.Toolkit;
import org.graphstream.algorithm.generator.BarabasiAlbertGenerator;
import org.graphstream.algorithm.generator.Generator;
import org.graphstream.algorithm.generator.RandomGenerator;

public class Mesure {
    public static void main(String... args) {
    	
        Graph graphe = new DefaultGraph("graphe");
        FileSource donner = new FileSourceEdge();

        donner.addSink(graphe);
        try {
        	donner.readAll(Mesure.class.getClassLoader().getResourceAsStream("com-dblp.ungraph.txt"));
        } catch( IOException e) {
            e.printStackTrace();
        } finally {
        	donner.removeSink(graphe);
        }   
        System.out.println("\n ********************************************Question 2 mesure de départ ************************************************* \n");
        
        //Nombre de nœuds 
        System.out.println("\n Nombre de nœuds: \n  " + graphe.getNodeCount());
        //Nombre d'arêtes
        System.out.println("\n Nombre d'arêtes:\n " + graphe.getEdgeCount());
        //Degré moyen
        System.out.println("\n Degré moyen: \n " + Toolkit.averageDegree(graphe));
        //coefficient de clustering Moyen
        System.out.println("\n coefficient de clustering Moyen : \n " + Toolkit.averageClusteringCoefficient(graphe));	
        System.out.println("Moyenne de Coefficient de clustering dans un reseau aleatoire : " + Toolkit.averageDegree(graphe) / graphe.getNodeCount());
       
        
        System.out.println("\n ********************************************Question 3 *************************************************\n");
        
        System.out.println("\n Le réseau est-il connexe  : \n " + Toolkit.isConnected(graphe));
        System.out.println("\n Un réseau aléatoire de la même taille et degré moyen sera-t-il connexe : \n " + (Toolkit.averageDegree(graphe) > Math.log(graphe.getNodeCount())));
        System.out.println("\n À partir de quel degré moyen un réseau aléatoire avec cette taille devient connexe :\n " + Math.log(graphe.getNodeCount()));
    	
        
        
        System.out.println("\n ********************************************Question 4*************************************************\n");
        System.out.println("\n Données \n");
        printDegreeDistribution(graphe);
        ecrituredonnees(graphe);
       
       
        System.out.println("\n Est-ce qu'on observe une ligne droite en log-log ? : \n Oui ") ;
        System.out.println("\n Que cela nous indique ? : \n Indique que notre distribution des degrés est fonction de loi de puissance ");
        
        
        System.out.println("\n ********************************************Question 5*************************************************\n");
        List<Node> random = null;
        calculemoyen(random,graphe,1000);
        System.out.println("\n ********************************************Question 6*************************************************\n");
        
        System.out.println("\n Creation Reseau random \n ");
        int NbNodes = 317080;
        int degremoyen = 6;
        Graph g = new SingleGraph("random");
        ReseauRandom(g,NbNodes, degremoyen);
        System.out.println("\n Nombre de nodes reseau aleatoire :  \n   "+g.getNodeCount());
        System.out.println("\n Nombre de edges reseau aleatoire  : \n     "+g.getEdgeCount());
        System.out.println("\n Moyenne des degree reseau aleatoire : \n "+Toolkit.averageDegree(g));
        System.out.println("\n Moyenne de coefficient de clustering reseau aleatoire \n   : "+Toolkit.averageClusteringCoefficient(g));
        System.out.println("\n le reseau aleatoire est connexe ?  :\n  " +  Toolkit.isConnected(g) );
        System.out.println("\n Distance moyenne reseau aleatoire ? : \n" +Math.log(g.getNodeCount())/Math.log(Toolkit.averageDegree(g))) ;
        
        System.out.println("\n Creation Reseau BarabasiAlbert\n ");
        Graph g2 = new SingleGraph("Barabàsi-Albert");
        barabasiAlbert(g2, NbNodes, degremoyen);
        System.out.println("\n Nombre de nodes reseau Barabasi Albert :  \n   "+g2.getNodeCount());
        System.out.println("\n Nombre de edges reseau Barabasi Albert  :  \n   "+g2.getEdgeCount());
        System.out.println("\n Moyenne des degree reseau Barabasi Albert : \n"+Toolkit.averageDegree(g2));
        System.out.println("\n Moyenne de coefficient de clustering reseau Barabasi Albert   :\n "+Toolkit.averageClusteringCoefficient(g2));
        System.out.println("\n le reseau Barabarasi Albert est connexe ? : " +  Toolkit.isConnected(g2) );
        System.out.println("\n Distance moyenne reseau Barabarasi ? :\n " +Math.log(g2.getNodeCount())/Math.log(Math.log(g2.getNodeCount())));
        
        /***********************************À part ***********************************/
        Cree_Image_Avec_Gnuplot("script_distribution_degree_linéaire");
        Cree_Image_Avec_Gnuplot("script_distribution_degree_loglog");
        Cree_Image_Avec_Gnuplot("script_distribution_distance");
        Cree_Image_Avec_Gnuplot("script_loi_poisson");
        Cree_Image_Avec_Gnuplot("script_loi_puissance_compa");
    }
    
    
    public static void Cree_Image_Avec_Gnuplot(String nomFichier)
	{
		try {
			Runtime commandPrompt = Runtime.getRuntime();
			commandPrompt.exec("gnuplot ./src/main/resources/TPMesure/"+nomFichier+".gnuplot");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

    
    private static void calculemoyen(List<Node> random,Graph graphe , int nb) {
    	random = Toolkit.randomNodeSet(graphe,nb);
        double distance = 0 ;
        double nombre = 0;


        Map<Integer, Integer> map = new HashMap<>();
        System.out.println("\n veuillez attendre s'il vous plait quelques minutes pour le calcule moyen: \n " );
        for (int i = 0; i < 1000; i++){
            BreadthFirstIterator breadFrstIterateur = new BreadthFirstIterator(random.get(i));
            while (breadFrstIterateur.hasNext()){
            	int el = breadFrstIterateur.getDepthOf(breadFrstIterateur.next());
                distance +=  el;
                nombre++;
                if(map.containsKey(el)){
                	    int valeur = map.get(el);
                        map.put(el,valeur+1);
                }else{
                    map.put(el,1);}
            	}
         }
        try{
            File fichier2 = new File("./src/main/resources/TPMesure/DistributionDistance.dat");
            System.out.println("\n Ecriture des données sur :\n  "+fichier2);
            PrintWriter printWriter = new PrintWriter(fichier2, "UTF-8");
            for (Integer mapKey : map.keySet()){
                printWriter.write(String.format(Locale.US, "%6d%20.8f%n",mapKey,(double)(map.get(mapKey))/nombre));
             }
            printWriter.close();
        }catch(IOException e) {
            e.printStackTrace();
        }
                System.out.println("\n L'hypothèse des six degrés de séparation se confirme-t-elle ? :\n  Non");
                System.out.println("\n C'est un réseau petit monde ?:\n  Non");
                double distance_moyenne = distance/nombre;
                System.out.println("\n La distance moyenne :\n "+distance_moyenne);
                System.out.println("\n distance moyenne dans un reseau aleatoire :\n "+Math.log(graphe.getNodeCount())/Math.log(Toolkit.averageDegree(graphe)));
   
    }
    
    
    private static void ecrituredonnees(Graph graphe) {
    	File fichier = new File("./src/main/resources/TPMesure/DistributionDegree.dat");
    	  try{
              System.out.println("\n Ecriture des données sur : \n "+fichier);
              PrintWriter printWriter = new PrintWriter(fichier, "UTF-8");
              int[] degredistribution = Toolkit.degreeDistribution(graphe);
              for (int k = 0; k < degredistribution.length; k++) {
                  if (degredistribution[k] != 0) {
                  	printWriter.write(String.format(Locale.US, "%6d%20.8f%n", k, (double)degredistribution[k] / graphe.getNodeCount()));
                  }
              }
              printWriter.close();
          	} catch(IOException e) {
          e.printStackTrace();
          	}
    }

    
    private static void printDegreeDistribution(Graph graphe) {
        int[] dgdis = Toolkit.degreeDistribution(graphe);
        for (int k = 0; k < dgdis.length; k++) {
            if (dgdis[k] != 0) {
                System.out.printf(Locale.US, "%6d%20.8f%n", k, (double)dgdis[k] / graphe.getNodeCount());
            }
        }
    }
   /** question 6 **/
    private static void barabasiAlbert(Graph graphe, int nbNodes, int maxLink) {
        Generator gen = new BarabasiAlbertGenerator(maxLink, false);
        gen.addSink(graphe);
        gen.begin();
        while (graphe.getNodeCount() < nbNodes && gen.nextEvents());
        gen.end();
    }
    private static void ReseauRandom(Graph graphe, int nbNodes, int degreemoyen) {
        Generator gen = new RandomGenerator(degreemoyen, false, false);
        gen.addSink(graphe);
        gen.begin();
        while (graphe.getNodeCount() < nbNodes && gen.nextEvents());
        gen.end();
    }
}

