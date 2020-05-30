package main;

import javax.swing.JOptionPane;

import service.Service;

public class Main {

	public static void main(String[] args) {
		Service service = new Service();
		int numP = Integer.parseInt(JOptionPane.showInputDialog("Quantas pessoas deseja gerar?"));
		for (int i = 0; i < numP; i++) {
			service.generateEverything();
			if (i == (numP/2)-1) {
				System.out.println("Tá acabando, pera ae");
			}
		}
		JOptionPane.showMessageDialog(null, "O arquivo foi criado no diretório raiz do projeto\n");
	}

}
