import java.math.BigInteger;
import java.util.Hashtable;

// Modificación: FibonacciThreadsMemoized.java
public class FibonacciThreadsMemoized implements Runnable {

    // IV) Se usa una Hashtable STATIC para compartir la 'memoria' 
    // entre todos los threads y evitar recalculos globales.
    private static final Hashtable<BigInteger, BigInteger> memo = new Hashtable<>();
    
    // Inicialización estática de los casos base: F(0)=0, F(1)=1
    static {
        memo.put(BigInteger.ZERO, BigInteger.ZERO); 
        memo.put(BigInteger.ONE, BigInteger.ONE);   
    }

    BigInteger fi;
    int num;

    public FibonacciThreadsMemoized(int n, BigInteger f) {
        num = n;
        fi = f;
    }

    @Override
    public void run() {
        // En un entorno real se usaría un logger. Usamos System.out.println
        System.out.println("Starte #" + num + " para F(" + fi + ")");
        BigInteger res = fibonacci(fi);
        System.out.println("Abschlussverfahren: " + num +
                           " - " + "fibonacci(" + fi + ") =" + res);
    }

    /**
     * Función Fibonacci recursiva con Memoización.
     * @param n La posición a calcular.
     * @return El n-ésimo número de Fibonacci.
     */
    public BigInteger fibonacci(BigInteger n) {
        
        // 1. Revisar Memoria (Memoization): Si ya está calculado, devolver el valor O(1)
        if (memo.containsKey(n)) {
            return memo.get(n);
        }
        
        // 2. Caso Base: Si n es menor a 2, debe ser 0 o 1.
        if (n.compareTo(BigInteger.TWO) < 0) {
            // Devuelve el valor del caso base.
            return memo.getOrDefault(n, n); 
        }

        // 3. Calcular Recursivamente y Almacenar:
        // Las llamadas recursivas ahora consultan y almacenan en 'memo'
        BigInteger nMinusOne = n.subtract(BigInteger.ONE);
        BigInteger nMinusTwo = n.subtract(BigInteger.TWO);
        
        BigInteger fibNMinusOne = fibonacci(nMinusOne);
        BigInteger fibNMinusTwo = fibonacci(nMinusTwo);
        
        BigInteger result = fibNMinusOne.add(fibNMinusTwo);

        // 4. Guardar resultado en la tabla para uso futuro
        memo.put(n, result);

        return result;
    }

    // El método main modificado para probar números más grandes
    static void main() {
        Thread[] threads = new Thread[10];

        // Se generan números aleatorios entre F(100) y F(1000) 
        // para demostrar la mejora de velocidad.
        for (int i = 0; i < 10; i++) {
            long algo = (long) (Math.random() * 900) + 100; // n entre 100 y 1000
            threads[i] = new Thread(
                    new FibonacciThreadsMemoized(i, BigInteger.valueOf(algo)));
        }
        
        for (int i = 0; i < 10; i++) threads[i].start();
    }
}
