package demo.chenj.processer;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Map;

/**
 * 另一个处理器OtherProcessor
 */
public class OtherProcessor implements Processor {

    private static Logger LOGGER = LoggerFactory.getLogger(OtherProcessor.class);


    @Override
    public void process(Exchange exchange) throws Exception {
        LOGGER.info("OtherProcessor exchangeid:"+exchange.getExchangeId());
        Message message = exchange.getIn();
        Map<String, Object> headers = message.getHeaders();
        for(String key:headers.keySet()){
            LOGGER.info("response header [{}:{}]", key, headers.get(key));
        }

        String returnBody = "";
        if(message.getBody() instanceof InputStream) {
            returnBody = StringUtil.analysisMessage((InputStream) message.getBody());
        }else if(message.getBody() instanceof String){
            returnBody = (String)message.getBody();
        }else if(message.getBody() instanceof  byte[]){
            returnBody = new String((byte[])message.getBody(), "UTF-8");
        }
        LOGGER.info("response body [{}]", returnBody);

        Message outMessage = exchange.getOut();
        outMessage.setBody(returnBody + " || 被OtherProcessor处理");

    }
}