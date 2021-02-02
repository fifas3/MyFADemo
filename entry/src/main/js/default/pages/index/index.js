//Ability：拥有独立的 Ability 生命周期，FA
//使用远端进程通信拉起并请求 PA 服务，适用于基本服务供多
//FA 调用或者服务在后台独立运行的场景。
//Internal Ability：与 FA 共进程，
//采用内部函数调用的方式和 FA 进行通信，适用于对服务响应时延要求较高的场景。
//该方式下 PA 不支持其他 FA 访问调用。

//异步的时候需要添加下面两行代码，提示cannot find module也可以 start
const globalRef = Object.getPrototypeOf(global) || global;
globalRef.regeneratorRuntime = require('@babel/runtime/regenerator');
// end
// abilityType: 0-Ability; 1-Internal Ability
const ABILITY_TYPE_EXTERNAL = 0;
const ABILITY_TYPE_INTERNAL = 1;
// syncOption(Optional, default sync): 0-Sync; 1-Async
const ACTION_SYNC = 0;
const ACTION_ASYNC = 1;
const ACTION_MESSAGE_CODE_PLUS = 1001;
export default {
    data: {
        title: "",
        todolist: [],
    },
    onInit() {
//        this.title = this.$t('strings.world');
        this.plus()
    },
    clickTest() {
        this.title = "clickTest";
    },
    plus:async function() {
        var actionData = {};
        actionData.firstNum = 1024;
        actionData.secondNum = 2048;

        var action = {};
        //表示包名称。如果在Intent中同时指定了BundleName和AbilityName，则Intent可以直接匹配到指定的Ability。
        action.bundleName = 'com.example.myfademo';
        //表示待启动的Ability名称。如果在Intent中同时指定了BundleName和AbilityName，则Intent可以直接匹配到指定的Ability。
//        action.abilityName = 'com.example.myfademo.ComputeServiceAbility'; //Ability
        action.abilityName = 'com.example.myfademo.ComputeInternalAbility'; //Internal Ability
        action.messageCode = 1001;
        action.data = actionData;
//        action.abilityType = ABILITY_TYPE_EXTERNAL; //abilityType（0：Ability; 1：Internal Ability）
        action.abilityType = ABILITY_TYPE_INTERNAL; //abilityType（0：Ability; 1：Internal Ability）
        action.syncOption = ACTION_SYNC;
        this.title = action.syncOption;
        var result = await FeatureAbility.callAbility(action);
        this.title =result ;
        for ( let x in result)
        {
            this.title = x +"<br />"

        }
//        result.then(r=>{
//            this.title = r+"<br />" ;
//        })
        var ret = JSON.parse(result);
        if (ret.code == 0) {
            this.title = JSON.stringify(ret.abilityResult)
            console.info('plus result is:' + JSON.stringify(ret.abilityResult));
        } else {
            this.title = JSON.stringify(ret.code)
            console.error('plus error code:' + JSON.stringify(ret.code));
        }
    },
    max(){

    }
}