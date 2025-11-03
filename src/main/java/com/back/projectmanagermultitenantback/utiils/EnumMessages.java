package com.back.projectmanagermultitenantback.utiils;

public enum EnumMessages {
    SELECT_SUCCESS("Données recupérées"),
    INSERTION_AVEC_SUCCESS("Donnée enregistrée"),
    DELETE_SUCCESS("Donnée supprimeée"),
    UPDATE_AVEC_SUCCESS("Donnée modifiée"),
    CONNEXION_ERRORED("Le mot de passe que vous avez saisi est incorrect."),
    Account_exits("The account already exists."),
    Account_doesnt_exits("The account doesn't exists.");

    private final String message;

    private EnumMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}