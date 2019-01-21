package demo.chenj;

import org.apache.camel.Endpoint;
import org.apache.camel.component.http4.HttpEndpoint;
import org.apache.camel.spring.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.net.URI;

/**
 * @author chenj
 * @date 2019-01-13 17:06
 */
public class FailoverXmlCamel {

    private static Logger LOGGER = LoggerFactory.getLogger(FailoverXmlCamel.class);

    public static void main(String[] args) throws Exception {

        ApplicationContext ap = new ClassPathXmlApplicationContext("META-INF/spring/camel-failover-context.xml");
        LOGGER.info("初始化....." + ap);

        synchronized (FailoverXmlCamel.class) {
            FailoverXmlCamel.class.wait();
        }

//        Main main = new Main();
//        // configure the location of the Spring XML file
//        main.setApplicationContextUri("META-INF/spring/camel-failover-context.xml");
//        // run and block until Camel is stopped (or JVM terminated)
//        AbstractApplicationContext applicationContext = main.getApplicationContext();
//        LOGGER.info("初始化....." + applicationContext);
//        main.run();
    }
}
