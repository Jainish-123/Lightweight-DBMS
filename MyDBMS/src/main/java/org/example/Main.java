package org.example;

import org.example.authentication.Authentication;
import org.example.query.database.DBCreation;
import org.example.query.userinput.InputQuery;

public class Main {
    public static void main(String[] args) {
        Authentication.doAuthentication();
        DBCreation.createDBIfNotExists();
        InputQuery.getQueryFromUser();
    }
}