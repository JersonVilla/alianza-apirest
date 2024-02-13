package com.springboot.alianza.apirest.dto;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String sharedKey;
    private String businessId;
    private String email;
    private String phone;
    private Date dataAdd;


}