package com.stock.capital.ipo.service.sample;

import com.stock.capital.common.service.exception.ServiceException;
import com.stock.capital.ipo.service.sample.vo.SampleVo;
import javax.annotation.Nonnull;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

/**
 * [SampleObjectMapperServiceImpl].
 *
 * @author Joker.Cheng.
 * @version 0.0.1
 */
@Service
@RestController
public class SampleObjectMapperServiceImpl implements SampleObjectMapperService {

  @Nonnull
  @Override
  public SampleVo emptyProperties() throws ServiceException {
    SampleVo sampleVo = new SampleVo();
    sampleVo.setId(111L);
    sampleVo.setPostCode("023452");
    return sampleVo;
  }
}
