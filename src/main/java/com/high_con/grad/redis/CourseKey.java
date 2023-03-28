package com.high_con.grad.redis;

public class CourseKey extends Base_Pr{

    public CourseKey(int expire, String pre) {
        super(expire,pre);
    }

    public static CourseKey getCL = new CourseKey(45,"cl");

    public static CourseKey getCT = new CourseKey(45,"ct");

    public static CourseKey getCourseStock = new CourseKey(0,"cs");
}
