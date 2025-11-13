import java.math.BigInteger;
import java.util.Hashtable;

// Archivo 2: FibonacciThreadsMemoizedBigInteger.java
public class FibonacciThreadsMemoizedBigInteger implements Runnable {

    // Hashtable STATIC para la Memoización: (Posición BigInteger, Valor BigInteger)
    private static final Hashtable<BigInteger, BigInteger> memo = new Hashtable<>();
    
    static {
        // Inicialización estática de los casos base: F(0)=0, F(1)=1
        memo.put(BigInteger.ZERO, BigInteger.ZERO); 
        memo.put(BigInteger.ONE, BigInteger.ONE);   
    }

    BigInteger fi;
    int num;

    public FibonacciThreadsMemoizedBigInteger(int n, BigInteger f) {
        num = n;
        fi = f;
    }

    @Override
    public void run() {
        System.out.println("Starte #" + num + " para F(" + fi + ")");
        BigInteger res = fibonacci(fi);
        System.out.println("Abschlussverfahren: " + num +
                           " - " + "fibonacci(" + fi + ") =" + res);
    }

    /**
     * Función Fibonacci recursiva con Memoización (O(n) en tiempo).
     * @param n La posición a calcular.
     * @return El n-ésimo número de Fibonacci.
     */
    public BigInteger fibonacci(BigInteger n) {
        
        // 1. Revisar Memoria: Si ya está en la tabla, devolver el valor.
        if (memo.containsKey(n)) {
            return memo.get(n);
        }
        
        // 2. Caso Base: Si n es menor a 2.
        if (n.compareTo(BigInteger.TWO) < 0) {
            return memo.getOrDefault(n, n); 
        }

        // 3. Calcular Recursivamente y Almacenar:
        BigInteger nMinusOne = n.subtract(BigInteger.ONE);
        BigInteger nMinusTwo = n.subtract(BigInteger.TWO);
        
        // Las llamadas recursivas ahora son eficientes
        BigInteger fibNMinusOne = fibonacci(nMinusOne);
        BigInteger fibNMinusTwo = fibonacci(nMinusTwo);
        
        BigInteger result = fibNMinusOne.add(fibNMinusTwo);

        // 4. Guardar resultado en la tabla para uso futuro (Memoización)
        memo.put(n, result);

        return result;
    }

    static void main() {
        Thread[] threads = new Thread[10];

        // Ahora podemos calcular números grandes con facilidad (ej. n entre 100 y 1000)
        for (int i = 0; i < 10; i++) {
            long algo = (long) (Math.random() * 900) + 100; 
            threads[i] = new Thread(
                    new FibonacciThreadsMemoizedBigInteger(i, BigInteger.valueOf(algo)));
        }
        
        for (int i = 0; i < 10; i++) threads[i].start();
    }
}
