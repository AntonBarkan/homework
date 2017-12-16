package org.homework.anton.testers.impl;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.homework.anton.model.input.AbstractDataTransferInput;
import org.homework.anton.model.output.AbstractDataTransferResult;
import org.homework.anton.model.service.DegradationCalculationService;
import org.homework.anton.testers.face.HttpTester;
import org.homework.anton.testers.face.HttpsTester;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;

@Service
public class LatencyTest implements HttpTester, HttpsTester {

    @Value("${default.degradation.level}")
    private double defaultDegradationLevel;

    @Resource
    private DegradationCalculationService degradationCalculationService;

    @Override
    public void test(String siteName, AbstractDataTransferResult abstractDataTransferResult) {
        HttpClient httpClient = new HttpClient();
        HttpMethod method = new GetMethod(siteName);

        try {
            long timerBefore = System.currentTimeMillis();
            httpClient.executeMethod(method);
            long timerAfter = System.currentTimeMillis();
            abstractDataTransferResult.setLatency((timerAfter - timerBefore) / 1000d);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            method.releaseConnection();
        }
    }

    @Override
    public boolean degraded(AbstractDataTransferResult oldAbstractDataTransferResult,
                            AbstractDataTransferResult newAbstractDataTransferResult,
                            AbstractDataTransferInput abstractDataTransferInput) {
        boolean result = oldAbstractDataTransferResult != null &&
                degradationCalculationService.isDegraded(oldAbstractDataTransferResult.getLatency(),
                newAbstractDataTransferResult.getLatency(),
                        abstractDataTransferInput.getDegradationRate() == 0 ?
                                defaultDegradationLevel : abstractDataTransferInput.getDegradationRate());
        newAbstractDataTransferResult.setLatencyDegradation(result);
        return result;
    }
}
