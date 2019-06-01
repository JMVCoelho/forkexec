package com.forkexec.pts.ws.cli;

/**
 * Client application.
 * 
 * Looks for Points using UDDI and arguments provided
 */
public class PointsClientApp {

	public static void main(String[] args) throws Exception {
		// Check arguments.
		if (args.length == 0) {
			System.err.println("Argument(s) missing!");
			System.err.println("Usage: java " + PointsClientApp.class.getName() + " wsURL OR uddiURL wsName");
			return;
		}
		String uddiURL = null;
		String wsName = null;
		String wsURL = null;
		int nreps = 0;
		int caso = 1;
		if (args.length == 1) {
			wsURL = args[0];
		} else if (args.length >= 3) {
			uddiURL = args[0];
			wsName = args[1];
			nreps = Integer.parseInt(args[2]);
			caso = Integer.parseInt(args[3]);
		}

		// Create client.
		PointsClient client = null;

		if (wsURL != null) {
			System.out.printf("Creating client for server at %s%n", wsURL);
			client = new PointsClient(wsURL);
		} else if (uddiURL != null) {
			System.out.printf("Creating client using UDDI at %s for server with name %s%n", uddiURL, wsName);
			client = new PointsClient(uddiURL, wsName, nreps, caso);
		}

		System.out.println("CONTROL OPERATIONS");
		Thread.sleep(2000);
		System.out.println("Invoke ping()...");

		String result = client.ctrlPing("client");
		System.out.print("Result: ");
		System.out.println(result);

		System.out.println("Adding points to yet uncreated users (u1,u2,u3)");
		System.out.println("Adding 100 points to u1 - u1 expected to have 200 now");
		client.addPoints("u1@domain", 100);
		System.out.println("Adding 100 points to u2 - u2 expected to have 200 now");
		client.addPoints("u2@domain", 100);
		System.out.println("Adding 103 points to u3 - u3 expected to have 203 now");
		client.addPoints("u3@domain", 103);
		System.out.println("Adding 103 points to u3 - u3 expected to have 306 now");
		client.addPoints("u3@domain", 103);

		System.out.println("Printing the results in cache: should be equal to the above");
		client.printCache();
		/*
		 * client.ctrlClear(); client.ctrlInit(3); client.ctrlPing("hi");
		 * client.ctrlClear(); client.printCache();
		 */
		// demonstracao de operacoes basicas
		System.out.println("BASIC OPERATIONS");
		Thread.sleep(6000);
		System.out.println("adicionar conta anon@domain");
		// client.activateUser("anon@domain");
		System.out.println("adicionar 400 pontos a conta anon2@domain - agora tem 500");
		client.addPoints("anon2@domain", 400);
		System.out.println("remover 100 pontos a conta anon2@domain - agora tem 400");
		client.spendPoints("anon2@domain", 100);
		System.out.print("pedir pontos da conta anon2@domain (expected 400): ");
		System.out.println("" + client.pointsBalance("anon2@domain"));

		// demonstracao de cache
		System.out.println("NOW USING ONLY CACHE");
		System.out.println("you can turn down all servers (10seconds)");
		Thread.sleep(10000);
		// System.out.println("pedir pontos de anon@domain (expected 100): " +
		// client.pointsBalance("anon@domain"));
		System.out.println("pedir pontos de anon2@domain (expected 400): " + client.pointsBalance("anon2@domain"));

		/*
		 * TestThread writer; for (int i = 0; i < 1; i++) { writer = new
		 * TestThread(client); Thread thread = new Thread(writer); thread.start(); }
		 */
		// The following remote invocation is just a basic example.
		// The actual tests are made using JUnit.
	}
}
