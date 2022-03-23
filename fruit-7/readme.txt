FruitController 中的方法都有获取参数和资源跳转的行为，将这些行为抽取出来


    资源跳转：
        1、中央控制器继承ViewBaseServlet
        2、将controller组件中的方法中资源跳转代码删除，修改为返回字符串,不继承ViewBaseServlet，并且各个方法中的resp形参都不需要了
        3、在中央控制器调用controller组件中的方法时获取此字符串（判断obj不等于null，强转为String）
        4、试图处理

            String methodReturnStr = null;
            if(returnObj != null){
                methodReturnStr = (String) returnObj;
            }
            if(methodReturnStr != null && methodReturnStr.startsWith("redirect:")){
                String redirectStr = methodReturnStr.substring("redirect:".length());
                resp.sendRedirect(redirectStr);
            }else if(methodReturnStr != null){
                super.processTemplate(methodReturnStr,req,resp);
            }

        5、在反射互殴去方法和调用方法时不传resp实参
        6、在中央控制器的init（往map里存id和class键值对）方法中调用父类init方法


    获取参数：
        1、在controller的方法中传入实参，不需要用req获取参数了
        2、在中央控制器中获取参数
            有的方法需要request参数，但获取时参数不好船，所以获取方法数组 循环。
            java8的新特性：获取方法的参数列表时可以获取参数名

            常见错误： IllegalArgumentException: argument type mismatch
                        参数类型不匹配，服务器获取到的是字符串类型，而controller方法中需要的是Integer类型，request、response、session不是字符串类型
