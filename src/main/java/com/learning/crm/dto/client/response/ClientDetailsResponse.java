package com.learning.crm.dto.response;

import com.learning.crm.models.ClientStatus;

import java.util.List;

public record ClientDetailsResponse(

        String id,
        String name,
        String phone,
        String email,
        ClientStatus status,
        List<NoteInfoResponse> notes

) {}
