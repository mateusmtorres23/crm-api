package com.learning.crm.dto.client.request;

import com.learning.crm.models.ClientStatus;
import jakarta.validation.constraints.NotBlank;

public record UpdateStatusRequest (

        ClientStatus newStatus

) {}
