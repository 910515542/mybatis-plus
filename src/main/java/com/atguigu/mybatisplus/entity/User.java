package com.atguigu.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    /*******@TableId******
     * type指定id生成策略
     * AUTO：自动增长
     * INPUT:不自动生成，自己插入
     *NONE：自己输入
     * UUID：随机唯一值
     *
     * ID_WORKER,ID_WORKER_STR:mp自带的策略，用的是snowflake算法，不用该注解默认识别类型使用其中一个
     * ID_WORKER：生成19位的值，数据类型是数字类型，就用这个
     *ID_WORKER_STR：数据类型是字符串，就用这个
     *
     * 生成策略一般有：1、自增长 2、UUID 3、Redis生成 4、snowflake算法
     */
    @TableId(type = IdType.ID_WORKER)
    private Long id;
    private String name;
    private Integer age;
    private String email;

    /*********自动填充******
     *      *注解填充字段 @TableField(.. fill = FieldFill.INSERT) 生成器策略部分也可以配置！
     *      * 自定义实现类 MyMetaObjectHandler
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;//数据库下划线对应实体类驼峰命名
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /********通过version实现乐观锁*****
     * 防止事务丢失：后提交的数据错误的覆盖了前提交的数据
     * version不能为null,为null的话mp始终无法执行修改操作
     * 新插入数据mp会自动为version初始化为0
     */
    @Version
    private int version;

    /**********逻辑删除****
     * 课通过如下配置改变是否被删除代表的值，默认没被删除为0，反之为1
     * mybatis-plus.global-config.db-config.logic-delete-value=1
     * mybatis-plus.global-config.db-config.logic-not-delete-value=0
     */
    @TableLogic
    private int deleted;
}
