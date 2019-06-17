package com.ieng365.common;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class RefletUtil  {
 
    /**
     * 打印类的信息：类的方法和类的成员变量名
     */
    public static void printMessage (Object obj){
        //获取类的类类型
        /*Class c2 = obj.getClass();
        //获取类的名称 
        System.out.println("类的名称："+c2.getName());
        *//**
         * Method类，方法对象
         * 一个方法就是一个Method的实例对象
         * getMethods()方法获取的是所有的public的函数，包括父类继承而来
         * getDeclaredMethods()获取的是所有自己声明的方法，不论访问权限
         *//*
        Method[] ms = c2.getMethods();
        Method[] declearMs = c2.getDeclaredMethods();
        for(int i = 0;i<ms.length;i++){
            //获得方法返回值类型的类类型
            Class returnType = ms[i].getReturnType();
            System.out.print(returnType.getName()+"  ");
            //获得方法名称
            System.out.print(ms[i].getName()+"(");
            //获取参数类型-->得到的是参数列表类型的类类型
            Class[] paramTypes = ms[i].getParameterTypes();
            for (Class class1 : paramTypes) {
                System.out.print(class1.getName()+", ");
            }
            System.out.println(")");
        }*/
        //获取成员变量的信息
        /*
         * 成员变量也是对象
         * java.lang.reflect.Field
         * Field中封装了关于成员变量的操作
         * 
         */
      /*  //获取类的类类型
        Class c = obj.getClass();
        //获取所有public变量的信息
        Field[] fs = c.getFields();
        //获取所有声明变量的信息，不论访问权限
        Field[] deFs = c.getDeclaredFields();
        for (Field field : deFs) {
            //获取变量的类的类类型
            Class cfs = field.getType();
            String fsTypeName = cfs.getName();
            String fsName = field.getName();
            System.out.println("类型名："+fsTypeName+",变量名："+fsName); 
        }*/
        
        //获取构造方法信息
        /*
         * 构造方法也是对象
         * java.lang.reflect.Constructor;
         * Field中封装了关于成员变量的操作
         * 
         */
        Class c = obj.getClass();
        Constructor[] cs = c.getConstructors();
        for (Constructor constructor : cs) {
            String csName = constructor.getName();
            System.out.print(csName+"(");
            
            Class[] paramTypes = constructor.getParameterTypes();
            for (Class class1 : paramTypes) {
               System.out.print(class1.getName()+",");
            }
            System.out.println(")");
        }
    }
    public static void main(String[] args) throws Exception {
        //printMessage(String.class.newInstance()); 
        
        List<Integer> list1 = new ArrayList<Integer>();
        List<String> list2 = new ArrayList<String>();
        System.out.println(list1.getClass().getName());
        System.out.println(list2.getClass().getName());
        System.out.println(list1.getClass() == list2.getClass());
        //list1.add("aaaa");
        Class c = list1.getClass();
        Method  m = c.getDeclaredMethod("add", Object.class);
        m.invoke(list1, "aaaa");
        System.out.println(list1.get(0));
    } 
    
    
}
    class MethodDemo{
        public static void main(String[] args) throws Exception {
            /**
             * 通过反射获取类的具体方法并调用
             */
            Class c = kkk.class;
            //获取具体的方法对象
            //Method m1 = c.getDeclaredMethod("print", new Class[]{int.class,int.class});
            Method m1 = c.getDeclaredMethod("print", int.class,int.class);
            //通过方法对象反射来调用方法
            m1.invoke(kkk.class.newInstance(), 10,20);
            System.out.println("----------------------------------");
            //Method m2 = c.getDeclaredMethod("print", new Class[]{String.class,String.class});
            Method m2 = c.getDeclaredMethod("print", String.class,String.class);
            //通过方法对象反射来调用方法
            m2.invoke(kkk.class.newInstance(), "hello","WORD");
            System.out.println("----------------------------------");
            //Method m3 = c.getDeclaredMethod("print", new Class[]{});
            Method m3 = c.getDeclaredMethod("print");
            //通过方法对象反射来调用方法
            m3.invoke(kkk.class.newInstance());
        }
    }
    class kkk{
    private int bbb = 5;
    
    public void print(){
        System.out.println("Hello Word");
    }
    public void print(int a,int b){
        System.out.println(a+b);
    }
    public void print(String a,String b){
        System.out.println(a.toUpperCase()+","+b.toLowerCase());
    }
    }
