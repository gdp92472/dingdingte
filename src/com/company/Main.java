package com.company;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.request.OapiUserGetByMobileRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.dingtalk.api.response.OapiUserGetByMobileResponse;

import java.util.Random;


public class Main {
    public static void main(String[] args) {
        try {
            //调用基本信息
            long AgentId = 779892760L;
            String Appkey = "dinge4kjsg0ixj63betj";
            String Appsecret = "aYhRF9lnbX7wdfaIiP4jVuOGCYEpzzcoXvarKiznwjRC7yw88die1G9sLBRfdcGa";
            String MobileNum = "18272192060";
            //获取token
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/gettoken");
            OapiGettokenRequest req = new OapiGettokenRequest();
            req.setAppkey(Appkey);
            req.setAppsecret(Appsecret);
            req.setHttpMethod("GET");
            OapiGettokenResponse tokenrsp = client.execute(req);
            String accesstoken = tokenrsp.getAccessToken();

            //通过手机号获取UserID
            DingTalkClient usercl = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/get_by_mobile");
            OapiUserGetByMobileRequest userreq = new OapiUserGetByMobileRequest();
            userreq.setMobile(MobileNum);
            userreq.setHttpMethod("GET");
            OapiUserGetByMobileResponse userrsp = usercl.execute(userreq, accesstoken);
            String userid = userrsp.getUserid();


            //发送消息
            DingTalkClient msgcl = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2");
            OapiMessageCorpconversationAsyncsendV2Request req1 = new OapiMessageCorpconversationAsyncsendV2Request();
            req1.setAgentId(AgentId);
            req1.setUseridList(userid);
            req1.setToAllUser(false);
            OapiMessageCorpconversationAsyncsendV2Request.Msg obj1 = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
            obj1.setMsgtype("text");
            OapiMessageCorpconversationAsyncsendV2Request.Text obj2 = new OapiMessageCorpconversationAsyncsendV2Request.Text();
            //消息不能重复发送
            Random r = new Random();
            int number = r.nextInt(50);
            obj2.setContent("出库通知" + number);
            obj1.setText(obj2);
            req1.setMsg(obj1);
            OapiMessageCorpconversationAsyncsendV2Response rsp = msgcl.execute(req1, accesstoken);
            System.out.println(rsp.getBody());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

