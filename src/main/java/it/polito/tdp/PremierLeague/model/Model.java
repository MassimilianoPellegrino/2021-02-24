package it.polito.tdp.PremierLeague.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;
import it.polito.tdp.PremierLeague.model.AzioneSaliente.Tipologia;

public class Model {
	
	private Graph<Player, DefaultWeightedEdge> grafo;
	private Map<Integer, Player> idMap;
	PremierLeagueDAO dao = new PremierLeagueDAO();
	double bestDelta;
	
	int indiceAzioni;
	int giocatoriSq1;
	int giocatoriSq2;
	int goalSq1;
	int goalSq2;
	
	public String creaGrafo(Match m) {
		grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		idMap = new HashMap<>();
		dao.getVertici(m, idMap);
		for(Arco a: dao.getArchi(m)) {
			if(a.getW()>0)
				Graphs.addEdgeWithVertices(grafo, idMap.get(a.getIdp1()), idMap.get(a.getIdp2()), a.getW());
			else
				Graphs.addEdgeWithVertices(grafo, idMap.get(a.getIdp2()), idMap.get(a.getIdp1()), -a.getW());
		}
		
		return "Grafo creato, con "+grafo.vertexSet().size()+" vertici e "+grafo.edgeSet().size()+" archi";
	}

	public Graph<Player, DefaultWeightedEdge> getGrafo() {
		return grafo;
	}
	
	public Player getBestPlayer() {
		
		Player player = null;
		
		bestDelta = 0;
		
		for(Player p: grafo.vertexSet()) {
			
			double sum = 0;
			
			for(DefaultWeightedEdge e: grafo.outgoingEdgesOf(p))
				sum += grafo.getEdgeWeight(e);
			for(DefaultWeightedEdge e: grafo.incomingEdgesOf(p))
				sum -= grafo.getEdgeWeight(e);
			
			if(sum>bestDelta) {
				bestDelta = sum;
				player = p;
			}
		}
		
		return player;
		
	}

	public double getBestDelta() {
		return bestDelta;
	}
	
	public List<Match> listAllMatches(){
		return dao.listAllMatches();
	}

	
	public AzioneSaliente generaAzione() {
		
		AzioneSaliente a = null;
		
		double x = Math.random();
		if(x<0.5)
			a = new AzioneSaliente(indiceAzioni++, Tipologia.GOAL);
		else if(x<0.8)
			a = new AzioneSaliente(indiceAzioni++, Tipologia.ESPULSIONE);
		else
			a = new AzioneSaliente(indiceAzioni++, Tipologia.INFORTUNIO);
		
		return a;
	}
	
	public String simula(int n, Match m) {
		
		indiceAzioni = 0;
		giocatoriSq1 = 11;
		giocatoriSq2 = 11;
		goalSq1 = 0;
		goalSq2 = 0;
		
		PriorityQueue<AzioneSaliente> queue = new PriorityQueue<AzioneSaliente>();
		
		for(int i=0; i<n; i++)
			queue.add(generaAzione());
		
		AzioneSaliente a;
		
		while((a = queue.poll())!=null) {
			
			System.out.println(queue.size());
			
			switch(a.getTipo()) {
			case GOAL:
				if(giocatoriSq1>giocatoriSq2)
					goalSq1++;
				else if(giocatoriSq2>giocatoriSq1)
					goalSq2++;
				else if(getTeamId(getBestPlayer(), m) == m.getTeamHomeID())
					goalSq1++;
				else
					goalSq2++;
				
			case ESPULSIONE:
				double x = Math.random();
				if(x<0.6) {
					if(getTeamId(getBestPlayer(), m) == m.getTeamHomeID())
						giocatoriSq1--;
					else
						giocatoriSq2--;
				}else {
					if(getTeamId(getBestPlayer(), m) == m.getTeamAwayID())
						giocatoriSq1--;
					else
						giocatoriSq2--;
				}
				
			/*case INFORTUNIO:
				double y = Math.random();
				queue.add(generaAzione());
				queue.add(generaAzione());
				if(y<0.5)
					queue.add(generaAzione());*/
			
			}
		}
		
		return "Risultato: "+m.getTeamHomeNAME()+" "+goalSq1+" - "+goalSq2+" "+m.getTeamAwayNAME()+"\n"
				+ "Espulsi: "+m.getTeamHomeNAME()+" - "+(11-giocatoriSq1)+"\n"
						+ "\t"+m.getTeamAwayNAME()+" - "+(11-giocatoriSq2);
			
			
	}
	
	public int getTeamId(Player p, Match m) {
		return dao.getTeamId(p, m);
	}
	
	
}
