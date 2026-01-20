package com.learning.crm.dto.request;

import com.learning.crm.models.ClientStatus;
import jakarta.validation.constraints.NotBlank;

public record UpdateStatusRequest (

        ClientStatus newStatus

) {}
