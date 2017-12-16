package org.homework.anton.testers.impl;

import org.homework.anton.model.input.ConfigurationFile;
import org.homework.anton.testers.face.Tester;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

public abstract class AbstractTester<T> implements Tester {

    @Resource
    private ConfigurationFile configurationFile;

    @Override
    public void test() throws IOException {
        for (T t : getDtos(configurationFile)) {
            test(t);
        }
    }

    protected abstract void test(T t);

    protected abstract List<? extends T> getDtos(ConfigurationFile configurationFile);
}
