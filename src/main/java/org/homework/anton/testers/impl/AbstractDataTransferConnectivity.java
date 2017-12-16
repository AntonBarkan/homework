package org.homework.anton.testers.impl;

import com.google.gson.Gson;
import org.checkerframework.checker.units.qual.A;
import org.homework.anton.model.input.AbstractDataTransferInput;
import org.homework.anton.model.output.AbstractDataTransferResult;
import org.homework.anton.model.output.ExecutionResult;
import org.homework.anton.model.service.ExecutionResultHistoryWalker;
import org.homework.anton.testers.face.AbstractDataTransferTester;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

public abstract class AbstractDataTransferConnectivity extends AbstractTester<AbstractDataTransferInput>{


    @Resource
    private Gson gson;

    @Autowired
    private ExecutionResultHistoryWalker executionResultHistoryWalker;

    @Autowired
    protected ExecutionResult executionResult;
    @Autowired
    private Collection<AbstractDataTransferResult> allertResults;

    @Override
    protected void test(AbstractDataTransferInput abstractDataTransferInput) {
        String protocol = getProtocol();
        String name = abstractDataTransferInput.getUrl();
        if (!name.startsWith(protocol)) {
            name = protocol + "://" + name;
        }
        AbstractDataTransferResult abstractDataTransferResult = new AbstractDataTransferResult();
        boolean degraded = false;
        for (AbstractDataTransferTester abstractDataTransferTester : getTesters()) {
            abstractDataTransferTester.test(name, abstractDataTransferResult);
            abstractDataTransferResult.setUrl(abstractDataTransferInput.getUrl());
            degraded |= abstractDataTransferTester.degraded(executionResultHistoryWalker.findLastValue(
                    (er) -> getAbstractDataTransferResult(er)
                            .stream()
                            .filter(x -> x.getUrl().equals(abstractDataTransferInput.getUrl()))
                            .findFirst()
                            .orElse(null)
                    ),
                    abstractDataTransferResult,
                    abstractDataTransferInput);
        }
        if (degraded) {
            allertResults.add(abstractDataTransferResult);
            System.err.println("Was degraded" + gson.toJson(abstractDataTransferResult));
        } else {
            System.out.println(gson.toJson(abstractDataTransferResult));
        }
        addToExecutionResult(abstractDataTransferResult);
    }

    protected abstract void addToExecutionResult(AbstractDataTransferResult abstractDataTransferResult);

    protected abstract List<AbstractDataTransferResult> getAbstractDataTransferResult(ExecutionResult executionResult);

    protected abstract List<? extends AbstractDataTransferTester> getTesters();

    protected abstract String getProtocol();
}
