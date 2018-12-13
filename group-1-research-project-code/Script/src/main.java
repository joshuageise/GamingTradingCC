import java.util.*;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.trade.Trade;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.Category;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.interactive.Player;
import org.dreambot.api.wrappers.items.Item;
import org.dreambot.api.Client;
import org.dreambot.core.Instance;

@ScriptManifest(author = "Group 1 CC", name = "Trades r kewl", version = 1.0, description = "CC by trading items", category = Category.MISC)
public class main extends AbstractScript {
	
	private Hashtable<String, String> table;
	private Hashtable<String, String> reverse;
	private List<String> items = new ArrayList<>();
	private Boolean loop = true;
	private String ultimateMessage = "";

	public void onStart() {
		log("Welcome to our script");
		table = new Hashtable<String, String>();
		table.put("0", "Bones");
		table.put("1", "Egg");
		table.put("EOF", "Pot");
		
		reverse = new Hashtable<String, String>();
		reverse.put("Bones", "0");
		reverse.put("Egg", "1");
		reverse.put("Pot", "EOF");
	}

	private enum State {
		PREP, TRADE, RECEIVE
	};

	private State getState() {
		// find out when to be in PREP or TRADE states
		
		// get current player's name
		Player self = getClient().getLocalPlayer();
		
		// if it is LRowli then be the sender
		if (self.getName().equals("LRowli")) {
			if (items.isEmpty()) {
				return State.PREP;
			}
			else {
				return State.TRADE;
			}
		}
		
		// else be the receiver
		return State.RECEIVE;
	}

	public void onExit() {

	}

	@Override
	public int onLoop() {
		switch (getState()) {
		case PREP:
			log("Prepping for trade");
			
			// prepare for sending data - get binary string
			String binary = new String("0110100001101001");
			
			// loop through binary string
			for (int i = 0; i < binary.length(); i++) {
				// extract item from lookup table - add to array
				String b = Character.toString(binary.charAt(i));
				String item = table.get(b);
				items.add(item);
			}
			
			// add EOF item
			String eof = table.get("EOF");
			items.add(eof);
			
			log(items.toString());
			
			break;
		case TRADE:
			
			if (items.size() != 0) {
			
				log("Initiating trade");
				
				// create a Trade
				Trade t = new Trade(getClient());
				
				// initiate trade with player
				t.tradeWithPlayer("RMobby");
				
				// wait for player to accept trade
				while (!t.isOpen()) {
					
				}
				
				// loop through items array
				int max = 0;
				if (items.size() > 28) {
					max = 28;
				}
				else {
					max = items.size();
				}
				List<String> items_sb = items.subList(0, max);
				for (String item : items_sb) {
					// add each item to trade 
					t.addItem(item, 1);
				}
				
				if (max != 0) {
					items = items.subList(max, items.size());
				}
				else {
					loop = false;
				}
				
			}
			
			break;
			
		case RECEIVE: 
			if (loop) {
				
				// accept trade
				//Trade r = getTrade();
				//r.acceptTrade();
				
				// create a Trade
				Trade r = new Trade(getClient());
				
				// initiate trade with player
				r.tradeWithPlayer("LRowli");
				
				log("Receiving items");
				
				// get list of items in trade
				//Item[] trade_items = r.getTheirItems();
				//log(trade_items.toString());
				Item[] trade_items = null;
				while(true) {
	
					trade_items = r.getTheirItems();
					if (trade_items != null) {
		
						int items_length =  trade_items.length;
						String eof_item = table.get("EOF");
						
						if (trade_items[items_length - 1].getName().equals(eof_item)) {
							// decline trade on EOF
							r.declineTrade();
							loop = false;
							break;
						}
							
						if (items_length == 28) {
							// decline trade on max items
							r.declineTrade();
							break;
						}
						
						sleep(500);
					}
				}
				
				String message = "";
				
				// loop through trade_items 
				for (Item it : trade_items) {
					// convert items to binary 
					String name = it.getName();
					message += reverse.get(name);
				}
				
				ultimateMessage += message;
				
				log("message: " + message);
				
				break;
			}
		}
		return Calculations.random(500, 600);
	}
}
