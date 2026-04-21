package com.xaw.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@RestController
public  class  TestController{
    @GetMapping("hello")
    public  void  hello(){
        try  (Entry entry = SphU.entry("Hello")){
            System.out.println( "少年强则国强！");//被保护的资源
        } catch  (BlockException e) {
            e.printStackTrace();
            System.out.println( "系统繁忙，请稍候");//被限流或者降级的处理
        }
    }
    @PostConstruct
    public  void  initFlowRules(){
        //1.创建存放流控规则的集合
        List<FlowRule> rules = new ArrayList<FlowRule>();
        //2.创建流控规则
        FlowRule rule = new  FlowRule();
        //设置流控规则针对的资源
        rule.setResource("Hello");
        //设置流控规则类型,RuleConstant.FLOW_GRADE_QPS类型
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        //设置QPS每秒最多只能通过的请求个数
        rule.setCount(2);
        //将流控规则添加到集合中
        rules.add(rule);
        //3.加载流控规则
        FlowRuleManager.loadRules(rules);
    }

}

