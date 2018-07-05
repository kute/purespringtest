package com.kute;

import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;

/**
 * created by kute on 2018/06/28 22:41
 */
@RunWith(PowerMockRunner.class)
@PowerMockIgnore({"javax.management.*", "javax.net.ssl.*"})
@ContextConfiguration(locations = {"classpath:applicationContext-core.xml"})
public abstract class BasePowerMockTest {

    protected static final Logger LOGGER = LoggerFactory.getLogger(BasePowerMockTest.class);
}
