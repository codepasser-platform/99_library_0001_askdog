// package com.stock.capital.common.web.exception;
//
// import static com.stock.capital.web.advice.AccessException.Error.ACCOUNT_EXPIRED;
// import static com.stock.capital.web.advice.AccessException.Error.ACCOUNT_LOCKED;
// import static com.stock.capital.web.advice.AccessException.Error.AUTH_FAILED;
// import static com.stock.capital.web.advice.AccessException.Error.AUTH_REQUIRED;
// import static com.stock.capital.web.advice.AccessException.Error.BAD_CREDENTIALS;
// import static com.stock.capital.web.advice.AccessException.Error.CREDENTIALS_EXPIRED;
// import static com.stock.capital.web.advice.AccessException.Error.DENIED;
// import static com.stock.capital.web.advice.AccessException.Error.USER_NOT_FOUND;
//
// import com.stock.capital.common.exception.AbstractException;
// import com.stock.capital.common.exception.Message;
// import java.util.HashMap;
// import java.util.Map;
// import org.springframework.security.access.AccessDeniedException;
// import org.springframework.security.authentication.AccountExpiredException;
// import org.springframework.security.authentication.BadCredentialsException;
// import org.springframework.security.authentication.CredentialsExpiredException;
// import org.springframework.security.authentication.InsufficientAuthenticationException;
// import org.springframework.security.authentication.LockedException;
// import org.springframework.security.core.AuthenticationException;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
//
/// **
// * [AccessException].
// *
// * <p>TODO spring security
// *
// * @author Joker.Cheng.
// * @version 0.0.1
// */
// public class AccessException extends AbstractException {
//
//  private static final long serialVersionUID = 5147201406297373117L;
//
//  public static final Map<String, Error> codeMap = new HashMap<>();
//
//  public enum Error {
//    DENIED,
//    AUTH_REQUIRED,
//    AUTH_FAILED,
//    BAD_CREDENTIALS,
//    ACCOUNT_LOCKED,
//    CREDENTIALS_EXPIRED,
//    ACCOUNT_EXPIRED,
//    USER_NOT_FOUND
//  }
//
//  static {
//    codeMap.put(AccessDeniedException.class.getTypeName(), DENIED);
//    codeMap.put(BadCredentialsException.class.getTypeName(), BAD_CREDENTIALS);
//    codeMap.put(LockedException.class.getTypeName(), ACCOUNT_LOCKED);
//    codeMap.put(CredentialsExpiredException.class.getTypeName(), CREDENTIALS_EXPIRED);
//    codeMap.put(AccountExpiredException.class.getTypeName(), ACCOUNT_EXPIRED);
//    codeMap.put(UsernameNotFoundException.class.getTypeName(), USER_NOT_FOUND);
//    codeMap.put(InsufficientAuthenticationException.class.getTypeName(), AUTH_REQUIRED);
//  }
//
//  public AccessException() {
//    super(DENIED);
//  }
//
//  public AccessException(Message message) {
//    super(message);
//    setCode(Error.valueOf(message.getPartial()));
//  }
//
//  public AccessException(AccessDeniedException exception) {
//    super(DENIED, exception);
//    setCodeValue(exception.getClass().getTypeName());
//  }
//
//  public AccessException(AuthenticationException exception) {
//    super(codeMap.getOrDefault(exception.getClass().getTypeName(), AUTH_FAILED), exception);
//  }
//
//  @Override
//  protected String messageResourceBaseName() {
//    return "exception.web";
//  }
//
//  @Override
//  protected String moduleName() {
//    return "WEB_ACCESS";
//  }
// }
