import java.util.Hashtable;

// Archivo 1: FibonacciThreadsMemoizedLong.java
public class FibonacciThreadsMemoizedLong implements Runnable {

    // Hashtable para memoizar: (Posición n, Valor F(n)) usando Long.
    private static final Hashtable<Long, Long> memo = new Hashtable<>();
    
    static {
        // Inicializar casos base para F(0)=0, F(1)=1
        memo.put(0L, 0L);
        memo.put(1L, 1L);
    }

    long fi; // La posición a calcular (puede ser la entrada original)
    int num;

    public FibonacciThreadsMemoizedLong(int n, long f) {
        num = n;
        fi = f;
    }

    @Override
    public void run() {
        // ... (resto del código de hilo)
        // Ojo: Si fi > 92, el resultado (res) se desbordará.
        long res = fibonacci(fi);
        System.out.println("Abschlussverfahren: " + num + 
                           " - " + "fibonacci(" + fi + ") =" + res);
    }

    // Función Fibonacci recursiva con Memoización (limitada por long)
    long fibonacci(long n) {
        
        // 1. Revisar Memoria
        if (memo.containsKey(n)) {
            return memo.get(n);
        }
        
        // 2. Calcular Recursivamente:
        long result = fibonacci(n - 1) + fibonacci(n - 2);

        // 3. Guardar resultado en la tabla y devolver
        memo.put(n, result);

        return result;
    }

    // El main original (calculando números aleatorios hasta 50) sigue siendo seguro
    static void main() {
        Thread[] threads = new Thread[10];

        for (int i = 0; i < 10; i++) {
            long algo = (long) (Math.random() * 50) + 1; // n entre 1 y 50
            threads[i] = new Thread(new FibonacciThreadsMemoizedLong(i, algo));
        }

        for (int i = 0; i < 10; i++) threads[i].start();
    }
}
