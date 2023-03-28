package com.high_con.grad.exception;

import com.high_con.grad.result.CodeMsg;
import com.sun.org.apache.bcel.internal.classfile.Code;

public class GlobeException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    private CodeMsg codeMsg;

    public GlobeException(CodeMsg codeMsg){
        super(codeMsg.toString());
        this.codeMsg = codeMsg;
    }

    public CodeMsg getCodeMsg() {
        return codeMsg;
    }

    public void setCodeMsg(CodeMsg codeMsg) {
        this.codeMsg = codeMsg;
    }
}
