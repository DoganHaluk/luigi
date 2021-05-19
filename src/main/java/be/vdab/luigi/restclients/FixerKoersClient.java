package be.vdab.luigi.restclients;

import be.vdab.luigi.exceptions.KoersClientException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Pattern;

@Component
class FixerKoersClient implements KoersClient {
    private static final Pattern PATTERN = Pattern.compile("^.*\"USD\": *(\\d+\\.?\\d*).*$");
    private final URL url;

    FixerKoersClient() {
        try {
            url = new URL("http://data.fixer.io/api/latest?access_key=123a6790e81d1564f6867c97893f3c7e&symbols=USD");
        } catch (MalformedURLException ex) {
            throw new KoersClientException("Fixer URL is verkeerd");
        }
    }

    @Override
    public BigDecimal getDollarKoers() {
        var woord = "";
        try (var stream = url.openStream()) {
            var matcher = PATTERN.matcher(new String(stream.readAllBytes()));
            if (!matcher.matches()) {
                throw new KoersClientException("Fixer data ongeldig");
            }
            return new BigDecimal(matcher.group(1));
        } catch (IOException ex) {
            woord = "faut";
            System.out.println(woord);
            throw new KoersClientException("Kan koers niet lezen via Fixer.", ex);
        }
    }
}