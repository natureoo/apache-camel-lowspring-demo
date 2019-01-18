package demo.chenj.processer;

import org.apache.camel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Order service as a plain POJO class
 */
public class OrderService {
    private static Logger LOGGER = LoggerFactory.getLogger(OrderService.class);


    public Object orderFailed(@Headers Map<String, Object> in, @Body String payload, @OutHeaders Map<String, Object> out, @ExchangeException Exception e, @Properties Map<String, Object> props ) {
        Object camelFailureEndpoint = props.get("CamelFailureEndpoint");//出错的endpoint
        Object camelExceptionCaught = props.get("CamelExceptionCaught");


        LOGGER.info("camelFailureEndpoint:"+camelFailureEndpoint);
        LOGGER.info("camelExceptionCaught:"+camelExceptionCaught);

        LOGGER.info("处理出错的endpoint ,发邮件告警");
        return "Order ERROR";
    }
}