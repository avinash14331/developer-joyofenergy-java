package uk.tw.energy.service;

import org.springframework.stereotype.Service;
import uk.tw.energy.domain.ElectricityReading;
import uk.tw.energy.domain.PricePlan;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class CostCalculatorService {
    private final AccountService accountService;
    private final PricePlanService pricePlanService;


    public CostCalculatorService(AccountService accountService, PricePlanService pricePlanService) {
        this.accountService = accountService;
        this.pricePlanService = pricePlanService;
    }

    public Optional<BigDecimal> calculateCostForSevenDays( String smartMeterId){
        String planName = accountService.getPricePlanIdForSmartMeterId(smartMeterId);
        return pricePlanService.getCostForLastSevenDays(smartMeterId,planName);

    }

}
