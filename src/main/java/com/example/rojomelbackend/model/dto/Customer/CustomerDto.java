package com.example.rojomelbackend.model.dto.Customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {

    private Long id;

    private String name;

    private String email;

    private Date dateCreated;

    private Date dateUpdated;

    private boolean isDeleted;

    public static interface IdStep {
        NameStep withId(Long id);
    }

    public static interface NameStep {
        EmailStep withName(String name);
    }

    public static interface EmailStep {
        DateCreatedStep withEmail(String email);
    }

    public static interface DateCreatedStep {
        DateUpdatedStep withDateCreated(Date dateCreated);
    }

    public static interface DateUpdatedStep {
        IsDeletedStep withDateUpdated(Date dateUpdated);
    }

    public static interface IsDeletedStep {
        BuildStep withIsDeleted(boolean isDeleted);
    }

    public static interface BuildStep {
        CustomerDto build();
    }


    public static class Builder implements IdStep, NameStep, EmailStep, DateCreatedStep, DateUpdatedStep, IsDeletedStep, BuildStep {
        private Long id;
        private String name;
        private String email;
        private Date dateCreated;
        private Date dateUpdated;
        private boolean isDeleted;

        private Builder() {
        }

        public static IdStep customerDto() {
            return new Builder();
        }

        @Override
        public NameStep withId(Long id) {
            this.id = id;
            return this;
        }

        @Override
        public EmailStep withName(String name) {
            this.name = name;
            return this;
        }

        @Override
        public DateCreatedStep withEmail(String email) {
            this.email = email;
            return this;
        }

        @Override
        public DateUpdatedStep withDateCreated(Date dateCreated) {
            this.dateCreated = dateCreated;
            return this;
        }

        @Override
        public IsDeletedStep withDateUpdated(Date dateUpdated) {
            this.dateUpdated = dateUpdated;
            return this;
        }

        @Override
        public BuildStep withIsDeleted(boolean isDeleted) {
            this.isDeleted = isDeleted;
            return this;
        }

        @Override
        public CustomerDto build() {
            return new CustomerDto(
                    this.id,
                    this.name,
                    this.email,
                    this.dateCreated,
                    this.dateUpdated,
                    this.isDeleted
            );
        }
    }
}
