package org.homework.anton.testers.impl;

import fr.bmartel.speedtest.SpeedTestReport;
import fr.bmartel.speedtest.SpeedTestSocket;
import fr.bmartel.speedtest.inter.ISpeedTestListener;
import fr.bmartel.speedtest.model.SpeedTestError;
import org.homework.anton.model.input.AbstractDataTransferInput;
import org.homework.anton.model.output.AbstractDataTransferResult;
import org.homework.anton.model.service.DegradationCalculationService;
import org.homework.anton.testers.face.HttpTester;
import org.homework.anton.testers.face.HttpsTester;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.util.Optional.ofNullable;

@Service
public class BandwidthTest implements HttpTester, HttpsTester {

    @Value("${default.degradation.level}")
    private double defaultDegradationLevel;

    @Resource
    private DegradationCalculationService degradationCalculationService;

    final Lock lock = new ReentrantLock();
    final Object s = new Object();

    @Override
    public void test(String siteName, AbstractDataTransferResult abstractDataTransferResult) {
        final Thread t = Thread.currentThread();
        SpeedTestSocket speedTestSocket  = new SpeedTestSocket();
        speedTestSocket.addSpeedTestListener(new ISpeedTestListener() {
            @Override
            public void onCompletion(SpeedTestReport report) {
                abstractDataTransferResult.setBandwidth(
                        report.getTransferRateBit().divide(new BigDecimal(1000000)).doubleValue());
                synchronized (lock) {
                    lock.notify();
                }
            }
            @Override
            public void onError(SpeedTestError speedTestError, String errorMessage) {
                abstractDataTransferResult.setBandwidth(Double.MAX_VALUE);
                System.out.println(errorMessage);
                synchronized (lock) {
                    lock.notify();
                }
            }

            @Override
            public void onProgress(float percent, SpeedTestReport report) {}
        });
        speedTestSocket.startDownload(siteName);
        try {
            synchronized (lock) {
                lock.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean degraded(AbstractDataTransferResult oldAbstractDataTransferResult,
                            AbstractDataTransferResult newAbstractDataTransferResult, AbstractDataTransferInput abstractDataTransferInput) {

        boolean result = oldAbstractDataTransferResult != null &&
                degradationCalculationService.isDegraded(oldAbstractDataTransferResult.getBandwidth(),
                newAbstractDataTransferResult.getBandwidth(),
                ofNullable(abstractDataTransferInput.getDegradationRate())
                        .orElse(defaultDegradationLevel));
        newAbstractDataTransferResult.setBandwidthDegradation(result);
        return result;
    }
}
