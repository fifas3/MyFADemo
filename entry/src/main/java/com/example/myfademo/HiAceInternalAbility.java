package com.example.myfademo;

import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class HiAceInternalAbility extends Ability {

    @Override
    public void onStart(Intent intent) {
        // 注册, 如果需要在Page初始化(onInit或之前)时调用AceInternalAbility的能力，注册操作需要在super.onStart之前进行
        ComputeInternalAbility.register(this);
        super.onStart(intent);
    }

    @Override
    public void onStop() {
        // 去注册
        ComputeInternalAbility.deregister();
        super.onStop();
    }
}