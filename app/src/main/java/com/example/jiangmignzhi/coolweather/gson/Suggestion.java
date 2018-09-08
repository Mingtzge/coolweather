package com.example.jiangmignzhi.coolweather.gson;

/*
* 生活建议
* "suggestion":{"comf":{"type":"comf","brf":"舒适","txt":"今天夜间不太热也不太冷，风力不大，相信您在这样的天气
* 条件下，应会感到比较清爽和舒适。"},
* "sport":{"type":"sport","brf":"较不宜","txt":"有降水，且风力较强，气压较低，推荐您在室内进行低强度运动；
* 若坚持户外运动，须注意避雨防风。"},
* "cw":{"type":"cw","brf":"不宜","txt":"不宜洗车，未来24小时内有雨，如果在此期间洗车，雨水和路上的泥水可能
* 会再次弄脏您的爱车。"}}
* */

import com.google.gson.annotations.SerializedName;

public class Suggestion {
    @SerializedName("comf")
    public Comfort comfort;
    public class Comfort{
        @SerializedName("brf")
        public String suitable;
        @SerializedName("txt")
        public String info;
    }

    public Sport sport;
    public class Sport{
        @SerializedName("brf")
        public String suitable;
        @SerializedName("txt")
        public String info;
    }

    @SerializedName("cw")
    public CarWash carWash;
    public class CarWash{
        @SerializedName("brf")
        public String suitable;
        @SerializedName("txt")
        public String info;
    }
}
