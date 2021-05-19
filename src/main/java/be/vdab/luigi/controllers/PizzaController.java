package be.vdab.luigi.controllers;

import be.vdab.luigi.domain.Pizza;
import be.vdab.luigi.exceptions.KoersClientException;
import be.vdab.luigi.services.EuroService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("pizzas")
class PizzaController {
    private final Pizza[] pizzas = {
            new Pizza(1, "Prosciutto", BigDecimal.valueOf(4), true),
            new Pizza(2, "Margherita", BigDecimal.valueOf(5), false),
            new Pizza(3, "Calzone", BigDecimal.valueOf(4), false)};

    private final EuroService euroService;

    PizzaController(EuroService euroService) {
        this.euroService = euroService;
    }

    @GetMapping
    public ModelAndView pizzas() {
        var modelAndView = new ModelAndView("pizzas", "pizzas", pizzas);
        modelAndView.addObject("getallen", List.of(3, 7));
        modelAndView.addObject("landen", Map.of("B", "België", "NL", "Nederland"));
        return modelAndView;
    }

    @GetMapping("{id}")
    public ModelAndView pizza(@PathVariable long id) {
        var modelAndView = new ModelAndView("pizza");
        Arrays.stream(pizzas).filter(pizza -> pizza.getId() == id).findFirst()
                .ifPresent(pizza -> {
                    modelAndView.addObject("pizza", pizza);
                    try {
                        modelAndView.addObject(
                                "inDollar", euroService.naarDollar(pizza.getPrijs()));
                    } catch (KoersClientException ex) {
    // Hier komt later code die de exception verwerkt.
                    }
                });
        return modelAndView;
    }

    private List<BigDecimal> uniekePrijzen() {
        return Arrays.stream(pizzas)
                .map(Pizza::getPrijs)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    @GetMapping("prijzen")
    public ModelAndView prijzen() {
        return new ModelAndView("prijzen", "prijzen", uniekePrijzen());
    }

    private List<Pizza> pizzasMetPrijs(BigDecimal prijs) {
        return Arrays.stream(pizzas)
                .filter(pizza -> pizza.getPrijs().compareTo(prijs) == 0)
                .collect(Collectors.toList());
    }

    @GetMapping("prijzen/{prijs}")
    public ModelAndView pizzasMetEenPrijs(@PathVariable BigDecimal prijs) {
        return new ModelAndView("prijzen", "pizzas", pizzasMetPrijs(prijs))
                .addObject("prijzen", uniekePrijzen());
    }
}
