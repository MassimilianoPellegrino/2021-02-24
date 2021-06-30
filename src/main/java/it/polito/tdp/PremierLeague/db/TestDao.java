package it.polito.tdp.PremierLeague.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.PremierLeague.model.Arco;
import it.polito.tdp.PremierLeague.model.Match;
import it.polito.tdp.PremierLeague.model.Model;
import it.polito.tdp.PremierLeague.model.Player;

public class TestDao {

	public static void main(String[] args) {
		TestDao testDao = new TestDao();
		testDao.run();
	}
	
	public void run() {
		PremierLeagueDAO dao = new PremierLeagueDAO();
		Model model = new Model();
		/*System.out.println("Players:");
		System.out.println(dao.listAllPlayers());
		System.out.println("Teams:");
		System.out.println(dao.listAllTeams());
		System.out.println("Actions:");
		System.out.println(dao.listAllActions());
		System.out.println("Matches:");
		System.out.println(dao.listAllMatches());*/
		
		Map<Integer, Player> map = new HashMap<>();
		
		List<Match> m = dao.listAllMatches();
		List<Player> p = dao.listAllPlayers();		
		Match match = null;
		Player player = null;
		
		for(Match mm: m)
			if(mm.getMatchID() == 1)
				match = mm;
		
		for(Player pp: p)
			if(pp.getPlayerID() == 8597)
				player = pp;
			
		/*List<Player> p = dao.getVertici(match, map);
		
		List<Arco> a = dao.getArchi(match);
		
		System.out.println(p.size()+"\n"+a.size());*/
		
		System.out.println(model.creaGrafo(match));
		System.out.println(model.simula(5, match));
		
		/*System.out.println(model.creaGrafo(match));
		System.out.println(model.getBestPlayer());
		System.out.println(model.getBestDelta());*/
		//System.out.println(dao.getTeamId(player, match));
		
		/*for(DefaultWeightedEdge e: model.getGrafo().edgeSet())
			System.out.println(model.getGrafo().getEdgeWeight(e));*/
	}

}
