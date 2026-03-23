# Simulação de Concorrência em Sistemas Bancários 🏦

Projeto prático desenvolvido para demonstrar e analisar o comportamento de um sistema de processamento de transações financeiras sob alta concorrência em **Java**. 

O principal objetivo dessa atividade é lidar com problemas clássicos de Sistemas Operacionais e Programação Concorrente, como *Race Conditions*, e aplicar a solução correta usando mecanismos de *Exclusão Mútua (Locks)*.

## 🚀 O que foi implementado

O código simula 1.000 threads atuando como "Caixas Eletrônicos". Todas tentam depositar R$ 10,00 ao mesmo tempo em uma única conta compartilhada (que começa zerada). O programa roda duas abordagens para comparar os resultados:

1. **Sem Sincronização (Race Condition):** As threads acessam a memória sem nenhum controle. Aqui dá pra ver claramente a perda de dinheiro por causa da condição de corrida e o fenômeno do *Heisenbug* atrapalhando a depuração.
2. **Com Segurança (Locks e Atomicidade):** A solução do problema. Utilizei a interface `Lock` (`ReentrantLock`) do Java, combinada com blocos `try-finally`, para blindar a região crítica e garantir que todo o dinheiro seja depositado corretamente, sem risco de Deadlocks.

## 🧠 Conceitos Teóricos Abordados
Além do código, a atividade serviu para analisar:
- O impacto negativo do Overhead de Contexto e o tempo de espera nas filas do Mutex na performance do sistema.
- As diferenças de escalonamento entre User-Level Threads (ULT) e Kernel-Level Threads (KLT) quando rola uma operação de I/O bloqueante (tipo ler um arquivo no disco).

## 🛠️ Como rodar o projeto na sua máquina

1. Certifique-se de ter o [JDK (Java Development Kit)](https://www.oracle.com/java/technologies/downloads/) instalado.
2. Clone este repositório no seu terminal:
   ```bash
   git clone [https://github.com/WilliamNRAmorim/simulacao-banco-threads.git](https://github.com/WilliamNRAmorim/simulacao-banco-threads.git)