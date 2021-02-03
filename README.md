# MyFADemo
harmony developer 的代码，实例是developer.harmonyos.com ，PA端（Internal Ability方式）章节。
新建device：phone，template：js
后期在config.json的deviceType里添加tv
index.js 分别调用Ability或Internal Ability
action.abilityName = 'com.example.myfademo.ComputeServiceAbility'; //Ability
action.abilityName = 'com.example.myfademo.ComputeInternalAbility'; //Internal Ability

HiAceInternalAbility 自己理解为，build的时候读取config.json里面的HiAceInternalAbility，通过HiAceInternalAbility注册ComputeInternalAbility


