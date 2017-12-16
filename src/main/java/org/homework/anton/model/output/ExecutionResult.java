package org.homework.anton.model.output;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ExecutionResult {

    private long executionTime;
    private List<DnsNameLookupResult> dnsNameLookupResults = new ArrayList<>();
    private List<AbstractDataTransferResult> httpResults = new ArrayList<>();
    private List<AbstractDataTransferResult> httpsResults = new ArrayList<>();

    public void addDnsNameLookupResult(DnsNameLookupResult dnsNameLookupResult) {
        dnsNameLookupResults.add(dnsNameLookupResult);
    }

    public void addHttpResult(AbstractDataTransferResult httpResult) {
        httpResults.add(httpResult);
    }

    public void addHttpsResult(AbstractDataTransferResult httpsResult) {
        httpsResults.add(httpsResult);
    }


}



