package com.chamika.books_project.emails;

import lombok.Getter;

@Getter
public enum EmailTemplateName {

    ACTIVATE_ACCOUNT("activate_account");  // * ACTIVATE_ACCOUNT: Represents the "activate_account" template.

    private final String name;

    EmailTemplateName(String name) {
        this.name = name;
    }
}