package be.vdab.luigi.controllers;

import be.vdab.luigi.domain.Pizza;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("pizzas")
class PizzaController {
    private final Pizza[] pizzas = {
            new Pizza(1, "Prosciutto", BigDecimal.valueOf(4), true),
            new Pizza(2, "Margherita", BigDecimal.valueOf(5), false),
            new Pizza(3, "Calzone", BigDecimal.valueOf(4), false)};

    @GetMapping
    public ModelAndView pizzas() {
        var modelAndView = new ModelAndView("pizzas", "pizzas", pizzas);
        modelAndView.addObject("getallen", List.of(3, 7));
        modelAndView.addObject("landen", Map.of("B", "België", "NL", "Nederland"));
        return modelAndView;
    }
}
