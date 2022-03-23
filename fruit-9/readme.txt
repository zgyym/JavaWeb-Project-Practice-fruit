IOC的实现（解耦合）
    1、将fruitServlet和fruitDAO改为null
    2、配置文件配置两个实现类的id和class
    3、新建BeanFactory接口并提供获取bean对象的方法
    4、实现接口
        重写getBean方法，在构造方法中读取配置文件并根据读取到的id和class文件创建对象，将id和该对象放到map集合中（DispatcherServlet中的）
    5、在DispatcherServlet中创建新的BeanFactory属性，并在构造方法中赋值。
    6、配置文件中配置属性（fruitDAO和fruitService）
        property标签用来表示属性；name表示属性名；ref表示引用其他bean的id值
    7、组装bean之间的依赖关系

                for(int i = 0 ; i<beanNodeList.getLength() ; i++){
                    Node beanNode = beanNodeList.item(i);
                    if(beanNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element beanElement = (Element) beanNode;
                        String beanId = beanElement.getAttribute("id");
                        NodeList beanChildNodeList = beanElement.getChildNodes();
                        for (int j = 0; j < beanChildNodeList.getLength() ; j++) {
                            Node beanChildNode = beanChildNodeList.item(j);
                            if(beanChildNode.getNodeType()==Node.ELEMENT_NODE && "property".equals(beanChildNode.getNodeName())){
                                Element propertyElement = (Element) beanChildNode;
                                String propertyName = propertyElement.getAttribute("name");
                                String propertyRef = propertyElement.getAttribute("ref");
                                 //1) 找到propertyRef对应的实例
                                Object refObj = beanMap.get(propertyRef);
                                 //2) 将refObj设置到当前bean对应的实例的property属性上去
                                Object beanObj = beanMap.get(beanId);
                                Class beanClazz = beanObj.getClass();
                                Field propertyField = beanClazz.getDeclaredField(propertyName);
                                propertyField.setAccessible(true);
                                propertyField.set(beanObj,refObj);
                            }
                        }
                    }
                }