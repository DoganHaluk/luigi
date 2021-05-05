package be.vdab.luigi.services;

import be.vdab.luigi.restclients.KoersClient;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
class EuroService {
    private final KoersClient koersClient;
    EuroService(KoersClient koersClient) {
        this.koersClient = koersClient;
    }
    public BigDecimal naarDollar(BigDecimal euro) {
        return euro.multiply(koersClient.getDollarKoers()).setScale(2, RoundingMode.HALF_UP);
    }
}
