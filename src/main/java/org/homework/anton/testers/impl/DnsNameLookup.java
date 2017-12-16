package org.homework.anton.testers.impl;

import com.google.gson.Gson;
import org.homework.anton.model.input.ConfigurationFile;
import org.homework.anton.model.output.DnsNameLookupResult;
import org.homework.anton.model.output.ExecutionResult;
import org.homework.anton.testers.face.Tester;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

import static java.net.InetAddress.*;

@Service
public class DnsNameLookup extends AbstractTester<String> {
    @Resource
    private ExecutionResult executionResult;

    @Resource
    private Gson gson;

    @Override
    protected void test(String name) {
        InetAddress[] allByName = new InetAddress[0];
        try {
            allByName = getAllByName(name);
            executionResult.addDnsNameLookupResult(new DnsNameLookupResult(true, name));
        } catch (UnknownHostException e) {
            executionResult.addDnsNameLookupResult(new DnsNameLookupResult(false, name));
        }
        System.out.println(gson.toJson(executionResult.getDnsNameLookupResults()));
    }

    @Override
    protected List<String> getDtos(ConfigurationFile configurationFile) {
        return configurationFile.getDnsNameLookup();
    }


}
