package com.rhtinterprise.RHTcatalog.dto;

import com.rhtinterprise.RHTcatalog.entities.User;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class UserInsertDTO extends UserDTO{
  private String password;

  public UserInsertDTO() {
      super();
  }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
