package com.learning.crm.dto.client.response;

import com.learning.crm.models.ClientStatus;

public record ClientInfoResponse(

        String id,
        String name,
        String phone,
        String email,
        ClientStatus status,
        int numberOfNotes

) {}
