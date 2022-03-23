添加模糊搜索功能
    1、修改index.html文件，通过div标签中的form表单来提交数据，还要添加一个input隐藏域name=operator value=search
    2、因为search.do的servlet与index的servlet相差不多，都需要分页显示，所以直接修改index的servlet
        1）doPost()方法直接调用doGet()方法，pageNo默认为1
        2）获取keyword参数

                String oper = request.getParameter("oper");
                        //如果operator!=null 说明 通过表单的查询按钮点击过来的
                        //如果operator是空的，说明 不是通过表单的查询按钮点击过来的

                        String keyword = null ;
                        if(!(operator == null || "".equals(operator)) && "search".equals(operator)){
                            //说明是点击表单查询发送过来的请求
                            //此时，pageNo应该还原为1 ， keyword应该从请求参数中获取
                            pageNo = 1 ;
                            keyword = request.getParameter("keyword");
                            //如果keyword为null，需要设置为空字符串""，否则查询时会拼接成 %null% , 我们期望的是 %%
                            if(keyword == null || "".equals(keyword)){
                                keyword = "" ;
                            }
                             //将keyword保存（覆盖）到session中
                            session.setAttribute("keyword",keyword);
                        }else{
                            //说明此处不是点击表单查询发送过来的请求
                			//比如查询结果出来后点击下一页
                			//（比如点击下面的上一页下一页或者直接在地址栏输入网址）
                            //此时keyword应该从session作用域获取
                            String pageNoStr = request.getParameter("pageNo");
                            if(!(pageNoStr == null || "".equals(pageNoStr))){
                                pageNo = Integer.parseInt(pageNoStr); //如果从请求中读取到pageNo，则类型转换。否则，pageNo默认就是1
                            }
                            //如果不是点击的查询按钮，那么查询是基于session中保存的现有keyword进行查询
                            Object keywordObj = session.getAttribute("keyword");
                            if(keywordObj!=null){
                                keyword = (String)keywordObj ;
                            }else{
                                keyword = "" ;
                            }
                        }



        3）修改DAO和实现类里的getFruitList(int pageNo)和getFruitCount()方法为getFruitList(String keyword,int pageNo)和getFruitCount(String keyword)方法
           把输入的keyword参数传入方法中获取结果
        4）资源整合
    3、将输入的关键字长期保留在搜索框
        th:value="${session.keyword}
        value 和 th:value 的值是相同的，但表的的意思应该是不同的