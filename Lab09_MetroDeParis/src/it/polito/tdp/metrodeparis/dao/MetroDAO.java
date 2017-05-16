package it.polito.tdp.metrodeparis.dao;

import java.awt.geom.Point2D;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jgrapht.UndirectedGraph;
import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.metrodeparis.model.Fermata;
import it.polito.tdp.metrodeparis.model.Linea;

public class MetroDAO {

	public void getAllFermate(Map<Integer,Fermata> map, UndirectedGraph<Fermata,DefaultWeightedEdge> grafo) {

		final String sql = "SELECT id_fermata, nome, coordx, coordy FROM fermata ORDER BY nome ASC";

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Fermata f = new Fermata(rs.getInt("id_Fermata"), rs.getString("nome"), new LatLng(rs.getDouble("coordx"), rs.getDouble("coordy")));
				grafo.addVertex(f);
				map.put(f.getIdFermata(), f);
			}

			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore di connessione al Database.");
		}

	}

	public void creaGrafo(Map<Integer,Fermata> fermate ,Map<Integer, Linea> linee , WeightedGraph<Fermata,DefaultWeightedEdge> grafo) {
		final String sql1 = "SELECT id_fermata, nome, coordx, coordy FROM fermata ORDER BY nome ASC";

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql1);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Fermata f = new Fermata(rs.getInt("id_Fermata"), rs.getString("nome"), new LatLng(rs.getDouble("coordx"), rs.getDouble("coordy")));
				grafo.addVertex(f);
				fermate.put(f.getIdFermata(), f);
			}

			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore di connessione al Database.");
		}
		
		final String sql2 = "SELECT id_linea,nome,velocita,intervallo FROM linea";

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql2);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Linea l = new Linea(rs.getInt("id_linea"),rs.getString("nome"),rs.getDouble("velocita"),rs.getDouble("intervallo"));
				linee.put(l.getIdLinea(),l);
			}

			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore di connessione al Database.");
		}
		
		final String sql3 = "SELECT id_linea,id_stazP,id_stazA FROM connessione" ;
		
		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql3);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Linea l ;
				Fermata p ;
				Fermata a ;
				if(linee.containsKey(rs.getInt("id_linea")) && fermate.containsKey(rs.getInt("id_stazP")) && fermate.containsKey(rs.getInt("id_stazA"))){
					l = linee.get(rs.getInt("id_linea"));
					p = fermate.get(rs.getInt("id_stazP"));
					a = fermate.get(rs.getInt("id_stazA"));
					if(grafo.vertexSet().contains(p) && grafo.vertexSet().contains(a)){
						DefaultWeightedEdge e = grafo.addEdge(p, a);
						Double peso = LatLngTool.distance(p.getCoords(),a.getCoords(), LengthUnit.KILOMETER);
						grafo.setEdgeWeight(e, peso);
					}
				}	
			}

			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore di connessione al Database.");
		}
		
	}
}
