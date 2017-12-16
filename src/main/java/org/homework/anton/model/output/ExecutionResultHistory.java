package org.homework.anton.model.output;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ExecutionResultHistory {
    private List<ExecutionResult> executionResults = new ArrayList<>();

    public void addExecutionResult(ExecutionResult executionResult) {
        executionResults.add(executionResult);
    }

}
