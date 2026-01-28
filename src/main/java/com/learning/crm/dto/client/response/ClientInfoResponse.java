package com.learning.crm.dto.response;

import com.learning.crm.models.ClientStatus;

public record ClientResponse(

        String id,
        String name,
        String phone,
        String email,
        ClientStatus status

) {}
