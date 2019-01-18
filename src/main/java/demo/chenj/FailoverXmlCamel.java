package demo.chenj;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author chenj
 * @date 2019-01-13 17:06
 */
public class FailoverXmlCamel {

    private static Logger LOGGER = LoggerFactory.getLogger(FailoverXmlCamel.class);

    public static void main(String[] args) throws Exception {

        ApplicationContext ap = new ClassPathXmlApplicationContext("META-INF/spring/camel-failover-context.xml");
        LOGGER.info("初始化....." + ap);
        // 没有具体的业务含义，只是保证主线程不退出
        synchronized (FailoverXmlCamel.class) {
            FailoverXmlCamel.class.wait();
        }
    }
}
