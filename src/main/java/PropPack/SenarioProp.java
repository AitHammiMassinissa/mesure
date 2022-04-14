package PropPack;

import org.graphstream.algorithm.Toolkit;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.Graphs;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


import static org.graphstream.algorithm.Toolkit.randomNode;

public class SenarioProp {

    /**
     * @var graph ,G_0,G_1,nombre_Infection,seuil,numero,nombre_jour
     */

    private Graph graph;
    private ArrayList<Node> G_0,G_1;
    private double[] nombre_Infection;
    private double seuil;
    private int numero,nombre_jour;

    /**
     *
     * @param numero
     * @param graph
     * @param nombre_jour
     */

    public SenarioProp(int numero, Graph graph, int nombre_jour)
    {
        this.graph= graph;
        this.nombre_jour= nombre_jour;
        this.nombre_Infection = new double[nombre_jour];
        this.G_0 = new ArrayList<>();
        this.G_1 = new ArrayList<>();
        this.seuil= 0.0;
        this.numero=numero;
        this.Reinitialiser();
    }
 /********************************************************Premier Senario *******************************************************/



    /**
     * Méthode du premier scenario
     */

    private void premier_senario()
	{
		List<Node> Liste_Infection = new ArrayList<>();
		Node Infecter_Premier = randomNode(this.graph);
		Infecter_Premier.setAttribute("Infecter", true);
		Liste_Infection.add(Infecter_Premier);

		List<Node> Liste_Porteur_Virus = new ArrayList<>();
		List<Node> Liste_Infection_Temp;

		double nombreInfecte[] = new double[this.nombre_jour];

		for (int i = 0; i < this.nombre_jour; i++)
		{
			Liste_Infection_Temp = new ArrayList<>(Liste_Infection);

			for(Node infecte : Liste_Infection_Temp)
			{
				infecte.neighborNodes().filter(v ->  !v.hasAttribute("Infecter")).forEach(v -> {
					int infection = (int) (Math.random()*7);
					if(infection == 0)
					{
						Liste_Porteur_Virus.add(v);
						v.setAttribute("Infecter", true);
					}
				});
				int miseAJour = (int) (Math.random()*14);
				if (miseAJour == 0)
				{
					infecte.removeAttribute("Infecter");
					Liste_Infection.remove(infecte);
				}
			}
			Liste_Infection.addAll(Liste_Porteur_Virus);
			Liste_Porteur_Virus.clear();
			nombreInfecte[i] = (double)Liste_Infection.size()/ this.graph.getNodeCount();
		}

		this.nombre_Infection = Deux_Tableau_Ass(this.nombre_Infection, nombreInfecte);
		this.Reinitialiser();

	}





    /********************************************************deuxieme Senario *******************************************************/

    /**
     * Méthode du deuxieme scenario
     */

    private void deuxieme_senario()
    {
    	this.Reinitialiser();

		List<Node> Liste_Infection = new ArrayList<>();

		Node Infecter_Premier = randomNode(this.graph);
		Infecter_Premier.setAttribute("Infecter", true);

		Liste_Infection.add(Infecter_Premier);
		Set_Anti_Random(this.graph);
		List<Node> Liste_Porteur_Virus = new ArrayList<>();
		List<Node> Liste_Infection_Temp;

		double Nombre_Infection[] = new double[this.nombre_jour];


		for (int i = 0; i < this.nombre_jour; i++)
		{
			Liste_Infection_Temp = new ArrayList<>(Liste_Infection);

			for(Node infecte : Liste_Infection_Temp)
			{
				infecte.neighborNodes().filter(v -> ! v.hasAttribute("Infecter") && ! v.hasAttribute("Non_Infecter")).forEach(v -> {
					int infection = (int) (Math.random()*7);

					if(infection == 0)
					{
						Liste_Porteur_Virus.add(v);
						v.setAttribute("Infecter", true);
					}
				});
				int miseAJour = (int) (Math.random()*14);
				if (miseAJour == 0)
				{
					infecte.removeAttribute("Infecter");
					Liste_Infection.remove(infecte);
				}
			}
			Liste_Infection.addAll(Liste_Porteur_Virus);
			Liste_Porteur_Virus.clear();
			Nombre_Infection[i] = (double)Liste_Infection.size()/ this.graph.nodes().filter(v-> !(boolean) v.hasAttribute("Non_Infecter")).count();

		}
		this.nombre_Infection = Deux_Tableau_Ass(this.nombre_Infection, Nombre_Infection);
		this.seuil = this.Calcule_du_seuil();
	
    }


