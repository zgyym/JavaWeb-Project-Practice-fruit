可以将DAO里的事务开启，事务提交和事务回滚提取到过滤器中
    一个service中由多个DAO，这几个DAO组件必须操作同一个connection，这样才能让三个操作处于同一个事务中；而要操作同一个connection，
    可以在DAO方法中传入connection参数，也可以使用ThreadLocal类




    将事务的处理封装成一个类：开启、提交、回滚（提交和回滚之后要关闭资源）
    在basedao下写ConnUtil工具类来获取连接、关闭资源
    修改DAO里获取连接和关闭资源的代码，
    写OpenSessionInViewFilter实现Filter：开启事务，放开，提交事务（回滚事务）
    DAO，DAOImpl。service，controller，中央控制器所有try处理的异常要重新处理