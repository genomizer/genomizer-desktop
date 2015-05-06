package communication;

import javax.net.ssl.*;

import org.apache.http.conn.ssl.X509HostnameVerifier;

import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

public class SSLTool {

    private static SSLContext sslContext;

    private static X509HostnameVerifier hostnameVerifier;

    public static void disableCertificateValidation() {
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }

            public void checkClientTrusted(X509Certificate[] certs,
                    String authType) {
            }

            public void checkServerTrusted(X509Certificate[] certs,
                    String authType) {
            }
        } };

        hostnameVerifier = new X509HostnameVerifier() {

            @Override
            public void verify(String arg0, SSLSocket arg1) throws IOException {
                // TODO Auto-generated method stub

            }

            @Override
            public void verify(String arg0, X509Certificate arg1)
                    throws SSLException {
                // TODO Auto-generated method stub

            }

            @Override
            public void verify(String arg0, String[] arg1, String[] arg2)
                    throws SSLException {

            }

            @Override
            public boolean verify(String hostname, SSLSession session) {
                // TODO Auto-generated method stub
                return true;
            }
        };

        // Install the all-trusting trust manager
        try {
            sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection
                    .setDefaultSSLSocketFactory(sslContext.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);
        } catch (Exception e) {
        }
    }

    public static SSLContext getSslContext() {
        return sslContext;
    }

    public static X509HostnameVerifier getHostnameVerifier() {
        return hostnameVerifier;
    }
}
