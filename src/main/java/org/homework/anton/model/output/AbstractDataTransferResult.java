package org.homework.anton.model.output;

import lombok.Data;

@Data
public class AbstractDataTransferResult {
    private String url;
    private double bandwidth;
    private double latency;
    private boolean latencyDegradation;
    private boolean bandwidthDegradation;
}
