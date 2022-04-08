package uk.tw.energy.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import uk.tw.energy.domain.MeterReadings;
import uk.tw.energy.service.AccountService;
import uk.tw.energy.service.CostCalculatorService;
import uk.tw.energy.service.MeterReadingService;
import uk.tw.energy.service.PricePlanService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CostCalculationControllerTest {
    private static final String SMART_METER_ID = "10101010";
    private CostCalculationController costCalculationController;
    private CostCalculatorService costCalculatorService;
    private AccountService accountService;
    private PricePlanService pricePlanService;
    private MeterReadingService meterReadingService;

    @BeforeEach
    public void setUp() {
        this.meterReadingService = new MeterReadingService(new HashMap<>());
        this.costCalculatorService = new CostCalculatorService(new AccountService(new HashMap<>()),new PricePlanService(new ArrayList<>(),meterReadingService));
        this.costCalculationController = new CostCalculationController(costCalculatorService);
    }

    @Test
    public void givenNoMeterIdIsSuppliedWhenStoringShouldReturnErrorResponse() {
        assertThat(costCalculationController.getLastWeekCost(null).getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
