package com.parking.parkinglot.ejb;

import com.parking.parkinglot.common.UserDto;
import com.parking.parkinglot.entities.Car;
import com.parking.parkinglot.entities.User;
import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class UsersBean {

    private static final Logger LOG = Logger.getLogger(UsersBean.class.getName());
    @PersistenceContext
    EntityManager entityManager;

  public static List<UserDto> findAllUsers(){
      LOG.info("findAllUsers");
      try{
          TypedQuery<Car> typedQuery = entityManager.createQuery("SELECT u FROM User u", User.class);
            List<Car> cars = typedQuery.getResultList();
            return copyUsersToDto(cars);
      }
      catch(Exception ex){
          throw new EJBException(ex);
      }

  }

    private List<UserDto> copyUsersToDto(List<Car> cars) {
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            userDtos.add(new UserDto(user));
        }
        return userDtos;

    }

}
