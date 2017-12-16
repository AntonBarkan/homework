# homework


## comand line parameters
requered parameters is
  - history.file -- path to history file
  - config.file -- path to input file, structure described below
  
optional:
  - `default.degradation.level`  --  default 0.1 (10%)
  
addition parameter should be passed to allow mail messages`
  
  - `spring.mail.host` -- default smtp.gmail.com 
  - `alert.mail`  -- addres to send mail, no default
  - `spring.mail.port` -- default 587
  - `spring.mail.properties.mail.transport.protocol` -- default smtp
  - `spring.mail.username`  --no default
  - `spring.mail.password`   --no default
  - `spring.mail.properties.mail.smtp.auth`  -- default true
  - `spring.mail.properties.mail.smtp.starttls.enable   -- default true
  - `spring.mail.properties.mail.smtp.starttls.required  -- default true
  - `spring.mail.properties.mail.smtp.quitwait`  -- default false

    
## Input file description
json file 
```
  {
  "dnsNameLookup": String[],
  "httpConnectivity": {"url": String, "degradationRate": double}[],
  "httpsConnectivity": {"url": String, "degradationRate": double}[],
  }
```

 - `dnsNameLookup` - list of names to dns lookup
 - `httpConnectivity` & `httpsConnectivity` - list of objects that contains urls and degradation threshold. 
 if `degradationRate` not provided will used `default.degradation.level`.

### for example:



```
  {
  "dnsNameLookup": ["2.testdebit.info"],
  "httpConnectivity": [{"url": "docs.oracle.com/javase/1.5.0/docs/api/java/util/concurrent/locks/Condition.html"}],
  "httpsConnectivity": [{"url": "docs.oracle.com/javase/1.5.0/docs/api/java/util/concurrent/locks/Condition.html", "degradationRate": 0}]
  }
```
