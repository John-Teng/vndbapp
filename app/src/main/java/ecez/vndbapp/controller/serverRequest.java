package ecez.vndbapp.controller;

import android.util.Log;

import org.json.JSONObject;

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

/**
 * Created by Teng on 10/13/2016.
 */
public class serverRequest {

    private final String HOST = "api.vndb.org";
    private final int PORT = 19535;
    private final char EOM = 0x04;
    private static InputStreamReader in;
    private static OutputStream out;
    private static SSLSocket socket;
    private static SocketFactory sf;

    public static synchronized boolean connect() { //Connect to server, instantiate IO writers and sockets
        try {
            sf = SSLSocketFactory.getDefault();
            socket = (SSLSocket) sf.createSocket(HOST, PORT);
            socket.setKeepAlive(false);
            socket.setSoTimeout(0);
            in = new InputStreamReader(socket.getInputStream());
            out = socket.getOutputStream();

            HostnameVerifier hv = HttpsURLConnection.getDefaultHostnameVerifier();
            SSLSession s = socket.getSession();

            if (!hv.verify(HOST, s)) {
                return false;
            }
        } catch (UnknownHostException uhe) {
            return false;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return false;
        }
        return true;
    }


    public static synchronized void disconnect() {
        //Close IOwriters and the Sockets
        try {
            socket.getInputStream().close();
            socket.getOutputStream().close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static synchronized String writeToServer(final String queryType, final String type, final String flags, final String filters, final JSONObject options) {
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

    private static String sendData (String serverCommand) {
        try {
            if (in.ready()) while (in.read() > -1) ;
            out.flush();
            out.write(serverCommand.toString().getBytes("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String jsonString = response();
        return jsonString;
    }

    private static String response () {
        StringBuilder response = new StringBuilder();
        try {
            int read = in.read();
            while (read != 4 && read > -1) {
                response.append((char) read);
                read = in.read();
                Log.d("Server Response",response.toString());
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return null;
        }
        return response.toString();
    }

}


