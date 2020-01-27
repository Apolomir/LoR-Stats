package com.rybalko.ua.lorstats.view;

public class ViewBuilder {

    private View view;

    public ViewBuilder() {
        this.view = new View();
    }

    public void setWins() {
        int wins = Integer.parseInt(view.getTextWins().getText()) + 1;
        view.getTextWins().setText(String.valueOf(wins));
    }

    public void setLoses() {
        int loses = Integer.parseInt(view.getTextLoses().getText()) + 1;
        view.getTextLoses().setText(String.valueOf(loses));
    }
}
