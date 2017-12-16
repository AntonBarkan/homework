package org.homework.anton;


import com.google.gson.Gson;
import org.homework.anton.model.input.ConfigurationFile;
import org.homework.anton.model.output.AbstractDataTransferResult;
import org.homework.anton.model.output.ExecutionResult;
import org.homework.anton.model.output.ExecutionResultHistory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.io.*;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import static com.google.common.base.Strings.isNullOrEmpty;
import static org.homework.anton.common.ErrorConstant.NOT_PROVIDED_HISTORY_FILE_PATH;

@Configuration
public class AppConfiguration {

    @Value("${history.file}")
    private String historyFilePath;

    @Value("${spring.mail.username}")
    private String mailFrom;

    @Value("${alert.mail:#{null}}")
    private String alertMailAddres;

    @Resource
    private JavaMailSenderImpl mailSender;

    @Bean
    public ExecutionResultHistory executionResultHistory() throws IOException {
        File f =  historyFile() ;
        if (f.exists()) {
            try (Reader reader = new FileReader(f)) {
                ExecutionResultHistory executionResultHistory = gson().fromJson(reader, ExecutionResultHistory.class);
                return executionResultHistory == null ? new ExecutionResultHistory() : executionResultHistory;
            }
        }
        return new ExecutionResultHistory();
    }

    @Bean
    public File historyFile() throws IOException {
        if (isNullOrEmpty(historyFilePath)) {
            throw new RuntimeException(NOT_PROVIDED_HISTORY_FILE_PATH);
        }
        return new File(historyFilePath);
    }

    @Bean
    public ExecutionResult executionResult() {
        ExecutionResult executionResult = new ExecutionResult();
        executionResult.setExecutionTime(new Date().getTime());
        return executionResult;
    }

    @Bean
    public ConfigurationFile configurationFile() throws IOException {
        ConfigurationFile configurationFile;
        try (Reader reader = new FileReader("C:\\d\\luminate-security\\src\\main\\test\\resources\\test-configs\\test0.json")) {
            return gson().fromJson(reader, ConfigurationFile.class);
        }
    }

    @Bean
    public Collection<AbstractDataTransferResult> allertResults() {
        return new HashSet<>();
    }

    @Bean
    public Gson gson() {
        return  new Gson();
    }

    @PreDestroy
    public void write() throws IOException {
        sendMail();
        try (FileWriter writer = new FileWriter(historyFile())) {
            ExecutionResultHistory executionResultHistories = executionResultHistory();
            executionResultHistories.addExecutionResult(executionResult());
            gson().toJson(executionResultHistories, writer);
        }
    }

    private void sendMail() {
        if (mailFrom ==null || alertMailAddres == null || mailSender.getUsername() == null ||
                mailSender.getPassword() == null) {
            System.out.println("mail not confederated.");
            return;
        }

        try {
            this.mailSender.send(mimeMessage -> {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage, 1);
                message.setFrom(mailFrom);
                message.setTo(alertMailAddres);
                message.setSubject("alert");
                message.setText(gson().toJson(allertResults()));
            });
            System.out.println("mail send to " + alertMailAddres + ".");
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("can't send mail.");
        }
    }
}
