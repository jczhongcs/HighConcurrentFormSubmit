package com.high_con.grad.controller;

import com.high_con.grad.result.CodeMsg;
import com.high_con.grad.result.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/showCourseTable")
@Controller
public class ShowCourseTableController {

    @RequestMapping("/getCourseTable")
    public Result<Integer> showCourseTable(){



        return Result.success(1);
    }


}
