package ecez.vndbapp.model;

import android.util.Log;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.UnknownHostException;

import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import ecez.vndbapp.controller.vndatabaseapp;

/**
 * Created by Teng on 10/13/2016.
 */
public class ServerRequest {

    private static final String HOST = "api.vndb.org";
    private static final int PORT = 19535;
    private static final char EOM = 0x04;
    private static InputStreamReader in;
    private static OutputStream out;
    private static SSLSocket socket;
    private static SocketFactory sf;
    private static String userName = "tropicalman", password = "ebola";

    public ServerRequest () {}

    private static ServerRequest ourInstance = new ServerRequest();

    public static ServerRequest getInstance() {
        return ourInstance;
    }

    public synchronized boolean connect() { //Connect to server, instantiate IO writers and sockets
        try {
            Log.d("Connection Attempt","Attempting to connect to the server");
            sf = SSLSocketFactory.getDefault();
            socket = (SSLSocket) sf.createSocket(HOST, PORT);
            socket.setKeepAlive(false);
            socket.setSoTimeout(0);
            in = new InputStreamReader(socket.getInputStream());
            out = socket.getOutputStream();

            HostnameVerifier hv = HttpsURLConnection.getDefaultHostnameVerifier();
            SSLSession s = socket.getSession();

            if (!hv.verify(HOST, s)) {
                Log.d("Connection not verfied", "The API's certificate is not valid. Expected " + HOST + ", found " + s.getPeerPrincipal().getName());
                return false;
            }
        } catch (UnknownHostException uhe) {
            return false;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return false;
        }
        if (socket.isConnected()) {
            Log.d("Connection Attempt","Socket is connected, connected to server");
            return true;
        }
        else
            return false;
    }

    public void disconnect() {
        //Close IOwriters and the Sockets
        try {
            socket.getInputStream().close();
            socket.getOutputStream().close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public synchronized String writeToServer(final String queryType, final String type, final String flags, final String filters, final String options) {
        final StringBuilder command = new StringBuilder();
        command.append(queryType);
        command.append(" ");
        command.append(type);
        command.append(' ');
        command.append(flags);
        command.append(' ');
        command.append(filters);
        if (options != null) {
            command.append(' ');
            command.append(options);
        }
        command.append(EOM);

        return sendData(command.toString());
    }

    private synchronized String sendData (String serverCommand) {
        try {
            Log.d("Writing to Server",serverCommand);
            if (in.ready()) while (in.read() > -1) ;
            out.flush();
            out.write(serverCommand.toString().getBytes("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("Server request","Sent command to server, awaiting response");
        String jsonString = response();
        return jsonString;
    }

    private synchronized String response () {
        StringBuilder response = new StringBuilder();
        try {
            Log.d("Read Attempt","Attempting to read from Server");
            int read = in.read();
            while (read != 4 && read > -1) {
                response.append((char) read);
                read = in.read();
                //Log.d("Partial Response",response.toString());
            }
            Log.d("Full Server Response",response.toString());
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return null;
        }
        return response.toString();
    }
    public synchronized boolean login() {
        Log.d("Login Attempt", "Attempting to Login to the server");
        if (!connect()) {
            return false;
        }
        SystemStatus.getInstance().connectedToServer = true;
        String response;
        StringBuilder s = new StringBuilder();
        s.append("login {\"protocol\":1,\"client\":\"tropicalebola430\",\"clientver\":0.21,\"username\":\"" + userName + "\",\"password\":\"" + password + "\"}");
        s.append(EOM);
        response = sendData(s.toString());
        Log.d("Login Attempt",response);
        if (response.equals("ok")) {
            return true;
        } else {
            return false;
        }
    }
}