    /********************************************************Troisieme Senario *******************************************************/

    /**
     * Méthode du troisieme scenario
     */

    private void troisieme_senario()
    {
    	this.Reinitialiser();
		this.G_0.clear();
		this.G_1.clear();
		List<Node> Liste_Infection = new ArrayList<>();
		List<Node> Liste_Porteur_Virus = new ArrayList<>();

		double Nombre_Infection[] = new double[nombre_jour];

		Node[] Noeud_Graph= this.graph.nodes().toArray(Node[]::new);

		for (int i = 0; i < Noeud_Graph.length/2; i++)
		{
			Node v = Noeud_Graph[i];
			this.G_0.add(v);
			Node[] voisin =  v.neighborNodes().toArray(Node[]::new);

			// ajoute aléatoirement à un voisin l'anti-virus
			if(voisin.length>0)
			{
				int random = (int)(Math.random()*voisin.length-1);
				Node n = voisin[random];
				n.setAttribute("Non_Infecter",true);
				n.removeAttribute("Infecter");
				this.G_1.add(n);
			}
		}

		Node patientZero = randomNode(this.graph);
		patientZero.setAttribute("Infecter", true);
		patientZero.removeAttribute("Non_Infecter");

		Liste_Infection.add(patientZero);
		List<Node> listeInfectesTmp;

		for (int i = 0; i < this.nombre_jour; i++)
		{
			listeInfectesTmp = new ArrayList<>(Liste_Infection);

			for(Node infecte : listeInfectesTmp)
			{
				infecte.neighborNodes().filter(v -> ! v.hasAttribute("Infecter") && ! v.hasAttribute("Non_Infecter")).forEach(v -> {
					int infection = (int) (Math.random()*7);
					if(infection == 0)
					{
						Liste_Porteur_Virus.add(v);
						v.setAttribute("Infecter", true);
					}
				});

				int miseAJour = (int) (Math.random()*14);
				if (miseAJour == 0)
				{
					infecte.removeAttribute("Infecter");
					Liste_Infection.remove(infecte);
				}

			}
			Liste_Infection.addAll(Liste_Porteur_Virus);
			Liste_Porteur_Virus.clear();
			Nombre_Infection[i] = (double)Liste_Infection.size() / this.graph.nodes().filter(v-> ! v.hasAttribute("Non_Infecter")).count();
		}

		this.nombre_Infection = Deux_Tableau_Ass(this.nombre_Infection, Nombre_Infection);
		this.seuil = this.Calcule_du_seuil();
    }


  /********************************************************Méthode utiliser *******************************************************/
    
 /**
     * Méthode de réinitialisation qui nous permet de supprimer un attributs
     */


    private void Reinitialiser()
    {
        this.graph.nodes().forEach(n->{
        	n.removeAttribute("Infecter");
			n.removeAttribute("Non_Infecter");
        });
    }
    
    /**
     *
     * @param tab1
     * @param tab2
     * @return  tableau_resultat
     */
    private static double[] Deux_Tableau_Ass(double[] tab1, double[] tab2) {
        double[] tableau_resultat = new double[tab1.length];
        for (int i = 0; i < tab1.length; i++) tableau_resultat[i] = tab1[i] + tab2[i];
        return tableau_resultat;
    }


     /**
     * @param graph
     */

