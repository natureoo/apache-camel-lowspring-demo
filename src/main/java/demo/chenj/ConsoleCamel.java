package demo.chenj;

import demo.chenj.processer.HttpProcessor;
import demo.chenj.processer.OtherProcessor;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.spring.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ConsoleCamel  {

    private static Logger LOGGER = LoggerFactory.getLogger(ConsoleCamel.class);

    private CamelContext context;

    private static List<String> sendUriList;

    public ConsoleCamel(CamelContext context){
        this.context = context;
    }

    public static void main(String[] args) throws Exception {
        Main main = new Main();
        // configure the location of the Spring XML file
        main.setApplicationContextUri("META-INF/spring/camel-context.xml");
        // run and block until Camel is stopped (or JVM terminated)
        main.run();
    }

}
