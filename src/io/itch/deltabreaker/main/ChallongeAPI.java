package io.itch.deltabreaker.main;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import me.Swedz.jca.JCA;
import me.Swedz.jca.web.Web;

public class ChallongeAPI {

	private static final String API_KEY= "4DmX3HJW4tyPGSeHbNwIE2k01eAdvkNayQEZXaiJ";
	
	public static Player[] getResults(String url) throws Exception {
		ArrayList<Player> players = new ArrayList<Player>();
		
		String[] bracketName = url.split("/");
		
		JCA.api_key = API_KEY;
		JSONArray ja = (JSONArray) new JSONParser().parse(Web.get("https://api.challonge.com/v1/tournaments/" + bracketName[bracketName.length - 1] + "/participants.json",
				30 * 1000, true));
		
		for(int i = 0; i < ja.size(); i++) {
			JSONObject jo = (JSONObject) ja.get(i);
			JSONObject njo = (JSONObject) jo.get("participant");
			
			int placement = Math.toIntExact((long) njo.get("final_rank"));
			boolean placed;
			do {
				placed = false;
				for(Player p : players) {
					if(p.placement == placement) {
						placed = true;
						placement++;
						break;
					}
				}
			} while(placed);
			players.add(new Player((String) njo.get("display_name"), placement));
		}
		
		return players.toArray(new Player[players.size()]);
	}
	
}
