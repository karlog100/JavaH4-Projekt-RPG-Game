/*;
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//#Randerstypen
package hacknslash.randerstypen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import hacknslash.randerstypen.SQL.SQLInsert;
import hacknslash.randerstypen.SQL.SQLSelect;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author jan
 */
public class HackNSlashRandersTypen {

    /**
     * @param args the command line arguments
     */
    //Global variabels
    static Player MyPlayer = null;
    
    public static void main(String[] args) throws IOException, SQLException {
        
        //Variabels
        
        boolean quitGame = false;
        
        //Welcome Message
        System.out.println("Welcome, dear player!");
        
        // Choose between a new game, loading a old game or exit the game
        do {
            System.out.println("Choose an option:");
            System.out.println("1. New game");
            System.out.println("2. Load game");
            System.out.println("3. Quit game");
            
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            
            int Choise = 0;
            
            // Read line and try to call parseInt on it.
	    String line = in.readLine();
	    int result;
	    try {
		result = Integer.parseInt(line);
	    } catch (NumberFormatException exception) {
		result = 0;
            }
            
            switch (result){
                case 1:  StartGame();
                         break;
                case 2:  LoadGame();
                         break;
                case 3:  quitGame = true;
                         break;
                default:
                         System.out.println("Your input is not valid! - Please try again.");
                         break;
            }
        } while (!quitGame);
        
    }
    
    private static void StartGame() throws SQLException {
        
        String Username = null;
        boolean IsAvailable = false;
        
        do {
            try {
                System.out.println("Type username:");
                Username = ReadUsername();
            } catch (IOException ex) {
                Logger.getLogger(HackNSlashRandersTypen.class.getName()).log(Level.SEVERE, null, ex);
            }

            IsAvailable = CheckUsername(Username);
        } while(!IsAvailable);
                
        if(Username != null) {
            String SQLStatement_AddUser = "INSERT INTO players (Name) VALUES ('" + Username + "')";
            SQLInsert SQLInsertUser = new SQLInsert(SQLStatement_AddUser);
        }
        
        LoadGame(Username);
    }
    
    private static String ReadUsername() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        return in.readLine();
    }
    
    private static boolean CheckUsername(String Username) throws SQLException {
        boolean UsernameAvailable = false;

        System.out.println("Choose a username:");

        String SQLStatement_Check = "SELECT Name FROM players WHERE Name='" + Username + "' LIMIT 1;";
        SQLSelect SQLReturn = new SQLSelect(SQLStatement_Check);
        UsernameAvailable = (SQLReturn.ResultListOfLists.size() < 1 );
        
        return UsernameAvailable;
    }
    
    private static void LoadGame(String Username) {
        try {
            String SQLStatement_GetDetails = "SELECT Name, Level, Experience, MapLevel, CurrentPosition, Health, Mana FROM players WHERE Name='" + Username + "' LIMIT 1;";
            
            SQLSelect SQLUserDetails = new SQLSelect(SQLStatement_GetDetails);
            ArrayList PlayerDetails = SQLUserDetails.ResultListOfLists.get(0);
            MyPlayer = new Player(PlayerDetails.get(0).toString(), 
                    PlayerDetails.get(1).toString(),
                    PlayerDetails.get(2).toString(),
                    PlayerDetails.get(3).toString(),
                    PlayerDetails.get(4).toString(),
                    PlayerDetails.get(5).toString(),
                    PlayerDetails.get(6).toString());
            
            
        } catch (SQLException ex) {
            Logger.getLogger(HackNSlashRandersTypen.class.getName()).log(Level.SEVERE, null, ex);
        }
        GameLoop();
    }
    
    private static void LoadGame() throws IOException, SQLException {
        boolean IsAvailable = true;
        String Username = null;
        
        System.out.println("Please type username to load from:");
        
        do {
            try {
                Username = ReadUsername();
            } catch (IOException ex) {
                Logger.getLogger(HackNSlashRandersTypen.class.getName()).log(Level.SEVERE, null, ex);
            }

            IsAvailable = CheckUsername(Username);
        } while(IsAvailable);
        
        LoadGame(Username);
    }

    private static void GameLoop() {
        do {
        Map CurrentMap = new Map(MyPlayer.MapLevel());
            try {
                InMapLoop(CurrentMap);
            } catch (IOException ex) {
                Logger.getLogger(HackNSlashRandersTypen.class.getName()).log(Level.SEVERE, null, ex);
            }
        } while(true);
    }

    private static void InMapLoop(Map CurrentMap) throws IOException {
        boolean MapNotFinished = true;
        do {
            MyPlayer.SetPos(CurrentMap.Move(MyPlayer.CurrPos()));
            System.out.println(MyPlayer.LastPos());
            boolean Win = false;
            if(!Helpers.RndBool()) {
                System.out.println("Combat mode initiated");
                if(CombatMode()) {
                    MyPlayer.GiveExp(MyPlayer.MapLevel());
                }
                else {
                    MyPlayer.SetPos(MyPlayer.GetLastPos());
                    MyPlayer.MaxHealth();
                }
            }
            MapNotFinished = !CurrentMap.HasChest(MyPlayer.CurrPos());
            MyPlayer.RegenHealth();
        } while(MapNotFinished);
        MyPlayer.GiveExp((MyPlayer.MapLevel() +10) * 2);
    }
    


    //not yet finished
    private static boolean CombatMode() {
        boolean PlayerAttackFirst = Helpers.RndBool();
        System.out.println("Player attacks first: " + PlayerAttackFirst);
        boolean MonsterDied = false;
        
        int Damage = 0;
        Monster MyMonster = new Monster(MyPlayer.MapLevel());
        System.out.println(MyMonster.EntityName);
        
        if(true) {
        //if(!PlayerAttackFirst) {
            System.out.println("Monster Attacks");
            Damage = MyMonster.Attack();
            MyPlayer.DamageTaken(Damage);
            System.out.println("Monster damage:" + Damage);
        }
        System.out.println("Combat loop");
        do {
           Damage = 0;
           Damage = MyPlayer.Attack();
           System.out.println("Damage to monster:" + Damage);
           MyMonster.DamageTaken(Damage);
           MonsterDied = (MyMonster.Health() <= 0);
           if(!MonsterDied) {
               Damage = 0;
               Damage = MyMonster.Attack();
               MyPlayer.DamageTaken(Damage);
               MonsterDied = (MyPlayer.Health() <= 0);
           }
        } while(!MonsterDied);
        return (MyPlayer.Health() > 0);
    }  
}
