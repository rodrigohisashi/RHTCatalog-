package com.rhtinterprise.RHTcatalog.dto;

import com.rhtinterprise.RHTcatalog.entities.Role;

public class RoleDTO {
    private Long id;
    private String authority;

    public RoleDTO() {


    }

    public Long getId() {
        return id;
    }

    public RoleDTO(Long id, String authority) {
        this.id = id;
        this.authority = authority;
    }

    public RoleDTO(Role role) {
        this.id = role.getId();
        this.authority = role.getAuthority();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }


}
