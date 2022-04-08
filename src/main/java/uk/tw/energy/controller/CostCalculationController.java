package uk.tw.energy.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.tw.energy.domain.ElectricityReading;
import uk.tw.energy.service.CostCalculatorService;
import uk.tw.energy.service.MeterReadingService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/costs")
public class CostCalculationController {

    CostCalculatorService costCalculatorService;

    public CostCalculationController(CostCalculatorService costCalculatorService) {
        this.costCalculatorService = costCalculatorService;
    }

    @GetMapping("/read/{smartMeterId}")
    public ResponseEntity getLastWeekCost(@PathVariable String smartMeterId) {
        Optional<BigDecimal> costForSevenDays=costCalculatorService.calculateCostForSevenDays(smartMeterId);

        return costForSevenDays.isPresent()
                ? ResponseEntity.ok(costForSevenDays.get())
                : ResponseEntity.notFound().build();
    }
}
