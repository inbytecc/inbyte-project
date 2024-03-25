# README
通用 - 邮箱组件

## 客户端配置
```
spring:
  mail:
    host: smtp.163.com
    username: inbyte@163.com
    password: LGYKXLIGSHWDCYAC
    default-encoding: UTF-8
    
inbyte:
  merchant:
    mctName: vipmmt
    mctNo: 1
  component:
    user:
      allowRegisterNotVerified: true
      avatars:
        - https://thirdwx.qlogo.cn/mmopen/vi_32/POgEwh4mIHO4nibH0KlMECNjjGxQUq24ZEaGT4poC6icRiccVGKSyXwibcPq4BWmiaIGuG1icwxaQX6grC9VemZoJ8rg/132
      email:
        forget-pwd-title: VIPMMT-找回密码
        forgetPwdContent: "亲爱的用户，您好！
                    您本次邮箱重置验证码为：%s
                    请勿将验证码透露给其他人
                    感谢您的访问，祝您使用愉快"
        registerTitle: VIPMMT-账号注册
        registerContent: "亲爱的用户，您好！
                    您本次账号注册验证码为：%s
                    请勿将验证码透露给其他人
                    感谢您的访问，祝您使用愉快"


```