package org.homework.anton.testers.impl;

import org.homework.anton.model.input.AbstractDataTransferInput;
import org.homework.anton.model.input.ConfigurationFile;
import org.homework.anton.model.output.AbstractDataTransferResult;
import org.homework.anton.model.output.ExecutionResult;
import org.homework.anton.testers.face.HttpsTester;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class HttpsConnectivity  extends AbstractDataTransferConnectivity {

    @Autowired
    List<HttpsTester> httpsTesters;

    @Override
    protected void addToExecutionResult(AbstractDataTransferResult abstractDataTransferResult) {
        executionResult.addHttpsResult(abstractDataTransferResult);
    }

    @Override
    protected List<AbstractDataTransferResult> getAbstractDataTransferResult(ExecutionResult executionResult) {
        return executionResult.getHttpsResults();
    }

    @Override
    protected List<HttpsTester> getTesters() {
        return httpsTesters;
    }

    @Override
    protected String getProtocol() {
        return "https";
    }

    @Override
    protected List<AbstractDataTransferInput> getDtos(ConfigurationFile configurationFile) {
        return configurationFile.getHttpsConnectivity();
    }
}
