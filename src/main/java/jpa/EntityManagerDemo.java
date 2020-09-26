package jpa;

import entity.Demo;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class EntityManagerDemo {
    public static void main(String[] args) {
        EntityManager en = MyPersistence.JAVA5.getEntityManager();

       Demo demo = en.find(Demo.class,1);
        System.out.println(demo);
        Demo newDemo = new Demo();
        newDemo.setName("TESTOWY");
        newDemo.setPoints(300);
        //modyfikacja bazy wymaga transakcji
        en.getTransaction().begin();
        en.persist(newDemo);
        en.getTransaction().commit();

        //zapytanie zwraccjące wszystkie obiekty o podanej nazwie i iliczbie punkóttw
        Query query = en.createQuery(
                "select d from Demo d where d.name = :qname and d.points = :qpoints", Demo.class);
        query.setParameter("qname","TESTOWY");
        query.setParameter("qpoints",300);
        List<Demo> resultList = query.getResultList();
        for (Demo d:resultList) {
            System.out.println(d);
        }
//usunięcie obiektu z bazy
        en.getTransaction().begin();
        en.remove(demo);
        en.getTransaction().commit();
        //obiekt usunięty z bazy ale istniejący w pamięci
        System.out.println(demo);
        demo = en.find(Demo.class,1);
        //obiekt pobrany z bazy po usunięciu
        System.out.println(demo);
        en.close();
    }
}
