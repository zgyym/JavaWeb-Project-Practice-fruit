package myssm.myspringmvc;


import myssm.ioc.BeanFactory;
import myssm.ioc.ClassPathXmlApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

@WebServlet("*.do")
public class DispatcherServlet extends ViewBaseServlet{
    private BeanFactory beanFactory;

    public DispatcherServlet() {
    }

    @Override
    public void init() throws ServletException {
        super.init();
        beanFactory = new ClassPathXmlApplicationContext();

    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");

        String servletPath = request.getServletPath();
        servletPath = servletPath.substring(1);
        int lastDotIndex = servletPath.lastIndexOf(".do");
        servletPath = servletPath.substring(0,lastDotIndex);

        Object controllerBeanObj = beanFactory.getBean(servletPath);



        String operate = request.getParameter("operate");
        if(operate == null || "".equals(operate)){
            operate = "index";
        }

        try {
            Method[] methods = controllerBeanObj.getClass().getDeclaredMethods();
            for(Method method : methods){
                if(operate.equals(method.getName())){
                    //1.统一获取请求参数

                    //1-1.获取当前方法的参数，返回参数数组
                    Parameter[] parameters = method.getParameters();
                    //1-2.parameterValues 用来承载参数的值
                    Object[] parameterValues = new Object[parameters.length];
                    for (int i = 0; i < parameters.length; i++) {
                        Parameter parameter = parameters[i];
                        String parameterName = parameter.getName() ;
                        //如果参数名是request,response,session 那么就不是通过请求中获取参数的方式了
                        if("request".equals(parameterName)){
                            parameterValues[i] = request ;
                        }else if("response".equals(parameterName)){
                            parameterValues[i] = response ;
                        }else if("session".equals(parameterName)){
                            parameterValues[i] = request.getSession() ;
                        }else{
                            //从请求中获取参数值
                            String parameterValue = request.getParameter(parameterName);
                            String typeName = parameter.getType().getName();

                            Object parameterObj = parameterValue ;

                            if(parameterObj != null) {
                                if ("java.lang.Integer".equals(typeName)) {
                                    parameterObj = Integer.parseInt(parameterValue);
                                }
                            }

                            parameterValues[i] = parameterObj ;
                        }
                    }
                    //2.controller组件中的方法调用
                    method.setAccessible(true);
                    Object returnObj = method.invoke(controllerBeanObj,parameterValues);

                    //3.视图处理
                    String methodReturnStr = null;
                    if(returnObj != null){
                        methodReturnStr = (String) returnObj;
                    }
                    if(methodReturnStr != null && methodReturnStr.startsWith("redirect:")){
                        String redirectStr = methodReturnStr.substring("redirect:".length());
                        response.sendRedirect(redirectStr);
                    }else if(methodReturnStr != null){
                        super.processTemplate(methodReturnStr,request,response);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new DispatcherServletException("DispatcherServlet出错了");
        }


        /*try {
            Method method = controllerBeanObj.getClass().getDeclaredMethod(operate,HttpServletRequest.class);
            if(method != null){

                //2、controller组件中的方法调用
                method.setAccessible(true);
                Object returnObj = method.invoke(controllerBeanObj, req);


                //3、视图处理
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


            }else{
                throw new RuntimeException("operate值非法!");
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }*/



        /*Method[] methods = controllerBeanObj.getClass().getDeclaredMethods();
        for (Method m : methods) {
            String methodName = m.getName();
            if(operate.equals(methodName)){
                try {
                    m.setAccessible(true);
                    //找到与operate同名的方法，通过反射机制调用他
                    m.invoke(this,req,resp);
                    return;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        throw new RuntimeException("operate值非法");

         */
    }
}
