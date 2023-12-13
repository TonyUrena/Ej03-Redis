import redis.clients.jedis.Jedis;
import java.util.Scanner;

public class Cliente {

    public static void main(String[] args) {
        Jedis jedis = new Jedis("localhost");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Ingrese un comando (shorten URL, exit, url SHORTEDURL):");
            String comando = scanner.nextLine();

            if (comando.startsWith("shorten")) {
                String[] partes = comando.split(" ");
                if (partes.length == 2) {
                    String url = partes[1];
                    jedis.rpush("DAVID:URLS_TO_SHORT", url);
                    System.out.println("URL enviada para acortar.");
                } else {
                    System.out.println("Formato incorrecto. Debe ser: shorten URL");
                }
            } else if (comando.equals("exit")) {
                System.out.println("Saliendo del programa.");
                break;
            } else if (comando.startsWith("url")) {
                String[] partes = comando.split(" ");
                if (partes.length == 2) {
                    String shortedUrl = partes[1];
                    String originalUrl = jedis.hget("DAVID:SHORTED_URLS", shortedUrl);
                    if (originalUrl != null) {
                        System.out.println("URL original: " + originalUrl);
                    } else {
                        System.out.println("URL no encontrada.");
                    }
                } else {
                    System.out.println("Formato incorrecto. Debe ser: url SHORTEDURL");
                }
            } else {
                System.out.println("Comando no reconocido.");
            }
        }

        jedis.close();
        scanner.close();
    }
}
