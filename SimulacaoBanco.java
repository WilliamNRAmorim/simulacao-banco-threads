import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SimulacaoBanco {

    static class ContaBancaria {
        private double saldoAtual = 0.0;
        private final Lock trava = new ReentrantLock();

        public void depositarComProblema(double valor) {
            double temp = this.saldoAtual;
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.saldoAtual = temp + valor;
        }

        public void depositarProtegido(double valor) {
            trava.lock();
            try {
                double temp = this.saldoAtual;
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.saldoAtual = temp + valor;
            } finally {
                trava.unlock(); 
            }
        }

        public double getSaldo() {
            return this.saldoAtual;
        }

        public void resetarConta() {
            this.saldoAtual = 0.0;
        }
    }

    public static void main(String[] args) {
        ContaBancaria minhaConta = new ContaBancaria();
        int totalThreads = 1000;
        double valorDeposito = 10.0;

        System.out.println("======== INICIANDO PARTE 1 (SEM LOCK) ========");
        long tInicio1 = System.currentTimeMillis();
        
        Thread[] vetorThreads = new Thread[totalThreads];
        
        for (int i = 0; i < totalThreads; i++) {
            vetorThreads[i] = new Thread(() -> {
                minhaConta.depositarComProblema(valorDeposito);
            });
            vetorThreads[i].start();
        }
        
        for (int i = 0; i < totalThreads; i++) {
            try {
                vetorThreads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        long tFim1 = System.currentTimeMillis();
        
        System.out.println(">>> Saldo que deveria ter: R$ " + (totalThreads * valorDeposito));
        System.out.println(">>> Saldo que realmente ficou: R$ " + minhaConta.getSaldo());
        System.out.println(">>> Tempo gasto na operacao: " + (tFim1 - tInicio1) + " ms\n");

        minhaConta.resetarConta();

        System.out.println("======== INICIANDO PARTE 2 (COM LOCK) ========");
        long tInicio2 = System.currentTimeMillis();
        
        Thread[] vetorThreads2 = new Thread[totalThreads];
        
        for (int i = 0; i < totalThreads; i++) {
            vetorThreads2[i] = new Thread(() -> {
                minhaConta.depositarProtegido(valorDeposito);
            });
            vetorThreads2[i].start();
        }
        
        for (int i = 0; i < totalThreads; i++) {
            try {
                vetorThreads2[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        long tFim2 = System.currentTimeMillis();
        
        System.out.println(">>> Saldo que deveria ter: R$ " + (totalThreads * valorDeposito));
        System.out.println(">>> Saldo protegido por lock: R$ " + minhaConta.getSaldo());
        System.out.println(">>> Tempo gasto na operacao: " + (tFim2 - tInicio2) + " ms");
    }
}