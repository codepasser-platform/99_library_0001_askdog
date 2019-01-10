package com.stock.capital.ipo.dao.repository.redis;

import static com.stock.capital.common.exception.CommonException.Error.IDENTIFYING_CODE_CYCLE_ERROR;

import com.stock.capital.common.business.category.IdentifyingCodeType;
import com.stock.capital.common.exception.CommonException;
import com.stock.capital.common.processor.annotation.InjectLogger;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

/**
 * [IdentifyingCodeRepository].
 *
 * @author Joker.Cheng.
 * @version 0.0.1
 */
@Repository
public class IdentifyingCodeRepository {

  @InjectLogger private Logger logger;

  @Autowired private StringRedisTemplate template;

  // @Autowired private RegistrationIdentifyingCodeSms registrationIdentifyingCodeSms;

  // @Autowired private PasswordIdentifyingCodeSms passwordIdentifyingCodeSms;

  /**
   * 索取认证码.
   *
   * @param phone 电话号码
   * @param identifyingCodeType 认证码类型
   * @param hashValues 认证记录
   * @param timeout 超时时间
   * @param unit 时间单位
   */
  public String claimIdentifyingCode(
      String phone,
      IdentifyingCodeType identifyingCodeType,
      Map<String, String> hashValues,
      long timeout,
      TimeUnit unit)
      throws CommonException {
    if (!isExceedDuration(phone, identifyingCodeType)) {
      throw new CommonException(IDENTIFYING_CODE_CYCLE_ERROR);
    }
    String identifyingCode = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
    resetOwnerIdentifyingCode(phone, identifyingCodeType, timeout, unit, identifyingCode);
    saveIdentifyingCode(phone, identifyingCodeType, hashValues, timeout, unit, identifyingCode);
    // TODO Implementing SMS Features
    if (identifyingCodeType == IdentifyingCodeType.REGISTRATION) {
      // registrationIdentifyingCodeSms.send(phone, identifyingCode);
      logger.info("registrationIdentifyingCodeSms.send(phone, identifyingCode)");
    } else if (identifyingCodeType == IdentifyingCodeType.RECOVER) {
      // passwordIdentifyingCodeSms.send(phone, identifyingCode);
      logger.info("passwordIdentifyingCodeSms.send(phone, identifyingCode);");
    }
    return identifyingCode;
  }

  /**
   * 赎回认证码.
   *
   * @param identifyingCodeType 认证类型
   * @param phone 电话号码
   * @param identifyingCode 认证码
   */
  public void redeemIdentifyingCode(
      IdentifyingCodeType identifyingCodeType, String phone, String identifyingCode) {
    String key = generateIdentifyingCodeKey(identifyingCodeType, phone, identifyingCode);
    if (template.hasKey(key)) {
      HashOperations<String, String, String> operations = template.opsForHash();
      String owner = operations.get(key, "_owner");
      if (owner != null) {
        operations.delete(generateOwnerKey(owner), identifyingCodeType);
      }
      template.delete(key);
    }
  }

  /**
   * 认证码校验.
   *
   * @param identifyingCodeType 认证类型
   * @param phone 电话号码
   * @param identifyingCode 认证码
   * @return 校验结果
   */
  public boolean isIdentifyingCodeValidate(
      IdentifyingCodeType identifyingCodeType, String phone, String identifyingCode) {
    String key = generateIdentifyingCodeKey(identifyingCodeType, phone, identifyingCode);
    if (template.hasKey(key)) {
      Long expire = template.getExpire(key);
      return expire > 0;
    }
    return false;
  }

  /**
   * 认证码获取频率校验(间隔大于一分钟重新分配认证码).
   *
   * @param phone 电话
   * @param identifyingCodeType 认证类型
   * @return 校验结果
   */
  private boolean isExceedDuration(String phone, IdentifyingCodeType identifyingCodeType) {
    HashOperations<String, String, String> hashOperations = template.opsForHash();
    String ownerKey = generateOwnerKey(phone);
    String previousIdentifyingCode = hashOperations.get(ownerKey, identifyingCodeType.toString());

    String key = generateIdentifyingCodeKey(identifyingCodeType, phone, previousIdentifyingCode);
    HashOperations<String, Object, Object> hashOperationsForValidate = template.opsForHash();
    Map<Object, Object> map = hashOperationsForValidate.entries(key);
    if (!map.isEmpty()) {
      long duration = new Date().getTime() - (Long.parseLong(map.get("issue_time").toString()));
      if (duration < 60 * 1000) {
        return false;
      }
    }
    return true;
  }

  /**
   * 重置认证码.
   *
   * @param phone 电话号码
   * @param identifyingCodeType 认证类型
   * @param timeout 超时时间
   * @param unit 时间单位
   * @param identifyingCode 认证码
   */
  private void resetOwnerIdentifyingCode(
      String phone,
      IdentifyingCodeType identifyingCodeType,
      long timeout,
      TimeUnit unit,
      String identifyingCode) {
    HashOperations<String, String, String> hashOperations = template.opsForHash();
    String ownerKey = generateOwnerKey(phone);

    String previousIdentifyingCode = hashOperations.get(ownerKey, identifyingCodeType.toString());
    template.delete(
        generateIdentifyingCodeKey(identifyingCodeType, phone, previousIdentifyingCode));
    hashOperations.put(ownerKey, identifyingCodeType.toString(), identifyingCode);
    template.expire(ownerKey, timeout, unit);
  }

  /**
   * 缓存校验码.
   *
   * @param phone 电话号码
   * @param identifyingCodeType 认证类型
   * @param hashValues 认证记录
   * @param timeout 超时时间
   * @param unit 时间单位
   * @param identifyingCode 认证码
   */
  private void saveIdentifyingCode(
      String phone,
      IdentifyingCodeType identifyingCodeType,
      Map<String, String> hashValues,
      long timeout,
      TimeUnit unit,
      String identifyingCode) {
    String key = generateIdentifyingCodeKey(identifyingCodeType, phone, identifyingCode);
    HashOperations<String, Object, Object> hashOperations = template.opsForHash();
    hashOperations.putAll(key, hashValues);
    hashOperations.put(key, "_owner", phone);
    template.expire(key, timeout, unit);
  }

  /**
   * 生成认证码所有者缓存key.
   *
   * @param phone 电话号
   * @return 认证码所有者缓存key
   */
  private String generateOwnerKey(String phone) {
    return String.format("identifyingCode:owners:%s", phone);
  }

  /**
   * 生成认证码缓存key.
   *
   * @param identifyingCodeType 认证类型
   * @param phone 电话号码
   * @param identifyingCode 认证码
   * @return 认证码缓存key
   */
  private String generateIdentifyingCodeKey(
      IdentifyingCodeType identifyingCodeType, String phone, String identifyingCode) {
    return String.format(
        "identifyingCode:identifyingCodes:%s:%s:%s", identifyingCodeType, phone, identifyingCode);
  }
}
