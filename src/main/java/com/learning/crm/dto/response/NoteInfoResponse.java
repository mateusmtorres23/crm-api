package com.learning.crm.dto.response;

import java.time.Instant;

public record NoteInfoResponse(

        String id,
        String content,
        Instant createdAt

) {}
