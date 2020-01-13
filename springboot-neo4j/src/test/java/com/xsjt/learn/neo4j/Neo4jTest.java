package com.xsjt.learn.neo4j;

import com.alibaba.fastjson.JSON;
import com.xsjt.learn.neo4j.dao.MemberInvitRepository;
import com.xsjt.learn.neo4j.model.MemberInvit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Set;

/**
 * 测试类
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class Neo4jTest {

    @Autowired
    private MemberInvitRepository memberInvitRepository;

    /**
     * 保存官方用户，假设叫做 官方
     */
    @Test
    public void testSaveFirst() {
        MemberInvit official = MemberInvit.builder().level(0).mId(1L).name("官方").build();
        MemberInvit result = memberInvitRepository.save(official);
        System.out.println("result=" + JSON.toJSONString(result));
    }


    /**
     * 根据节点id 查询官方用户
     */
    @Test
    public void testFindByMId() {
        MemberInvit memberInvit = memberInvitRepository.findBymId(1L);
        System.out.println(JSON.toJSONString(memberInvit));
    }

    /**
     * 删除节点
     */
    @Test
    public void testDelete() {
        MemberInvit official = memberInvitRepository.findBymId(2000L);
        memberInvitRepository.delete(official);
    }

    /**
     * 官方绑定隔壁老王
     */
    @Test
    public void testBindWang() {
        // 创建隔壁老王节点
        MemberInvit memberInvit = MemberInvit.builder().mId(2000L).level(0).name("隔壁老王").build();
        // 官方节点
        MemberInvit official = memberInvitRepository.findBymId(1L);
        // 官方发展了隔壁老王
        official.develop(memberInvit);

        MemberInvit result = memberInvitRepository.save(official);
        System.out.println("result=" + JSON.toJSONString(result));
    }

    /**
     * SpringBoot+Neo4j在社交电商中，讲述你是怎么被绑定为下线的
     * 隔壁老王绑定小张、小李、小吴、小周
     */
    @Test
    public void testWangBind() {
        // 隔壁老王节点
        MemberInvit laowang = memberInvitRepository.findBymId(2000L);
        // 小张节点
        MemberInvit xiaozhang = MemberInvit.builder().mId(2001L).level(0).name("小张").build();
        laowang.develop(xiaozhang);
        // 小李节点
        MemberInvit xiaoli = MemberInvit.builder().mId(2002L).level(0).name("小李").build();
        laowang.develop(xiaoli);
        // 小吴节点
        MemberInvit xiaowu = MemberInvit.builder().mId(2003L).level(0).name("小吴").build();
        laowang.develop(xiaowu);
        // 小周节点
        MemberInvit xiaozhou = MemberInvit.builder().mId(2004L).level(0).name("小周").build();
        laowang.develop(xiaozhou);

        MemberInvit result = memberInvitRepository.save(laowang);
        System.out.println("result=" + JSON.toJSONString(result));
    }
}