    private static void Set_Anti_Random(Graph graph)
    {
        List<Node> liste = Toolkit.randomNodeSet(graph, (int) (graph.getNodeCount()/2));
        liste.stream().filter(v ->! v.hasAttribute("Infecter")).forEach(v-> {
            v.setAttribute("Non_Infecter", true);
        });

    }



/**
     *
     * @return degreMoyen/degreCarreMoyen
     */
    private  double Calcule_du_seuil()
    {
        Graph g= Graphs.clone(this.graph);
        this.graph.nodes().filter(n->n.hasAttribute("Non_Infecter")).forEach(v -> {
            g.removeNode(v.getId());
        });
        double Degree_Carr_Moeyn = 0.0;
        double Degree;
        double Degree_Moyen = Toolkit.averageDegree(g);
        for (Node n: g.nodes().collect(Collectors.toList())) Degree_Carr_Moeyn += Math.pow(n.getDegree(), 2);
        Degree= Degree_Carr_Moeyn / g.getNodeCount();
        return Degree_Moyen/Degree;
    }
/**
 * 
 * @param nomFichier
 * @param tab
 * @param dossier
 */
    public static void creerFichierData(String nomFichier, double[] tab,String dossier)
	{
    	try {
			File f = new File("./src/main/resources/TpPropagation/" + dossier + "/" + nomFichier + ".dat");
			FileWriter writer = new FileWriter(f);

			for (int i = 0; i < tab.length; i++)
				writer.write((i+1) + " " + tab[i]*100 + "\n");

			writer.close();

		} catch (Exception e) {
			System.out.println(e);
			}
	}

       /**
     * 
     * @param tableau
     * @param nbExec
     * @return resultat
     */
    
    private static double[] Moyenne(double[] tableau, int nbExec)
    {
        double[] resultat = new double[tableau.length];
        for (int i = 0; i < tableau.length; i++) resultat[i] = tableau[i]/nbExec;
        return resultat;
    }

      /**********************************************Demmarer les trois senario**********************************************/
    
    
	    
	/**
	 * @return void 
	 */
    public void run()
    {
    	
        if(this.numero == 1) premier_senario();
        if(this.numero == 2) deuxieme_senario();
        if(this.numero == 3) troisieme_senario();
    }

    /**
     * 
     * @param n
     * @param graph
     * @param premier_senario
     * @param deuxieme_senario
     * @param troisieme_senario
     */

    public static void nombre_execution(int n,Graph graph,SenarioProp premier_senario,SenarioProp deuxieme_senario,SenarioProp troisieme_senario) {
	   
       
        for (int i = 0; i < n; i++)
        {
            System.out.println("\n Numéro d'execution \n " + (i+1) );
            premier_senario.run();
            deuxieme_senario.run();
            troisieme_senario.run();
        }
        
    }
   
