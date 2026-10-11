package com.icesi.sgmms;

import com.icesi.sgmms.controller.SGMMSController;
import com.icesi.sgmms.view.ConsoleView;

public class Main {

    public static void main(String[] args) {
        SGMMSController controller = new SGMMSController();
        ConsoleView view = new ConsoleView(controller);

        view.start();
    }
}