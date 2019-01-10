package com.stock.capital.ipo.sample;

import static com.google.common.base.Preconditions.checkArgument;
import static com.stock.capital.common.exception.CommonException.Error.QR_CODE_GENERATE_FAILED;
import static com.stock.capital.common.model.RegexPattern.REGEX_PHONE;
import static com.stock.capital.common.service.exception.ConflictException.Error.USER_PHONE;
import static com.stock.capital.common.service.exception.ForbiddenException.Error.PERMISSIONS;
import static com.stock.capital.common.service.exception.IllegalArgumentException.Error.INVALID_IDENTIFYING_CODE;
import static com.stock.capital.common.service.exception.IllegalTermsException.Error.IDENTIFYING_CODE;
import static com.stock.capital.common.service.exception.NotFoundException.Error.USER;
import static com.stock.capital.common.service.exception.ReferenceException.Error.FAILED;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import com.stock.capital.common.exception.AbstractException;
import com.stock.capital.common.exception.CommonException;
import com.stock.capital.common.model.validation.Group;
import com.stock.capital.common.service.exception.ConflictException;
import com.stock.capital.common.service.exception.ForbiddenException;
import com.stock.capital.common.service.exception.IllegalArgumentException;
import com.stock.capital.common.service.exception.IllegalTermsException;
import com.stock.capital.common.service.exception.NotFoundException;
import com.stock.capital.common.service.exception.ReferenceException;
import com.stock.capital.common.service.exception.ServiceException;
import com.stock.capital.ipo.service.sample.bo.SampleBo;
import com.stock.capital.ipo.service.sample.bo.SampleGroupBo;
import javax.annotation.Nonnull;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.validation.annotation.Validated;
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
@RequestMapping("/console/sample/uncaught")
public class SampleUncaughtExceptionApi {

  @RequestMapping(value = "/conflict", method = GET, produces = APPLICATION_JSON_UTF8_VALUE)
  public boolean conflict() throws ServiceException {
    if (true) {
      throw new ConflictException(USER_PHONE);
    }
    return false;
  }

  @RequestMapping(value = "/forbidden", method = GET, produces = APPLICATION_JSON_UTF8_VALUE)
  public boolean forbidden() throws ServiceException {
    if (true) {
      throw new ForbiddenException(PERMISSIONS);
    }
    return false;
  }

  @RequestMapping(value = "/illegal", method = GET, produces = APPLICATION_JSON_UTF8_VALUE)
  public boolean illegal() throws ServiceException {
    if (true) {
      throw new IllegalArgumentException(INVALID_IDENTIFYING_CODE);
    }
    return false;
  }

  @RequestMapping(value = "/terms", method = GET, produces = APPLICATION_JSON_UTF8_VALUE)
  public boolean terms() throws ServiceException {
    if (true) {
      throw new IllegalTermsException(IDENTIFYING_CODE);
    }
    return false;
  }

  @RequestMapping(value = "/not-found", method = GET, produces = APPLICATION_JSON_UTF8_VALUE)
  public boolean notFound() throws ServiceException {
    if (true) {
      throw new NotFoundException(USER);
    }
    return false;
  }

  @RequestMapping(value = "/reference", method = GET, produces = APPLICATION_JSON_UTF8_VALUE)
  public boolean reference() throws ServiceException {
    if (true) {
      throw new ReferenceException(FAILED);
    }
    return false;
  }

  @RequestMapping(value = "/reference-jpa", method = GET, produces = APPLICATION_JSON_UTF8_VALUE)
  public boolean referenceJpa() throws ServiceException {
    if (true) {
      throw new JpaObjectRetrievalFailureException(new EntityNotFoundException("user not found"));
    }
    return false;
  }

  @RequestMapping(value = "/runtime", method = GET, produces = APPLICATION_JSON_UTF8_VALUE)
  public boolean runtime() throws ServiceException {
    if (true) {
      throw new RuntimeException("mock runtime exception");
    }
    return false;
  }

  @RequestMapping(value = "/abstract", method = GET, produces = APPLICATION_JSON_UTF8_VALUE)
  public boolean abstracts() throws AbstractException {
    if (true) {
      throw new CommonException(QR_CODE_GENERATE_FAILED);
    }
    return false;
  }

  @RequestMapping(value = "/illegal-state", method = GET, produces = APPLICATION_JSON_UTF8_VALUE)
  public boolean illegalState() throws ServiceException {
    if (true) {
      throw new IllegalStateException("invalid state");
    }
    return false;
  }

  @RequestMapping(value = "/illegal-valid", method = POST, produces = APPLICATION_JSON_UTF8_VALUE)
  public boolean illegalValid(@Nonnull @Valid @RequestBody SampleBo sampleBo)
      throws ServiceException {
    return false;
  }

  @RequestMapping(value = "/illegal-argument", method = GET, produces = APPLICATION_JSON_UTF8_VALUE)
  public boolean illegalArgument(@Nonnull @RequestParam("phone") String phone)
      throws ServiceException {
    checkArgument(phone.matches(REGEX_PHONE), "invalid phone number");
    return false;
  }

  @RequestMapping(value = "/illegal-group", method = POST, produces = APPLICATION_JSON_UTF8_VALUE)
  public boolean illegalGroup(
      @Nonnull @Validated(Group.Create.class) @RequestBody SampleGroupBo sampleGroupBo)
      throws ServiceException {
    return false;
  }
}
