package com.hotelservice.hotelApi.service;

import com.hotelservice.hotelApi.DTO.RoomDTO;
import com.hotelservice.hotelApi.constant.CommonExceptionStatus;
import com.hotelservice.hotelApi.exception.CommonException;
import com.hotelservice.hotelApi.mappers.RoomListMapper;
import com.hotelservice.hotelApi.mappers.RoomMapper;
import com.hotelservice.hotelApi.model.Hotel;
import com.hotelservice.hotelApi.model.Room;
import com.hotelservice.hotelApi.repository.HotelRepository;
import com.hotelservice.hotelApi.repository.RoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RoomService {

    public final RoomRepository roomRepository;
    public final HotelRepository hotelRepository;

    private RoomListMapper roomListMapper;
    private RoomMapper roomMapper;

    public RoomDTO addRoom(String hotelName, RoomDTO roomDTO) throws CommonException {
        Optional<Hotel> hotel = hotelRepository.findHotelByName(hotelName);
        Room room = roomMapper.toEntity(roomDTO);
        if(hotel.isPresent()){
            room.setId(UUID.randomUUID());
            room.setHotelId(hotel.get().getId());
            roomRepository.save(room);
            return roomDTO;
        }
        else{
            throw new CommonException(CommonExceptionStatus.HOTEL_NOT_FOUND,
                    "Unable to add room: hotel with this name is not found",
                    HttpStatus.NOT_FOUND);
        }
    }

    public RoomDTO getRoom(String hotelName, Integer number) throws CommonException {
        Optional<Hotel> hotel = hotelRepository.findHotelByName(hotelName);
        if(hotel.isPresent()){
            Optional<Room> room = roomRepository.
                    findByHotelIdAndNumber(hotel.get().getId(), number);
            if(room.isPresent()){
                return roomMapper.toDTO(room.get());
            }
            throw new CommonException(CommonExceptionStatus.ROOM_NOT_FOUND,
                                        "Unable to get room: room with this number is not found",
                                        HttpStatus.NOT_FOUND);
        }
        throw new CommonException(CommonExceptionStatus.HOTEL_NOT_FOUND,
                                    "Unable to get room: hotel with this name is not found",
                                    HttpStatus.NOT_FOUND);
    }

    public List<RoomDTO> getAllRooms(String hotelName) throws CommonException {
        Optional<Hotel> hotel = hotelRepository.findHotelByName(hotelName);
        if(hotel.isPresent()){
            List<Room> roomList = roomRepository.findAll();
            if(!roomList.isEmpty()){
                return roomListMapper.toDTOList(roomList);
            }
            throw new CommonException(CommonExceptionStatus.NO_ROOMS_FOUND,
                    "No rooms found", HttpStatus.NOT_FOUND);
        }
        throw new CommonException(CommonExceptionStatus.HOTEL_NOT_FOUND,
                "Unable to get get all rooms: hotel with this name is not found",
                HttpStatus.NOT_FOUND);

    }

    public RoomDTO updateRoom(String hotelName, RoomDTO roomDTO) throws CommonException {

        Optional<Hotel> hotel = hotelRepository.findHotelByName(hotelName);
        if(hotel.isPresent())
        {
            Optional<Room> room = roomRepository.
                    findByHotelIdAndNumber(hotel.get().getId(), roomDTO.getNumber());
            if(room.isPresent())
            {
                roomRepository.delete(room.get());
                roomMapper.updateRoomFromDTO(roomDTO, room.get());
                return roomMapper.toDTO(roomRepository.save(room.get()));
            }
            else{
                throw new CommonException(CommonExceptionStatus.ROOM_NOT_FOUND,
                        "Unable to update room: room with this number is not found",
                        HttpStatus.NOT_FOUND);
            }
        }
        else{
            throw new CommonException(CommonExceptionStatus.HOTEL_NOT_FOUND,
                    "Unable to update room: hotel with this name is not found",
                    HttpStatus.NOT_FOUND);
        }
    }

    public RoomDTO deleteRoom(String hotelName, Integer number) throws CommonException {
        Optional<Hotel> optHotel = hotelRepository.findHotelByName(hotelName);
        if(optHotel.isPresent()){
            Optional<Room> opt_room = roomRepository.
                    findByHotelIdAndNumber(optHotel.get().getId(), number);
            if(opt_room.isPresent()){
                roomRepository.delete(opt_room.get());
                return roomMapper.toDTO(opt_room.get());
            }
            throw new CommonException(CommonExceptionStatus.ROOM_NOT_FOUND,
                    "Unable to delete room: room with this number is not found",
                    HttpStatus.NOT_FOUND);
        }
        throw new CommonException(CommonExceptionStatus.HOTEL_NOT_FOUND,
                "Unable to delete room: hotel with this name is not found",
                HttpStatus.NOT_FOUND);
    }

    public List<RoomDTO> getAllAvailableRooms(String hotelName, Boolean isAvailable) throws CommonException {
        Optional<Hotel> hotel = hotelRepository.findHotelByName(hotelName);
        if(hotel.isPresent()){
                return roomListMapper.toDTOList(roomRepository.
                        findByHotelIdAndAvailable(hotel.get().getId(), isAvailable));
        }
        throw new CommonException(CommonExceptionStatus.HOTEL_NOT_FOUND,
                "Unable to find rooms: hotel with this name is not found",
                HttpStatus.NOT_FOUND);
    }

    public RoomDTO setRoomAvailable(String hotelName, Integer roomNumber, Boolean availability) throws CommonException {
        Optional<Hotel> optHotel = hotelRepository.findHotelByName(hotelName);
        if (optHotel.isPresent()) {
            Optional<Room> opt_room = roomRepository.findByHotelIdAndNumber(optHotel.get().getId(), roomNumber);
            if (opt_room.isPresent()) {
                opt_room.get().setAvailable(availability);
                roomRepository.save(opt_room.get());
                return roomMapper.toDTO(opt_room.get());
            }
            throw new CommonException(CommonExceptionStatus.ROOM_NOT_FOUND,
                    "Unable to set room availability: room with this number is not found",
                    HttpStatus.NOT_FOUND);
        }
        throw new CommonException(CommonExceptionStatus.HOTEL_NOT_FOUND,
                "Unable to set room availability: hotel with this name is not found",
                HttpStatus.NOT_FOUND);
    }

    public RoomDTO addTagToRoom(String hotelName, Integer roomNumber, String tag) throws CommonException {
        Optional<Hotel> optHotel = hotelRepository.findHotelByName(hotelName);
        if (optHotel.isPresent()){
            Optional<Room> optRoom = roomRepository.findByHotelIdAndNumber(optHotel.get().getId(), roomNumber);
            if(optRoom.isPresent()) {
                roomRepository.addTagToRoom(optRoom.get().getId(), tag);
                return roomMapper.toDTO(roomRepository.findByHotelIdAndNumber(optHotel.get().getId(), roomNumber).get());
            }
            throw new CommonException(CommonExceptionStatus.ROOM_NOT_FOUND,
                    "Cannot add tag to room: room with this number doesn't exist",
                    HttpStatus.NOT_FOUND);
        }
        throw new CommonException(CommonExceptionStatus.HOTEL_NOT_FOUND,
                "Cannot add tag to room: hotel with this name doesn't exist",
                HttpStatus.NOT_FOUND);
    }

    public List<RoomDTO> getAllRoomsByHotelNameAndTags(String hotelName, Set<String> tags) throws CommonException {
        Optional<Hotel> optHotel = hotelRepository.findHotelByName(hotelName);
        if(optHotel.isPresent()){
            List<Room> roomsList = roomRepository.getRoomsByHotelIdAndTags(optHotel.get().getId(), tags);
            if(!roomsList.isEmpty()){
                return roomListMapper.toDTOList(roomsList);
            }
            throw new CommonException(CommonExceptionStatus.NO_ROOMS_FOUND,
                    "No rooms with this tags found",
                    HttpStatus.NOT_FOUND);
        }
        throw new CommonException(CommonExceptionStatus.HOTEL_NOT_FOUND,
                "No hotels with this name found",
                HttpStatus.NOT_FOUND);
    }

}