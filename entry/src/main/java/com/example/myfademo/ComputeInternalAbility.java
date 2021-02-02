package com.example.myfademo;

import ohos.rpc.IRemoteObject;
import ohos.ace.ability.AceInternalAbility;
import ohos.app.AbilityContext;
import ohos.rpc.MessageOption;
import ohos.rpc.MessageParcel;
import ohos.rpc.RemoteException;
import ohos.utils.zson.ZSONObject;

import java.util.HashMap;
import java.util.Map;

public class ComputeInternalAbility extends AceInternalAbility  {
    private static final String TAG = ComputeInternalAbility.class.getSimpleName();
    private static final String BUNDLE_NAME = "com.example.myfademo";
    private static final String ABILITY_NAME = "com.example.myfademo.ComputeInternalAbility";
    private static final int ERROR = -1;
    private static final int SUCCESS = 0;
    private static final int PLUS = 1001;

    private static ComputeInternalAbility instance;
    private AbilityContext abilityContext;

    // 如果多个Ability实例都需要注册当前InternalAbility实例，
    // 需要更改构造函数，设定自己的bundleName和abilityName
    public ComputeInternalAbility() {
        super(BUNDLE_NAME, ABILITY_NAME);
    }
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
                // 返回结果当前仅支持String，对于复杂结构可以序列化为ZSON字符串上报
                Map<String, Object> zsonResult = new HashMap<String, Object>();
                zsonResult.put("code", SUCCESS);
                zsonResult.put("abilityResult", "ComputeInternalAbility");
                // SYNC
                if (option.getFlags() == MessageOption.TF_SYNC) {
                    reply.writeString(ZSONObject.toZSONString(zsonResult));
                } else {
                    // ASYNC
                    MessageParcel responseData = MessageParcel.obtain();
                    responseData.writeString(ZSONObject.toZSONString(zsonResult));
                    IRemoteObject remoteReply = reply.readRemoteObject();
                    try {
                        remoteReply.sendRequest(0, responseData, MessageParcel.obtain(), new MessageOption());
                    } catch (RemoteException exception) {
                        return false;
                    } finally {
                        responseData.reclaim();
                        MessageParcel.obtain().reclaim();
                    }
                }
                break;
            }
            default: {
                reply.writeString("service not defined");
                return false;
            }
        }
        return true;
    }

    // Internal ability registration.
    public static void register(AbilityContext abilityContext) {
        instance = new ComputeInternalAbility();
        instance.onRegister(abilityContext);
    }

    private void onRegister(AbilityContext abilityContext) {
        this.abilityContext = abilityContext;
        this.setInternalAbilityHandler((code, data, reply, option) -> {
            return this.onRemoteRequest(code, data, reply, option);
        });
    }

    // Internal ability deregistration.
    public static void deregister() {
        instance.onDeregister();
    }

    private void onDeregister() {
        abilityContext = null;
        this.setInternalAbilityHandler(null);
    }
}