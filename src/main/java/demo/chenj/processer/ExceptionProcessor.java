package demo.chenj.processer;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.model.RouteDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class ExceptionProcessor implements Processor {

    private static Logger LOGGER = LoggerFactory.getLogger(ExceptionProcessor.class);

       @Override
    public void process(Exchange exchange)  {
           Map<String, Object> properties = exchange.getProperties();
           Exception cause = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);

           LOGGER.info("--ondelivery cause--:" + cause.toString());
    }


}
