package org.homework.anton;

import com.google.gson.Gson;
import org.homework.anton.model.input.ConfigurationFile;
import org.homework.anton.testers.face.Tester;
import org.homework.anton.testers.impl.HttpConnectivity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

@SpringBootApplication
public class InternetConnectivityTest implements CommandLineRunner {

    public static void main(String[] args) {
            SpringApplication app = new SpringApplication(InternetConnectivityTest.class);
            app.setBannerMode(Banner.Mode.OFF);
            app.run(args);
    }

    @Autowired
    private List<Tester> testers;

    @Autowired
    private HttpConnectivity HTTPConnectivity;

    @Autowired
    private Gson gson;

    @Override
    public void run(String... args) throws IOException {
        for (Tester t : testers) {
            t.test();
        }
    }


}
