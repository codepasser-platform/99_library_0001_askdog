package com.stock.capital.common.utils;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.Integer.parseInt;
import static java.lang.Integer.toUnsignedLong;
import static java.lang.System.getProperty;
import static java.lang.System.getenv;
import static java.lang.System.setProperty;
import static java.time.temporal.ChronoField.INSTANT_SECONDS;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * ID 生成器. [SERVER * 16 + SEQ * 16 + TIME * 32].
 *
 * @author Joker.Cheng.
 * @version 0.0.1
 */
public class IdGenerator {

  private static final Logger logger = LoggerFactory.getLogger(IdGenerator.class);

  private static final IdGenerator instance = new IdGenerator();
  private long defaultTime;
  private int machineId;
  private int sequence = 0;
  private long seconds;

  private static final String MACHINE_ID = "machine.id";

  private static final String YYYY_MM_DD = "yyyy-MM-dd";

  private static final String DEFAULT_TIME = "2018-01-01";

  private static final String DEFAULT_ID = "00";

  {
    try {
      // TODO MACHINE_ID
      setProperty(MACHINE_ID, DEFAULT_ID);
      defaultTime =
          new SimpleDateFormat(YYYY_MM_DD).parse(DEFAULT_TIME).toInstant().getLong(INSTANT_SECONDS);
      String mid = getProperty(MACHINE_ID, getenv(MACHINE_ID));
      machineId = parseInt(StringUtils.isEmpty(mid) ? DEFAULT_ID : mid);
    } catch (ParseException ignore) {
      logger.warn(ignore.getLocalizedMessage());
    }
  }

  private void calculate() {
    long now = currentSeconds();
    if (now == seconds) {
      if (++sequence > 0xFFFF) {
        logger.warn("sequence overflow, waiting.");
        try {
          wait(100);
          calculate();
        } catch (InterruptedException ignore) {
          logger.warn(ignore.getLocalizedMessage());
        }
      }
    } else {
      seconds = now;
      sequence = 0;
    }
  }

  private long currentSeconds() {
    return new Date().toInstant().getLong(INSTANT_SECONDS) - defaultTime;
  }

  private synchronized long generate() {
    checkArgument(0xFFFFFF >= machineId, "machine id overflow");
    calculate();
    long result = 0;
    result = result | (toUnsignedLong(machineId) << 48);
    result = result | (toUnsignedLong(sequence) << 32);
    result = result | seconds;
    return result;
  }

  public static long next() {
    return instance.generate();
  }

  public static String nextVarchar() {
    return String.valueOf(instance.generate());
  }

  //  public static void main(String... args) {
  //    System.out.println(next());
  //    System.out.println(nextVarchar());
  //  }
}
