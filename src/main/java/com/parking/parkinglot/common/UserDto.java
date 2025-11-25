package com.parking.parkinglot.common;


    public class UserDto {
        Long id;
        String email;
        private String password;
        String username;

        public UserDto(Long id, String username, String email, String password) {
            this.id = id;
            this.username = username;
            this.email = email;
            this.password = password;
        }

        public UserDto(com.parking.parkinglot.entities.User user) {
            this.id=user.getId();
            this.username=user.getUsername();
            this.email=user.getEmail();
            this.password=user.getPassword();
        }

        public Long getId() {
            return id;
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }

        public String getUsername() {
            return username;
        }
    }



