import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Server extends Thread {
    //initialize socket and input stream
    private Socket socket = null;
    private DataInputStream in = null;

    // constructor with port
    public Server(Socket socket) {
        this.socket = socket;
    }
    public void run()
    {
        if(socket == null){
            return;
        }
        try {
            // takes input from the client socket
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(this.socket.getInputStream()));
            PrintWriter out =
                    new PrintWriter(this.socket.getOutputStream(), true);

            String inputLine, outputLine;

            // Initiate conversation with client
            KnockKnockProtocol kkp = new KnockKnockProtocol();
            outputLine = kkp.processInput(null);
            out.println(outputLine);
            while ((inputLine = in.readLine()) != null) {
                outputLine = kkp.processInput(inputLine);
                out.println(outputLine);
                if (outputLine.equals("Bye."))
                   break;
            }
            System.out.println("Closing connection");

            // close connection
            this.socket.close();
            in.close();
        } catch (IOException i) {
            System.out.println(i.getMessage());
        }
    }
}