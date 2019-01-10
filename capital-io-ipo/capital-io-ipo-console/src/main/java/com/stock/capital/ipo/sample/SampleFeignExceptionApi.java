package com.stock.capital.ipo.sample;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import com.stock.capital.common.exception.AbstractException;
import com.stock.capital.common.service.exception.ServiceException;
import com.stock.capital.ipo.service.sample.SampleUncaughtExceptionService;
import com.stock.capital.ipo.service.sample.bo.SampleBo;
import com.stock.capital.ipo.service.sample.bo.SampleGroupBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * [SampleUncaughtExceptionApi].
 *
 * @author Joker.Cheng.
 * @version 0.0.1
 */
@RestController
@RequestMapping("/console/sample/uncaught/feign")
public class SampleFeignExceptionApi {

  @Autowired private SampleUncaughtExceptionService sampleUncaughtExceptionService;

  @RequestMapping(value = "/conflict", method = GET, produces = APPLICATION_JSON_UTF8_VALUE)
  public boolean conflict() throws ServiceException {
    sampleUncaughtExceptionService.conflict();
    return false;
  }

  @RequestMapping(value = "/forbidden", method = GET, produces = APPLICATION_JSON_UTF8_VALUE)
  public boolean forbidden() throws ServiceException {
    sampleUncaughtExceptionService.forbidden();
    return false;
  }

  @RequestMapping(value = "/illegal", method = GET, produces = APPLICATION_JSON_UTF8_VALUE)
  public boolean illegal() throws ServiceException {
    sampleUncaughtExceptionService.illegal();
    return false;
  }

  @RequestMapping(value = "/terms", method = GET, produces = APPLICATION_JSON_UTF8_VALUE)
  public boolean terms() throws ServiceException {
    sampleUncaughtExceptionService.terms();
    return false;
  }

  @RequestMapping(value = "/not-found", method = GET, produces = APPLICATION_JSON_UTF8_VALUE)
  public boolean notFound() throws ServiceException {
    sampleUncaughtExceptionService.notFound();
    return false;
  }

  @RequestMapping(value = "/reference", method = GET, produces = APPLICATION_JSON_UTF8_VALUE)
  public boolean reference() throws ServiceException {
    sampleUncaughtExceptionService.reference();
    return false;
  }

  @RequestMapping(value = "/reference-jpa", method = GET, produces = APPLICATION_JSON_UTF8_VALUE)
  public boolean referenceJpa() throws ServiceException {
    sampleUncaughtExceptionService.referenceJpa();
    return false;
  }

  @RequestMapping(value = "/runtime", method = GET, produces = APPLICATION_JSON_UTF8_VALUE)
  public boolean runtime() throws ServiceException {
    sampleUncaughtExceptionService.runtime();
    return false;
  }

  @RequestMapping(value = "/abstract", method = GET, produces = APPLICATION_JSON_UTF8_VALUE)
  public boolean abstracts() throws AbstractException {
    sampleUncaughtExceptionService.abstracts();
    return false;
  }

  @RequestMapping(value = "/illegal-state", method = GET, produces = APPLICATION_JSON_UTF8_VALUE)
  public boolean illegalState() throws ServiceException {
    sampleUncaughtExceptionService.illegalState();
    return false;
  }

  @RequestMapping(value = "/illegal-valid", method = POST, produces = APPLICATION_JSON_UTF8_VALUE)
  public boolean illegalValid(@RequestBody SampleBo sampleBo) throws ServiceException {
    sampleUncaughtExceptionService.illegalValid(sampleBo);
    return false;
  }

  @RequestMapping(value = "/illegal-argument", method = GET, produces = APPLICATION_JSON_UTF8_VALUE)
  public boolean illegalArgument(@RequestParam(value = "phone", required = false) String phone)
      throws ServiceException {
    sampleUncaughtExceptionService.illegalArgument(phone);
    return false;
  }

  @RequestMapping(value = "/illegal-group", method = POST, produces = APPLICATION_JSON_UTF8_VALUE)
  public boolean illegalGroup(@RequestBody SampleGroupBo sampleGroupBo) throws ServiceException {
    sampleUncaughtExceptionService.illegalGroup(sampleGroupBo);
    return false;
  }
}
