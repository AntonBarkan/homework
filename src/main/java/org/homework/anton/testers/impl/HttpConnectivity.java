package org.homework.anton.testers.impl;

import org.homework.anton.model.input.AbstractDataTransferInput;
import org.homework.anton.model.input.ConfigurationFile;
import org.homework.anton.model.output.AbstractDataTransferResult;
import org.homework.anton.model.output.ExecutionResult;
import org.homework.anton.testers.face.HttpTester;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class HttpConnectivity extends AbstractDataTransferConnectivity {

    @Resource
    List<HttpTester> httpTesters;

    @Override
    protected void addToExecutionResult(AbstractDataTransferResult abstractDataTransferResult) {
        executionResult.addHttpResult(abstractDataTransferResult);
    }

    @Override
    protected List<AbstractDataTransferResult> getAbstractDataTransferResult(ExecutionResult executionResult) {
        return executionResult.getHttpResults();
    }

    @Override
    protected List<HttpTester> getTesters() {
        return httpTesters;
    }

    @Override
    protected String getProtocol() {
        return "http";
    }

    @Override
    protected List<AbstractDataTransferInput> getDtos(ConfigurationFile configurationFile) {
        return configurationFile.getHttpConnectivity();
    }
}
