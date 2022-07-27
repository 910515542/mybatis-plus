package com.atguigu.mybatisplus;

import com.atguigu.mybatisplus.entity.User;
import com.atguigu.mybatisplus.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class MybatisPlusApplicationTests {

    @Autowired
    private UserMapper userMapper;

    //添加和查询
    @Test
    void contextLoads() {
        User user1 = new User();
        user1.setAge(23);
        user1.setId(22L);
        user1.setEmail("910@qq.com");
        user1.setName("dada");
        int res1 = userMapper.insert(user1);
        List<User> userList = userList = userMapper.selectList(null);
        for(User user:userList){
            System.out.println(user);
        }
    }
    //修改
    @Test
    void update() {
        User user1 = userMapper.selectById(6L);
        user1.setName("xiaoxuan");
//        user1.setVersion(2);
        int res = userMapper.updateById(user1);
        List<User> userList = userList = userMapper.selectList(null);
        for(User user:userList){
            System.out.println(user);
        }
    }

    //测试“乐观锁”
    @Test
    void locker() {
        User user = userMapper.selectById(11L);
        user.setEmail("910@qq.com");
        user.setName("locker");
        user.setAge(11);
        int res = userMapper.updateById(user);
        System.out.println(res == 1?"更新成功":"更新失败");
    }
    //批量id查询
    @Test
    void selectByBatchId() {
        List<Long> list = new ArrayList<>();
        list.add(1L);
        list.add(2L);
        list.add(3L);
        List<User> userList = userMapper.selectBatchIds(list);
        for(User user:userList){
            System.out.println(user);
        }
    }
    //使用map实现简单条件查询
    @Test
    void selectByMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "Jone");
        map.put("age", 18);
        List<User> userList = userMapper.selectByMap(map);
        for(User user:userList){
            System.out.println(user);
        }
    }
    //分页插件进行分页操作
    @Test
    void selectByPage() {
        //得到page对象，给出当前页、每页记录数两个参数
        Page<User> page = new Page<>(1, 5);
        //调用对应方法，执行完后，对应数据会保存的page对象里
        //Preparing: SELECT id,name,age,email,create_time,update_time,version FROM user LIMIT 0,5
        userMapper.selectPage(page, null);
        //执行完，对page对象进行操作
        System.out.println(page.getCurrent());//当前页
        System.out.println(page.getSize());//每页记录数
        System.out.println(page.getTotal());//总记录数
        System.out.println(page.getPages());//总页数
        System.out.println(page.hasNext());//是否有下一页
        System.out.println(page.hasPrevious());//是否有上一页
        List<User> userList = page.getRecords();//查询到的所有记录
        for(User user:userList){
            System.out.println(user);
        }
    }
    /*********删除与批量删除（逻辑删除）*****
     * usrMapper.deleteById(id);
     * usrMapper.deleteBatchIds(List);
     */
    @Test
    void delete() {
        int res = userMapper.deleteById(1L);
        System.out.println(res);
        List<User> userList = userList = userMapper.selectList(null);
        for(User user:userList){
            System.out.println(user);
        }
    }
    /**
     * 复杂条件查询：QueryWrapper
     */
    @Test
    void query() {
        //创建queryWrapper对象
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //通过对象方法设置条件

        /**
         * ge:大于 gt：大于等于 le：小于 lt：小于等于
         */
//        queryWrapper.ge("age", 10);//设置age大于10的条件
        /**
         * eq:等于 nq：不等于
         */
//        queryWrapper.eq("name", "xiaoxuan");//name等于“xiaoxuan”
        /**
         * between
         */
//        queryWrapper.between("age", 10, 20);
        /**
         * like
         */
//        queryWrapper.like("name", "x");
        /**
         * 降序orderByDesc
         */
//        queryWrapper.orderByDesc("age");
        /**
         * 在sql语句最后拼接语句last
         */
//        queryWrapper.last("limit 1");//在执行sql最后加上limit 1
        // ... FROM user  WHERE deleted=0 limit 1
        /**
         * select指定要查询的列
         */
        queryWrapper.select("name", "age");//只查出name和age两列
        //select name age from user ...
        List<User> userList = userList = userMapper.selectList(queryWrapper);
        for(User user:userList){
            System.out.println(user);
        }

    }
}
