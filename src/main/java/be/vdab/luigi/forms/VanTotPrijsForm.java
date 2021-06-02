package be.vdab.luigi.forms;

import java.math.BigDecimal;

public class VanTotPrijsForm {
    private final BigDecimal van;
    private final BigDecimal tot;

    public VanTotPrijsForm(BigDecimal van, BigDecimal tot) {
        this.van = van;
        this.tot = tot;
    }

    public BigDecimal getVan() {
        return van;
    }

    public BigDecimal getTot() {
        return tot;
    }
}
