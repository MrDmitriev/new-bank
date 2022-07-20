package newbank;

import java.io.IOException;

import newbank.client.ExampleClient;
import newbank.server.NewBankServer;

class Main {
    public static void main(String[] args) throws IOException {
        new NewBankServer(14002).start();
		new ExampleClient("localhost",14002).start();
    }
}
