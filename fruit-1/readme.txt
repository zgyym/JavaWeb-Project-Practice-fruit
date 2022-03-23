
如果html页面参数需要动态改变的话，需要使用thymeleaf语法---->th:

绝对路径 th:href="@{/add.html}"



一、编辑和修改特定库存信息：
    1、要求点击水果名称修改库存信息，在名称添加超链接标签（td标签的th:text会覆盖超链接，所以将th:text放在a标签里）
        th:href="@{/edit.do(fid=${fruit.fid})}
        要将fid联通亲求带入服务器，用（）来代替字符串拼接
    2、写edit.do的servlet（使用注解来对servlet进行注册）
        默认使用doGet，重写doGet()
            1）获取请求带入服务器的参数fid，返回字符串
            2）将字符串转为int类型
            3）调用DAO里的方法根据fid取一个Fruit对象
            4）将该对象保存到request的保存作用域
            5）调用processTemplate()方法进行处理模板的渲染
    3、写edit.html文件
        用一个表单来接受各种信息，并且加一个隐藏域来给服务器传fid而不让用户看到
            th:action="@{/update.do}" method="post"
            th:object="${fruit}"（fruit是一个保存在request保存作用域的对象）使用这个属性在input标签中的value就可以省略 fruit.
            添加提交的按钮
    4、写update.do的servlet
        form表单提交时用post，重写doPost()方法
            1）根据edit.html提交到服务器的数据进行获取参数（fname，price，fcount，remark 注意类型转换）
            2）根据获取的参数来创建一个新的Fruit对象
            3）调用DAO里的方法进行对数据库数据的更新
            4）最后资源跳转
                不要使用super.processTemplate("index",request,response);
                上面代码相当于request.getRequestDispatcher("index.html").forward(request,response);
                此处需要重定向，目的是重新给IndexServlet发请求，重新获取furitList，然后覆盖到session中，
                这样index.html页面上显示的session中的数据才是最新的，所以用resp.sendRedirect("index");

二、删除
    1、要求点击操作中的删除图标来对数据进行删除，首先对index.html进行编辑
        th:onclick="|delfruit(${fruit.fid})|"代替字符串拼接
    2、写js代码
        if(confirm("是否确认删除？")){
                window.location.href='del.do?fid='+fid;
        }
        confirm是一个确认弹窗，确定执行{}里的内容
        window.location.href='del.do?fid='+fid;可以在请求时将fid传给服务器，以便于服务器可以获取参数
    3、写del.do的servlet
        doGet()方法
            1）获取参数fid，转为int类型
            2）根据fid调用DAO里的删除方法
            3）资源跳转resp.sendRedirect("index");

三、添加（此处有问题：add.html文件中的表单的action不要加th可以成功运行）
    1、要求可以添加数据，首先对index.html进行编辑
        添加一个超链接来跳转到添加页面（div标签） <a th:href="@{/add.html}" style="border:0px solid blue;margin-bottom:4px;">添加新库存记录</a>
    2、写add.html文件
        <form action="add.do" method="post"> 和 input 标签
    3、写add.do的servlet
        doPost()方法：
            1）修改编码
            2）获取参数
            3）调用DAO的方法进行添加
            4）最后资源跳转









