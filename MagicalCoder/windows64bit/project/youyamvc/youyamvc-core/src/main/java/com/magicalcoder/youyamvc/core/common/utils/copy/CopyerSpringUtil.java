package com.magicalcoder.youyamvc.core.common.utils.copy;

import org.springframework.beans.BeanUtils;

/**
 * Created by www.magicalcoder.com on 2015/8/7.
 * 799374340@qq.com
 * 属性拷贝 还有很多方法
 */
public class CopyerSpringUtil {

    public static void copyProperties(Object source,Object target){
        BeanUtils.copyProperties(source,target);
    }
    public static void copyProperties(Object source,Object target,Class<?> tClass){
        BeanUtils.copyProperties(source,target,tClass);
    }

    public static void copyProperties(Object source,Object target,String ... ignoreFields) {
        BeanUtils.copyProperties(source, target, ignoreFields);
    }

    public static void main(String[] args) {
        C c = new C();
        c.setC("c");
        A a = new A();
        a.setHello("hello");
        a.setHi("Hi");
        a.setC(c);
        B b = new B();
        BeanUtils.copyProperties(a,b);
        System.out.println(b.getHello()+b.getHi()+b.getC().getC());
    }

    static class A{
        private C c;
        private String hello;
        private String hi;

        public String getHello() {
            return hello;
        }

        public void setHello(String hello) {
            this.hello = hello;
        }

        public String getHi() {
            return hi;
        }

        public void setHi(String hi) {
            this.hi = hi;
        }

        public C getC() {
            return c;
        }

        public void setC(C c) {
            this.c = c;
        }
    }
    static class B extends A{
        private String nihao;

        public String getNihao() {
            return nihao;
        }

        public void setNihao(String nihao) {
            this.nihao = nihao;
        }
    }
    static class C {
        private String c;

        public String getC() {
            return c;
        }

        public void setC(String c) {
            this.c = c;
        }
    }
}
