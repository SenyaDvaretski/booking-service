package com.hotelservice.hotelApi.repositories;

import com.hotelservice.hotelApi.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoomRepository extends JpaRepository<Room,Integer>{

}
