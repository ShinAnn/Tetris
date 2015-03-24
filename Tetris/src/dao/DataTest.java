package dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dto.Player;

public class DataTest implements IData {

	public List<Player> loadData() {
		List<Player> players = new ArrayList<Player>();
		return players;
	}

	@Override
	public void saveData(Player players) {
	}
	
	public DataTest(HashMap<String,String> param){
		
	}

}
