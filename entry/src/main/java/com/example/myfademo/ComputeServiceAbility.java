package com.example.myfademo;

import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;
import ohos.rpc.IRemoteObject;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

// ohos相关接口包
import ohos.rpc.IRemoteBroker;
import ohos.rpc.RemoteObject;
import ohos.rpc.MessageParcel;
import ohos.rpc.MessageOption;
import ohos.utils.zson.ZSONObject;

import java.util.HashMap;
import java.util.Map;

public class ComputeServiceAbility extends Ability {
    private static final String TAG = "ComputeServiceAbility";
    private MyRemote remote = new MyRemote();
    // FA在请求PA服务时会调用AbilityconnectAbility连接PA，
    // 连接成功后，需要在onConnect返回一个remote对象，供FA向PA发送消息
    @Override
    protected IRemoteObject onConnect(Intent intent) {
        //1 onConnect 建立链接
        super.onConnect(intent);
        return remote.asObject();
    }
    class MyRemote extends RemoteObject implements IRemoteBroker {
        private static final int ERROR = -1;
        private static final int SUCCESS = 0;
        private static final int PLUS = 1001;

        MyRemote() {
            super("MyService_MyRemote");
        }

        @Override
        //code Js端发送的业务请求编码（PA端定义需要与Js端业务请求码保持一致）
        //data  Js端发送的MessageParcel对象，当前仅支持json字符串格式。
        //reply 将本地业务响应返回给Js端的MessageParcel对象，当前仅支持String格式。
        //option 指示请求是同步还是异步方式。
        //onRemoteRequest()中实现PA提供的业务逻辑，不同的业务通过业务码来区分
        public boolean onRemoteRequest(int code, MessageParcel data, MessageParcel reply, MessageOption option) {
            switch (code) {
                case PLUS: {
                    String zsonStr = data.readString();
                    RequestParam param = ZSONObject.stringToClass(zsonStr, RequestParam.class);

                    // 返回结果仅支持可序列化的Object类型
                    Map<String, Object> zsonResult = new HashMap<String, Object>();
                    zsonResult.put("code", SUCCESS);
//                    zsonResult.put("abilityResult", param.getFirstNum() + param.getSecondNum());
                    zsonResult.put("abilityResult", "11成功123222ServiceAbility返回数据");
                    reply.writeString(ZSONObject.toZSONString(zsonResult));
                    break;
                }
                default: {
                    reply.writeString("service not defined");
                    return false;
                }
            }
            return true;
        }

        @Override
        public IRemoteObject asObject() {
            return this;
        }
    }
}