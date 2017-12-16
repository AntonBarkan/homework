package org.homework.anton.model.output;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DnsNameLookupResult {
    private boolean status;
    private String name;

}
