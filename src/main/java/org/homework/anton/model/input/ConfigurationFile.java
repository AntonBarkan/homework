package org.homework.anton.model.input;

import lombok.Data;

import java.util.List;

@Data
public class ConfigurationFile {
    private List<String> dnsNameLookup;
    private List<AbstractDataTransferInput> httpConnectivity;
    private List<AbstractDataTransferInput> httpsConnectivity;
}
