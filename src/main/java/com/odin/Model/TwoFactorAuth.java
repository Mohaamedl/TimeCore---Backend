package com.odin.Model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.odin.domain.VerificationType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Embeddable
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TwoFactorAuth {
    @Enumerated(EnumType.STRING)
    @JsonProperty("send_to")
    private VerificationType send_to = VerificationType.EMAIL;

    @JsonProperty
    private boolean is_enabled;
    /**
     * TODO
     * Verify whys just default values for is_enable and send_to appear
     * in the database
     *
     */


    public void setIs_enabled(boolean is_enabled) {
        this.is_enabled = is_enabled;
    }

    public void setSend_to(VerificationType send_to) {
        this.send_to = send_to;
    }

    public VerificationType getSend_to() {
        return send_to;
    }

    public boolean isIs_enabled() {
        return is_enabled;
    }

}
