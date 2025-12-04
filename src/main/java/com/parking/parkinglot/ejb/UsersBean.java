package com.parking.parkinglot.ejb;

import com.parking.parkinglot.common.UserDto;
import com.parking.parkinglot.entities.User;
import com.parking.parkinglot.entities.UserGroup;
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

  public List<UserDto> findAllUsers(){
      LOG.info("findAllUsers");
      try{
          TypedQuery<User> typedQuery = entityManager.createQuery("SELECT u FROM User u", User.class);
            List<User> users = typedQuery.getResultList();
            return copyUsersToDto(users);
      }
      catch(Exception ex){
          throw new EJBException(ex);
      }

  }

    private List<UserDto> copyUsersToDto(List<User> users) {
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            UserDto userDto = new UserDto(
                    user.getId(), user.getUsername(), user.getEmail()
            );
            userDtos.add(userDto);
        }
        return userDtos;
    }

    public boolean authenticate(String username, String password) {
        LOG.info("authenticate: " + username);
        try {
            TypedQuery<User> typedQuery = entityManager.createQuery(
                "SELECT u FROM User u WHERE u.username = :username AND u.password = :password",
                User.class
            );
            typedQuery.setParameter("username", username);
            typedQuery.setParameter("password", password);

            List<User> users = typedQuery.getResultList();
            return !users.isEmpty();
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }

    public void grantPermission(String username, String permission) {
        LOG.info("grantPermission: " + username + " - " + permission);
        try {
            // Check if permission already exists
            TypedQuery<UserGroup> query = entityManager.createQuery(
                "SELECT ug FROM UserGroup ug WHERE ug.username = :username AND ug.userGroup = :userGroup",
                UserGroup.class
            );
            query.setParameter("username", username);
            query.setParameter("userGroup", permission);

            if (query.getResultList().isEmpty()) {
                UserGroup userGroup = new UserGroup();
                userGroup.setUsername(username);
                userGroup.setUserGroup(permission);
                entityManager.persist(userGroup);
            }
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }

    public void revokePermission(String username, String permission) {
        LOG.info("revokePermission: " + username + " - " + permission);
        try {
            TypedQuery<UserGroup> query = entityManager.createQuery(
                "SELECT ug FROM UserGroup ug WHERE ug.username = :username AND ug.userGroup = :userGroup",
                UserGroup.class
            );
            query.setParameter("username", username);
            query.setParameter("userGroup", permission);

            List<UserGroup> results = query.getResultList();
            for (UserGroup ug : results) {
                entityManager.remove(ug);
            }
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }

    public List<String> getUserPermissions(String username) {
        LOG.info("getUserPermissions: " + username);
        try {
            TypedQuery<UserGroup> query = entityManager.createQuery(
                "SELECT ug FROM UserGroup ug WHERE ug.username = :username",
                UserGroup.class
            );
            query.setParameter("username", username);

            List<String> permissions = new ArrayList<>();
            for (UserGroup ug : query.getResultList()) {
                permissions.add(ug.getUserGroup());
            }
            return permissions;
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }

}
