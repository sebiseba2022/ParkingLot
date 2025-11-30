package com.parking.parkinglot.common;


    public class UserDto {
        Long id;
        String email;
        String username;

        public UserDto(Long id, String username, String email) {
            this.id = id;
            this.username = username;
            this.email = email;
        }

        public UserDto(com.parking.parkinglot.entities.User user) {
            this.id=user.getId();
            this.username=user.getUsername();
            this.email=user.getEmail();
        }

        public Long getId() {
            return id;
        }

        public String getEmail() {
            return email;
        }

        public String getUsername() {
            return username;
        }
    }



