package com.example.myfademo;

import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;
import ohos.rpc.IRemoteObject;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

import com.example.myfademo.RequestParam;
//import com.example.myfademo.utils.PlayMusicUtil;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;
import ohos.app.Context;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.rpc.*;
import ohos.utils.zson.ZSONObject;
import java.util.HashMap;
import java.util.Map;

public class PlayAbility extends Ability {
    private static final String TAG = "PlayAbility";
    static final HiLogLabel label = new HiLogLabel(HiLog.LOG_APP, 1, "MY_TAG");
    private static final int ERROR = -1;
    private static final int SUCCESS = 0;
    private static final int PLUS = 1001;
    private PlayRemote remote;

    @Override
    protected void onStart(Intent intent) {
        System.out.println("IRemoteObject0");
        super.onStart(intent);
    }

    @Override
    protected IRemoteObject onConnect(Intent intent) {
        super.onConnect(intent);
        Context c = getContext();
        System.out.println("IRemoteObject1");
        remote = new PlayRemote();
        return remote.asObject();
    }

    class PlayRemote extends RemoteObject implements IRemoteBroker {

        public PlayRemote() {
            super("PlayRemote 666");
        }

        @Override
        public boolean onRemoteRequest(int code, MessageParcel data, MessageParcel reply, MessageOption option) throws RemoteException {
            switch (code) {
                case PLUS: {

                    String zsonStr = data.readString();
                    RequestParam param = ZSONObject.stringToClass(zsonStr, RequestParam.class);

                    // 返回结果仅支持可序列化的Object类型
                    Map<String, Object> zsonResult = new HashMap<String, Object>();
                    zsonResult.put("code", SUCCESS);
//                    zsonResult.put("abilityResult", param.getFirstNum() + param.getSecondNum());
                    zsonResult.put("abilityResult","firstNum");
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