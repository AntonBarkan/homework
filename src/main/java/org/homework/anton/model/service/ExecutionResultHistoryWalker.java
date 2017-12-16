package org.homework.anton.model.service;

import org.homework.anton.model.output.AbstractDataTransferResult;
import org.homework.anton.model.output.ExecutionResult;
import org.homework.anton.model.output.ExecutionResultHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.function.Function;

@Service
public class ExecutionResultHistoryWalker {
    @Autowired
    private ExecutionResultHistory executionResultHistory;

    public AbstractDataTransferResult findLastValue(Function<ExecutionResult, AbstractDataTransferResult> fun) {
        return executionResultHistory.getExecutionResults().stream()
                .sorted((x,y) -> Long.compare(y.getExecutionTime(), x.getExecutionTime()))
                .map(fun)
                .filter(o -> o != null)
                .findFirst()
                .orElse(null);
    }

}
