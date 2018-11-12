# SO4M (Simple Operation For Mybatis)
使用Mybatis注解方式操作数据库
### 目前可用类(Class)
类名 | 功能介绍
:---: | :---:
SimpleInsertLanguageDriver | 插入操作
SimpleUpdateLanguageDriver | 修改操作，空属性不会设为空
NullableUpdateLanguageDriver | 修改操作，空属性会被设置到数据库中
SimpleSelectInLanguageDriver | 查询in操作

### 使用注解方式前后对比
```java
import com.assicIcon.SO4M.language.deiver.SimpleUpdateLanguageDriver;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Lang;

public interface UserInfoMapper {
	
    // Before
    @Update(
    "<script>\n" +
        "update user_info\n" +
        "<set>\n" +
            "<if test=\"id != null\">\n" +
    				"id = #{id}\n" +
            "</if>\n" +
            "<if test=\"username != null\">\n" +
                "username = #{username}\n" +
            "</if>\n" +
        "</set>\n" +
        "where id = #{id}\n" +
    "</script>"
    )
    void insert(UserInfo userInfo);
    
    // After 
    @Update("update user_info (#{userInfo}) where id = #{id}")
    @Lang(SimpleUpdateLanguageDriver.class)
    void insert(UserInfo userInfo);
    
}
```

### 使用(Using)
- #### 添加maven依赖
> 此项目还未发布到maven中央仓库，如果需要可下载源码设置到自己的本地仓库
````xml
<dependency>
    <groupId>com.github.assicIcon</groupId>
    <artifactId>SO4M</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
````

- #### 插入操作(insert)
> 在mapper接口的方法上加上Mybatis的@Lang注解，并指定相应插入操作类
````java
import com.assicIcon.SO4M.language.deiver.SimpleInsertLanguageDriver;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Lang;

public interface UserInfoMapper {
	
    @Insert("insert into user_info (#{userInfo})")
    @Lang(SimpleInsertLanguageDriver.class)
    void insert(UserInfo userInfo);
    
}
````

- #### 修改操作(update)
> 在mapper接口的方法上加上Mybatis的@Lang注解，并指定相应修改操作类
````java
import com.assicIcon.SO4M.language.deiver.SimpleUpdateLanguageDriver;
import com.assicIcon.SO4M.language.deiver.NullableUpdateLanguageDriver;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Lang;

public interface UserInfoMapper {
	
    /**
    * 空属性不会被设置到数据库中
    */
    @Update("update user_info (#{userInfo})")
    @Lang(SimpleUpdateLanguageDriver.class)
    void update(UserInfo userInfo);
    
    /**
    * 空属性会被设置到数据库中
    */
    @Update("update user_info (#{userInfo})")
    @Lang(NullableUpdateLanguageDriver.class)
    void updateNullable(UserInfo userInfo);
    
}
````

- #### Select in 操作(In)
> 在mapper接口的方法上加上Mybatis的@Lang注解，并指定SimpleSelectInLanguageDriver类

````java
import com.assicIcon.SO4M.language.deiver.SimpleSelectInLanguageDriver;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Lang;

public interface UserInfoMapper {
	
	@Select("select * from user_info where id in (#{ids})")
	@Lang(SimpleSelectInLanguageDriver.calss)
	List<UserInfo> selectIn(List<Integer> ids);
	
}
````

### 注解(annotation)
注解名称 | 功能
:------: | :---:
Invisible | 对操作不可见

- #### Invisible注解
> 用于实体类的属性上，表示该属性不会被插入或更改
````java
import java.util.Date;

public class UserInfo {
	
	private Integer id;
	
	private String name;
	
	// ....
	
    // createTime的值将不会被插入或修改
	@Invisible
	private Date createTime;
	
}

````



