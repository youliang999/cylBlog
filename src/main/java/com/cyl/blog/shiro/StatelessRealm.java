package com.cyl.blog.shiro;

import com.cyl.blog.entity.User;
import com.cyl.blog.helper.ApplicationContextUtil;
import com.cyl.blog.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.*;

/**
 * 此处要实现Authorizer
 * 
 * @author zhou
 * 
 */
public class StatelessRealm extends AuthorizingRealm{
  static final Map<String, Set<String>> permissions;

  static{
    permissions = new HashMap<>();
    permissions.put("admin", new HashSet<>(Arrays.asList("admin:*:*")));
    permissions.put("editor",
        new HashSet<>(Arrays.asList("admin:post:*", "admin:category:*", "admin:upload:insert", "admin:user:edit")));
    permissions.put("contributor", new HashSet<>(Arrays.asList("admin:user:edit", "admin:post:insert")));
  }
  
  public StatelessRealm(){
    super();
    setCachingEnabled(false);
  }

  @Override
  public String getName(){
    return "StatelessRelam";
  }
  
  @Override
  public boolean supports(AuthenticationToken token) {
  //仅支持StatelessToken类型的Token
  return token instanceof StatelessToken;
  }

  /**
   * 获取授权信息
   */
  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals){
    UserService userService = ApplicationContextUtil.getBean(UserService.class);
    String userid = (String) principals.getPrimaryPrincipal();
    User user = userService.loadById(userid);

    SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
    authorizationInfo.setRoles(new HashSet<>(Arrays.asList(user.getRole())));
    authorizationInfo.setStringPermissions(permissions.get(user.getRole()));
    return authorizationInfo;
  }

  /**
   * 获取身份验证信息
   */
  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException{
    UserService userService = ApplicationContextUtil.getBean(UserService.class);
    String userid = (String) token.getPrincipal();
    User user = userService.loadById(userid);
    if(user == null){
      throw new UnknownAccountException();// 没找到帐号
    }
    /*
     * 可使用setCredentialsMatcher设置shiro的密码验证，让shiro用自己的验证(
     * 默认使用CredentialsMatcher进行密码匹配)
     */
    SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user.getId(), user.getPassword(),
        getName());

    return authenticationInfo;
  }

}
