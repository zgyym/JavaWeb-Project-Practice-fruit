
如果servlet的方法很多，那switch。。。case就会很长，所以利用反射机制来调用方法
    找到与operate同名的方法，通过反射机制调用此方法来代替switch。。。case

        Method[] methods = this.getClass().getDeclaredMethods();
                for (Method m : methods) {
                    String methodName = m.getName();
                    if(operate.equals(methodName)){
                        try {
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