    /**************************************************Executer les fichier .gnuplot et sauvgarder les images dans un dossier*****************************/
    /**
     * 
     * @param nomFichier
     * @param dossier
     */
    public static void Cree_Image_Avec_Gnuplot(String nomFichier, String dossier)
    {
        try {
            Runtime commandPrompt = Runtime.getRuntime();
            commandPrompt.exec("gnuplot ./src/main/resources/TpPropagation/"+dossier+"/"+nomFichier+".gnuplot");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

       
/************************************************** Simulation **********************************************************/

   
private static void Simulation (Graph graph, int nbExec, int nbJour, String dossier)
{
    
    SenarioProp premier_senario = new SenarioProp(1, graph, nbJour);
    
    SenarioProp  deuxieme_senario = new SenarioProp(2, graph, nbJour);
   
    SenarioProp  troisieme_senario = new SenarioProp(3, graph, nbJour);

    
    nombre_execution(nbExec,graph,premier_senario,deuxieme_senario,troisieme_senario);
   
    double[] nombreInfecte1 = premier_senario.getNombre_Infection();
    double[] nombreInfecte2 = deuxieme_senario.getNombre_Infection();
    double[] nombreInfecte3 = troisieme_senario.getNombre_Infection();
    
  


    String[] nomFichiers = {"Evoloution_premier_senario", "Evolution_deuxieme_senario", "Evolution_troisieme_senario"};
    creerFichierData(nomFichiers[0], Moyenne(nombreInfecte1, nbExec),dossier);
    creerFichierData(nomFichiers[1], Moyenne(nombreInfecte2, nbExec),dossier);
    creerFichierData(nomFichiers[2], Moyenne(nombreInfecte3, nbExec),dossier);
    
    
    Cree_Image_Avec_Gnuplot("premier_senario", dossier);
    Cree_Image_Avec_Gnuplot("deuxieme_senario", dossier);
    Cree_Image_Avec_Gnuplot("troisieme_senario", dossier);
    System.out.println("\n Le graph dans le dossier \n " + dossier);

    System.out.println("\n Group 0 = \n" + troisieme_senario.getG_0());
    System.out.println("\n Group 1 = \n" + troisieme_senario.getG_1());

    System.out.println("\n seuil épidémique du deuxième senario (de façon aleatoire ) \n: " + deuxieme_senario.getSeuil());
    System.out.println("\n seuil épidémique du troisieme senario (de façon sélective) \n " + troisieme_senario.getSeuil());
}

    /**********************************************Getteur et setteur **********************************************/

                /**
                 *
                 * @return Graph graph
                 */
                public Graph getGraph() {
                    return graph;
                }

                /**
                 *
                 * @param graph
                 */
                public void setGraph(Graph graph) {
                    this.graph = graph;
                }

                /**
                 *
                 * @return nombre_jour
                 */
                public int getNombre_jour() {
                    return nombre_jour;
                }

                /**
                 *
                 * @param nombre_jour
                 */
                public void setNombre_jour(int nombre_jour) {
                    this.nombre_jour = nombre_jour;
                }

                /**
                 *
                 * @return nombre_Infection
                 */
                public double[] getNombre_Infection() {
                    return nombre_Infection;
                }

                /**
                 *
                 * @param nombre_Infection
                 */
                public void setNombre_Infection(double[] nombre_Infection) {
                    this.nombre_Infection = nombre_Infection;
                }

                /**
                 *
                 * @return G_0 (groupe 0)
                 */
                public double getG_0() {
                	double degreMoyen = 0;

            		for (Node n : this.G_0)
            			degreMoyen += n.getDegree();

            		return degreMoyen/this.G_0.size();
                }

                /**
                 *
                 * @param g_0
                 */
                public void setG_0(ArrayList<Node> g_0) {
                    G_0 = g_0;
                }

                /**
                 *
                 * @return G_1 (groupe 1)
                 */
                public double getG_1() {
                	double degreMoyen = 0;

            		for (Node n : this.G_1)
            			degreMoyen += n.getDegree();

            		return degreMoyen/this.G_1.size();
                }

                /**
                 *
                 * @param g_1
                 */
                public void setG_1(ArrayList<Node> g_1) {
                    G_1 = g_1;
                }

                /**
                 *
                 * @return seuil
                 */
                public double getSeuil() {
                    return seuil;
                }

                /**
                 *
                 * @param seuil
                 */
                public void setSeuil(double seuil) {
                    this.seuil = seuil;
                }

                /**
                 *
                 * @return numero
                 */
                public int getNumero() {
                    return numero;
                }

                /**
                 *
                 * @param numero
                 */
                public void setNumero(int numero) {
                    this.numero = numero;
                }

	
/*****************************************Test********************************************************************/


                    public static void main(String[] args) {
                        System.out.println("\n Simulation à partir du fichier com-dblp.ungraph.txt \n");
                        Graph g1 = Graph_Gen.Gen_Graph_fichier("com-dblp.ungraph.txt");
                        Simulation(g1,5,90,"Donnee_telecharger");
                        System.out.println("\n Simulation à partir d'un générateur random  \n");
                        Graph gAleatoire = Graph_Gen.Gen_Graph_Al(30000, 10);
                        Simulation(gAleatoire,5,90,"Donnee_Aleat");
                        System.out.println("\n Simulation à partir d'un générateur BarabasiAlbert  \n");
                        Graph gBarabasi = Graph_Gen.Gen_Graph_BarabasiAlbert((int) Toolkit.averageDegree(gAleatoire),gAleatoire.getNodeCount());
                        Simulation(gBarabasi,5,90,"Donnee_Barabasi");
                    }

}