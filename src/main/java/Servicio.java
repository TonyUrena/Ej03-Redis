import redis.clients.jedis.Jedis;
import java.util.UUID;

public class Servicio {

    public static void main(String[] args) {
        Jedis jedis = new Jedis("localhost"); // Ajusta esto según tu configuración de Redis

        while (true) {
            String urlToShort = jedis.lpop("DAVID:URLS_TO_SHORT");
            if (urlToShort != null) {
                String shortedUrl = acortarURL(urlToShort);
                jedis.hset("DAVID:SHORTED_URLS", shortedUrl, urlToShort);
                System.out.println("URL acortada: " + shortedUrl);
            }
        }
    }

    private static String acortarURL(String originalURL) {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}
