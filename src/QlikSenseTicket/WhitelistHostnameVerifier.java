package QlikSenseTicket;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import java.util.HashSet;
import java.util.Set;

// singleton
enum WhitelistHostnameVerifier implements HostnameVerifier {
    // these hosts get whitelisted
    INSTANCE("localhost", "86.120.69.36");

    private Set<String> whitelist = new HashSet<>();
    private HostnameVerifier defaultHostnameVerifier =
            HttpsURLConnection.getDefaultHostnameVerifier();

    WhitelistHostnameVerifier(String... hostnames) {
        for (String hostname : hostnames) {
            whitelist.add(hostname);
        }
    }

    @Override
    public boolean verify(String host, SSLSession session) {
        if (whitelist.contains(host)) {
            return true;
        }
        // important: use default verifier for all other hosts
        return defaultHostnameVerifier.verify(host, session);
    }
}