package application;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class Server extends Application
{
	public void start(Stage primaryStage)
	{
		TextArea ta = new TextArea();
		
		Scene scene = new Scene(new ScrollPane(ta), 450, 200);
		primaryStage.setTitle("Server");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		new Thread(() -> {
			try
			{
				ServerSocket serverSocket = new ServerSocket(8000);
				Platform.runLater(() ->
					ta.appendText("Server started at " + new Date() + "\n"));
				Socket socket = serverSocket.accept();
				
				DataInputStream inputFromClient = new DataInputStream(socket.getInputStream());
				DataOutputStream outputToClient = new DataOutputStream(socket.getOutputStream());
				
				boolean isPrime = true;
				while (true)
				{
					int number = inputFromClient.readInt();
//					if (number == 2)
//					{
//						isPrime = true;
//					}
//					
//					else if (number == 0 || number == 1 || number % 2 == 0)
//					{
//						isPrime = false;
//					}
					
					
						for (int i = 2; i <= number / 2; i++)
						{
							int remainder = number % i; 
							if (remainder == 0)
							{
								isPrime = false;
								break;
							}
						}
						
					
				//	double area = radius * radius * Math.PI;
					
					outputToClient.writeBoolean(isPrime);
					
					Platform.runLater(() ->
					{
						ta.appendText("Number received from client to check prime number is: " + number + "\n");
						//ta.appendText("Area is: " + isPrime + "\n");
					});
				}
			}
			catch (IOException ex)
			{
				ex.printStackTrace();
			}
		}).start();
	}

	public static void main(String[] args)
	{
		launch(args);
	}
}
