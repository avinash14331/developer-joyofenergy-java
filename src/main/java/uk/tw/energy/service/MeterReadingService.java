package uk.tw.energy.service;

import org.springframework.stereotype.Service;
import uk.tw.energy.domain.ElectricityReading;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MeterReadingService {

    private final Map<String, List<ElectricityReading>> meterAssociatedReadings;

    public MeterReadingService(Map<String, List<ElectricityReading>> meterAssociatedReadings) {
        this.meterAssociatedReadings = meterAssociatedReadings;
    }

    public Optional<List<ElectricityReading>> getReadings(String smartMeterId) {
        return Optional.ofNullable(meterAssociatedReadings.get(smartMeterId));
    }

    public Optional<List<ElectricityReading>> getLastSevenDaysReadings(String smartMeterId) {
        List<ElectricityReading> readings = meterAssociatedReadings.get(smartMeterId);
        Instant cur = Instant.now();
        List<ElectricityReading> ans=  readings.stream().filter((er) ->
            er.getTime().isAfter(Instant.now().minus(Duration.ofDays(7))) && er.getTime().isBefore(Instant.now())
         ).collect(Collectors.toList());
        return Optional.ofNullable(ans);
    }

    public void storeReadings(String smartMeterId, List<ElectricityReading> electricityReadings) {
        if (!meterAssociatedReadings.containsKey(smartMeterId)) {
            meterAssociatedReadings.put(smartMeterId, new ArrayList<>());
        }
        meterAssociatedReadings.get(smartMeterId).addAll(electricityReadings);
    }
}
