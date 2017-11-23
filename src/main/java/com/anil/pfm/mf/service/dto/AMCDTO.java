package com.anil.pfm.mf.service.dto;


import java.io.Serializable;
import java.util.Objects;

import javax.validation.constraints.NotNull;

/**
 * A DTO for the AMC entity.
 */
public class AMCDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

    @NotNull
    private String name;

    private String website;

    private String logoURL;

    private Long mfrtAgentId;
    
    private MFRTAgentDTO mfrtAgent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getLogoURL() {
        return logoURL;
    }

    public void setLogoURL(String logoURL) {
        this.logoURL = logoURL;
    }

    public Long getMfrtAgentId() {
        return mfrtAgentId;
    }

    public void setMfrtAgentId(Long mFRTAgentId) {
        this.mfrtAgentId = mFRTAgentId;
    }

    public MFRTAgentDTO getMfrtAgent() {
		return mfrtAgent;
	}

	public void setMfrtAgent(MFRTAgentDTO mfrtAgent) {
		this.mfrtAgent = mfrtAgent;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AMCDTO aMCDTO = (AMCDTO) o;
        if(aMCDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), aMCDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AMCDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", website='" + getWebsite() + "'" +
            ", logoURL='" + getLogoURL() + "'" +
            "}";
    }
}
