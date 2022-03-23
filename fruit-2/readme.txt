添加分页功能

    1、首先编辑index.html文件，添加div，并且在div里添加四个按钮
    2、分页显示不需要创建新的servlet，所以直接修改index的servlet
    3、编辑index.js文件，添加一个点击的方法
        function page(pageNo) {
            window.location.href='index?pageNo='+pageNo;
        }
        将第几页传给服务器
    4、修改index的servlet文件
        页面显示通常开始为第一页，所以pageNo初始值设为1
        获取传入服务器的第几页的参数
        将这个参数赋值给pageNo
        将pageNo写入session作用域
        在DAO里写getFruitList(int pageNo)方法，然后调用此方法得到第pageNo页的数据
        将得到的数据保存到session的保存作用域，便于index.html调用

        因为到这里时尾页没有设置，如果要设置尾页，就需要知道最多有多少页，就需要知道总的数据条数
        所以在DAO里写getFruitCount()方法，在servlet里调用获取总的记录数，根据记录数算出总页数。并
        保存到session保存作用域，最后html文件的尾页按钮绑定js事件出传入总页数

        还有可以访问到 0，-1，-2... 和总页数后面的页面，所以需要对各个按钮进行禁用
         （动态）th:disabled="${session.pageNo == session.pageCount}", disabled的值等于true的时候禁用该按钮

        资源整合
