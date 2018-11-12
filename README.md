# SO4M (Simple Operation For Mybatis)
使用Mybatis注解方式操作数据库
### 目前可用类(Class)
类名 | 功能介绍
:---: | :---:
SimpleInsertLanguageDriver | 插入操作
SimpleUpdateLanguageDriver | 修改操作，空属性不会设为空
NullableUpdateLanguageDriver | 修改操作，空属性会被设置到数据库中
SimpleSelectInLanguageDriver | 查询in操作

### 使用(Using)
- #### 添加maven依赖
> 此项目还未发布到maven中央仓库，如果需要可下载源码设置到自己的本地仓库
````xml
<dependency>
    <groupId>com.mitang</groupId>
    <artifactId>SO4M</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
````

- #### 插入操作(insert)
> 在mapper接口的方法上加上Mybatis的@Lang注解，并指定相应插入操作类
````java
import com.mitang.SO4M.language.deiver.SimpleInsertLanguageDriver;
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
import com.mitang.SO4M.language.deiver.SimpleUpdateLanguageDriver;
import com.mitang.SO4M.language.deiver.NullableUpdateLanguageDriver;
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
import com.mitang.SO4M.language.deiver.SimpleSelectInLanguageDriver;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Lang;

public interface UserInfoMapper {
	
	@Select("select * from user_info where id in (#{ids})")
	@Lang(SimpleSelectInLanguageDriver.calss)
	List<UserInfo> selectIn(List<Integer> ids);
	
}
````

