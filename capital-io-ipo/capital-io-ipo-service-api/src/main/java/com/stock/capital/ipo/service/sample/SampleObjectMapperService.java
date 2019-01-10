package com.stock.capital.ipo.service.sample;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import com.stock.capital.common.service.exception.ServiceException;
import com.stock.capital.ipo.service.sample.vo.SampleVo;
import javax.annotation.Nonnull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * [SampleObjectMapperService].
 *
 * @author Joker.Cheng.
 * @version 0.0.1
 */
@FeignClient("ipo-service")
@RequestMapping("/sample/mapper")
public interface SampleObjectMapperService {

  @Nonnull
  @RequestMapping(value = "/empty", method = GET, produces = APPLICATION_JSON_UTF8_VALUE)
  SampleVo emptyProperties() throws ServiceException;
}
