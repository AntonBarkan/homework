package org.homework.anton.model.service;

import org.springframework.stereotype.Service;

@Service
public class DegradationCalculationService {

    public boolean isDegraded(double before, double now, double degradationLevel) {
        return before * (1 + degradationLevel) < now;
    }
}
