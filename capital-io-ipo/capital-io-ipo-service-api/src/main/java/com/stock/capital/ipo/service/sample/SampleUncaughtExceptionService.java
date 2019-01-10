package com.stock.capital.ipo.service.sample;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import com.stock.capital.common.exception.AbstractException;
import com.stock.capital.common.model.validation.Group;
import com.stock.capital.common.service.exception.ServiceException;
import com.stock.capital.ipo.service.sample.bo.SampleBo;
import com.stock.capital.ipo.service.sample.bo.SampleGroupBo;
import javax.annotation.Nonnull;
import javax.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * [SampleUncaughtExceptionService].
 *
 * @author Joker.Cheng.
 * @version 0.0.1
 */
@FeignClient("ipo-service")
@RequestMapping("/sample/uncaught")
public interface SampleUncaughtExceptionService {

  @RequestMapping(value = "/conflict", method = GET, produces = APPLICATION_JSON_UTF8_VALUE)
  boolean conflict() throws ServiceException;

  @RequestMapping(value = "/forbidden", method = GET, produces = APPLICATION_JSON_UTF8_VALUE)
  boolean forbidden() throws ServiceException;

  @RequestMapping(value = "/illegal", method = GET, produces = APPLICATION_JSON_UTF8_VALUE)
  boolean illegal() throws ServiceException;

  @RequestMapping(value = "/terms", method = GET, produces = APPLICATION_JSON_UTF8_VALUE)
  boolean terms() throws ServiceException;

  @RequestMapping(value = "/not-found", method = GET, produces = APPLICATION_JSON_UTF8_VALUE)
  boolean notFound() throws ServiceException;

  @RequestMapping(value = "/reference", method = GET, produces = APPLICATION_JSON_UTF8_VALUE)
  boolean reference() throws ServiceException;

  @RequestMapping(value = "/reference-jpa", method = GET, produces = APPLICATION_JSON_UTF8_VALUE)
  boolean referenceJpa() throws ServiceException;

  @RequestMapping(value = "/runtime", method = GET, produces = APPLICATION_JSON_UTF8_VALUE)
  boolean runtime() throws ServiceException;

  @RequestMapping(value = "/abstract", method = GET, produces = APPLICATION_JSON_UTF8_VALUE)
  boolean abstracts() throws AbstractException;

  @RequestMapping(value = "/illegal-state", method = GET, produces = APPLICATION_JSON_UTF8_VALUE)
  boolean illegalState() throws ServiceException;

  @RequestMapping(value = "/illegal-valid", method = POST, produces = APPLICATION_JSON_UTF8_VALUE)
  boolean illegalValid(@Nonnull @Valid @RequestBody SampleBo sampleBo) throws ServiceException;

  @RequestMapping(value = "/illegal-argument", method = GET, produces = APPLICATION_JSON_UTF8_VALUE)
  boolean illegalArgument(@Nonnull @RequestParam("phone") String phone) throws ServiceException;

  @RequestMapping(value = "/illegal-group", method = POST, produces = APPLICATION_JSON_UTF8_VALUE)
  boolean illegalGroup(
      @Nonnull @Validated(Group.Create.class) @RequestBody SampleGroupBo sampleGroupBo)
      throws ServiceException;
}
