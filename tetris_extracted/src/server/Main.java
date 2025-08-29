package server;

import com.google.gson.Gson;
import java.io.*;
import java.net.*;

public class Main {
    public static void main(String[] args) {
        int port = 3000;
        Gson gson = new Gson();
        
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server listening on port " + port);
            
            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(
                         new InputStreamReader(clientSocket.getInputStream()));
                     PrintWriter out = new PrintWriter(
                         new OutputStreamWriter(clientSocket.getOutputStream()), true)) {
                    
                    String gameStateJson = in.readLine();
                    PureGame gameState = gson.fromJson(gameStateJson, PureGame.class);
                    
                    Simulation simulation = new Simulation(
                        gameState.getCells(),
                        gameState.getCurrentShape(),
                        gameState.getNextShape()
                    );
                    
                    int[] optimizedMove = simulation.getOptimizedMove();
                    OpMove opMove = new OpMove(optimizedMove[0], optimizedMove[1]);
                    
                    String response = gson.toJson(opMove);
                    out.println(response);
                }
            }
        } catch (Exception e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }
}