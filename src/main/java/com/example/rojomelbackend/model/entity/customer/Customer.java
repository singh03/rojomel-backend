package com.example.rojomelbackend.model.entity.customer;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private boolean isDeleted;

    private Date dateCreated;

    private Date dateUpdated;

    public static interface NameStep {
        EmailStep withName(String name);
    }

    public static interface EmailStep {
        DeletedStep withEmail(String email);
    }

    public static interface DeletedStep {
        DateCreatedStep withDeleted(boolean deleted);
    }

    public static interface DateCreatedStep {
        DateUpdatedStep withDateCreated(Date dateCreated);
    }

    public static interface DateUpdatedStep {
        BuildStep withDateUpdated(Date dateUpdated);
    }

    public static interface BuildStep {
        Customer build();
    }


    public static class Builder implements NameStep, EmailStep, DeletedStep, DateCreatedStep, DateUpdatedStep, BuildStep {
        private String name;
        private String email;
        private boolean deleted;
        private Date dateCreated;
        private Date dateUpdated;

        private Builder() {
        }

        public static NameStep customer() {
            return new Builder();
        }

        @Override
        public EmailStep withName(String name) {
            this.name = name;
            return this;
        }

        @Override
        public DeletedStep withEmail(String email) {
            this.email = email;
            return this;
        }

        @Override
        public DateCreatedStep withDeleted(boolean deleted) {
            this.deleted = deleted;
            return this;
        }

        @Override
        public DateUpdatedStep withDateCreated(Date dateCreated) {
            this.dateCreated = dateCreated;
            return this;
        }

        @Override
        public BuildStep withDateUpdated(Date dateUpdated) {
            this.dateUpdated = dateUpdated;
            return this;
        }

        @Override
        public Customer build() {
            Customer customer = new Customer();
            customer.setName(this.name);
            customer.setEmail(this.email);
            customer.setDeleted(this.deleted);
            customer.setDateCreated(this.dateCreated);
            customer.setDateUpdated(this.dateUpdated);
            return customer;
        }
    }
}
