优化servlet
    将所有的servlet类都写到一个FruitServlet类中
        1）将各个servlet中的实现方法重写到FruitServlet中并改名和private
        2）在FruitServlet中写service方法调用各个实现方法
        3）修改前端的请求都改为fruit.do
        4）删除原来的servlet
        5）修改重定向的页面