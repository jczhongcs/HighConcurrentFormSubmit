package com.high_con.grad.result;

public class CodeMsg {
    private int code;
    private String msg;

    public CodeMsg fillA(Object args){
        int code = this.code;
        String message = String.format(this.msg,args);
        return new CodeMsg(code,message);
    }

    public static CodeMsg SUCCESS = new CodeMsg(0,"success");
    public static CodeMsg SERVER_ERR = new CodeMsg(400100,"SERVER IS IN EXCEPTION");

    public static CodeMsg BIND_ERR = new CodeMsg(400101,"There is exception in here");


    public static CodeMsg SESSION_ERR = new CodeMsg(400210,"SESSION MISSING");

    public static CodeMsg PWD_ERR = new CodeMsg(400211,"YOUR PASSWORD IS EMPTY OR SPACE ONLY");

    public static CodeMsg PHONE_ERR = new CodeMsg(400212,"YOUR PHONE IS NOT FIT");

    public static CodeMsg USER_NOTADMINORSTU = new CodeMsg(400220,"登录角色错误");


    public static CodeMsg USER_NOTEXISTS = new CodeMsg(400213,"NOT FOUND USER");

    public static CodeMsg PASSWORD_ERR = new CodeMsg(400214,"PASSWORD_ERROR");

    public static CodeMsg STOCK_EMP = new CodeMsg(400500,"SELL OUT");

    public static CodeMsg REP_KILL = new CodeMsg(400501,"REP_Kill");

    public static CodeMsg REP_SEL = new CodeMsg(400502,"不能重复选课");

    public static CodeMsg REP_SUB = new CodeMsg(400503,"不能重复提交相同内容");

    public static CodeMsg ORDER_NOT_FOUND = new CodeMsg(400601,"订单不存在");

    public static CodeMsg USER_CREATED_FAILED = new CodeMsg(400800,"请至少填写id，昵称，密码");

    public static CodeMsg COURSE_CREATED_FAILED = new CodeMsg(400804,"课程创建失败");
    private CodeMsg(int code, String msg) {
        this.code=code;
        this.msg=msg;
    }

    public int getCode() {
        return code;
    }

   /* public void setCode(int code) {
        this.code = code;
    }*/

    public String getMsg() {
        return msg;
    }

 /*   public void setMsg(String msg) {
        this.msg = msg;
    }*/
}
