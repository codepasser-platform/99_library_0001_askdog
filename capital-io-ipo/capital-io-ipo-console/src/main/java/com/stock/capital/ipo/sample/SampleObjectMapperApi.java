package com.stock.capital.ipo.sample;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import com.stock.capital.common.service.exception.ServiceException;
import com.stock.capital.ipo.service.sample.vo.SampleVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * [SampleObjectMapperApi].
 *
 * @author Joker.Cheng.
 * @version 0.0.1
 */
@RestController
@RequestMapping("/console/sample/mapper")
public class SampleObjectMapperApi {

  @RequestMapping(value = "/empty", method = GET, produces = APPLICATION_JSON_UTF8_VALUE)
  public SampleVo emptyProperties() throws ServiceException {
    SampleVo vo = new SampleVo();
    vo.setId(123);
    vo.setPostCode("1231231");
    return vo;
  }
}
