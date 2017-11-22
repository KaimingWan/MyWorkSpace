package transaction;

/**
 * Spring事务可以按照声明式事务（用注解@Transaction）还是编程式事务执行(使用TransactionTemplate自己更加细粒度处理事务，代码级别)
 *
 * 事务类型分为本地事务、全局事务（符合JTA规范）
 *
 *  事务配置XML（一般配置成声明式的，方便），主要配置清楚：
 * 1. 数据源
 * 2. 事务管理器类型(TM)
 * 3. 事务代理方式（一般采用基于注解）
 *
 * XML配置example见SpringTransaction.xml
 *
 *
 * TM可以选择JDBC、hibernate、mybatis、JTA、JDO等等类型
 *
 * Created by Kaiming Wan on 2017/1/15.
 */
public class LocalJDBCTransaction {
}
