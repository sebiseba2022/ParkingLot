package com.parking.parkinglot.ejb;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import com.parking.parkinglot.common.CarDto;
import com.parking.parkinglot.entities.Car;
import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;


@Stateless
public class CarsBean {

    private static final Logger LOG = Logger.getLogger(CarsBean.class.getName());
    @PersistenceContext
    EntityManager entityManager;

  public List<CarDto> findAllCars(){
      LOG.info("findAllCars");
      try{
          TypedQuery<Car> typedQuery = entityManager.createQuery("SELECT c FROM Car c", Car.class);
            List<Car> cars = typedQuery.getResultList();
            return copyCarsToDto(cars);
      }
      catch(Exception ex){
          throw new EJBException(ex);
      }

  }

    private List<CarDto> copyCarsToDto(List<Car> cars) {
        List<CarDto> carDtos = new ArrayList<>();
        for (Car car : cars) {
           CarDto carDto = new CarDto(car.getId(), car.getLicensePlate(), car.getParkingSpot(), car.getOwner().getUsername());
           carDtos.add(carDto);
        }
        return carDtos;
    }

    public CarDto findCarById(Long id) {
        LOG.info("findCarById: " + id);
        try {
            Car car = entityManager.find(Car.class, id);
            if (car != null) {
                return new CarDto(car.getId(), car.getLicensePlate(), car.getParkingSpot(), car.getOwner().getUsername());
            }
            return null;
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }

    public void createCar(String licensePlate, String parkingSpot, Long ownerId) {
        LOG.info("createCar");
        Car car = new Car();
        car.setLicensePlate(licensePlate);
        car.setParkingSpot(parkingSpot);
        
        com.parking.parkinglot.entities.User owner = entityManager.find(com.parking.parkinglot.entities.User.class, ownerId);
        car.setOwner(owner);
        
        entityManager.persist(car);
    }

    public void updateCar(Long carId, String licensePlate, String parkingSpot, Long ownerId) {
        LOG.info("updateCar: " + carId);
        try {
            Car car = entityManager.find(Car.class, carId);
            if (car != null) {
                car.setLicensePlate(licensePlate);
                car.setParkingSpot(parkingSpot);
                
                com.parking.parkinglot.entities.User owner = entityManager.find(com.parking.parkinglot.entities.User.class, ownerId);
                car.setOwner(owner);
                
                entityManager.merge(car);
            }
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }

    public void deleteCarsByIds(List<Long> carIds) {
        LOG.info("deleteCarsByIds: " + carIds.size() + " cars");
        try {
            for (Long carId : carIds) {
                Car car = entityManager.find(Car.class, carId);
                if (car != null) {
                    entityManager.remove(car);
                }
            }
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }

}
