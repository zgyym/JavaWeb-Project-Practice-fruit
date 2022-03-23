在项目中可能会有多个servlet，每个servlet里又有许多方法，比如：FruitServlet，UserServlet，OrderServlet等
这样就会在每一个servlet里写一个反射去调用方法，所以进行优化
    先创建一个核心控制器（中央控制器）DispatcherServlet,将所有的请求都交给中央控制器，由中央控制器来根据用户的请求来交给不同的servlet处理
    servlet的名字修改为controller并删除WebServlet

            @WebServlet("*.do")
            DispatcherServlet
                1、继承ViewBaseServlet
                2、重写HttpServletRequest的service方法
                    1）设置编码
                    2）获取servletPath
                    3）对servletPath进行字符串截取
                            //假设url是：  http://localhost:8080/pro15/hello.do
                            //那么servletPath是：    /hello.do
                            // 思路是：
                            // 第1步： /hello.do ->   hello   或者  /fruit.do  -> fruit
                            // 第2步： hello -> HelloController 或者 fruit -> FruitController

    创建一个xml配置文件applicationContext.xml
        设置beans标签

    继续写HttpServletRequest类
        在init(重写)中解析配置文件，把id为键和class为值放到一个Map对象里，在service方法中根据键值对获取值,根据获取到的值来调用对应类中的方法