package org.homework.anton.testers.face;

import org.homework.anton.model.input.AbstractDataTransferInput;
import org.homework.anton.model.output.AbstractDataTransferResult;

public interface AbstractDataTransferTester {

    void test(String siteName, AbstractDataTransferResult abstractDataTransferResult);

    boolean degraded(
            AbstractDataTransferResult oldAbstractDataTransferResult,
            AbstractDataTransferResult newAbstractDataTransferResult,
            AbstractDataTransferInput abstractDataTransferInput);
}
